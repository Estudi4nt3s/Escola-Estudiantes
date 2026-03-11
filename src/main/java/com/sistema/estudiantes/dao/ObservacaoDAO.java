package com.sistema.estudiantes.dao;


import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ObservacaoDAO {

    public void inserir(Observacao observacao) {
        String sql = """
            INSERT INTO Observacoes (Texto, DataCriacao, alunomatricula, ProfessorId)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, observacao.getTexto());
            psmt.setObject(2, observacao.getDataCriacao());
            psmt.setInt(3, observacao.getIdAluno().getMatricula());
            psmt.setInt(4, observacao.getIdProfessor().getId());

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Observacao> listar() {
        List<Observacao> lista = new ArrayList<>();
        String sql = """
                SELECT o.*, 
                       ua.nome AS aluno_nome,
                       ua.sobrenome AS aluno_sobrenome,
                       up.nome AS prof_nome,
                       up.sobrenome AS prof_sobrenome
                FROM observacoes o
                JOIN alunos a ON o.alunomatricula = a.matricula
                JOIN usuarios ua ON a.usuarioid = ua.id
                JOIN professores p ON o.professorid = p.id
                JOIN usuarios up ON p.usuarioid = up.id
                """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Professor p = new Professor(rs.getInt("professorid"));
                Aluno a = new Aluno(rs.getInt("alunomatricula"));

                Usuario u = new Usuario();
                u.setNome(rs.getString("aluno_nome"));
                u.setSobrenome(rs.getString("aluno_sobrenome"));

                a.setUsuarioId(u);

                Usuario up = new Usuario();
                up.setNome(rs.getString("prof_nome"));
                up.setSobrenome(rs.getString("prof_sobrenome"));

                p.setUsuario(up);

                Observacao o = new Observacao(
                        rs.getInt("id"),
                        rs.getString("texto"),
                        rs.getObject("datacriacao", LocalDate.class),
                        a,
                        p
                );
                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Observacao> listarDisciplina(int valor){
        List<Observacao> observacaos = new ArrayList<>();
        String sql = """
                 SELECT o.*, professorid, u.nome as prof, d.nome as disc FROM observacoes o 
                     join professores p on o.professorid = p.id
                     join usuarios u on p.usuarioid = u.id
                     join disciplinas d on p.disciplinaid = d.id
                          WHERE alunomatricula = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {

                    Professor p = new Professor(rs.getInt("professorid"));
                    Usuario u = new Usuario();
                    u.setNome(rs.getString("prof"));
                    p.setUsuario(u);

                    Disciplina d = new Disciplina();
                    d.setNome(rs.getString("disc"));
                    p.setDisciplina(d);

                    Aluno a = new Aluno(rs.getInt("alunomatricula"));

                    Observacao o = new Observacao(
                            rs.getInt("id"),
                            rs.getString("texto"),
                            rs.getObject("datacriacao", LocalDate.class),
                            a,
                            p
                    );
                    System.out.println(o.getIdProfessor().getUsuario().getNome());
                    observacaos.add(o);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return observacaos;
    }

    public List<Observacao> listarComFiltro(String condicao, int valor){
        List<Observacao> observacaos = new ArrayList<>();
        String sql = "SELECT o.*, ua.nome as nome_aluno, ua.sobrenome as sobrenome_aluno, " +
                "up.nome as nome_prof, up.sobrenome as sobrenome_prof " +
                "FROM observacoes o " +
                "JOIN alunos a ON o.alunomatricula = a.matricula " +
                "JOIN usuarios ua ON a.usuarioid = ua.id " +
                "JOIN professores p ON o.professorid = p.id " +
                "JOIN usuarios up ON p.usuarioid = up.id " +
                "WHERE " + condicao;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Professor p = new Professor(rs.getInt("professorid"));
                    Aluno a = new Aluno(rs.getInt("alunomatricula"));

                    Usuario u = new Usuario();
                    u.setNome(rs.getString("nome_aluno"));
                    u.setSobrenome(rs.getString("sobrenome_aluno"));

                    a.setUsuarioId(u);

                    Usuario up = new Usuario();
                    up.setNome(rs.getString("nome_prof"));
                    up.setSobrenome(rs.getString("sobrenome_prof"));

                    p.setUsuario(up);

                    Observacao o = new Observacao(
                            rs.getInt("id"),
                            rs.getString("texto"),
                            rs.getObject("datacriacao", LocalDate.class),
                            a,
                            p
                    );

                    observacaos.add(o);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return observacaos;
    }

    public boolean atualizar(Observacao o) {
        String sql = "UPDATE Observacoes SET Texto = ?, DataCriacao = ? WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, o.getTexto());
            psmt.setObject(2, o.getDataCriacao());
            psmt.setInt(3, o.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Observacoes WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, id);
            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
