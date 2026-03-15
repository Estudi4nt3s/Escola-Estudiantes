package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.DisciplinasAdm;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaAdmDAO {

    public List<DisciplinasAdm> listarTodasComRelacionamentos() {
        List<DisciplinasAdm> lista = new ArrayList<>();
        // SQL com LEFT JOIN para trazer o nome do professor mesmo que a disciplina esteja sem prof
        String sql = "SELECT d.Id, d.Nome, p.Nome as ProfessorNome " +
                "FROM Disciplinas d " +
                "LEFT JOIN Professores p ON d.Id = p.DisciplinaId";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DisciplinasAdm d = new DisciplinasAdm();
                d.setId(rs.getInt("Id"));
                d.setNome(rs.getString("Nome"));
                d.setProfessorNome(rs.getString("ProfessorNome") != null ? rs.getString("ProfessorNome") : "Sem Professor");
                lista.add(d);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void salvar(DisciplinasAdm d, String acao) {
        String sql = "novo".equals(acao) ?
                "INSERT INTO Disciplinas (Nome) VALUES (?)" :
                "UPDATE Disciplinas SET Nome = ? WHERE Id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNome());
            if ("editar".equals(acao)) ps.setInt(2, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public DisciplinasAdm buscarPorId(int id) {
        String sql = "SELECT * FROM Disciplinas WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DisciplinasAdm d = new DisciplinasAdm();
                    d.setId(rs.getInt("Id"));
                    d.setNome(rs.getString("Nome"));
                    return d;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM Disciplinas WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}