package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.ProfessorAdmDAO;
import com.sistema.estudiantes.dao.DisciplinaAdmDAO;
import com.sistema.estudiantes.model.Disciplina;
import com.sistema.estudiantes.model.Professor;
import com.sistema.estudiantes.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Random;

@WebServlet("/ProfessorAdminServlet")
public class ProfessorAdminServlet extends HttpServlet {
    private ProfessorAdmDAO dao = new ProfessorAdmDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        DisciplinaAdmDAO discDao = new DisciplinaAdmDAO();

        // 1. Carrega os dados básicos que a tabela sempre precisa
        request.setAttribute("listaProfessores", dao.listarComTudo());
        request.setAttribute("listaDisciplinas", discDao.listarTodasComRelacionamentos());

        // 2. Lógica para Ações Específicas
        if ("novo".equals(acao)) {
            request.setAttribute("acao", "novo");
        } else if ("editar".equals(acao) || "pre-excluir".equals(acao)) {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Professor p = dao.buscarPorId(id); // Agora que mudamos para LEFT JOIN no DAO, vai retornar o objeto!
                request.setAttribute("professorEditar", p);
                request.setAttribute("acao", acao); // Ex: "editar" ou "pre-excluir"
            }
        }

        // 3. Lógica de vínculo vindo da página de disciplinas
        String idDiscVinculo = request.getParameter("idDisciplina");
        if (idDiscVinculo != null) {
            request.setAttribute("idDisciplinaVinculo", idDiscVinculo);
        }

        // 4. Único encaminhamento (Forward) para a página
        request.getRequestDispatcher("/views/professores_a.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");

        if ("cadastrar".equals(acao) || "novo".equals(acao)) {
            Professor p = new Professor();
            p.setNome(request.getParameter("nome"));

            // Disciplina
            String idDisciplina = request.getParameter("disciplinaId");
            if (idDisciplina != null && !idDisciplina.isEmpty()) {
                Disciplina d = new Disciplina();
                d.setId(Integer.parseInt(idDisciplina));
                p.setDisciplina(d);
            }

            // Gera e salva com senha
            String email = request.getParameter("email");
            String senha = gerarSenha(7);

            // Aqui está a chamada para o DAO que criamos
            dao.cadastrarComSenha(p, email, senha);

        } else if ("editar".equals(acao)) {
            // Lógica de edição que você já tinha
            Professor p = new Professor();
            p.setId(Integer.parseInt(request.getParameter("id")));
            p.setNome(request.getParameter("nome"));

            String idDisciplina = request.getParameter("disciplinaId");
            if (idDisciplina != null && !idDisciplina.isEmpty()) {
                Disciplina d = new Disciplina();
                d.setId(Integer.parseInt(idDisciplina));
                p.setDisciplina(d);
            }
            dao.salvar(p, acao);

        } else if ("excluir".equals(acao)) {
            String idStr = request.getParameter("id");
            if (idStr != null) dao.excluir(Integer.parseInt(idStr));
        }

        response.sendRedirect("ProfessorAdminServlet");
    }

    private String gerarSenha(int tamanho) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        java.util.Random rnd = new java.util.Random();
        StringBuilder sb = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++)
            sb.append(caracteres.charAt(rnd.nextInt(caracteres.length())));
        return sb.toString();
    }
}