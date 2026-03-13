package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.DisciplinaAdmDAO;
import com.sistema.estudiantes.dao.ProfessorAdmDAO;
import com.sistema.estudiantes.dao.TurmaAdmDAO;
import com.sistema.estudiantes.model.DisciplinasAdm;
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

        String acao = request.getParameter("acao");
        String idParam = request.getParameter("id");

        request.setAttribute("listaTurmasAuto", turmaDao.listarTodas());
        request.setAttribute("listaProfessoresAuto", professorDao.listarComTudo());

        if (idParam != null && "editar".equals(acao)) {
            request.setAttribute("disciplinaEditar", dao.buscarPorId(Integer.parseInt(idParam)));
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

        // 1. Criar Professor Rápido (Ajustado para o novo DAO que só pede nome e discId)
        if ("criarProfessorRapido".equals(acao)) {
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            // Se o seu DAO agora só recebe Nome e DisciplinaId:
            String nomeCompleto = nome + " " + sobrenome;
            int disciplinaId = Integer.parseInt(request.getParameter("disciplinaId"));

            dao.criarProfessorBasico(nomeCompleto, disciplinaId);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 2. Salvar, Editar ou Excluir
        if ("excluir".equals(acao)) {
            String idStr = request.getParameter("id");
            if (idStr != null) dao.excluir(Integer.parseInt(idStr));

        } else if ("novo".equals(acao) || "editar".equals(acao)) {
            DisciplinasAdm d = new DisciplinasAdm();
            if ("editar".equals(acao)) {
                d.setId(Integer.parseInt(request.getParameter("id")));
            }

            d.setNome(request.getParameter("nome"));
            // IMPORTANTE: Se você removeu Carga Horária do banco, o DAO ignora,
            // mas o Servlet ainda precisa tratar se o campo vier do form
            String cargaStr = request.getParameter("cargaHoraria");
            d.setCargaHoraria(cargaStr != null && !cargaStr.isEmpty() ? Integer.parseInt(cargaStr) : 0);

            d.setProfessorNome(request.getParameter("professorNome"));
            d.setTurmaNome(request.getParameter("turmaNome"));

            dao.salvar(d, acao);
        }

        response.sendRedirect("DisciplinaAdminServlet");
    }
}