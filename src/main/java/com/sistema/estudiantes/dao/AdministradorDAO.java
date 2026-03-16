package com.sistema.estudiantes.dao;

import com.sistema.estudiantes.conexao.Conexao;
import com.sistema.estudiantes.model.Admin;
import com.sistema.estudiantes.model.RankingDTO;

import java.sql.*;
import java.util.*;

public class AdministradorDAO {

    // --- MÉTODOS DE AUTENTICAÇÃO ---
    public List<Admin> buscarLogin(String usuario) {
        List<Admin> lista = new ArrayList<>();
        String sql = "SELECT * FROM Admins WHERE Usuario = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            if (conn == null) return lista;
            psmt.setString(1, usuario);
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    Admin admin = new Admin();
                    admin.setId(rs.getInt("Id"));
                    admin.setUsuario(rs.getString("Usuario"));
                    admin.setSenha(rs.getString("Senha"));
                    lista.add(admin);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar login: " + e.getMessage());
        }
        return lista;
    }

    // --- MÉTODOS DE CONFIGURAÇÃO E MÉTRICAS ---

    public List<RankingDTO> obterRankingAlunos() {
        List<RankingDTO> ranking = new ArrayList<>();
        // Usamos AVG para calcular a média e damos o apelido 'media'
        String sql = "SELECT a.nome, AVG((n.n1 + n.n2) / 2) AS media " +
                "FROM alunos a " +
                "JOIN notas n ON a.matricula = n.alunomatricula " +
                "GROUP BY a.nome " +
                "ORDER BY media";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                RankingDTO dto = new RankingDTO();
                dto.setNome(rs.getString("nome"));
                dto.setMedia(rs.getDouble("media"));
                ranking.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ranking;
    }

    public double calcularMediaGeral() {
        String sql = "SELECT AVG((n1 + n2) / 2) AS media_total FROM notas";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("media_total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public int contarTotalAlunos() {
        String sql = "SELECT COUNT(*) FROM alunos";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int contarAlunosVinculados() {
        // Exemplo: se no banco for "usuario_id", mude aqui:
        String sql = "SELECT COUNT(*) FROM alunos WHERE usuarioid IS NOT NULL";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int contarAlunosPendentes() {
        // Mesma correção aqui
        String sql = "SELECT COUNT(*) FROM alunos WHERE usuarioid IS NULL";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
    // 1. Resumo quantitativo (Para a IA entender o tamanho da base)
    public String obterResumoEstatisticas() {
        return String.format(
                "Total de alunos: %d. Media geral da escola: %.2f. " +
                        "Alunos vinculados: %d. Alunos pendentes: %d.",
                contarTotalAlunos(), calcularMediaGeral(),
                contarAlunosVinculados(), contarAlunosPendentes()
        );
    }

    // 2. Ranking de desempenho (Para a IA identificar turmas ou alunos de destaque)
    public String obterRankingParaIA() {
        List<RankingDTO> ranking = obterRankingAlunos();
        StringBuilder sb = new StringBuilder("Top alunos: ");
        for (int i = 0; i < Math.min(ranking.size(), 5); i++) {
            sb.append(ranking.get(i).getNome()).append(" (")
                    .append(String.format("%.2f", ranking.get(i).getMedia())).append("), ");
        }
        return sb.toString();
    }
}