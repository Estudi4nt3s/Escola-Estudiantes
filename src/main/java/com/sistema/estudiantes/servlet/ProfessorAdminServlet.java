package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.ProfessorAdmDAO;
import com.sistema.estudiantes.dao.DisciplinaAdmDAO;
import com.sistema.estudiantes.model.Disciplina;
import com.sistema.estudiantes.model.Professor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/ProfessorAdminServlet")
public class ProfessorAdminServlet extends HttpServlet {
    private ProfessorAdmDAO dao = new ProfessorAdmDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DisciplinaAdmDAO discDao = new DisciplinaAdmDAO();
        request.setAttribute("listaDisciplinas", discDao.listar());
        request.setAttribute("listaProfessores", dao.listarComTudo());
        request.getRequestDispatcher("/views/professores_a.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        if ("novo".equals(acao) || "editar".equals(acao)) {
            Professor p = new Professor();
            if ("editar".equals(acao)) {
                p.setId(Integer.parseInt(request.getParameter("id")));
            }

            p.setNome(request.getParameter("nome"));

            Disciplina d = new Disciplina();
            d.setId(Integer.parseInt(request.getParameter("disciplinaId")));
            p.setDisciplina(d);

            dao.salvar(p, acao);

        } else if ("excluir".equals(acao)) {
            dao.excluir(Integer.parseInt(request.getParameter("id")));
        }

        response.sendRedirect("ProfessorAdminServlet");
    }
}