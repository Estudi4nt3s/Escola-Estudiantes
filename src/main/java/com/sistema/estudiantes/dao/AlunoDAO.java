package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;

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
            psmt.setString(4, aluno.getSenha());

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
                        rs.getString("senha")
                );
                lista.add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    public boolean atualizar(Aluno aluno) {

        String sql = "UPDATE Aluno " +
                "SET nome = ?, dataNascimento = ?, senha = ? " +
                "WHERE matricula = ?";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {

            psmt.setString(1, aluno.getNome());
            psmt.setDate(2, new java.sql.Date(aluno.getDataNascimento().getTime()));
            psmt.setString(3, aluno.getSenha());
            psmt.setInt(4, aluno.getMatricula());

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
