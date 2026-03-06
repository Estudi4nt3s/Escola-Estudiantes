package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoAdminDAO {

    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        // JOIN para buscar Nome/Sobrenome de Usuarios + dados de Alunos
        String sql = "SELECT a.*, u.Nome, u.Sobrenome " +
                "FROM Alunos a " +
                "JOIN Usuarios u ON a.UsuarioId = u.Id " +
                "ORDER BY a.Matricula DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Aluno a = new Aluno();
                a.setMatricula(rs.getInt("Matricula"));
                a.setCpf(rs.getString("Cpf"));
                a.setNome(rs.getString("Nome") + " " + rs.getString("Sobrenome"));

                Date data = rs.getDate("DataNascimento");
                if (data != null) {
                    a.setDataNascimento(data.toLocalDate());
                }

                a.setTelefonePai(rs.getString("TelefonePai"));
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

            conn.setAutoCommit(false);



            if ("novo".equals(acao)) {

                // A LINHA QUE FALTAVA É ESSA:

                String sqlUser = "INSERT INTO Usuarios (Nome, Sobrenome, Email, Senha) VALUES (?, ?, ?, ?) RETURNING Id";



                String nomeCompleto = aluno.getNome().trim();

                String nome = nomeCompleto;

                String sobrenome = "";



                if (nomeCompleto.contains(" ")) {

                    nome = nomeCompleto.substring(0, nomeCompleto.indexOf(" "));

                    sobrenome = nomeCompleto.substring(nomeCompleto.indexOf(" ") + 1);

                }



                PreparedStatement stmtU = conn.prepareStatement(sqlUser);

                stmtU.setString(1, nome);

                stmtU.setString(2, sobrenome);

                stmtU.setString(3, nome.toLowerCase() + "." + System.currentTimeMillis() + "@escola.com");

                stmtU.setString(4, "123456");



                ResultSet rs = stmtU.executeQuery();

                int usuarioId = 0;

                if (rs.next()) usuarioId = rs.getInt(1);



                String sqlAluno = "INSERT INTO Alunos (Cpf, DataNascimento, UsuarioId, TelefonePai) VALUES (?, ?, ?, ?)";

                PreparedStatement stmtA = conn.prepareStatement(sqlAluno);

                String cpfLimpo = aluno.getCpf().replaceAll("[^0-9]", "");
                stmtA.setString(1, cpfLimpo);

                stmtA.setDate(2, Date.valueOf(aluno.getDataNascimento()));

                stmtA.setInt(3, usuarioId);

                stmtA.setString(4, aluno.getTelefonePai());

                stmtA.executeUpdate();



            } else if ("editar".equals(acao)) {

                // 1. Atualizar Usuarios

                String sqlUpUser = "UPDATE Usuarios SET Nome = ?, Sobrenome = ? WHERE Id = (SELECT UsuarioId FROM Alunos WHERE Matricula = ?)";

                PreparedStatement stmtU = conn.prepareStatement(sqlUpUser);

                String[] partesNome = aluno.getNome().split(" ", 2);

                stmtU.setString(1, partesNome[0]);

                stmtU.setString(2, partesNome.length > 1 ? partesNome[1] : "");

                stmtU.setInt(3, aluno.getMatricula());

                stmtU.executeUpdate();



                // 2. Atualizar Alunos

                String sqlUpAluno = "UPDATE Alunos SET Cpf = ?, DataNascimento = ?, TelefonePai = ? WHERE Matricula = ?";

                PreparedStatement stmtA = conn.prepareStatement(sqlUpAluno);

                stmtA.setString(1, aluno.getCpf());

                stmtA.setDate(2, Date.valueOf(aluno.getDataNascimento()));

                stmtA.setString(3, aluno.getTelefonePai());

                stmtA.setInt(4, aluno.getMatricula());

                stmtA.executeUpdate();

            }

            conn.commit(); // Se chegou aqui, ele salva de verdade!

        } catch (Exception e) {

            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }

            e.printStackTrace();

        } finally {

            Conexao.desconectar(conn);

        }

    }

    public Aluno buscarPorMatricula(int matricula) {
        String sql = "SELECT a.*, u.Nome, u.Sobrenome FROM Alunos a JOIN Usuarios u ON a.UsuarioId = u.Id WHERE a.Matricula = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, matricula);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    Aluno a = new Aluno();
                    a.setMatricula(rs.getInt("Matricula"));
                    a.setCpf(rs.getString("Cpf"));
                    a.setNome(rs.getString("Nome") + " " + rs.getString("Sobrenome"));
                    a.setDataNascimento(rs.getDate("DataNascimento").toLocalDate());
                    a.setTelefonePai(rs.getString("TelefonePai"));
                    return a;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void excluir(int matricula) {
        String sqlNotas = "DELETE FROM Notas WHERE AlunoId = ?";
        String sqlObs = "DELETE FROM Observacoes WHERE AlunoMatricula = ?";
        String sqlAluno = "DELETE FROM Alunos WHERE Matricula = ?";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false); // Transação para garantir que apague tudo ou nada

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
}