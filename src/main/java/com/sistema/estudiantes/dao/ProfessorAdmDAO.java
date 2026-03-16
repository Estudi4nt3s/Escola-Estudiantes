package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorAdmDAO {

    public List<Professor> listarComTudo() {
        List<Professor> lista = new ArrayList<>();
        // Mudei o JOIN para LEFT JOIN aqui também!
        String sql = "SELECT p.Id, p.Nome, p.UsuarioId, p.DisciplinaId, u.Email, u.Senha, d.Nome as DNome " +
                "FROM Professores p " +
                "LEFT JOIN Usuarios u ON p.UsuarioId = u.Id " +
                "LEFT JOIN Disciplinas d ON p.DisciplinaId = d.Id " +
                "ORDER BY p.Id";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Verifica se o ID da disciplina é válido para evitar erros ao instanciar
                Disciplina d = null;
                int discId = rs.getInt("DisciplinaId");
                if (!rs.wasNull()) {
                    d = new Disciplina(discId, rs.getString("DNome"));
                }

                Usuario u = new Usuario();
                u.setId(rs.getInt("UsuarioId"));
                u.setEmail(rs.getString("Email"));
                u.setSenha(rs.getString("Senha"));

                Professor p = new Professor(rs.getInt("Id"), rs.getString("Nome"), u, d);
                lista.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    // Dentro de ProfessorAdmDAO.java, substitua o salvar ou crie um método novo
    public boolean salvarCompleto(Professor p, String senha, String acao) {
        String sqlUser = "INSERT INTO Usuarios (Email, Senha) VALUES (?, ?)";
        String sqlProf = "INSERT INTO Professores (Nome, UsuarioId, DisciplinaId) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);

            // 1. Insere Usuário
            try (PreparedStatement psUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, p.getUsuario().getEmail());
                psUser.setString(2, senha);
                psUser.executeUpdate();

                ResultSet rs = psUser.getGeneratedKeys();
                if (rs.next()) {
                    int uId = rs.getInt(1);
                    // 2. Insere Professor com o ID do usuário
                    try (PreparedStatement psProf = conn.prepareStatement(sqlProf)) {
                        psProf.setString(1, p.getNome());
                        psProf.setInt(2, uId);
                        psProf.setInt(3, p.getDisciplina().getId());
                        psProf.executeUpdate();
                    }
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    public boolean cadastrarComSenha(Professor p, String email, String senha) {
        String sqlUser = "INSERT INTO Usuarios (Email, Senha) VALUES (?, ?)";
        String sqlProf = "INSERT INTO Professores (Nome, UsuarioId, DisciplinaId) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, email);
                psUser.setString(2, senha); // A senha gerada entra aqui
                psUser.executeUpdate();

                ResultSet rs = psUser.getGeneratedKeys();
                if (rs.next()) {
                    int uId = rs.getInt(1);
                    try (PreparedStatement psProf = conn.prepareStatement(sqlProf)) {
                        psProf.setString(1, p.getNome());
                        psProf.setInt(2, uId);
                        psProf.setInt(3, p.getDisciplina().getId());
                        psProf.executeUpdate();
                    }
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    // Método mantido para lidar com a edição (UPDATE) de professores existentes
    public boolean salvar(Professor professor, String acao) {
        String sql = "UPDATE Professores SET Nome = ?, DisciplinaId = ? WHERE Id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, professor.getNome());

            if (professor.getDisciplina() != null) {
                ps.setInt(2, professor.getDisciplina().getId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            ps.setInt(3, professor.getId());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void excluir(int id) {
        String sql = "DELETE FROM Professores WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Professor buscarPorId(int id) {
        // Mudei para LEFT JOIN aqui também
        String sql = "SELECT p.*, d.Nome as DNome, u.Email " +
                "FROM Professores p " +
                "LEFT JOIN Disciplinas d ON p.DisciplinaId = d.Id " +
                "LEFT JOIN Usuarios u ON p.UsuarioId = u.Id WHERE p.Id = ?";

        try (Connection conn = Conexao.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Disciplina d = null;
                    int discId = rs.getInt("DisciplinaId");
                    if (!rs.wasNull()) {
                        d = new Disciplina(discId, rs.getString("DNome"));
                    }
                    Usuario u = new Usuario();
                    u.setEmail(rs.getString("Email"));
                    return new Professor(rs.getInt("Id"), rs.getString("Nome"), u, d);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    // Verifica se o professor existe pelo nome (usado no AJAX do Modal de Disciplinas)
    public boolean verificarSeExistePorNome(String nome) {
        String sql = "SELECT COUNT(*) FROM Professores WHERE Nome = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}