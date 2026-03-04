package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AlunoDAO;
import com.sistema.estudiantes.dao.TurmaDAO;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Turma;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/aluno")
public class ServletAluno extends HttpServlet {

    private final AlunoDAO alunoDAO = new AlunoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String acao = null;
        String subAcao = request.getParameter("sub_acao");
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);

        switch (subAcao != null ? subAcao : "") {
            case "buscar_todos":
                buscarTodos(request, response, acao, subAcao, id);
                break;
            case "":
                buscarTodos(request, response, acao, subAcao, id);
                break;
            default:
                break;
        }
    }

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response, String acao, String subAcao, int id)
            throws ServletException, IOException {
        try {
            preencherTabela(request, id);

            encaminhar(request, response, "views/alunos.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherTabela(HttpServletRequest request, int id) {
        List<Aluno> alunos = alunoDAO.listarPorTurma(id);
        request.setAttribute("alunos", alunos);

        TurmaDAO turmaDAO = new TurmaDAO();

        List<Turma> turmas = turmaDAO.listarComFiltro("id = ?", id);

        Turma turma = null;
        if (turmas != null && !turmas.isEmpty()) {
            turma = turmas.get(0);
        }

        request.setAttribute("turmaSelecionada", turma);
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