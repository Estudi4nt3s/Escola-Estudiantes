package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    // 1. INSERIR (Sem matrícula no SQL para o banco gerar sozinho)
    public boolean inserir(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, cpf, datanascimento, telefone, emailresponsavel, turmaid) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, aluno.getNome());
            psmt.setString(2, aluno.getCpf());
            psmt.setObject(3, aluno.getDataNascimento());
            psmt.setString(4, aluno.getTelefonePai());
            psmt.setString(5, aluno.getEmailResponsavel());
            psmt.setInt(6, aluno.getTurmaId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. LISTAR (Geral)
    public List<Aluno> listar() {

        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alunos ORDER BY matricula ASC";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearAluno(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 3. LISTAR POR MATRÍCULA (Mantido nome original)
    public List<Aluno> listarMatricula(int matricula) {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos WHERE matricula = ?";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alunos.add(mapearAluno(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alunos;
    }

    // 4. LISTAR POR USUÁRIO (Mantido nome original)
    public List<Aluno> listarUsuario(int usuarioid) {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos WHERE usuarioid = ?";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioid);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alunos.add(mapearAluno(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public List<Aluno> listarUsuarioAluno(int usuarioid) {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos WHERE usuarioid = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioid);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("matricula: " + rs.getInt("matricula"));
                    System.out.println("nome: " + rs.getString("nome"));
                    System.out.println("turmaid: " + rs.getInt("turmaid"));
                    Aluno aluno = new Aluno(
                            rs.getInt("matricula"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getObject("datanascimento", LocalDate.class),
                            null,
                            null,
                            rs.getInt("turmaid")
                    );
                    alunos.add(aluno);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public List<Aluno> listarPorTurma(int turmaId) {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos WHERE turmaid = ?";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, turmaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alunos.add(mapearAluno(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alunos;
    }

    // 6. BUSCAR POR CPF (Necessário para o envio de e-mail no Servlet)
    public Aluno buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM alunos WHERE cpf = ?";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, cpf);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAluno(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 7. ATUALIZAR
    public boolean atualizar(Aluno aluno) {
        String sql = "UPDATE alunos SET nome=?, cpf=?, datanascimento=?, telefone=?, emailresponsavel=?, turmaid=?, usuarioid=? WHERE matricula=?";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, aluno.getNome());
            psmt.setString(2, aluno.getCpf());
            psmt.setObject(3, aluno.getDataNascimento());
            psmt.setString(4, aluno.getTelefonePai());
            psmt.setString(5, aluno.getEmailResponsavel());
            psmt.setInt(6, aluno.getTurmaId());

            if (aluno.getUsuarioId() != null && aluno.getUsuarioId().getId() != null) {
                psmt.setInt(7, aluno.getUsuarioId().getId());
            } else {
                psmt.setNull(7, Types.INTEGER);
            }

            psmt.setInt(8, aluno.getMatricula());
            return psmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 8. EXCLUIR
    public int excluir(int matricula) {
        String sql = "DELETE FROM alunos WHERE matricula = ?";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, matricula);
            return psmt.executeUpdate() > 0 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // MÉTODO AUXILIAR DE MAPEAMENTO
    private Aluno mapearAluno(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        int userId = rs.getInt("usuarioid");
        if (!rs.wasNull()) { u.setId(userId); }

        Aluno aluno = new Aluno();
        aluno.setMatricula(rs.getInt("matricula"));
        aluno.setNome(rs.getString("nome"));
        aluno.setCpf(rs.getString("cpf"));
        aluno.setDataNascimento(rs.getObject("datanascimento", LocalDate.class));
        aluno.setUsuarioId(u);
        aluno.setTelefonePai(rs.getString("telefone")); // Nome da coluna no banco é 'telefone'
        aluno.setEmailResponsavel(rs.getString("emailresponsavel"));
        aluno.setTurmaId(rs.getInt("turmaid"));

        return aluno;
    }
}