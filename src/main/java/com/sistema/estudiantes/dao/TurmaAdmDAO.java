package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.model.TurmaAdm;
import com.sistema.estudiantes.conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaAdmDAO {

    public List<TurmaAdm> listarTodas() {
        List<TurmaAdm> lista = new ArrayList<>();
        // SQL AJUSTADO: Busca o Ano e conta os alunos vinculados na tabela Alunos
        String sql = "SELECT t.Id, t.Nome, t.Ano, " +
                "(SELECT COUNT(*) FROM Alunos a WHERE a.TurmaId = t.Id) as QtdAlunos " +
                "FROM Turmas t ORDER BY t.Nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TurmaAdm t = new TurmaAdm();
                t.setId(rs.getInt("Id"));
                t.setNome(rs.getString("Nome"));
                t.setAno(rs.getInt("Ano")); // Adicionado busca do Ano
                t.setQuantidadeAlunos(rs.getInt("QtdAlunos")); // Preenche a quantidade calculada
                lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void salvar(TurmaAdm turma, String acao) {
        String sql = "novo".equals(acao) ?
                "INSERT INTO Turmas (Nome, Ano) VALUES (?, ?)" :
                "UPDATE Turmas SET Nome = ?, Ano = ? WHERE Id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getAno());

            if ("editar".equals(acao)) {
                stmt.setInt(3, turma.getId());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TurmaAdm buscarPorId(int id) {
        String sql = "SELECT * FROM Turmas WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TurmaAdm t = new TurmaAdm();
                    t.setId(rs.getInt("Id"));
                    t.setNome(rs.getString("Nome"));
                    t.setAno(rs.getInt("Ano"));
                    return t;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM Turmas WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}