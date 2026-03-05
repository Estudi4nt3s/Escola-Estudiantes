package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AulaDAO {
    public void inserir(Aula aula) {
        String sql = """
            INSERT INTO Aulas (horarioinicio, horariofim, professorid, turmaid, diasemana)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setObject(1, aula.getHorarioInicio());
            psmt.setObject(2, aula.getHorarioFim());
            psmt.setInt(3, aula.getProfessorId().getId());
            psmt.setInt(4, aula.getTurmaId().getId());
            psmt.setString(5, aula.getDiaSemana());

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aula> listar() {

        List<Aula> lista = new ArrayList<>();
        String sql = "SELECT * FROM Aulas";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql);
                ResultSet rs = psmt.executeQuery()
        ) {

            while (rs.next()) {
                Professor p = new Professor(rs.getInt("professorid"));
                Turma t = new Turma(rs.getInt("turmaid"));
                Aula aula = new Aula(
                        rs.getInt("id"),
                        rs.getObject("horarioinicio", LocalTime.class),
                        rs.getObject("horariofim", LocalTime.class),
                        p,
                        t,
                        rs.getString("diasemana")

                );
                lista.add(aula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Aula> listarComFiltro(String condicao, String valor) {

        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT * FROM Aula WHERE " + condicao;

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
                stmt.setString(1, valor);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Professor p = new Professor(rs.getInt("professorid"));
                    Turma t = new Turma(rs.getInt("turmaid"));
                    Aula aula = new Aula(
                            rs.getInt("id"),
                            rs.getObject("horarioinicio", LocalTime.class),
                            rs.getObject("horariofim", LocalTime.class),
                            p,
                            t,
                            rs.getString("diasemana")


                    );
                    aulas.add(aula);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return aulas;
    }

    public List<Aula> listarComFiltro(String condicao, int valor, String data) {

        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT * FROM Aula WHERE " + condicao;

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, valor);
            stmt.setString(2, data);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Professor p = new Professor(rs.getInt("professorid"));
                    Turma t = new Turma(rs.getInt("turmaid"));
                    Aula aula = new Aula(
                            rs.getInt("id"),
                            rs.getObject("horarioinicio", LocalTime.class),
                            rs.getObject("horariofim", LocalTime.class),
                            p,
                            t,
                            rs.getString("diasemana")


                    );
                    aulas.add(aula);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return aulas;
    }



    public boolean atualizar(Aula a) {
        String sql = """
        UPDATE Aulas
           SET horarioinicio = ?, horariofim = ?, professorid = ?, turmaid = ?, diasemana = ?
         WHERE Id = ?
    """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {


            psmt.setObject(1, a.getHorarioInicio());
            psmt.setObject(2, a.getHorarioFim());
            psmt.setInt(3, a.getProfessorId().getId());
            psmt.setInt(4, a.getTurmaId().getId());
            psmt.setString(5, a.getDiaSemana());
            psmt.setInt(6, a.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean excluir(int id) {
        String sql = "DELETE FROM Aulas WHERE Id = ?";

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
