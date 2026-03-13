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
        // Removemos os JOINs com a tabela de Usuarios
        String sql = """
            SELECT o.*, a.nome AS aluno_nome, p.nome AS prof_nome
            FROM observacoes o
            JOIN alunos a ON o.alunomatricula = a.matricula
            JOIN professores p ON o.professorid = p.id
            """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Professor p = new Professor(rs.getInt("professorid"));
                p.setNome(rs.getString("prof_nome")); // Nome direto do Professor

                Aluno a = new Aluno();
                a.setMatricula(rs.getInt("alunomatricula"));
                a.setNome(rs.getString("aluno_nome")); // Nome direto do Aluno

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
                 SELECT o.*, p.nome as prof, a.nome as aluno, d.nome as disc FROM observacoes o 
                     join professores p on o.professorid = p.id
                     join disciplinas d on p.disciplinaid = d.id
                     join alunos a on o.alunomatricula = a.matricula
                          WHERE alunomatricula = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {

                    Professor p = new Professor(rs.getInt("professorid"));
                    p.setNome(rs.getString("prof"));

                    Disciplina d = new Disciplina();
                    d.setNome(rs.getString("disc"));
                    p.setDisciplina(d);

                    Aluno a = new Aluno(rs.getInt("alunomatricula"));
                    a.setNome(rs.getString("aluno"));

                    Observacao o = new Observacao(
                            rs.getInt("id"),
                            rs.getString("texto"),
                            rs.getObject("datacriacao", LocalDate.class),
                            a,
                            p
                    );
                    System.out.println(o.getIdProfessor().getNome());
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
        String sql = "SELECT o.*, a.nome as aluno, " +
                "p.nome as prof " +
                "FROM observacoes o " +
                "JOIN alunos a ON o.alunomatricula = a.matricula " +
                "JOIN professores p ON o.professorid = p.id " +
                "WHERE " + condicao;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Professor p = new Professor(rs.getInt("professorid"));
                    Aluno a = new Aluno(rs.getInt("alunomatricula"));

                    a.setNome(rs.getString("aluno"));
                    p.setNome(rs.getString("prof"));

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
