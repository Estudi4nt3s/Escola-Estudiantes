package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.TurmaAluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TurmaAlunoDAO {

    public void inserir(int matriculaAluno, int idTurma) {
        String sql = "INSERT INTO TurmaAluno (MatriculaAluno, IdTurma) VALUES (?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, matriculaAluno);
            psmt.setInt(2, idTurma);
            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TurmaAluno> listar() {
        List<TurmaAluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM TurmaAluno";
        Aluno aluno = new Aluno();

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {

                TurmaAluno ta = new TurmaAluno(
                        rs.getInt("Id"),
                        rs.getInt("MatriculaAluno"),
                        rs.getInt("IdTurma")
                );
                lista.add(ta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean atualizar(TurmaAluno ta) {
        String sql = """
            UPDATE TurmaAluno
               SET MatriculaAluno = ?
             WHERE Id = ?
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, ta.getAluno().getMatricula());
            psmt.setInt(2, ta.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM TurmaAluno WHERE Id = ?";

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
