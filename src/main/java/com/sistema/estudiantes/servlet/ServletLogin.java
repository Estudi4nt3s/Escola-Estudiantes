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

//        Pegando os Parâmetros
        String email = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        senha = senha!=null?senha.strip():"";
        email = email!=null?email.strip():"";

//        Declarando variáveis com valores padrões
        boolean validarEmail = false;
        boolean validarSenha;

        System.out.println("Entrou no servlet");
//        Validando para ver se nós estamos tentando logar
            System.out.println("Não é adm 💔");
            String regexFuncionario = "^[a-zA-Z]+\\.[a-zA-Z]+$";
            boolean professor = email.matches(regexFuncionario);
            UsuarioDAO userDAO = new UsuarioDAO();
            List<Usuario> users = userDAO.listarComFiltro("email = ?",email);
            if (!users.isEmpty()) {
                validarSenha = users.getFirst().getSenha().equals(senha);
                if (validarSenha) {
                    //Valida que o usuário está cadastrado
                    LocalDate hoje = LocalDate.now();
                    String dia = String.format("%02d",hoje.getDayOfMonth());
                    String mes = String.format("%02d",hoje.getMonthValue());
                    Locale ptBr = new Locale("pt", "BR");
                    String semana = hoje.getDayOfWeek().getDisplayName(TextStyle.SHORT, ptBr).toUpperCase().substring(0, 3);
                    String[] data = {dia,mes,semana};

                    request.getSession().setAttribute("usuario", users.getFirst());
                    request.getSession().setAttribute("data", data);

                    if (professor) {
                        //Dataload professor
                        ProfessorDAO profDAO = new ProfessorDAO();
                        ProfDisciplinaDAO profDisciplinaDAO = new ProfDisciplinaDAO();
                        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
                        AulaDAO  aulaDAO = new AulaDAO();
                        TurmaDAO turmaDAO = new TurmaDAO();

                        List<Professor> profs = profDAO.listarComFiltro(users.getFirst().getId());
                        ProfessorDisciplina profdisciplinas = profDisciplinaDAO.buscarPorId(users.getFirst().getId());
                        List<Disciplina> disciplinas = disciplinaDAO.listarComFiltro("id",String.valueOf(profdisciplinas.getIdDisciplina().getId()));
                        List<Aula> aulas = aulaDAO.listarComFiltro("disciplinaid = ? AND diasemana = ? order by 2",disciplinas.getFirst().getId(), data[2]);
                        List<Turma> turmas = turmaDAO.listar();

                        request.getSession().setAttribute("professor", profs.getFirst());
                        request.getSession().setAttribute("disciplina", disciplinas.getFirst());
                        request.getSession().setAttribute("aulas", aulas);
                        request.getSession().setAttribute("turmas", turmas);

                        request.getRequestDispatcher("views/home_p.jsp").forward(request, response);
                    } else {
                        //Dataload aluno
                        AlunoDAO alunoDAO = new AlunoDAO();
                        NotaDAO notaDAO = new NotaDAO();
                        TurmaAlunoDAO turmaAlunoDAO = new TurmaAlunoDAO();
                        TurmaDAO turmaDAO = new TurmaDAO();
                        AulaDAO aulaDAO = new AulaDAO();
                        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();


                        List<Aluno> aluno = alunoDAO.listarComFiltro(users.getFirst().getId());
                        List<TurmaAluno> turmaAluno = turmaAlunoDAO.listarComFiltro("matriculaaluno = ?",aluno.getFirst().getMatricula());
                        List<Turma> turmas = turmaDAO.listarComFiltro("id = ? order by 2 desc",turmaAluno.getFirst().getIdTurma().getId());
                        List<Aula> aulas = aulaDAO.listarComFiltro("diasemana = ? order by 2",semana);
                        List<Disciplina> disciplina = disciplinaDAO.listar();
                        List<Nota> notas = notaDAO.listarComFiltro("idaluno = ?", aluno.getFirst().getMatricula());

                        int qtdMateria = 0;
                        String[] materia = new String[6];
                        for(int i = 0;i < 6;i++) {
                            int id = aulas.get(i).getDisciplinaId().getId();
                            for (Disciplina value : disciplina) {
                                if (value.getId() == id) {
                                    String disciplinas = disciplina.get(i).getNome();
                                    String nfdNormalizedString = Normalizer.normalize(disciplinas, Normalizer.Form.NFD);

                                    // 2. Cria regex para encontrar os diacríticos (acentos)
                                    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                                    materia[i] = pattern.matcher(nfdNormalizedString).replaceAll("");
                                    qtdMateria++;
                                    break;
                                }
                            }
                        }
                        request.getSession().setAttribute("aluno", aluno.getFirst());
                        request.getSession().setAttribute("turma", turmas.getFirst());
                        request.getSession().setAttribute("media", notaDAO.Media(aluno.getFirst().getMatricula()));
                        request.getSession().setAttribute("materia", materia);
                        request.getSession().setAttribute("notas", notas);
                        request.getSession().setAttribute("qtdMateria", qtdMateria);
                        request.getRequestDispatcher("views/home.jsp").forward(request, response);
                    }
                }
            else {
                    request.getSession().setAttribute("erro","aaaaaa");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
        }
        else{
            request.getSession().setAttribute("erro","aaaaaa");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}