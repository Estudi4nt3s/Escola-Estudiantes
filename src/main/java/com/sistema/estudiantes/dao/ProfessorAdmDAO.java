package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Professor;
import com.sistema.estudiantes.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorAdmDAO {

    // Lista com JOIN para a tabela principal
    public List<Professor> listarComUsuarios() {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT p.Id, p.Nome, p.UsuarioId, u.Email FROM Professores p " +
                "JOIN Usuarios u ON p.UsuarioId = u.Id ORDER BY u.Id DESC";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario(rs.getInt("UsuarioId"));
                u.setEmail(rs.getString("Email"));
                lista.add(new Professor(rs.getInt("Id"), rs.getString("Nome"), u));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    // Busca um único professor para o Modal
    public List<Professor> listarComFiltro(int id) {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT p.Id, p.Nome, p.UsuarioId, u.Email FROM Professores p " +
                "JOIN Usuarios u ON p.UsuarioId = u.Id WHERE p.Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario(rs.getInt("UsuarioId"));
                    u.setEmail(rs.getString("Email"));
                    lista.add(new Professor(rs.getInt("Id"), rs.getString("Nome"), u));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    // MÉTODO NOVO: Atualiza Professores e Usuarios ao mesmo tempo
    public boolean atualizarProfessorCompleto(int profId, int usuId, String nome, String email, String senha) {
        String sqlProf = "UPDATE Professores SET Nome = ? WHERE Id = ?";

        // Verifica se o admin digitou uma nova senha ou deixou em branco
        boolean temSenha = (senha != null && !senha.trim().isEmpty());
        String sqlUsu = "UPDATE Usuarios SET Email = ?" + (temSenha ? ", Senha = ?" : "") + " WHERE Id = ?";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false); // Inicia transação

            // 1. Atualiza Nome do Professor
            try (PreparedStatement psP = conn.prepareStatement(sqlProf)) {
                psP.setString(1, nome);
                psP.setInt(2, profId);
                psP.executeUpdate();
            }

            // 2. Atualiza Login do Usuário
            try (PreparedStatement psU = conn.prepareStatement(sqlUsu)) {
                psU.setString(1, email);
                if (temSenha) {
                    psU.setString(2, senha);
                    psU.setInt(3, usuId);
                } else {
                    psU.setInt(2, usuId);
                }
                psU.executeUpdate();
            }

            conn.commit(); // Salva as duas alterações
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cadastrarCompleto(String nome, String sobrenome, String email, String senha) {
        String sqlU = "INSERT INTO Usuarios (Nome, Sobrenome, Email, Senha) VALUES (?, ?, ?, ?)";
        String sqlP = "INSERT INTO Professores (Nome, UsuarioId, DisciplinaId) VALUES (?, ?, 1)";
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
            if (uid != -1) {
                try (PreparedStatement psP = conn.prepareStatement(sqlP)) {
                    psP.setString(1, nome + " " + sobrenome);
                    psP.setInt(2, uid);
                    psP.executeUpdate();
                }
                conn.commit();
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM Professores WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}