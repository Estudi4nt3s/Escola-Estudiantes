package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Disciplina;
import com.sistema.estudiantes.model.Professor;
import com.sistema.estudiantes.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProfessorDAO {

    public void inserir(Professor professor) {
        String sql = "INSERT INTO Professores (nome, UsuarioId, disciplinaid) VALUES (?, ?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, professor.getNome());
            ps.setInt(2, professor.getUsuario().getId());
            ps.setInt(3, professor.getDisciplina().getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Professor> listar(){
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Professores";

        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql);
                ResultSet rs = psmt.executeQuery()
        ) {
            while (rs.next()){
                Usuario u = new Usuario(rs.getInt("usuarioid"));
                Disciplina d = new Disciplina(rs.getInt("disciplinaid"));
                Professor professor = new Professor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        u,
                        d
                );
                lista.add(professor);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public List<Professor> listarComFiltro(int id) {
        List<Professor> professores = new ArrayList<>();
        String sql = "SELECT id, nome, usuarioid, disciplinaid FROM professores WHERE id = ?";

        try (Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario(rs.getInt("UsuarioId"));
                    Disciplina d = new Disciplina(rs.getInt("disciplinaid"));
                    Professor prof = new Professor(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            u,
                            d
                    );
                    professores.add(prof);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professores;
    }

    public Professor buscarPorId(int id) {
        String sql = "SELECT * FROM Professores WHERE id = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setInt(1, id);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                return new Professor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        new Usuario(rs.getInt("usuarioid")),
                        new Disciplina(rs.getInt("disciplinaid"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean atualizar(Professor professor){
        String sql = "UPDATE Professores SET usuarioid = ? WHERE id = ?";
        try(
                Connection conn = new Conexao().conectar();
                PreparedStatement psmt = conn.prepareStatement(sql)
                ) {
            psmt.setInt(1, professor.getUsuario().getId());
            psmt.setInt(2, professor.getId());

            return psmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int excluir(int id){
        String sql = "DELETE FROM Professores WHERE id = ?";

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