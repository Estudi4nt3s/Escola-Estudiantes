package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Turma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    public void inserir(int ano, String serie, char letra) {
        String sql = "INSERT INTO Turma (Ano, Serie, Letra) VALUES (?, ?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, ano);
            psmt.setString(2, serie);
            psmt.setString(3, String.valueOf(letra));

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Turma> listar() {
        List<Turma> lista = new ArrayList<>();
        String sql = "SELECT * FROM Turma";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Turma t = new Turma(
                        rs.getInt("Id"),
                        rs.getInt("Ano"),
                        rs.getString("curso"),
                        rs.getString("Serie"),
                        rs.getString("Letra").charAt(0)
                );
                lista.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean atualizar(Turma t) {
        String sql = "UPDATE Turma SET Ano = ?, Serie = ?, Letra = ? WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, t.getAno());
            psmt.setString(2, t.getSerie());
            psmt.setString(3, String.valueOf(t.getLetra()));
            psmt.setInt(4, t.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Turma WHERE Id = ?";

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

