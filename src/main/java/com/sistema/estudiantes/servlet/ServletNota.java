package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.NotaDAO;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Disciplina;
import com.sistema.estudiantes.model.Nota;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/nota")
public class ServletNota extends HttpServlet {

    private final NotaDAO notaDAO = new NotaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String subAcao = request.getParameter("sub_acao");

        if ("buscar_todos".equals(subAcao)) {
            buscarTodos(request, response);
        } else if ("buscar_por_id".equals(subAcao)) {
            buscarPorId(request, response);
        } else {
            buscarTodos(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String subAcao = request.getParameter("sub_acao");

        if ("inserir".equals(subAcao)) {
            inserir(request, response);
        } else if ("atualizar".equals(subAcao)) {
            atualizar(request, response);
        } else if ("excluir".equals(subAcao)) {
            excluir(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/nota?sub_acao=buscar_todos");
        }
    }

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Nota> notas = notaDAO.listar();
        request.setAttribute("notas", notas);

        encaminhar(request, response, "/views/notas.jsp");
    }

    private void buscarPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/nota?sub_acao=buscar_todos");
            return;
        }

        int id = Integer.parseInt(idStr);
        List<Nota> lista = notaDAO.listarComFiltro("id=?", id);
        Nota nota = lista.isEmpty() ? null : lista.get(0);

        request.setAttribute("nota", nota);
        encaminhar(request, response, "/views/notas.jsp");
    }

    private void inserir(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int disciplinaId = Integer.parseInt(request.getParameter("disciplinaid"));
        int alunoId = Integer.parseInt(request.getParameter("idaluno"));
        double valor = Double.parseDouble(request.getParameter("valor"));

        Nota nota = new Nota();
        Disciplina d = new Disciplina(disciplinaId);
        Aluno a = new Aluno(alunoId);

        nota.setIdDisciplina(d);
        nota.setIdAluno(a);
        nota.setN1(valor);
        nota.setN2(valor);

        notaDAO.inserir(nota);

        response.sendRedirect(request.getContextPath() + "/nota?sub_acao=buscar_todos");
    }

    private void atualizar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        List<Nota> lista = notaDAO.listarComFiltro("id=?", id);
        Nota nota = lista.isEmpty() ? null : lista.get(0);

        if (nota != null) {

            // atualiza só os campos permitidos
            if (request.getParameter("valor") != null) {
                nota.setN1(Double.parseDouble(request.getParameter("valor")));
                nota.setN2(Double.parseDouble(request.getParameter("valor")));
            }

            notaDAO.atualizar(nota);
        }

        response.sendRedirect(request.getContextPath() + "/nota?sub_acao=buscar_todos");
    }

    private void excluir(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        notaDAO.excluir(id);

        response.sendRedirect(request.getContextPath() + "/nota?sub_acao=buscar_todos");
    }

    private void encaminhar(HttpServletRequest request, HttpServletResponse response, String jsp)
            throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        rd.forward(request, response);
    }
}