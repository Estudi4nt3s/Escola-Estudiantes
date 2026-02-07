package com.sistema.estudiantes.dao;
import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    public void inserir(String nome) {
            String sql = "INSERT INTO Disciplina (Nome) VALUES (?)";

            try (Connection conn = new Conexao().conectar();
                 PreparedStatement psmt = conn.prepareStatement(sql)) {

                psmt.setString(1, nome);
                psmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public List<Disciplina> listar() {
            List<Disciplina> lista = new ArrayList<>();
            String sql = "SELECT * FROM Disciplina";

            try (Connection conn = new Conexao().conectar();
                 PreparedStatement psmt = conn.prepareStatement(sql);
                 ResultSet rs = psmt.executeQuery()) {

                while (rs.next()) {
                    Disciplina d = new Disciplina(
                            rs.getInt("Id"),
                            rs.getString("Nome")
                    );
                    lista.add(d);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return lista;
        }

        public boolean atualizar(Disciplina d) {
            String sql = "UPDATE Disciplina SET Nome = ? WHERE Id = ?";

            try (Connection conn = new Conexao().conectar();
                 PreparedStatement psmt = conn.prepareStatement(sql)) {

                psmt.setString(1, d.getNome());
                psmt.setInt(2, d.getId());

                return psmt.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean excluir(int id) {
            String sql = "DELETE FROM Disciplina WHERE Id = ?";

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


