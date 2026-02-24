package com.sistema.estudiantes.dao;


import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(String email, String senha) {
        String sql = "INSERT INTO Usuario (Email, Senha) VALUES (?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, email);
            psmt.setString(2, senha);
            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("Id"),
                        rs.getString("Email"),
                        rs.getString("Senha")
                );
                lista.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Usuario> listarComFiltro(String nomeColuna, Object valorColuna) {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT id, email, senha FROM usuario WHERE " + nomeColuna + " = ?";

        try (Connection conn = new Conexao().conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)){

            if (valorColuna instanceof Integer) {
                stmt.setInt(1, (Integer) valorColuna);
            } else if (valorColuna instanceof String) {
                stmt.setString(1, (String) valorColuna);
            } else {
                stmt.setObject(1, valorColuna);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario user = new Usuario(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                    usuarios.add(user);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao filtrar por " + nomeColuna + ": " + e.getMessage());
        }

        return usuarios;
    }

    public boolean atualizar(Usuario u) {
        String sql = "UPDATE Usuario SET Email = ?, Senha = ? WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, u.getEmail());
            psmt.setString(2, u.getSenha());
            psmt.setInt(3, u.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Usuario WHERE Id = ?";

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
