package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AdministradorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/servletConfiguracoes")
public class ServletConfiguracoes extends HttpServlet {

    // Seguindo seu padrão, instanciamos o DAO
    private AdministradorDAO dao = new AdministradorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // O DAO busca métricas e ranking direto do banco
        request.setAttribute("rankingReal", dao.obterRankingAlunos());
        request.setAttribute("totalAlunos", dao.contarTotalAlunos());
        request.setAttribute("mediaGeral", dao.calcularMediaGeral());

        request.getRequestDispatcher("/views/configuracoes_a.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String novaSenha = request.getParameter("novaSenha");
        HttpSession session = request.getSession();
        String idAdmin = (String) session.getAttribute("adminId");

        // Lógica de atualização de senha via DAO
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            dao.atualizarSenha(Integer.parseInt(idAdmin), novaSenha);
        }

        response.sendRedirect("servletConfiguracoes?status=success");
    }
}