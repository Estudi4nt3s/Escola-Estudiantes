package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {


    public boolean inserir(Aluno aluno) {

        String sql = "INSERT INTO Aluno (matricula, nome, dataNascimento, senha) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {

            psmt.setInt(1, aluno.getMatricula());
            psmt.setString(2, aluno.getNome());
            psmt.setDate(3, new java.sql.Date(aluno.getDataNascimento().getTime()));
            psmt.setString(4, aluno.getPhoto());
            psmt.setInt(5, aluno.getUsuario_id());

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
                Aluno aluno = new Aluno(
                        rs.getInt("matricula"),
                        rs.getString("nome"),
                        rs.getDate("dataNascimento"),
                        rs.getString("photo"),
                        rs.getInt("usuario_id")
                );
                lista.add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Aluno> listarComFiltro(String nomeColuna, Object valorColuna) {
        List<Aluno> alunos = new ArrayList<>();

        String sql = "SELECT * FROM aluno WHERE " + nomeColuna + " = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            if (valorColuna instanceof Integer) {
                stmt.setInt(1, (Integer) valorColuna);
            } else if (valorColuna instanceof String) {
                stmt.setString(1, (String) valorColuna);
            } else {
                stmt.setObject(1, valorColuna);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno(
                            rs.getInt("matricula"),
                            rs.getString("nome"),
                            rs.getDate("dataNascimento"),
                            rs.getString("photo"),
                            rs.getInt("usuario_id")
                    );
                    alunos.add(aluno);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao filtrar por " + nomeColuna + ": " + e.getMessage());
        }

        return alunos;
    }


    public boolean atualizar(Aluno aluno) {

        String sql = "UPDATE Aluno " +
                "SET nome = ?, dataNascimento = ?, photo = ?, usua " +
                "WHERE matricula = ?";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {

            psmt.setString(1, aluno.getNome());
            psmt.setDate(2, new java.sql.Date(aluno.getDataNascimento().getTime()));
            psmt.setString(3, aluno.getPhoto());
            psmt.setInt(5, aluno.getMatricula());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int excluir(int matricula){
        String sql = "DELETE * FROM Aluno WHERE matricula = ?";

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
