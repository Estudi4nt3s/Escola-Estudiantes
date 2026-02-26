package com.sistema.estudiantes.servlet;

import java.io.*;
import java.util.List;

import com.sistema.estudiantes.dao.UsuarioDAO;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Professor;
import com.sistema.estudiantes.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.sistema.estudiantes.dao.AlunoDAO;
import com.sistema.estudiantes.dao.ProfessorDAO;

@WebServlet(name = "servletLogin", value = "/servletLogin")
public class ServletLogin extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

//        Pegando os Parâmetros
        String emailStr = request.getParameter("usuario").strip();
        String senha = request.getParameter("senha").strip();

//        Declarando variáveis com valores padrões
        boolean validarEmail = false;
        boolean validarSenha = false;

        System.out.println("Entrou no servlet");
//        Validando para ver se nós estamos tentando logar
            System.out.println("Não é adm 💔");
            String regexFuncionario = "^[a-zA-Z]+\\.[a-zA-Z]+$";
            boolean professor = emailStr.matches(regexFuncionario);
            UsuarioDAO userDAO = new UsuarioDAO();
            int email = Integer.parseInt(emailStr);
            List<Usuario> users = userDAO.listarComFiltro(email);
            if (!users.isEmpty()) {
                validarEmail = true;
                validarSenha = users.getFirst().getSenha().equals(senha);
                if (validarSenha) {
                    request.getSession().setAttribute("usuario_id", users.getFirst().getId());
                    if (professor) {
                        ProfessorDAO profDAO = new ProfessorDAO();
                        List<Professor> profs = profDAO.listarComFiltro(users.getFirst().getId());
                        request.getSession().setAttribute("nome", profs.getFirst().getNome());
                        request.getRequestDispatcher("views/index.jsp").forward(request, response);
                    } else {
                        AlunoDAO alunoDAO = new AlunoDAO();
                        List<Aluno> aluno = alunoDAO.listarComFiltro(users.getFirst().getId());
                        request.getSession().setAttribute("nome", aluno.getFirst().getNome());
                        request.getRequestDispatcher("views/index.jsp").forward(request, response);
                    }
                }
            request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
        }
    }
}