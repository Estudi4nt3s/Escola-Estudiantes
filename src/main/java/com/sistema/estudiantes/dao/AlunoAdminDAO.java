package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoAdminDAO {

    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM Alunos ORDER BY Matricula";

        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Aluno a = new Aluno();
                a.setMatricula(rs.getInt("Matricula"));
                a.setNome(rs.getString("Nome"));
                a.setCpf(rs.getString("Cpf"));
                // Ajustado para 'telefone' conforme a imagem do seu banco
                a.setTelefonePai(rs.getString("telefone"));
                a.setTurmaId(rs.getInt("TurmaId"));
                // Ajustado para 'emailresponsavel' conforme a imagem do seu banco
                a.setEmailResponsavel(rs.getString("emailresponsavel"));

                if (rs.getDate("DataNascimento") != null) {
                    a.setDataNascimento(rs.getDate("DataNascimento").toLocalDate());
                }

                Usuario user = new Usuario();
                user.setId(rs.getInt("usuarioid"));
                a.setUsuarioId(user);

                lista.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void salvar(Aluno aluno, String acao) {
        Connection conn = null;
        try {
            conn = Conexao.conectar();
            if ("novo".equals(acao)) {
                // INSERT completo com emailresponsavel e telefone
                String sqlAluno = "INSERT INTO Alunos (Nome, Cpf, DataNascimento, telefone, TurmaId, emailresponsavel) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmtA = conn.prepareStatement(sqlAluno);

                stmtA.setString(1, aluno.getNome());
                stmtA.setString(2, aluno.getCpf().replaceAll("[^0-9]", ""));
                stmtA.setDate(3, Date.valueOf(aluno.getDataNascimento()));
                stmtA.setString(4, aluno.getTelefonePai());
                stmtA.setInt(5, aluno.getTurmaId());
                stmtA.setString(6, aluno.getEmailResponsavel());

                stmtA.executeUpdate();
            } else if ("editar".equals(acao)) {
                // UPDATE completo
                String sqlUpAluno = "UPDATE Alunos SET Nome = ?, Cpf = ?, DataNascimento = ?, telefone = ?, TurmaId = ?, emailresponsavel = ? WHERE Matricula = ?";
                PreparedStatement stmtA = conn.prepareStatement(sqlUpAluno);
                stmtA.setString(1, aluno.getNome());
                stmtA.setString(2, aluno.getCpf());
                stmtA.setDate(3, Date.valueOf(aluno.getDataNascimento()));
                stmtA.setString(4, aluno.getTelefonePai());
                stmtA.setInt(5, aluno.getTurmaId());
                stmtA.setString(6, aluno.getEmailResponsavel());
                stmtA.setInt(7, aluno.getMatricula());
                stmtA.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public Aluno buscarPorMatricula(int matricula) {
        String sql = "SELECT * FROM Alunos WHERE Matricula = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, matricula);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    Aluno a = new Aluno();
                    a.setMatricula(rs.getInt("Matricula"));
                    a.setNome(rs.getString("Nome"));
                    a.setCpf(rs.getString("Cpf"));
                    a.setDataNascimento(rs.getDate("DataNascimento").toLocalDate());
                    a.setTelefonePai(rs.getString("telefone"));
                    a.setTurmaId(rs.getInt("TurmaId"));
                    a.setEmailResponsavel(rs.getString("emailresponsavel"));
                    return a;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void excluir(int matricula) {
        String sqlNotas = "DELETE FROM notas WHERE alunomatricula = ?";
        String sqlObs = "DELETE FROM observacoes WHERE alunomatricula = ?";
        String sqlAluno = "DELETE FROM Alunos WHERE Matricula = ?";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);
            try (PreparedStatement st1 = conn.prepareStatement(sqlNotas);
                 PreparedStatement st2 = conn.prepareStatement(sqlObs);
                 PreparedStatement st3 = conn.prepareStatement(sqlAluno)) {

                st1.setInt(1, matricula);
                st1.executeUpdate();

                st2.setInt(1, matricula);
                st2.executeUpdate();

                st3.setInt(1, matricula);
                st3.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Aluno buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM Alunos WHERE Cpf = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Aluno a = new Aluno();
                    a.setMatricula(rs.getInt("Matricula"));
                    a.setNome(rs.getString("Nome"));
                    a.setCpf(rs.getString("Cpf"));
                    a.setTelefonePai(rs.getString("telefone"));
                    a.setEmailResponsavel(rs.getString("emailresponsavel"));
                    return a;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}