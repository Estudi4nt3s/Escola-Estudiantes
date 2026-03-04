package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.model.DisciplinasAdm;
import com.sistema.estudiantes.conexao.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaAdmDAO{

    public List<DisciplinasAdm> listarTodasComRelacionamentos() {

        List<DisciplinasAdm> lista = new ArrayList<>();

        String sql = """
            SELECT d.id,
                   d.nome,
                   d.carga_horaria,
                   p.nome AS professor_nome,
                   t.nome AS turma_nome
            FROM disciplina d
            LEFT JOIN professor p ON d.professor_id = p.id
            LEFT JOIN turma t ON d.turma_id = t.id
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                DisciplinasAdm d = new DisciplinasAdm();

                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setCargaHoraria(rs.getInt("carga_horaria"));
                d.setProfessorNome(rs.getString("professor_nome"));
                d.setTurmaNome(rs.getString("turma_nome"));

                lista.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public DisciplinasAdm buscarPorId(int id) {

        String sql = """
            SELECT d.id,
                   d.nome,
                   d.carga_horaria,
                   p.nome AS professor_nome,
                   t.nome AS turma_nome
            FROM disciplina d
            LEFT JOIN professor p ON d.professor_id = p.id
            LEFT JOIN turma t ON d.turma_id = t.id
            WHERE d.id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                DisciplinasAdm d = new DisciplinasAdm();

                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setCargaHoraria(rs.getInt("carga_horaria"));
                d.setProfessorNome(rs.getString("professor_nome"));
                d.setTurmaNome(rs.getString("turma_nome"));

                return d;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void excluir(int id) {

        String sql = "DELETE FROM disciplina WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}