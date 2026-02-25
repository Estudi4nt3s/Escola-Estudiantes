package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.*;

import java.sql.*;
import java.time.LocalTime;
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

    public List<Aula> listarComFiltro(String nomeColuna, Object valorColuna) {

        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT Id, Horario, DisciplinaId, TurmaId, diaSemana FROM Aula WHERE " + nomeColuna + " = ?";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
                stmt.setObject(1, valorColuna);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Aula aula = new Aula(
                            rs.getInt("Id"),
                            rs.getObject("Horario", LocalTime.class),
                            rs.getInt("DisciplinaId"),
                            rs.getInt("TurmaId"),
                            rs.getString("diaSemana")
                    );

                    aulas.add(aula);
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao filtrar Aula por " + nomeColuna + ": " + e.getMessage());
        }

        return aulas;
    }



    public boolean atualizar(Aula a) {
        String sql = """
        UPDATE Aula
           SET horario = ?, disciplinaid = ?, turmaid = ?, diasemana = ?
         WHERE Id = ?
    """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {


            psmt.setObject(1, a.getHorario());
            psmt.setInt(2, a.getDisciplinaId());
            psmt.setInt(3, a.getTurmaId());
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
