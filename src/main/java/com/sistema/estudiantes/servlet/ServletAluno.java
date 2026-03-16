package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AlunoDAO;
import com.sistema.estudiantes.dao.TurmaDAO;
import com.sistema.estudiantes.dao.UsuarioDAO;
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

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

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
        AlunoDAO alunoDAO = new AlunoDAO();

        List<Aluno> alunosDaTurma = alunoDAO.listarPorTurma(id);
        request.setAttribute("alunos", alunosDaTurma);

        TurmaDAO turmaDAO = new TurmaDAO();
        List<Turma> turmas = turmaDAO.listarComFiltro("id = ?", id);
        Turma turma = (turmas != null && !turmas.isEmpty()) ? turmas.get(0) : null;

        request.setAttribute("turmaSelecionada", turma);
        request.getSession().setAttribute("turmaSelecionada", turma);
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