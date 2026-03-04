package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.DisciplinaAdmDAO;
import com.sistema.estudiantes.model.Disciplina;

import com.sistema.estudiantes.model.DisciplinasAdm;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/DisciplinaAdminServlet")
public class DisciplinaAdminServlet extends HttpServlet {

    private DisciplinaAdmDAO dao = new DisciplinaAdmDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao == null) {
            listar(request, response);
        } else {
            switch (acao) {

                case "novo":
                    response.sendRedirect("views/form_disciplina.jsp");
                    break;

                case "editar":
                    int idEditar = Integer.parseInt(request.getParameter("id"));
                    DisciplinasAdm disciplina = dao.buscarPorId(idEditar);
                    request.setAttribute("disciplina", disciplina);
                    request.getRequestDispatcher("views/form_disciplina.jsp")
                            .forward(request, response);
                    break;

                case "excluir":
                    int idExcluir = Integer.parseInt(request.getParameter("id"));
                    dao.excluir(idExcluir);
                    response.sendRedirect("DisciplinaAdminServlet");
                    break;

                default:
                    listar(request, response);
            }
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<DisciplinasAdm> lista = dao.listarTodasComRelacionamentos();

        request.setAttribute("listaDisciplinas", lista);
        request.getRequestDispatcher("views/disciplinas_a.jsp")
                .forward(request, response);
    }
}