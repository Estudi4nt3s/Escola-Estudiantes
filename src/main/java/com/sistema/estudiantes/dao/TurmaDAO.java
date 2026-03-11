package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Disciplina;
import com.sistema.estudiantes.model.Nota;
import com.sistema.estudiantes.model.Turma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    public void inserir(int ano, String serie, char letra) {
        String sql = "INSERT INTO Turmas (Ano, Serie, Letra) VALUES (?, ?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, ano);
            psmt.setString(2, serie);
            psmt.setString(3, String.valueOf(letra));

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Turma> listar() {
        List<Turma> lista = new ArrayList<>();
        String sql = "SELECT * FROM Turmas";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Turma t = new Turma(
                        rs.getInt("Id"),
                        rs.getInt("Ano"),
                        rs.getString("nome")
                );
                lista.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Turma> listarComFiltro(String condicao, int valor) {
        List<Turma> turmas = new ArrayList<>();
        String sql = "SELECT * FROM turmas WHERE " + condicao;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, valor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Turma turma = new Turma(
                            rs.getInt("id"),
                            rs.getInt("ano"),
                            rs.getString("nome")
                    );
                    turmas.add(turma);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return turmas;
    }

    public boolean atualizar(Turma t) {
        String sql = "UPDATE Turmas SET Ano = ?, nome = ? WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, t.getAno());
            psmt.setString(2, t.getNome());
            psmt.setInt(3, t.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Turmas WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, id);
            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

