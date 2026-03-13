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
        String idParam = request.getParameter("id"); // Pegamos o ID como String primeiro

        // Só tentamos buscar a turma se a ação for de editar/excluir E o ID não for nulo
        if (idParam != null && !idParam.trim().isEmpty()) {
            if ("editar".equals(acao) || "pre-excluir".equals(acao)) {
                try {
                    int id = Integer.parseInt(idParam);
                    TurmaAdm turma = dao.buscarPorId(id);
                    request.setAttribute("turmaEditar", turma);
                } catch (NumberFormatException e) {
                    // Se o ID não for um número válido, apenas ignora
                    System.out.println("Erro ao converter ID: " + idParam);
                }
            }
        }

        // Busca a lista e manda para o JSP
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
                dao.excluir(Integer.parseInt(request.getParameter("id")));
            }else if ("novo".equals(acao) || "editar".equals(acao)) {
            TurmaAdm t = new TurmaAdm();
            if ("editar".equals(acao)) t.setId(Integer.parseInt(request.getParameter("id")));

            t.setNome(request.getParameter("nome"));
            t.setAno(Integer.parseInt(request.getParameter("ano")));

            // REMOVIDO: Não pegamos mais a quantidade do request.
            // O banco calcula isso sozinho na listagem.

            dao.salvar(t, acao);
        }
            // Se chegou aqui, deu certo
            request.getSession().setAttribute("mensagemSucesso", "Operação realizada com sucesso!");

        } catch (Exception e) {
            // Se deu erro, guarda a mensagem para mostrar no JSP
            request.getSession().setAttribute("mensagemErro", "Não foi possível realizar a operação: " + e.getMessage());
            e.printStackTrace();
        }

        response.sendRedirect("TurmaAdmServlet");
    }
}