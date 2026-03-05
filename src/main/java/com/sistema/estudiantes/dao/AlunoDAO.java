package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public boolean inserir(Aluno aluno) {

        String sql = "INSERT INTO Alunos (matricula, cpf, datanascimento, usuarioid, telefonepai, turmaid) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {

            psmt.setInt(1, aluno.getMatricula());
            psmt.setString(2, aluno.getCpf());
            psmt.setObject(3, aluno.getDataNascimento());
            psmt.setInt(4, aluno.getUsuarioId().getId());
            psmt.setString(5, aluno.getTelefonePai());
            psmt.setInt(6, aluno.getTurmaId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Aluno> listar() {

        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM Alunos";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql);
                ResultSet rs = psmt.executeQuery()
        ) {

            while (rs.next()) {
                Usuario u = new Usuario(rs.getInt("usuarioid"));
                Aluno aluno = new Aluno(
                        rs.getInt("matricula"),
                        rs.getString("cpf"),
                        rs.getObject("datanascimento", LocalDate.class),
                        u,
                        rs.getString("telefonepai"),
                        rs.getInt("turmaid")
                );
                lista.add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Aluno> listarComFiltro(int matricula) {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos WHERE matricula = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario(rs.getInt("usuarioid"));
                    Aluno aluno = new Aluno(
                            rs.getInt("matricula"),
                            rs.getString("cpf"),
                            rs.getObject("datanascimento", LocalDate.class),
                            u,
                            rs.getString("telefonepai"),
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
        String sql = """
                    SELECT a.* 
                    FROM alunos a
                    INNER JOIN turmas t ON t.id = a.turmaid
                    WHERE t.id = ?
        """;
                try (Connection conn = Conexao.conectar();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setInt(1, turmaId);

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        Usuario u = new Usuario(rs.getInt("usuarioid"));
                        Aluno aluno = new Aluno(
                                rs.getInt("matricula"),
                                rs.getString("cpf"),
                                rs.getObject("datanascimento", LocalDate.class),
                                u,
                                rs.getString("telefonepai"),
                                rs.getInt("turmaid")
                        );

                        alunos.add(aluno);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return alunos;
            }

        public boolean atualizar(Aluno aluno) {

            String sql = "UPDATE Alunos " +
                    "SET usuarioid = ?" +
                    "WHERE matricula = ?";

            try (
                    Connection conn = new Conexao().conectar();
                    PreparedStatement psmt = conn.prepareStatement(sql)
            ) {
                psmt.setInt(4, aluno.getUsuarioId().getId());
                psmt.setInt(6, aluno.getMatricula());

                return psmt.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public int excluir(int matricula){
            String sql = "DELETE FROM Alunos WHERE matricula = ?";

            try(   Connection conn = new Conexao().conectar();
                   PreparedStatement psmt = conn.prepareStatement(sql)
            ){
                psmt.setInt(1, matricula);

                if (psmt.executeUpdate() > 0){
                    return 1;
                }else {
                    return 0;
                }

            }catch (SQLException e){
                e.printStackTrace();
                return -1;
            }
        }
        }