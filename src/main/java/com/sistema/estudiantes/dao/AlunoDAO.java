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

        String sql = "INSERT INTO Aluno (matricula, cpf, nome, datanascimento, usuarioid, telefonepai) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {

            psmt.setInt(1, aluno.getMatricula());
            psmt.setString(2, aluno.getCpf());
            psmt.setString(3, aluno.getNome());
            psmt.setObject(4, aluno.getDataNascimento());
            psmt.setInt(5, aluno.getUsuarioId().getId());
            psmt.setString(6, aluno.getTelefonePai());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Aluno> listar() {

        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM Aluno";

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
                        rs.getString("nome"),
                        rs.getObject("datanascimento", LocalDate.class),
                        u,
                        rs.getString("telefonepai")
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
        String sql = "SELECT * FROM aluno WHERE matricula = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario(rs.getInt("usuarioid"));
                    Aluno aluno = new Aluno(
                            rs.getInt("matricula"),
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getObject("datanascimento", LocalDate.class),
                            u,
                            rs.getString("telefonepai")
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
                    FROM aluno a
                    INNER JOIN turmaaluno ta ON ta.matriculaaluno = a.matricula
                    WHERE ta.idturma = ?
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
                                rs.getString("nome"),
                                rs.getObject("datanascimento", LocalDate.class),
                                u,
                                rs.getString("telefonepai")
                        );

                        alunos.add(aluno);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return alunos;
            }

        public boolean atualizar(Aluno aluno) {

            String sql = "UPDATE Aluno " +
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
            String sql = "DELETE FROM Aluno WHERE matricula = ?";

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