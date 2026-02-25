package com.sistema.estudiantes.dao;


import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Observacao;
import com.sistema.estudiantes.model.Professor;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ObservacaoDAO {

    public void inserir(String texto, LocalDate dataCriacao, int idProfessor, int idAluno, int idDisciplina) {
        String sql = """
            INSERT INTO Observacao (Texto, DataCriacao, IdProfessor, IdAluno, IdDisciplina)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, texto);
            psmt.setObject(2, dataCriacao);
            psmt.setInt(3, idProfessor);
            psmt.setInt(4, idAluno);
            psmt.setInt(5, idDisciplina);

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
                        rs.getInt("id"),
                        rs.getString("texto"),
                        rs.getObject("datacriacao", LocalDate.class),
                        rs.getInt("professorid"),
                        rs.getInt("idaluno"),
                        rs.getInt("disciplinaid")
                );
                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean atualizar(Observacao o) {
        String sql = "UPDATE Observacao SET Texto = ?, DataCriacao = ? WHERE Id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, o.getTexto());
            psmt.setObject(2, o.getDataCriacao());
            psmt.setInt(3, o.getId());

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
