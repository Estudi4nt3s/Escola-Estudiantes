package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import java.sql.*;
import java.util.*;

public class RelatorioDAO {
    public List<Map<String, String>> buscarDadosRelatorio() {
        List<Map<String, String>> lista = new ArrayList<>();

        String sql = "SELECT " +
                "    t.nome AS turma_nome, " +
                "    COALESCE(d.nome, 'Sem Disciplina') AS disciplina_nome, " +
                "    COALESCE(p.nome, 'Não atribuído') AS professor_nome, " +
                "    COALESCE(ROUND(AVG((n.n1 + n.n2) / 2), 1), 0.0) AS media_turma, " +
                "    COUNT(n.id) AS total_avaliacoes " +
                "FROM turmas t " +
                "LEFT JOIN alunos a ON t.id = a.turmaid " +
                "LEFT JOIN notas n ON a.matricula = n.alunomatricula " +
                "LEFT JOIN disciplinas d ON n.disciplinaid = d.id " +
                "LEFT JOIN professores p ON p.disciplinaid = d.id " +
                "GROUP BY t.nome, d.nome, p.nome " +
                "ORDER BY t.nome, d.nome;";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Map<String, String> m = new HashMap<>();
                m.put("turma", rs.getString("turma_nome"));
                m.put("materia", rs.getString("disciplina_nome"));
                m.put("professor", rs.getString("professor_nome"));
                m.put("media", String.format("%.1f", rs.getDouble("media_turma")));
                m.put("qtd", rs.getString("total_avaliacoes"));
                lista.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}