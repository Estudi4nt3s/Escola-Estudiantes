package com.sistema.estudiantes.servlet;

import java.io.*;
import java.text.Normalizer;
import java.time.LocalDate;
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
                validarEmail = true;
                validarSenha = users.getFirst().getSenha().equals(senha);
                if (validarSenha) {
                    request.getSession().setAttribute("usuario", users.getFirst());
                    if (professor) {
                        ProfessorDAO profDAO = new ProfessorDAO();
                        List<Professor> profs = profDAO.listarComFiltro(users.getFirst().getId());
                        request.getSession().setAttribute("professor", profs.getFirst());
                        request.getRequestDispatcher("views/home_p.jsp").forward(request, response);
                    } else {
                        LocalDate hoje = LocalDate.now();
                        String dia = String.format("%02d",hoje.getDayOfMonth());
                        String mes = String.format("%02d",hoje.getMonthValue());
                        Locale ptBr = new Locale("pt", "BR");
                        String semana = "QUI";


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


                        String[] materia = new String[6];
                        String[] data = {dia,mes,semana};
                        for(int i = 0;i < 6;i++) {
                            int id = aulas.get(i).getDisciplinaId().getId();
                            for (Disciplina value : disciplina) {
                                if (value.getId() == id) {
                                    String disciplinas = disciplina.get(i).getNome();
                                    String nfdNormalizedString = Normalizer.normalize(disciplinas, Normalizer.Form.NFD);

                                    // 2. Cria regex para encontrar os diacríticos (acentos)
                                    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                                    materia[i] = pattern.matcher(nfdNormalizedString).replaceAll("");
                                    break;
                                }
                            }
                        }
                        request.getSession().setAttribute("aluno", aluno.getFirst());
                        request.getSession().setAttribute("turma", turmas.getFirst());
                        request.getSession().setAttribute("media", notaDAO.Media(aluno.getFirst().getMatricula()));
                        request.getSession().setAttribute("data", data);
                        request.getSession().setAttribute("materia", materia);
                        request.getSession().setAttribute("notas", notas);
                        request.getRequestDispatcher("views/home.jsp").forward(request, response);
                    }
                }
            request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
        }
            else{
                request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
            }
    }
}