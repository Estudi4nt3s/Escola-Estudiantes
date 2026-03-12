package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.*;
import com.sistema.estudiantes.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/observacao")
public class ServletObservacao extends HttpServlet {

    private final ObservacaoDAO observacaoDAO = new ObservacaoDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final ProfessorDAO professorDAO = new ProfessorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String subAcao = request.getParameter("sub_acao");

        if ("buscar_todos".equals(subAcao)) {
            buscarTodos(request, response);
        } else if ("buscar_por_id".equals(subAcao)) {
            buscarPorId(request, response);
        } else if ("excluir".equals(subAcao)) {
            excluir(request, response);
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
        } else {
            response.sendRedirect(request.getContextPath() + "/observacao?sub_acao=buscar_todos");
        }
    }

    private void buscarTodos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Observacao> observacoes = observacaoDAO.listar();
        List<Aluno> alunos = alunoDAO.listar();
        List<Professor> professores = professorDAO.listar();

        if(observacoes == null){
            observacoes = new ArrayList<>();
        }

        request.setAttribute("observacoes", observacoes);
        request.setAttribute("alunoSelecionado", alunos);
        request.setAttribute("professores", professores);

        encaminhar(request, response, "/views/observacoes.jsp");
    }

    private void buscarPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int alunoId = Integer.parseInt(request.getParameter("id"));

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        int professorId = usuario.getId();

        List<Observacao> observacoesAluno = observacaoDAO.listarComFiltro("o.alunomatricula = ?", alunoId);

        List<Observacao> filtradas = new ArrayList<>();

        if (observacoesAluno != null) {
            for (Observacao o : observacoesAluno) {
                if (o.getIdProfessor().getId() == professorId) {
                    filtradas.add(o);
                }
            }
        }

        List<Aluno> alunos = alunoDAO.listarMatricula(alunoId);
        Aluno aluno = null;

        if(alunos != null && !alunos.isEmpty()){
            aluno = alunos.get(0);
        }

        request.setAttribute("observacoes", filtradas);
        request.getSession().setAttribute("alunoSelecionado", aluno);

        encaminhar(request, response, "/views/observacoes.jsp");

    }

    private void inserir(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String texto = request.getParameter("texto");
        int alunoId = Integer.parseInt(request.getParameter("alunomatricula"));

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        int professorId = usuario.getId();

        Observacao observacao = new Observacao();

        observacao.setTexto(texto);
        observacao.setDataCriacao(LocalDate.now());
        observacao.setIdAluno(new Aluno(alunoId));
        observacao.setIdProfessor(new Professor(professorId));

        observacaoDAO.inserir(observacao);

        response.sendRedirect(request.getContextPath() + "/observacao?sub_acao=buscar_por_id&id=" + alunoId);
    }

    private void atualizar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String texto = request.getParameter("texto");

        LocalDate dataCriacao = LocalDate.now();

        Observacao observacao = new Observacao(id, texto, dataCriacao);

        observacaoDAO.atualizar(observacao);

        List<Observacao> lista = observacaoDAO.listarComFiltro("o.id = ?", id);
        Observacao obs = lista.get(0);

        int alunoId = obs.getIdAluno().getMatricula();

        response.sendRedirect(request.getContextPath() + "/observacao?sub_acao=buscar_por_id&id=" + alunoId);
    }

    private void excluir(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        List<Observacao> lista = observacaoDAO.listarComFiltro("o.id = ?", id);
        Observacao obs = lista.get(0);

        int alunoId = obs.getIdAluno().getMatricula();

        observacaoDAO.excluir(id);

        response.sendRedirect(request.getContextPath() + "/observacao?sub_acao=buscar_por_id&id=" + alunoId);
    }

    private void encaminhar(HttpServletRequest request, HttpServletResponse response, String jsp)
            throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        rd.forward(request, response);
    }
}