package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.model.Disciplina;
import com.sistema.estudiantes.model.DisciplinasAdm;
import com.sistema.estudiantes.conexao.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaAdmDAO {
    // Retorna a lista simples de disciplinas para o SELECT do formulário
    public List<Disciplina> listar() {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM Disciplinas ORDER BY Nome ASC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("Id"));
                d.setNome(rs.getString("Nome"));
                lista.add(d);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<DisciplinasAdm> listarTodasComRelacionamentos() {
        List<DisciplinasAdm> lista = new ArrayList<>();
        String sql = "SELECT * FROM DisciplinaAdm ORDER BY Id DESC";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DisciplinasAdm d = new DisciplinasAdm();
                d.setId(rs.getInt("Id"));
                d.setNome(rs.getString("Nome"));
                d.setCargaHoraria(rs.getInt("CargaHoraria"));
                d.setProfessorNome(rs.getString("ProfessorNome"));
                d.setTurmaNome(rs.getString("TurmaNome"));
                lista.add(d);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public void salvar(DisciplinasAdm d, String acao) {
        try (Connection conn = Conexao.conectar()) {
            garantirDisciplinaReal(d.getNome(), conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "novo".equals(acao)
                ? "INSERT INTO DisciplinaAdm (Nome, CargaHoraria, ProfessorNome, TurmaNome) VALUES (?, ?, ?, ?)"
                : "UPDATE DisciplinaAdm SET Nome=?, CargaHoraria=?, ProfessorNome=?, TurmaNome=? WHERE Id=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getNome());
            stmt.setInt(2, d.getCargaHoraria());
            stmt.setString(3, d.getProfessorNome());
            stmt.setString(4, d.getTurmaNome());
            if ("editar".equals(acao)) stmt.setInt(5, d.getId());
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public boolean professorExisteNoBancoReal(String nomeProfessor) {
        String sql = "SELECT COUNT(*) FROM Professores WHERE Nome ILIKE ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeProfessor);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public void criarProfessorCompleto(String nome, String sobrenome, String email) {
        Connection conn = null;
        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false);

            String sqlUser = "INSERT INTO Usuarios (Nome, Sobrenome, Email, Senha) VALUES (?, ?, ?, ?)";
            int usuarioId = -1;

            try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
                stmtUser.setString(1, nome);
                stmtUser.setString(2, sobrenome);
                stmtUser.setString(3, email);
                stmtUser.setString(4, "senha123"); // Senha padrão
                stmtUser.executeUpdate();

                ResultSet rs = stmtUser.getGeneratedKeys();
                if (rs.next()) usuarioId = rs.getInt(1);
            }

            if (usuarioId != -1) {
                int disciplinaId = garantirDisciplinaReal(nome, conn);

                String sqlProf = "INSERT INTO Professores (Nome, UsuarioId, DisciplinaId) VALUES (?, ?, ?)";
                try (PreparedStatement stmtProf = conn.prepareStatement(sqlProf)) {
                    stmtProf.setString(1, nome + " " + sobrenome);
                    stmtProf.setInt(2, usuarioId);
                    stmtProf.setInt(3, disciplinaId);
                    stmtProf.executeUpdate();
                }
            }

            conn.commit(); // Salva tudo
        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private int garantirDisciplinaReal(String nomeDisc, Connection conn) throws SQLException {
        String sqlBusca = "SELECT Id FROM Disciplinas WHERE Nome ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlBusca)) {
            stmt.setString(1, nomeDisc);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }

        String sqlInsert = "INSERT INTO Disciplinas (Nome) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nomeDisc);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return 1; // Fallback para ID 1 caso tudo falhe
    }

    public DisciplinasAdm buscarPorId(int id) {
        String sql = "SELECT * FROM DisciplinaAdm WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DisciplinasAdm d = new DisciplinasAdm();
                d.setId(rs.getInt("Id"));
                d.setNome(rs.getString("Nome"));
                d.setCargaHoraria(rs.getInt("CargaHoraria"));
                d.setProfessorNome(rs.getString("ProfessorNome"));
                d.setTurmaNome(rs.getString("TurmaNome"));
                return d;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM DisciplinaAdm WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

}