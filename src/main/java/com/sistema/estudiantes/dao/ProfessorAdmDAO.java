package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorAdmDAO {

    public List<Professor> listarComTudo() {
        List<Professor> lista = new ArrayList<>();
        // LEFT JOIN com Usuarios para não sumir com o professor se ele não tiver e-mail
        String sql = "SELECT p.Id, p.Nome, p.UsuarioId, p.DisciplinaId, u.Email, d.Nome as DNome " +
                "FROM Professores p " +
                "LEFT JOIN Usuarios u ON p.UsuarioId = u.Id " +
                "JOIN Disciplinas d ON p.DisciplinaId = d.Id " +
                "ORDER BY p.Id";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Disciplina d = new Disciplina(rs.getInt("DisciplinaId"), rs.getString("DNome"));

                Usuario u = new Usuario();
                u.setId(rs.getInt("UsuarioId"));
                u.setEmail(rs.getString("Email"));

                // Usa o construtor completo do seu Model: id, nome, usuario, disciplina
                Professor p = new Professor(rs.getInt("Id"), rs.getString("Nome"), u, d);
                lista.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public boolean salvar(Professor professor, String acao) {
        String sql;
        if ("novo".equals(acao)) {
            sql = "INSERT INTO Professores (Nome, DisciplinaId) VALUES (?, ?)";
        } else {
            sql = "UPDATE Professores SET Nome = ?, DisciplinaId = ? WHERE Id = ?";
        }

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, professor.getNome());
            ps.setInt(2, professor.getDisciplina().getId());

            if ("editar".equals(acao)) {
                ps.setInt(3, professor.getId());
            }

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM Professores WHERE Id = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Professor buscarPorId(int id) {
        String sql = "SELECT p.*, d.Nome as DNome, u.Email " +
                "FROM Professores p " +
                "JOIN Disciplinas d ON p.DisciplinaId = d.Id " +
                "LEFT JOIN Usuarios u ON p.UsuarioId = u.Id WHERE p.Id = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Disciplina d = new Disciplina(rs.getInt("DisciplinaId"), rs.getString("DNome"));
                    Usuario u = new Usuario();
                    u.setEmail(rs.getString("Email"));
                    return new Professor(rs.getInt("Id"), rs.getString("Nome"), u, d);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}