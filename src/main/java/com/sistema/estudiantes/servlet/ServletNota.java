package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AlunoDAO;
import com.sistema.estudiantes.dao.DisciplinaDAO;
import com.sistema.estudiantes.dao.NotaDAO;
import com.sistema.estudiantes.dao.ObservacaoDAO;
import com.sistema.estudiantes.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/nota")
public class ServletNota extends HttpServlet {

    private final NotaDAO notaDAO = new NotaDAO();
    private final ObservacaoDAO observacaoDAO = new ObservacaoDAO();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        List<Disciplina> todasDisciplinas = disciplinaDAO.listar();
        List<Observacao> observacoes = observacaoDAO.listar();

        if(observacoes == null){
            observacoes = new ArrayList<>();
        }

        request.getSession().setAttribute("notas", notas);
        request.getSession().setAttribute("disciplinas", todasDisciplinas);
        request.getSession().setAttribute("observacoes", observacoes);

        encaminhar(request, response, "/views/notas.jsp");
    }

    private void buscarPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("professor") != null) {
            int alunoId = Integer.parseInt(request.getParameter("id"));

            List<Nota> notasDoAluno = notaDAO.listarComFiltro("alunomatricula = ?", alunoId);
            List<Disciplina> todasDisciplinas = disciplinaDAO.listar();
            List<Observacao> observacoes = observacaoDAO.listarComFiltro("alunomatricula = ?", alunoId);
            List<Aluno> alunos = alunoDAO.listarMatricula(alunoId);

            Professor professor = (Professor) request.getSession().getAttribute("professor");
            int professorId = professor.getId();

            List<Observacao> filtradas = new ArrayList<>();

            if(observacoes != null){
                for (Observacao o : observacoes) {
                    if (o.getIdProfessor().getId() == professorId) {
                        filtradas.add(o);
                    }
                }
            }

            request.getSession().setAttribute("notas", notasDoAluno);
            request.getSession().setAttribute("disciplinas", todasDisciplinas);
            request.getSession().setAttribute("observacoes", filtradas);
            request.getSession().setAttribute("alunoSelecionado", alunos.getFirst());
            encaminhar(request, response, "/views/notas.jsp");
        } else {
            int alunoId = Integer.parseInt(request.getParameter("id"));

            List<Nota> notasDoAluno = notaDAO.listarComFiltro("alunomatricula = ?", alunoId);
            List<Disciplina> todasDisciplinas = disciplinaDAO.listar();
            List<Observacao> observacoes = observacaoDAO.listarDisciplina(alunoId);
            List<Aluno> alunos = alunoDAO.listarMatricula(alunoId);


            request.getSession().setAttribute("notas", notasDoAluno);
            request.getSession().setAttribute("disciplinas", todasDisciplinas);
            request.getSession().setAttribute("observacoes", observacoes);
            request.getSession().setAttribute("alunoSelecionado", alunos.getFirst());
            encaminhar(request, response, "/views/aluno.jsp");
        }
    }

    private void inserir(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int disciplinaId = Integer.parseInt(request.getParameter("disciplinaid"));
        int alunoId = Integer.parseInt(request.getParameter("idaluno"));
        double n1 = Double.parseDouble(request.getParameter("n1"));
        double n2 = Double.parseDouble(request.getParameter("n2"));

        Nota nota = new Nota();
        Disciplina d = new Disciplina(disciplinaId);
        Aluno a = new Aluno(alunoId);

        nota.setIdDisciplina(d);
        nota.setIdAluno(a);
        nota.setN1(n1);
        nota.setN2(n2);

        notaDAO.inserir(nota);

        response.sendRedirect(request.getContextPath() + "/nota?sub_acao=buscar_todos");
    }

    private void atualizar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        List<Nota> lista = notaDAO.listarComFiltro("id=?", id);
        Nota nota = lista.isEmpty() ? null : lista.get(0);

        if (nota != null) {

            String campo = request.getParameter("campo");
            Double valor = request.getParameter("valor").equals("null")?null:Double.parseDouble(request.getParameter("valor"));
            System.out.println(valor);

            if ("n1".equals(campo)) {
                nota.setN1(valor);
            }
            else if ("n2".equals(campo)) {
                nota.setN2(valor);
            }
            System.out.println("Nota N2: " + nota.getN2());
            if(!notaDAO.atualizar(nota)){
                notaDAO.inserir(nota);
            }

            int alunoId = nota.getIdAluno().getMatricula();

            response.sendRedirect(request.getContextPath() + "/nota?sub_acao=buscar_por_id&id=" + alunoId);
        }
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