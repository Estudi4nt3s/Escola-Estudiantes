package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.model.Disciplina;
import com.sistema.estudiantes.model.DisciplinasAdm;
import com.sistema.estudiantes.conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaAdmDAO {

    public List<Disciplina> listar() {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT id, nome FROM disciplinas ORDER BY nome ASC";
        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                lista.add(d);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<DisciplinasAdm> listarTodasComRelacionamentos() {
        List<DisciplinasAdm> lista = new ArrayList<>();
        // MUDANÇA AQUI: Buscamos o nome direto da tabela professores (p.nome)
        String sql = "SELECT d.id, d.nome, " +
                "COALESCE(p.nome, 'Sem Professor') as prof_nome " +
                "FROM disciplinas d " +
                "LEFT JOIN professores p ON p.disciplinaid = d.id " +
                "ORDER BY d.id DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DisciplinasAdm d = new DisciplinasAdm();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setProfessorNome(rs.getString("prof_nome"));

                d.setCargaHoraria(0);
                d.setTurmaNome("N/A");
                lista.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void salvar(DisciplinasAdm d, String acao) {
        String sql = "novo".equals(acao)
                ? "INSERT INTO disciplinas (nome) VALUES (?)"
                : "UPDATE disciplinas SET nome=? WHERE id=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getNome());
            if ("editar".equals(acao)) stmt.setInt(2, d.getId());
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Simplificado para a nova lógica: Criamos apenas o Professor com o nome direto
    public void criarProfessorBasico(String nomeCompleto, int disciplinaId) {
        String sql = "INSERT INTO professores (nome, disciplinaid) VALUES (?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeCompleto);
            stmt.setInt(2, disciplinaId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DisciplinasAdm buscarPorId(int id) {
        String sql = "SELECT id, nome FROM disciplinas WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DisciplinasAdm d = new DisciplinasAdm();
                    d.setId(rs.getInt("id"));
                    d.setNome(rs.getString("nome"));
                    return d;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void excluir(int id) {
        // Primeiro removemos a referência nas notas/observações se houver (opcional dependendo do seu banco)
        String sql = "DELETE FROM disciplinas WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}