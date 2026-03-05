package com.sistema.estudiantes.servlet;

import java.io.*;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.TextStyle;
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

        if (!users.isEmpty()) {
            Usuario usuarioLogado = users.getFirst();
            boolean validarSenha = usuarioLogado.getSenha().equals(senha);

            if (validarSenha) {
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
                    ProfessorDAO profDAO = new ProfessorDAO();
                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
                    AulaDAO aulaDAO = new AulaDAO();
                    TurmaDAO turmaDAO = new TurmaDAO();

                    // Busca na tabela Professor usando usuarioid
                    List<Professor> profs = profDAO.listarComFiltro(usuarioLogado.getId());

                    if (!profs.isEmpty()) {
                        Professor prof = profs.getFirst();
                        // Busca disciplina usando o disciplinaid da tabela Professor
                        Disciplina disc = disciplinaDAO.buscarComFiltro("id", String.valueOf(prof.getDisciplinaId().getId()));
                        // Busca aulas usando o professorid (como está no seu banco)
                        List<Aula> aulas = aulaDAO.listarComFiltro("professorid = ? AND diasemana = ? order by horarioinicio", prof.getId(), semana);
                        List<Turma> turmas = turmaDAO.listar();

                        request.getSession().setAttribute("professor", prof);
                        request.getSession().setAttribute("disciplina", disc);
                        request.getSession().setAttribute("aulas", aulas);
                        request.getSession().setAttribute("turmas", turmas);

                        request.getRequestDispatcher("views/home_p.jsp").forward(request, response);
                        return;
                    }
                } else {
                    AlunoDAO alunoDAO = new AlunoDAO();
                    NotaDAO notaDAO = new NotaDAO();
                    TurmaDAO turmaDAO = new TurmaDAO();
                    AulaDAO aulaDAO = new AulaDAO();
                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
                    ProfessorDAO profDAO = new ProfessorDAO();

                    List<Aluno> alunos = alunoDAO.listarComFiltro(usuarioLogado.getId());

                    if (!alunos.isEmpty()) {
                        Aluno aluno = alunos.getFirst();
                        List<Turma> turmas = turmaDAO.listarComFiltro("id = ?", String.valueOf(aluno.getTurmaId()));
                        List<Aula> aulas = aulaDAO.listarComFiltro("turmaid = ? AND diasemana = ? order by horarioinicio", aluno.getTurmaId(), semana);
                        List<Disciplina> todasDisciplinas = disciplinaDAO.listar();
                        List<Nota> notas = notaDAO.listarComFiltro("idaluno = ?", String.valueOf(aluno.getMatricula()));

                        int qtdMateria = 0;
                        String[] materia = new String[6];

                        int limite = Math.min(aulas.size(), 6);
                        for (int i = 0; i < limite; i++) {
                            int profId = aulas.get(i).getProfessorId().getId();
                            Professor p = profDAO.buscarPorId(profId);
                            int discId = p.getDisciplinaId().getId();

                            for (Disciplina d : todasDisciplinas) {
                                if (d.getId() == discId) {
                                    String nomeDisc = d.getNome();
                                    String nfd = Normalizer.normalize(nomeDisc, Normalizer.Form.NFD);
                                    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                                    materia[i] = pattern.matcher(nfd).replaceAll("");
                                    qtdMateria++;
                                    break;
                                }
                            }
                        }

                        request.getSession().setAttribute("aluno", aluno);
                        request.getSession().setAttribute("turma", !turmas.isEmpty() ? turmas.getFirst() : null);
                        request.getSession().setAttribute("media", notaDAO.Media(aluno.getMatricula()));
                        request.getSession().setAttribute("materia", materia);
                        request.getSession().setAttribute("notas", notas);
                        request.getSession().setAttribute("qtdMateria", qtdMateria);
                        request.getSession().setAttribute("disciplinas", todasDisciplinas);

                        request.getRequestDispatcher("views/home.jsp").forward(request, response);
                        return;
                    }
                }
            } else {
                request.getSession().setAttribute("erro", "Senha Incorreta");
            }
        } else {
            request.getSession().setAttribute("erro", "Usuário não encontrado");
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}