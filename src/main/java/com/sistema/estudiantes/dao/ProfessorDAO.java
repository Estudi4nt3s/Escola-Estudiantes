package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProfessorDAO {

    public void inserir(String nome) {
        String sql = "INSERT INTO Professor (Nome) VALUES (?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Professor> listar(){
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Professor";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql);
                ResultSet rs = psmt.executeQuery()
        ) {
            while (rs.next()){
                Professor professor = new Professor(
                        rs.getInt("id"),
                        rs.getString("Nome"),
                        rs.getString("Senha")
                );
                lista.add(professor);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public boolean atualizar(Professor professor){
        String sql = "UPDATE Professor" +
                "Nome = ?" +
                "WHERE id = ?";
        try(
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql)
                ) {
            psmt.setString(1, professor.getNome());
            psmt.setString(2, professor.getSenha());

            return psmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int excluir(int id){
        String sql = "DELETE * FROM Professor WHERE id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setInt(1, id);

            if(psmt.executeUpdate() > 0){
                return 1;
            }else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}