package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.model.TurmaAdm;
import com.sistema.estudiantes.conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaAdmDAO {

    public List<TurmaAdm> listarTodas() {
        List<TurmaAdm> lista = new ArrayList<>();
        // Removidas as aspas duplas para o Postgres ignorar o Case Sensitive
        String sql = "SELECT * FROM TurmaAdm ORDER BY Id DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TurmaAdm t = new TurmaAdm();
                t.setId(rs.getInt("Id"));
                t.setNome(rs.getString("Nome"));
                t.setAno(rs.getInt("Ano"));
                t.setQuantidadeAlunos(rs.getInt("QuantidadeAlunos"));
                lista.add(t);
            }
        } catch (Exception e) {
            System.err.println("Erro no DAO listarTodas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public void salvar(TurmaAdm turma, String acao) {
        String sql;
        if ("novo".equals(acao)) {
            sql = "INSERT INTO TurmaAdm (Nome, Ano, QuantidadeAlunos) VALUES (?, ?, ?)";
        } else {
            sql = "UPDATE TurmaAdm SET Nome = ?, Ano = ?, QuantidadeAlunos = ? WHERE Id = ?";
        }

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getAno());
            // Trata o nulo da QuantidadeAlunos
            stmt.setInt(3, turma.getQuantidadeAlunos());

            if ("editar".equals(acao)) {
                stmt.setInt(4, turma.getId());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erro no DAO salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TurmaAdm buscarPorId(int id) {
        String sql = "SELECT * FROM TurmaAdm WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TurmaAdm t = new TurmaAdm();
                    t.setId(rs.getInt("Id"));
                    t.setNome(rs.getString("Nome"));
                    t.setAno(rs.getInt("Ano"));
                    t.setQuantidadeAlunos(rs.getInt("QuantidadeAlunos"));
                    return t;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM TurmaAdm WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}