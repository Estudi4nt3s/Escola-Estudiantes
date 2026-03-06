package com.sistema.estudiantes.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/servletConfiguracoes")
public class ServletConfiguracoes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/configuracoes_a.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String nomeEscola = request.getParameter("nomeEscola");
        String corPrincipal = request.getParameter("corPrincipal");
        String novaSenha = request.getParameter("novaSenha");


        response.sendRedirect("servletConfiguracoes");
    }
}