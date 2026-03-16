package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AdministradorDAO;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.RankingDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/servletConfiguracoes")
public class ServletConfiguracoes extends HttpServlet {

    // Seguindo seu padrão, instanciamos o DAO
    private AdministradorDAO dao = new AdministradorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Busca a lista completa
        List<RankingDTO> rankingCompleto = dao.obterRankingAlunos();

        // 2. Cria a sublista com limite de 3
        List<RankingDTO> top3 = rankingCompleto.stream()
                .limit(3)
                .collect(java.util.stream.Collectors.toList());

        // 3. ATENÇÃO: Envia SOMENTE o top3 (o que você chamou de rankingReal)
        request.setAttribute("rankingReal", top3);

        // 4. Mantém as outras métricas
        request.setAttribute("totalAlunos", dao.contarTotalAlunos());
        request.setAttribute("mediaGeral", dao.calcularMediaGeral());
        request.setAttribute("totalVinculados", dao.contarAlunosVinculados());
        request.setAttribute("totalPendentes", dao.contarAlunosPendentes());
        request.getRequestDispatcher("/views/configuracoes_a.jsp").forward(request, response);
    }

}