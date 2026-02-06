package com.example.estudiantes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class AlunoDAO {

//    public boolean inserir(String matricula, String nome, Date dataNasc){
//        Conexao conexao = new Conexao();
//
//        try {
//            Connection conn = conexao.conectar();
//            PreparedStatement psmt = conn.prepareStatement(
//                    "INSERT INTO Aluno("Matricula, Nome, DataNascimento") VALUES" +
//                            "(?, ?, ?)");
//            psmt.setString(1, Matricula);
//            psmt.setString(2, Nome);
//            psmt.setDate(3, DataNascimento);
//            return psmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }finally {
//            conexao.desconectar(conn);
//        }
//    }

//    public List<Aluno> listar(){
//        ConexaoBD conexao = new ConexaoBD();
//        Connection conn = null;
//        Aluno aluno = null;
//        List<Aluno> lista = new ArrayList<>();
//
//        try {
//            conn = conexao.conectar();
//            String sql = "SELECT * FROM matricula";
//            PreparedStatement psmt = conn.prepareStatement(sql);
//            ResultSet rs = psmt.executeQuery();
//
//            while (rs.next()){
//                aluno = new Aluno(
//                        rs.getString("matricula"),
//                        rs.getString("nome"),
//                        rs.getDate("data_nascimento")
//                )
//            }
//        }
//    }

}
