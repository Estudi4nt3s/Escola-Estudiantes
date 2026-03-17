package com.sistema.estudiantes.servlet;

import java.io.*;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import com.sistema.estudiantes.dao.*;
import com.sistema.estudiantes.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "servletLogin", value = "/servletLogin")
public class ServletLogin extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Pegando os Parâmetros
        String email = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        senha = (senha != null) ? senha.strip() : "";
        email = (email != null) ? email.strip() : "";

        System.out.println("Tentativa de login: " + email);


        UsuarioDAO userDAO = new UsuarioDAO();
        List<Usuario> users = userDAO.listarComFiltro("email = ?", email);
        System.out.println("Usuarios encontrados: " + users.size());

        if (!users.isEmpty()) {
            System.out.println("Entrou");
            Usuario usuarioLogado = users.getFirst();
            boolean validarSenha = usuarioLogado.getSenha().equals(senha);
            System.out.println("Senha válida" + validarSenha);

            if (validarSenha) {
                System.out.println("Sai bixo");
                // Configuração de Data para a Sessão
                LocalDate hoje = LocalDate.now();
                String dia = String.format("%02d", hoje.getDayOfMonth());
                String mes = String.format("%02d", hoje.getMonthValue());
                Locale ptBr = new Locale("pt", "BR");
                String semana = hoje.getDayOfWeek().getDisplayName(TextStyle.SHORT, ptBr).toUpperCase().substring(0, 3);
                String[] data = {dia, mes, semana};

                request.getSession().setAttribute("usuario", usuarioLogado);
                request.getSession().setAttribute("data", data);

                String regexFuncionario = "^[a-zA-Z]+\\.[a-zA-Z]+$";
                boolean ehProfessor = email.matches(regexFuncionario);

                if (ehProfessor) {
                    System.out.println("É tetra");
                    ProfessorDAO profDAO = new ProfessorDAO();
                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
                    AulaDAO aulaDAO = new AulaDAO();
                    TurmaDAO turmaDAO = new TurmaDAO();

                    // Busca na tabela Professor usando usuarioid
                    System.out.println("usu");
                    List<Professor> profs = profDAO.listarFiltro(usuarioLogado.getId());

                    if (!profs.isEmpty()) {
                        System.out.println("Java fudido");
                        Professor prof = profs.getFirst();
                        Disciplina disc = disciplinaDAO.buscarComFiltro("id", String.valueOf(prof.getDisciplina().getId()));
                        List<Aula> aulas = aulaDAO.listarPorProfessor(prof.getId(), semana);
                        List<Turma> turmas = turmaDAO.listar();

                        request.getSession().setAttribute("professor", prof);
                        request.getSession().setAttribute("disciplina", disc);
                        request.getSession().setAttribute("aulas", aulas);
                        request.getSession().setAttribute("turmas", turmas);

                        request.getRequestDispatcher("views/home_p.jsp").forward(request, response);
                        return;
                    }
                    else{
                        request.setAttribute("erro", "Usuário não encontrado");
                    }
                } else {
                    AlunoDAO alunoDAO = new AlunoDAO();
                    NotaDAO notaDAO = new NotaDAO();
                    TurmaDAO turmaDAO = new TurmaDAO();
                    AulaDAO aulaDAO = new AulaDAO();
                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
                    ProfessorDAO profDAO = new ProfessorDAO();
                    ObservacaoDAO observacaoDAO = new ObservacaoDAO();

                    System.out.println("Buscando aluno com usuarioid: " + usuarioLogado.getId());
                    List<Aluno> alunos = alunoDAO.listarUsuarioAluno(usuarioLogado.getId());
                    System.out.println("Alunos encontrados: " + alunos.size());

                    if (!alunos.isEmpty()) {
                        Aluno aluno = alunos.getFirst();
                        System.out.println("Aluno: " + aluno.getNome() + " | TurmaId: " + aluno.getTurmaId());

                        List<Turma> turmas = turmaDAO.listarComFiltro("id = ?", aluno.getTurmaId());
                        System.out.println("Turmas: " + turmas.size());

                        List<Aula> aulas = aulaDAO.listarPorTurma(semana, aluno.getTurmaId());
                        System.out.println("Aulas: " + aulas.size());

                        List<Disciplina> todasDisciplinas = disciplinaDAO.listar();
                        System.out.println("Disciplinas: " + todasDisciplinas.size());

                        List<Nota> notas = notaDAO.listarComFiltro("alunomatricula = ?", aluno.getMatricula());
                        System.out.println("Notas: " + notas.size());

                        List<Observacao> observacoes = observacaoDAO.listarDisciplina(aluno.getMatricula());
                        System.out.println("Observacoes: " + observacoes.size());

                        int qtdMateria = 0;
                        String[] materia = new String[6];

                        int limite = Math.min(aulas.size(), 6);
                        for (int i = 0; i < limite; i++) {
                            Integer profId = aulas.get(i).getProfessorId().getId();
                            Professor p = profDAO.buscarPorId(profId);
                            Integer discId = (p != null && p.getDisciplina() != null)
                                    ? p.getDisciplina().getId()
                                    : null;

                            for (Disciplina d : todasDisciplinas) {
                                if (d.getId() == discId) {
                                    String nomeDisc = d.getNome();
                                    String nfd = Normalizer.normalize(nomeDisc, Normalizer.Form.NFD);
                                    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                                    materia[i] = d.getNome();
                                            //pattern.matcher(nfd).replaceAll("");
                                    qtdMateria++;
                                    break;
                                }
                            }
                        }

                        request.getSession().setAttribute("aluno", aluno);
                        request.getSession().setAttribute("aulas",aulas);
                        request.getSession().setAttribute("turma", !turmas.isEmpty() ? turmas.getFirst() : null);
                        request.getSession().setAttribute("materia", materia);
                        request.getSession().setAttribute("notas", notas);
                        request.getSession().setAttribute("qtdMateria", qtdMateria);
                        request.getSession().setAttribute("disciplinas", todasDisciplinas);
                        request.getSession().setAttribute("observacoes", observacoes);

                        request.getRequestDispatcher("views/home.jsp").forward(request, response);
                        return;
                    }
                    else{
                        request.setAttribute("erro", "Usuário não encontrado");
                    }
                }
            } else {
                System.out.println("Chegou");
                request.setAttribute("erro", "Senha Incorreta");
            }
        } else {
            System.out.println("Não encontrou");
            request.setAttribute("erro", "Usuário não encontrado");
        }
        request.setAttribute("erro", "Usuário não encontrado");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}