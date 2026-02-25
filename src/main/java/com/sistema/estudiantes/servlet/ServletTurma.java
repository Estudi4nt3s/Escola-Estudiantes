package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.TurmaDAO;
import com.sistema.estudiantes.model.Turma;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/turma")
public class ServletTurma extends HttpServlet {

    private final TurmaDAO turmaDAO = new TurmaDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String acao = null;
        String subAcao = request.getParameter("sub_acao");

        switch (subAcao != null ? subAcao : "") {
            case "buscar_todos":
                buscarTodos(request, response, acao, subAcao);
                break;
            case "":
                buscarTodos(request, response, acao, subAcao);
                break;
            default:
                break;
        }
    }

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao)
            throws ServletException, IOException {
        try {
            preencherTabela(request);

            encaminhar(request, response, "views/turmas.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherTabela(HttpServletRequest request) {
        List<Turma> turmas = turmaDAO.listar();
        request.setAttribute("turmas", turmas);
    }

    protected void encaminhar(HttpServletRequest request, HttpServletResponse response, String jsp)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        if (rd != null) {
            rd.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Não foi possível direcionar a resposta.");
        }
    }
}