package com.sistema.estudiantes.dao;


import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (email, senha, photo) VALUES (?, ?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, usuario.getEmail());
            psmt.setString(2, usuario.getSenha());
            psmt.setString(3, usuario.getFoto());
            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("photo")
                );
                lista.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Usuario> listarComFiltro(String condicao, String filtro) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE " + condicao;

        try (Connection conn = new Conexao().conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario user = new Usuario(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("Photo")
                    );
                    usuarios.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public List<Usuario> listarFiltros(String condicao, boolean filtro1, String filtro2) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE " + condicao;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setBoolean(1, filtro1);
            stmt.setString(2, filtro2);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario user = new Usuario(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("Photo")
                    );
                    usuarios.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean atualizar(Usuario u) {
        String sql = "UPDATE Usuarios SET email = ?, senha = ?, photo = ? WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, u.getEmail());
            psmt.setString(2, u.getSenha());
            psmt.setString(3, u.getFoto());
            psmt.setInt(4, u.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Usuarios WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, id);
            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarToken(Integer id, String tokenRecuperacao, LocalDateTime tokenExpira) {
        String sql = """
        UPDATE usuarios
        SET tokenrecuperacao = ?, tokenexpira = ?
        WHERE id = ?
    """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tokenRecuperacao);
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(tokenExpira));
            stmt.setInt(3, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscarPorToken(String token) {

        String sql = "SELECT * FROM usuarios WHERE tokenrecuperacao = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, token);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));

                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean limparToken(Integer id) {

        String sql = """
        UPDATE usuarios
        SET tokenrecuperacao = NULL,
            tokenexpira = NULL
        WHERE id = ?
    """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
