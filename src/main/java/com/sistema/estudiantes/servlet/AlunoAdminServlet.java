package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AlunoAdminDAO;
import com.sistema.estudiantes.dao.TurmaAdmDAO;
import com.sistema.estudiantes.model.Aluno;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/AlunoAdminServlet")
public class AlunoAdminServlet extends HttpServlet {

    private AlunoAdminDAO dao = new AlunoAdminDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if ("editar".equals(acao) || "pre-excluir".equals(acao)) {
            String matriculaStr = request.getParameter("matricula");
            if (matriculaStr != null) {
                int matricula = Integer.parseInt(matriculaStr);
                Aluno aluno = dao.buscarPorMatricula(matricula);
                request.setAttribute("alunoEditar", aluno);
            }
        }

        List<Aluno> lista = dao.listarTodos();
        request.setAttribute("listaAlunos", lista);
        TurmaAdmDAO turmaDao = new TurmaAdmDAO();
        request.setAttribute("listaTurmas", turmaDao.listarTodas());

        RequestDispatcher rd = request.getRequestDispatcher("/views/aluno_a.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");

        if ("excluir".equals(acao)) {
            int matricula = Integer.parseInt(request.getParameter("matricula"));
            dao.excluir(matricula);
        }
        else if ("novo".equals(acao) || "editar".equals(acao)) {
            Aluno aluno = new Aluno();

            if ("editar".equals(acao)) {
                aluno.setMatricula(Integer.parseInt(request.getParameter("matricula")));
            }

            aluno.setNome(request.getParameter("nome"));
            aluno.setCpf(request.getParameter("cpf"));
            aluno.setDataNascimento(LocalDate.parse(request.getParameter("dataNascimento")));
            aluno.setTelefonePai(request.getParameter("telefonePai"));
            String turmaIdStr = request.getParameter("turmaId");
            if (turmaIdStr != null && !turmaIdStr.isEmpty()) {
                aluno.setTurmaId(Integer.parseInt(turmaIdStr));
            } else {
                aluno.setTurmaId(1);
            }
            dao.salvar(aluno, acao);
        }

        response.sendRedirect(request.getContextPath() + "/AlunoAdminServlet");
    }
}