package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.model.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    private String url = "jdbc:mysql://localhost:3306/seu_banco";
    private String user = "root";
    private String password = "1234";

    public List<Admin> buscarLogin(String usuario) {

        List<Admin> lista = new ArrayList<>();

        String sql = "SELECT * FROM admins WHERE usuario = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsuario(rs.getString("usuario"));
                admin.setSenha(rs.getString("senha"));

                lista.add(admin);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}