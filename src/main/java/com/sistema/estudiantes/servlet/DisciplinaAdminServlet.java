//package com.sistema.estudiantes.servlet;
//
//import com.sistema.estudiantes.dao.DisciplinaAdmDAO;
//import com.sistema.estudiantes.dao.ProfessorAdmDAO;
//import com.sistema.estudiantes.dao.TurmaAdmDAO;
//import com.sistema.estudiantes.model.DisciplinasAdm;
//import com.sistema.estudiantes.model.Professor;
//import com.sistema.estudiantes.model.Turma;
//import com.sistema.estudiantes.model.TurmaAdm;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/DisciplinaAdminServlet")
//public class DisciplinaAdminServlet extends HttpServlet {
//    private DisciplinaAdmDAO dao = new DisciplinaAdmDAO();
//    private ProfessorAdmDAO professorDao = new ProfessorAdmDAO();
//    private TurmaAdmDAO turmaDao = new TurmaAdmDAO();
//
//
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // 1. Carregar dados auxiliares (Professores e Turmas)
//        List<TurmaAdm> listaTurmas = turmaDao.listarTodas();
//        request.setAttribute("listaTurmasAuto", listaTurmas);
//        request.setAttribute("listaProfessoresAuto", professorDao.listarComTudo());
//        request.setAttribute("listaTurmasAuto", turmaDao.listarTodas());
//
//        String acao = request.getParameter("acao");
//        String idParam = request.getParameter("id");
//
//        // 2. Lógica AJAX (Verificação)
//        if ("verificarProfessor".equals(acao)) {
//            String nomeVerificar = request.getParameter("nome");
//            boolean existe = dao.professorExisteNoBancoReal(nomeVerificar);
//            response.setContentType("application/json");
//            response.getWriter().write("{\"existe\": " + existe + "}");
//            return;
//        }
//
//        // 3. Carregar Disciplina para Edição/Exclusão
//        if (idParam != null && ("editar".equals(acao) || "pre-excluir".equals(acao))) {
//            request.setAttribute("disciplinaEditar", dao.buscarPorId(Integer.parseInt(idParam)));
//        }
//
//        // 4. GARANTIR A LISTAGEM (O ponto onde você dizia que não aparecia)
//        List<DisciplinasAdm> lista = dao.listarTodasComRelacionamentos();
//
//        // Log para depuração (se aparecer no console do servidor, o banco está ok)
//        System.out.println("Disciplinas encontradas: " + (lista != null ? lista.size() : "null"));
//
//        request.setAttribute("listaDisciplinas", lista);
//        request.getRequestDispatcher("/views/disciplinas_a.jsp").forward(request, response);
//    }
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        String acao = request.getParameter("acao");
//
//        // 1. Lógica AJAX: Criar Professor e Usuário "na hora"
//        if ("criarProfessorRapido".equals(acao)) {
//            String nome = request.getParameter("nome");
//            String sobrenome = request.getParameter("sobrenome");
//            String email = request.getParameter("email");
//
//            dao.criarProfessorCompleto(nome, sobrenome, email);
//            response.setStatus(HttpServletResponse.SC_OK);
//            return;
//        }
//
//        // 2. Lógica Padrão: Salvar, Editar ou Excluir Disciplina
//        if ("excluir".equals(acao)) {
//            String idStr = request.getParameter("id");
//            if (idStr != null) dao.excluir(Integer.parseInt(idStr));
//        } else if ("novo".equals(acao) || "editar".equals(acao)) {
//            DisciplinasAdm d = new DisciplinasAdm();
//            if ("editar".equals(acao)) {
//                d.setId(Integer.parseInt(request.getParameter("id")));
//            }
//
//            d.setNome(request.getParameter("nome"));
//            d.setCargaHoraria(Integer.parseInt(request.getParameter("cargaHoraria")));
//            d.setProfessorNome(request.getParameter("professorNome"));
//            d.setTurmaNome(request.getParameter("turmaNome"));
//
//            dao.salvar(d, acao);
//        }
//
//        response.sendRedirect("DisciplinaAdminServlet");
//    }
//}