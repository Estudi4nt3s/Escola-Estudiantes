package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    public void inserir(Nota nota) {
        String sql = """
            INSERT INTO Nota (disciplinaid, idaluno, idturma, N1, N2)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, nota.getIdDisciplina().getId());
            psmt.setInt(2, nota.getIdAluno().getMatricula());
            psmt.setInt(3, nota.getIdTurma().getId());
            psmt.setDouble(4, nota.getN1());
            psmt.setDouble(5, nota.getN2());

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Nota> listar() {
        List<Nota> lista = new ArrayList<>();
        String sql = "SELECT *, avg(valor) FROM Nota";

        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {

                Disciplina d = new Disciplina(rs.getInt("disciplinaid"));
                Aluno a = new Aluno(rs.getInt("idaluno"));
                Turma t = new Turma(rs.getInt("idturma"));

                Nota n = new Nota(
                        rs.getInt("Id"),
                        d,
                        a,
                        t,
                        rs.getDouble("N1"),
                        rs.getDouble("N2")
                );

                lista.add(n);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Nota> listarComFiltro(String condicao, Object valor){
        List<Nota> listaNota = new ArrayList<>();
        String sql = "SELECT *, avg(valor) FROM Nota WHERE "+condicao;

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, valor);

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {

                    Disciplina disciplina = new Disciplina(rs.getInt("disciplinaid"));
                    Aluno aluno = new Aluno(rs.getInt("idaluno"));
                    Turma turma = new Turma(rs.getInt("idturma"));

                    Nota nota = new Nota(
                            rs.getInt("id"),
                            disciplina,
                            aluno,
                            turma,
                            rs.getDouble("N1"),
                            rs.getDouble("N2")
                    );

                    listaNota.add(nota);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaNota;
    }

    public double Media(int id){
        String sql = "SELECT avg(valor) as media FROM Nota WHERE idaluno = ?";
        double media = 0;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    media = rs.getDouble("media");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return media;
    }

    public boolean atualizar(Nota n) {
        String sql = """
        UPDATE Nota
           SET disciplinaid = ?, IdAluno = ?, IdTurma = ?, N1 = ?, N2
         WHERE Id = ?
    """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, n.getIdDisciplina().getId());
            psmt.setInt(2, n.getIdAluno().getMatricula());
            psmt.setInt(3, n.getIdTurma().getId());
            psmt.setDouble(4, n.getN1());
            psmt.setDouble(5, n.getN2());
            psmt.setInt(6, n.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean excluir(int id) {
        String sql = "DELETE FROM Nota WHERE Id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, id);
            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
