//package com.sistema.estudiantes.servlet;
//
//import com.sistema.estudiantes.dao.ProfessorAdmDAO;
//import com.sistema.estudiantes.dao.DisciplinaAdmDAO;
//import com.sistema.estudiantes.model.Professor;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//import java.io.IOException;
//
//@WebServlet("/ProfessorAdminServlet")
//public class ProfessorAdminServlet extends HttpServlet {
//    private ProfessorAdmDAO dao = new ProfessorAdmDAO();
//    private DisciplinaAdmDAO discDao = new DisciplinaAdmDAO();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        DisciplinaAdmDAO discDao = new DisciplinaAdmDAO();
//        ProfessorAdmDAO profDao = new ProfessorAdmDAO();
//
//        // Busca a lista para o Select do Modal
//        request.setAttribute("listaDisciplinas", discDao.listar());
//
//        // Busca os professores para a tabela
//        request.setAttribute("listaProfessores", profDao.listarComTudo());
//
//        request.getRequestDispatcher("/views/professores_a.jsp").forward(request, response);
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String acao = request.getParameter("acao");
//        if ("novo".equals(acao) || "editar".equals(acao)) {
//            // Lógica de salvar/editar professor
//            String nome = request.getParameter("nome");
//            String sobrenome = request.getParameter("sobrenome");
//            String email = request.getParameter("email");
//            int discId = Integer.parseInt(request.getParameter("disciplinaId"));
//
//            // Chame aqui o seu método de salvar no DAO
//            response.sendRedirect("ProfessorAdminServlet");
//        } else if ("excluir".equals(acao)) {
//            dao.excluir(Integer.parseInt(request.getParameter("id")));
//            response.sendRedirect("ProfessorAdminServlet");
//        }
//    }
//}