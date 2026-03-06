package com.sistema.estudiantes.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/servletConfiguracoes")
public class ServletConfiguracoes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/configuracoes_a.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Pegando novos parâmetros úteis
        String anoLetivo = request.getParameter("anoLetivo");
        String limiteAlunos = request.getParameter("limiteAlunos");
        String matriculasAbertas = request.getParameter("matriculasAbertas");
        String novaSenha = request.getParameter("novaSenha");

        HttpSession session = request.getSession();

        // Salvando na sessão para simular um banco de dados
        session.setAttribute("anoLetivo", anoLetivo);
        session.setAttribute("limiteAlunos", limiteAlunos);
        session.setAttribute("matriculasAbertas", "on".equals(matriculasAbertas));

        // Se a senha não estiver vazia, você processaria a troca aqui
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            System.out.println("Senha alterada para: " + novaSenha);
        }

        response.sendRedirect("servletConfiguracoes?status=success");
    }
}