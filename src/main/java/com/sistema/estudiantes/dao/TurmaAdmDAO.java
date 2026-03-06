package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.model.TurmaAdm;
import com.sistema.estudiantes.conexao.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaAdmDAO {

    public List<TurmaAdm> listarTodas() {

        List<TurmaAdm> lista = new ArrayList<>();

        String sql = "SELECT * FROM turmaadm";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                TurmaAdm turma = new TurmaAdm();

                turma.setId(rs.getInt("id"));
                turma.setNome(rs.getString("nome"));
                turma.setAno(rs.getInt("ano"));
                turma.setQuantidadeAlunos(rs.getInt("quantidade_alunos"));

                lista.add(turma);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public TurmaAdm buscarPorId(int id) {

        TurmaAdm turma = null;

        String sql = "SELECT * FROM turmaadm WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                turma = new TurmaAdm();

                turma.setId(rs.getInt("id"));
                turma.setNome(rs.getString("nome"));
                turma.setAno(rs.getInt("ano"));
                turma.setQuantidadeAlunos(rs.getInt("quantidade_alunos"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return turma;
    }

    public void excluir(int id) {

        String sql = "DELETE FROM turmaadm WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}