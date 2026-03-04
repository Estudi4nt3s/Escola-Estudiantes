package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AlunoAdminDAO;
import com.sistema.estudiantes.model.Aluno;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/AlunoAdminServlet")
public class AlunoAdminServlet extends HttpServlet {

    private AlunoAdminDAO dao = new AlunoAdminDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Aluno> lista = dao.listarTodos();
        request.setAttribute("listaAlunos", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/views/aluno_a.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if ("excluir".equals(acao)) {
            int matricula = Integer.parseInt(request.getParameter("matricula"));
            dao.excluir(matricula);
        }

        response.sendRedirect(request.getContextPath() + "/AlunoAdminServlet");
    }
}