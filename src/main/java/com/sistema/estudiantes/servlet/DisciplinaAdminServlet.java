package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.DisciplinaAdmDAO;
import com.sistema.estudiantes.dao.ProfessorAdmDAO;
import com.sistema.estudiantes.dao.TurmaAdmDAO;
import com.sistema.estudiantes.model.DisciplinasAdm;
import com.sistema.estudiantes.model.Professor;
import com.sistema.estudiantes.model.Turma;
import com.sistema.estudiantes.model.TurmaAdm;
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
        List<Professor> listaProfessores = professorDao.listarComUsuarios(); // Pega todos
        List<TurmaAdm> listaTurmas = turmaDao.listarTodas(); // Pega todas

        request.setAttribute("listaProfessoresAuto", listaProfessores);
        request.setAttribute("listaTurmasAuto", listaTurmas);
        String acao = request.getParameter("acao");
        String idParam = request.getParameter("id");
        String nomeVerificar = request.getParameter("nome");

        // 1. Lógica AJAX: Verificação de existência do Professor
        if ("verificarProfessor".equals(acao)) {
            boolean existe = dao.professorExisteNoBancoReal(nomeVerificar);
            response.setContentType("application/json");
            response.getWriter().write("{\"existe\": " + existe + "}");
            return;
        }

        // 2. Lógica de Carregamento para Edição ou Exclusão
        if (idParam != null && ("editar".equals(acao) || "pre-excluir".equals(acao))) {
            DisciplinasAdm d = dao.buscarPorId(Integer.parseInt(idParam));
            request.setAttribute("disciplinaEditar", d);
        }

        // 3. Listagem Geral
        List<DisciplinasAdm> lista = dao.listarTodasComRelacionamentos();
        request.setAttribute("listaDisciplinas", lista);
        request.getRequestDispatcher("/views/disciplinas_a.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");

        // 1. Lógica AJAX: Criar Professor e Usuário "na hora"
        if ("criarProfessorRapido".equals(acao)) {
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            String email = request.getParameter("email");

            dao.criarProfessorCompleto(nome, sobrenome, email);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 2. Lógica Padrão: Salvar, Editar ou Excluir Disciplina
        if ("excluir".equals(acao)) {
            String idStr = request.getParameter("id");
            if (idStr != null) dao.excluir(Integer.parseInt(idStr));
        } else if ("novo".equals(acao) || "editar".equals(acao)) {
            DisciplinasAdm d = new DisciplinasAdm();
            if ("editar".equals(acao)) {
                d.setId(Integer.parseInt(request.getParameter("id")));
            }

            d.setNome(request.getParameter("nome"));
            d.setCargaHoraria(Integer.parseInt(request.getParameter("cargaHoraria")));
            d.setProfessorNome(request.getParameter("professorNome"));
            d.setTurmaNome(request.getParameter("turmaNome"));

            dao.salvar(d, acao);
        }

        response.sendRedirect("DisciplinaAdminServlet");
    }
}