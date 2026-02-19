package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Disciplina;
import com.sistema.estudiantes.model.Professor;
import com.sistema.estudiantes.model.ProfessorDisciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfDisciplinaDAO {


    public void inserir(ProfessorDisciplina pd) {
        String sql = """
            INSERT INTO ProfessorDisciplina (IdProfessor, IdDisciplina)
            VALUES (?, ?)
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, pd.getIdProfessor().getId());
            psmt.setInt(2, pd.getIdDisciplina().getId());

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<ProfessorDisciplina> listar() {
        List<ProfessorDisciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM ProfessorDisciplina";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {

                ProfessorDisciplina pd = new ProfessorDisciplina(
                        rs.getInt("Id"),
                        new Professor(rs.getInt("IdProfessor")),
                        new Disciplina(rs.getInt("IdDisciplina"))
                );

                lista.add(pd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public ProfessorDisciplina buscarPorId(int id) {
        String sql = "SELECT * FROM ProfessorDisciplina WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, id);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                return new ProfessorDisciplina(
                        rs.getInt("Id"),
                        new Professor(rs.getInt("IdProfessor")),
                        new Disciplina(rs.getInt("IdDisciplina"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean atualizar(ProfessorDisciplina pd) {
        String sql = """
            UPDATE ProfessorDisciplina
               SET IdProfessor = ?, IdDisciplina = ?
             WHERE Id = ?
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, pd.getIdProfessor().getId());
            psmt.setInt(2, pd.getIdDisciplina().getId());
            psmt.setInt(3, pd.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean excluir(int id) {
        String sql = "DELETE FROM ProfessorDisciplina WHERE Id = ?";

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
