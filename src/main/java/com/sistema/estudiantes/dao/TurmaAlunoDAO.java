package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.*;

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
                Aluno a = new Aluno(rs.getInt("matriculaaluno"));
                Turma t = new Turma(rs.getInt("idturma"));
                TurmaAluno ta = new TurmaAluno(
                        rs.getInt("Id"),
                        a,
                        t
                );
                lista.add(ta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<TurmaAluno> listarComFiltro(String condicao, Object valor){
        List<TurmaAluno> turmaAluno = new ArrayList<>();
        String sql = "SELECT * FROM turmaaluno WHERE " + condicao;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, valor);

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Turma turma = new Turma(rs.getInt("idturma"));
                    Aluno aluna = new Aluno(rs.getInt("matriculaaluno"));
                    TurmaAluno ta = new TurmaAluno(rs.getInt("id"),aluna,turma);
                    turmaAluno.add(ta);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turmaAluno;
    }

    public boolean atualizar(TurmaAluno ta) {
        String sql = """
            UPDATE TurmaAluno
               SET MatriculaAluno = ?
             WHERE Id = ?
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, ta.getMatriculaAluno().getMatricula());
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
