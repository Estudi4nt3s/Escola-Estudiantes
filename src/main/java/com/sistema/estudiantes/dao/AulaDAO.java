package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AulaDAO {
    public void inserir(Time horario, int disciplinaId, int turmaId, String diaSemana) {
        String sql = """
            INSERT INTO Aula (horario, disciplinaid, turmaid, diasemana)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setTime(1, horario);
            psmt.setInt(2, disciplinaId);
            psmt.setInt(3, turmaId);
            psmt.setString(4, diaSemana);

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aula> listar() {
        List<Aula> lista = new ArrayList<>();
        String sql = "SELECT * FROM Aula";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {

                Disciplina d = new Disciplina(rs.getInt("disciplinaid"));
                Turma t = new Turma(rs.getInt("turmaid"));

                Aula a = new Aula(
                        rs.getInt("id"),
                        rs.getTime("horario"),
                        d,
                        t,
                        rs.getString("diasemana")
                );

                lista.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean atualizar(Aula a) {
        String sql = """
        UPDATE Aula
           SET horario = ?, disciplinaid = ?, turmaid = ?, diasemana = ?
         WHERE Id = ?
    """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {


            psmt.setTime(1, a.getHorario());
            psmt.setInt(2, a.getDisciplinaId().getId());
            psmt.setInt(3, a.getTurmaId().getId());
            psmt.setString(4, a.getDiaSemana());
            psmt.setInt(5, a.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean excluir(int id) {
        String sql = "DELETE FROM Aula WHERE Id = ?";

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
