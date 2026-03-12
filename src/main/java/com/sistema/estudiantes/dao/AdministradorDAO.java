package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    public List<Admin> buscarLogin(String usuario) {
        List<Admin> lista = new ArrayList<>();
        String sql = "SELECT * FROM Admins WHERE Usuario = ?";

        // Usamos o método estático conectar() da sua classe Conexao
        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            if (conn == null) return lista; // Evita NullPointerException se a conexão falhar

            psmt.setString(1, usuario);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    Admin admin = new Admin();
                    admin.setId(rs.getInt("Id"));
                    admin.setUsuario(rs.getString("Usuario"));
                    admin.setSenha(rs.getString("Senha"));
                    lista.add(admin);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar login: " + e.getMessage());
        }
        // O try-with-resources fecha automaticamente o psmt e o conn (chamando o close)
        return lista;
    }
}