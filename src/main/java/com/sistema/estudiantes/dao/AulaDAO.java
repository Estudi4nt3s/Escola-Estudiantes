package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AulaDAO {

    public boolean inserir(Aula aula) {

        String sql = "INSERT INTO Aula (Horario, DisciplinaId, TurmaId, diaSemana) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {

            psmt.setObject(1, aula.getHorario());
            psmt.setInt(2, aula.getDisciplinaId());
            psmt.setInt(3, aula.getTurmaId());
            psmt.setString(4, aula.getDiaSemana());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Aula> listar() {

        List<Aula> lista = new ArrayList<>();
        String sql = "SELECT * FROM Aula";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql);
                ResultSet rs = psmt.executeQuery()
        ) {

            while (rs.next()) {
                Aula aula = new Aula(
                        rs.getInt("Id"),
                        rs.getObject("Horario", LocalTime.class),
                        rs.getInt("DisciplinaId"),
                        rs.getInt("TurmaId"),
                        rs.getString("diaSemana")
                );

                lista.add(aula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Aula> listarComFiltro(String nomeColuna, Object valorColuna) {

        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT * FROM Aula WHERE " + nomeColuna + " = ?";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            // Tratamento para LocalTime
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

    public boolean atualizar(Aula aula) {

        String sql = "UPDATE Aula SET Horario = ?, DisciplinaId = ?, TurmaId = ?, diaSemana = ? WHERE Id = ?";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {

            psmt.setObject(1, aula.getHorario());
            psmt.setInt(2, aula.getDisciplinaId());
            psmt.setInt(3, aula.getTurmaId());
            psmt.setString(4, aula.getDiaSemana());
            psmt.setInt(5, aula.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int excluir(int id) {

        String sql = "DELETE FROM Aula WHERE Id = ?";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {

            psmt.setInt(1, id);

            if (psmt.executeUpdate() > 0) {
                return 1;
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}