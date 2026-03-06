package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.ProfessorAdmDAO;
import com.sistema.estudiantes.model.Professor;
import com.sistema.estudiantes.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProfessorAdminServlet")
public class ProfessorAdminServlet extends HttpServlet {
    private ProfessorAdmDAO dao = new ProfessorAdmDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        // Busca o professor específico para preencher o Modal de Editar ou Excluir
        if ("editar".equals(acao) || "pre-excluir".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                List<Professor> resultado = dao.listarComFiltro(id);
                if (!resultado.isEmpty()) {
                    // Atributo em singular para bater com o JSP
                    request.setAttribute("professorEditar", resultado.get(0));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Lista principal da tabela
        List<Professor> lista = dao.listarComUsuarios();
        request.setAttribute("listaProfessores", lista);

        // Caminho para o seu arquivo plural conforme confirmado
        request.getRequestDispatcher("/views/professores_a.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");

        if ("cadastrar".equals(acao)) {
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            boolean sucesso = dao.cadastrarCompleto(nome, sobrenome, email, senha);
            response.sendRedirect("ProfessorAdminServlet?status=" + (sucesso ? "success" : "error"));

        } else if ("excluir".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.excluir(id);
                response.sendRedirect("ProfessorAdminServlet?status=deleted");
            } catch (Exception e) {
                response.sendRedirect("ProfessorAdminServlet?status=error");
            }

        } else if ("editar".equals(acao)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String senha = request.getParameter("senha"); // O DAO tratará se estiver vazia

                // Chama o método de atualização em duas tabelas (Transação)
                boolean sucesso = dao.atualizarProfessorCompleto(id, usuarioId, nome, email, senha);

                if (sucesso) {
                    response.sendRedirect("ProfessorAdminServlet?status=editado");
                } else {
                    response.sendRedirect("ProfessorAdminServlet?status=erro_edit");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("ProfessorAdminServlet?status=error");
            }
        }
    }
}