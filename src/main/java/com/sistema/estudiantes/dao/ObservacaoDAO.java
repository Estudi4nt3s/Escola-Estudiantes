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
            INSERT INTO Observacoes (Texto, DataCriacao, alunomatricula, ProfessorId, DisciplinaId)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, observacao.getTexto());
            psmt.setObject(2, observacao.getDataCriacao());
            psmt.setInt(3, observacao.getIdProfessor().getId());
            psmt.setInt(4, observacao.getIdAluno().getMatricula());
            psmt.setInt(5, observacao.getIdDisciplina().getId());

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Observacao> listar() {
        List<Observacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM Observacoes";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Professor p = new Professor(rs.getInt("professorid"));
                Aluno a = new Aluno(rs.getInt("alunomatricula"));
                Disciplina d = new Disciplina(rs.getInt("disciplinaid"));

                Observacao o = new Observacao(
                        rs.getInt("id"),
                        rs.getString("texto"),
                        rs.getObject("datacriacao", LocalDate.class),
                        a,
                        p,
                        d
                );
                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Observacao> listarComFiltro(String condicao, int valor){
        List<Observacao> observacaos = new ArrayList<>();
        String sql = " SELECT * FROM observacoes WHERE " + condicao;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Professor p = new Professor(rs.getInt("professorid"));
                    Aluno a = new Aluno(rs.getInt("alunomatricula"));
                    Disciplina d = new Disciplina(rs.getInt("disciplinaid"));

                    Observacao o = new Observacao(
                            rs.getInt("id"),
                            rs.getString("texto"),
                            rs.getObject("datacriacao", LocalDate.class),
                            a,
                            p,
                            d
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
