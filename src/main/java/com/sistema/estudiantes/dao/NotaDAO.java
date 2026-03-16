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
            INSERT INTO Notas (disciplinaid, alunoid, N1, N2)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, nota.getIdDisciplina().getId());
            psmt.setInt(2, nota.getIdAluno().getMatricula());
            psmt.setDouble(3, nota.getN1());
            psmt.setDouble(4, nota.getN2());

            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Nota> listar() {
        List<Nota> lista = new ArrayList<>();
        String sql = "SELECT * FROM Notas";

        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {

                Disciplina d = new Disciplina(rs.getInt("disciplinaid"));
                Aluno a = new Aluno(rs.getInt("alunomatricula"));

                Nota n = new Nota(
                        rs.getInt("Id"),
                        d,
                        a,
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

    public List<Nota> listarComFiltro(String condicao, int valor){
        List<Nota> listaNota = new ArrayList<>();
        String sql = "SELECT * FROM notas WHERE " + condicao;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    double n1Value = rs.getDouble("N1");
                    Double n1 = rs.wasNull() ? null : n1Value;

                    double n2Value = rs.getDouble("N2");
                    Double n2 = rs.wasNull() ? null : n2Value;

                    Disciplina disciplina = new Disciplina(rs.getInt("disciplinaid"));
                    Aluno aluno = new Aluno(rs.getInt("alunomatricula"));
                    Nota nota = new Nota(
                            rs.getInt("id"),
                            disciplina,
                            aluno,
                            n1,
                            n2
                    );
                    listaNota.add(nota);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaNota;
    }

    public boolean atualizar(Nota n) {
        String sql = """
        UPDATE Notas
           SET disciplinaid = ?, alunomatricula = ?, N1 = ?, N2 = ?
         WHERE Id = ?
    """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, n.getIdDisciplina().getId());
            psmt.setInt(2, n.getIdAluno().getMatricula());
            if(n.getN1() != null){
                psmt.setDouble(3, n.getN1());
            }else{
                psmt.setNull(3, java.sql.Types.DOUBLE);
            }

            if(n.getN2() != null){
                psmt.setDouble(4, n.getN2());
            }else{
                psmt.setNull(4, java.sql.Types.DOUBLE);
            }
            psmt.setInt(5, n.getId());

            return psmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean excluir(int id) {
        String sql = "DELETE FROM Notas WHERE Id = ?";

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
