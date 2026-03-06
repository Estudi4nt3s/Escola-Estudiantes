package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Usuario;
import com.sistema.estudiantes.conexao.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoAdminDAO {

    public List<Aluno> listarTodos() {

        List<Aluno> lista = new ArrayList<>();

        String sql = "SELECT * FROM alunos";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Aluno aluno = new Aluno();

                aluno.setMatricula(rs.getInt("matricula"));
                aluno.setCpf(rs.getString("cpf"));

                Date dataSql = rs.getDate("data_nascimento");
                if (dataSql != null) {
                    aluno.setDataNascimento(dataSql.toLocalDate());
                }

                aluno.setTelefonePai(rs.getString("telefone_pai"));

                // Se usuario_id for FK
                int usuarioId = rs.getInt("usuario_id");
                Usuario usuario = new Usuario();
                usuario.setId(usuarioId); // só seta o ID
                aluno.setUsuarioId(usuario);

                lista.add(aluno);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void excluir(int matricula) {

        String sql = "DELETE FROM alunos WHERE matricula = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}