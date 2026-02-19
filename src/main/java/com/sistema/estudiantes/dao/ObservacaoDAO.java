package com.sistema.estudiantes.dao;


import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Observacao;
import com.sistema.estudiantes.model.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ObservacaoDAO {

    public void inserir(String texto, int idProfessor, int idAluno, int idDisciplina) {
        String sql = """
            INSERT INTO Observacao (Texto, IdProfessor, IdAluno, IdDisciplina)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, texto);
            psmt.setInt(2, idProfessor);
            psmt.setInt(3, idAluno);
            psmt.setInt(4, idDisciplina);

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Observacao> listar() {
        List<Observacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM Observacao";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                Observacao o = new Observacao(
                        rs.getInt("Id"),
                        rs.getString("Texto"),
                        rs.getTimestamp("DataCriacao"),
                        rs.getInt("IdProfessor"),
                        rs.getInt("IdAluno"),
                        rs.getInt("IdDisciplina")
                );
                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean atualizar(Observacao o) {
        String sql = "UPDATE Observacao SET Texto = ? WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, o.getTexto());
            psmt.setInt(2, o.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Observacao WHERE Id = ?";

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
