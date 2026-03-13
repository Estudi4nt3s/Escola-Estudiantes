package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.model.TurmaAdm;
import com.sistema.estudiantes.conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaAdmDAO {

    public List<TurmaAdm> listarTodas() {
        List<TurmaAdm> lista = new ArrayList<>();
        // Note que usamos 'Turmas t' para a tabela principal
        String sql = "SELECT t.*, COALESCE(v.qntdAlunos, 0) as total " +
                "FROM Turmas t " +
                "LEFT JOIN vwQntdAlunosTurma v ON t.Nome = v.Nome " +
                "ORDER BY t.Id DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TurmaAdm t = new TurmaAdm();
                t.setId(rs.getInt("Id"));
                t.setNome(rs.getString("Nome"));
                t.setAno(rs.getInt("Ano"));
                t.setQuantidadeAlunos(rs.getInt("total"));
                lista.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void salvar(TurmaAdm turma, String acao) {
        String sql;
        // Removido 'QuantidadeAlunos' do INSERT e UPDATE, pois a View cuida disso
        if ("novo".equals(acao)) {
            sql = "INSERT INTO Turmas (Nome, Ano) VALUES (?, ?)";
        } else {
            sql = "UPDATE Turmas SET Nome = ?, Ano = ? WHERE Id = ?";
        }

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getAno());

            if ("editar".equals(acao)) {
                stmt.setInt(3, turma.getId());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erro no DAO salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TurmaAdm buscarPorId(int id) {
        // Buscamos na tabela 'Turmas'
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
                    // A quantidade aqui pode ficar 0, pois no 'listarTodas' ela será preenchida pela View
                    return t;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void excluir(int id) {
        // Deletamos na tabela 'Turmas'
        String sql = "DELETE FROM Turmas WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}