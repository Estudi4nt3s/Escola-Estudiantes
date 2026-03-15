package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.DisciplinaAdmDAO;
import com.sistema.estudiantes.dao.ProfessorAdmDAO;
import com.sistema.estudiantes.dao.TurmaAdmDAO;
import com.sistema.estudiantes.model.DisciplinasAdm;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/DisciplinaAdminServlet")
public class DisciplinaAdminServlet extends HttpServlet {
    private DisciplinaAdmDAO dao = new DisciplinaAdmDAO();
    private ProfessorAdmDAO professorDao = new ProfessorAdmDAO();
    private TurmaAdmDAO turmaDao = new TurmaAdmDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String idParam = request.getParameter("id");

        request.setAttribute("listaTurmasAuto", turmaDao.listarTodas());
        request.setAttribute("listaProfessoresAuto", professorDao.listarComTudo());

        if (idParam != null && ("editar".equals(acao) || "pre-excluir".equals(acao))) {
            try {
                int id = Integer.parseInt(idParam);
                DisciplinasAdm d = dao.buscarPorId(id);
                request.setAttribute("disciplinaEditar", d);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        List<DisciplinasAdm> lista = dao.listarTodasComRelacionamentos();
        request.setAttribute("listaDisciplinas", lista);

        request.getRequestDispatcher("/views/disciplinas_a.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");

        if ("verificarProfessor".equals(acao)) {
            String nome = request.getParameter("nome");
            boolean existe = professorDao.verificarSeExistePorNome(nome);
            response.setContentType("application/json");
            response.getWriter().write("{\"existe\": " + existe + "}");
            return;
        }

        if ("criarProfessorRapido".equals(acao)) {
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            String email = request.getParameter("email");

            // Se o seu DisciplinaAdmDAO tiver o método criarProfessorBasico, use-o:
            String nomeCompleto = nome + " " + sobrenome;
            professorDao.cadastrarRapido(nomeCompleto, email); // Verifique se este método existe no seu DAO

            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if ("excluir".equals(acao)) {
            String idStr = request.getParameter("id");
            if (idStr != null) dao.excluir(Integer.parseInt(idStr));

        } else if ("novo".equals(acao) || "editar".equals(acao)) {
            DisciplinasAdm d = new DisciplinasAdm();
            if ("editar".equals(acao)) {
                d.setId(Integer.parseInt(request.getParameter("id")));
            }

            d.setNome(request.getParameter("nome"));
            d.setProfessorNome(request.getParameter("professorNome"));

            String turma = request.getParameter("turmaNome");
            if(turma != null) d.setTurmaNome(turma);

            dao.salvar(d, acao);
        }

        response.sendRedirect("DisciplinaAdminServlet");
    }
}