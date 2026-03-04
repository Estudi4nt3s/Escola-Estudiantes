package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.TurmaAdmDAO;
import com.sistema.estudiantes.model.TurmaAdm;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/TurmaAdminServlet")
public class TurmaAdmServlet extends HttpServlet {

    private TurmaAdmDAO dao;

    @Override
    public void init() {
        dao = new TurmaAdmDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao == null) {
            listar(request, response);
        } else {

            switch (acao) {

                case "excluir":
                    excluir(request, response);
                    break;

                case "editar":
                    editar(request, response);
                    break;

                default:
                    listar(request, response);
                    break;
            }
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<TurmaAdm> lista = dao.listarTodas();

        request.setAttribute("listaTurmas", lista);
        request.getRequestDispatcher("views/turmas_a.jsp")
                .forward(request, response);
    }

    private void excluir(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        dao.excluir(id);

        response.sendRedirect("TurmaAdminServlet");
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        TurmaAdm turma = dao.buscarPorId(id);

        request.setAttribute("turma", turma);
        request.getRequestDispatcher("views/form_turma.jsp")
                .forward(request, response);
    }
}