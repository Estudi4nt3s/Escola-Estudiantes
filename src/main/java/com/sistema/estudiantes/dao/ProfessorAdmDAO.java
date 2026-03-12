package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorAdmDAO {

    public List<Professor> listarComTudo() {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT p.Id, u.Nome, p.UsuarioId, p.DisciplinaId, u.Email, u.Nome as UNome, d.Nome as DNome " +
                "FROM Professores p " +
                "JOIN Usuarios u ON p.UsuarioId = u.Id " +
                "JOIN Disciplinas d ON p.DisciplinaId = d.Id";

        try (Connection conn = Conexao.conectar(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario(rs.getInt("UsuarioId"));
                u.setEmail(rs.getString("Email"));
                Disciplina d = new Disciplina(rs.getInt("DisciplinaId"), rs.getString("DNome"));
                lista.add(new Professor(rs.getInt("Id"), u, d));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public boolean cadastrarCompleto(String nome, String sobrenome, String email, String senha, int discId) {
        String sqlU = "INSERT INTO Usuarios (Nome, Sobrenome, Email, Senha) VALUES (?, ?, ?, ?)";
        String sqlP = "INSERT INTO Professores (UsuarioId, DisciplinaId) VALUES (?, ?)";
        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);
            int uid = -1;
            try (PreparedStatement psU = conn.prepareStatement(sqlU, Statement.RETURN_GENERATED_KEYS)) {
                psU.setString(1, nome); psU.setString(2, sobrenome);
                psU.setString(3, email); psU.setString(4, senha);
                psU.executeUpdate();
                ResultSet rs = psU.getGeneratedKeys();
                if (rs.next()) uid = rs.getInt(1);
            }
            try (PreparedStatement psP = conn.prepareStatement(sqlP)) {
                psP.setString(1, nome + " " + sobrenome);
                psP.setInt(2, uid);
                psP.setInt(3, discId);
                psP.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM Professores WHERE Id = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    public Professor buscarPorId(int id) {
        String sql = "SELECT p.Id, u.Nome, p.UsuarioId, p.DisciplinaId, u.Email, u.Sobrenome, d.Nome as DNome " +
                "FROM Professores p " +
                "JOIN Usuarios u ON p.UsuarioId = u.Id " +
                "JOIN Disciplinas d ON p.DisciplinaId = d.Id WHERE p.Id = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario(rs.getInt("UsuarioId"));
                    u.setEmail(rs.getString("Email"));
                    Disciplina d = new Disciplina(rs.getInt("DisciplinaId"), rs.getString("DNome"));
                    return new Professor(rs.getInt("Id"), u, d);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

}