package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.TurmaAdmDAO;
import com.sistema.estudiantes.model.TurmaAdm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/TurmaAdmServlet")
public class TurmaAdmServlet extends HttpServlet {
    private TurmaAdmDAO dao = new TurmaAdmDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.trim().isEmpty()) {
            if ("editar".equals(acao) || "pre-excluir".equals(acao)) {
                try {
                    int id = Integer.parseInt(idParam);
                    TurmaAdm turma = dao.buscarPorId(id);
                    request.setAttribute("turmaEditar", turma);
                } catch (NumberFormatException e) {
                    System.out.println("Erro ao converter ID: " + idParam);
                }
            }
        }

        List<TurmaAdm> lista = dao.listarTodas();
        request.setAttribute("listaTurmas", lista);
        request.getRequestDispatcher("/views/turmas_a.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");

        try {
            if ("excluir".equals(acao)) {
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    dao.excluir(Integer.parseInt(idStr));
                }
            } else if ("novo".equals(acao) || "editar".equals(acao)) {
                TurmaAdm t = new TurmaAdm();

                // Trata o ID apenas para edição
                if ("editar".equals(acao)) {
                    String idStr = request.getParameter("id");
                    if (idStr != null && !idStr.isEmpty()) {
                        t.setId(Integer.parseInt(idStr));
                    }
                }

                t.setNome(request.getParameter("nome"));

                // Trata o Ano vindo do formulário
                String anoStr = request.getParameter("ano");
                t.setAno(anoStr != null && !anoStr.isEmpty() ? Integer.parseInt(anoStr) : 0);

                dao.salvar(t, acao);
            }

            // Sucesso
            request.getSession().setAttribute("mensagemSucesso", "Operação realizada com sucesso!");

        } catch (Exception e) {
            // Erro
            request.getSession().setAttribute("mensagemErro", "Erro: " + e.getMessage());
            e.printStackTrace();
        }

        response.sendRedirect("TurmaAdmServlet");
    }
}