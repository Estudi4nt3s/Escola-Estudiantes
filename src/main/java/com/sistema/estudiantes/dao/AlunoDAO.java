package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AlunoDAO {

    public boolean inserir(String matricula, String nome, Date dataNasc){
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        try {
            PreparedStatement psmt = conn.prepareStatement(
                    "INSERT INTO Aluno(" +matricula + "," + nome + "," + dataNasc + ") VALUES (?, ?, ?)");
            psmt.setString(1, matricula);
            psmt.setString(2, nome);
            psmt.setDate(3, dataNasc);
            return psmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            conexao.desconectar(conn);
        }
    }

    public List<Aluno> listar(){
        Conexao conexao = new Conexao();
        Connection conn = null;
        Aluno aluno = null;
        List<Aluno> lista = new ArrayList<>();

        try {
            conn = conexao.conectar();
            String sql = "SELECT * FROM matricula";
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();

            while (rs.next()){
                aluno = new Aluno(
                        rs.getString("matricula"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento"),
                        rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
