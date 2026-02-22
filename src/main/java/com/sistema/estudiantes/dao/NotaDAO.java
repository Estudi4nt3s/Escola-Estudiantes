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

public class NotaDAO {

    public void inserir(int idDisciplina, int idAluno, int idTurma, double valor) {
        String sql = """
            INSERT INTO Nota (IdDisciplina, IdAluno, IdTurma, Valor)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, idDisciplina);
            psmt.setInt(2, idAluno);
            psmt.setInt(3, idTurma);
            psmt.setDouble(4, valor);

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Nota> listar() {
        List<Nota> lista = new ArrayList<>();
        String sql = "SELECT * FROM Nota";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {

                Disciplina d = new Disciplina(rs.getInt("IdDisciplina"));
                Aluno a = new Aluno(rs.getInt("IdAluno"));
                Turma t = new Turma(rs.getInt("IdTurma"));

                Nota n = new Nota(
                        rs.getInt("Id"),
                        d,
                        a,
                        t,
                        rs.getDouble("Valor")
                );

                lista.add(n);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }



    public boolean atualizar(Nota n) {
        String sql = """
        UPDATE Nota
           SET IdDisciplina = ?, IdAluno = ?, IdTurma = ?, Valor = ?
         WHERE Id = ?
    """;

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {


            psmt.setInt(1, n.getDisciplina().getId());
            psmt.setInt(2, n.getAluno().getMatricula());
            psmt.setInt(3, n.getTurma().getId());
            psmt.setDouble(4, n.getValor());
            psmt.setInt(5, n.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean excluir(int id) {
        String sql = "DELETE FROM Nota WHERE Id = ?";

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
