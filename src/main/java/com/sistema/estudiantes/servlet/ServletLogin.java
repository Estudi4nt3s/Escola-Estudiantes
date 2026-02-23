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
        String email = request.getParameter("usuario").strip();
        String senha = request.getParameter("senha").strip();

//        Declarando variáveis com valores padrões
        boolean validarEmail = false;
        boolean validarSenha = false;
        int posicao = -1;

//        Guardando informações para usar depois

        request.getSession().setAttribute("emailfuncionario", email);
        request.getSession().setAttribute("filtro","");
        request.getSession().setAttribute("tabela","");
        System.out.println("Entrou no servlet");
//        Validando para ver se nós estamos tentando logar
        if(email.equals("ADMFODAO@gmail.com") || senha.equals("AAAAAAAAAAAAAAAAAAAHHHHHHHHH")){
            System.out.println("Parametro correto");
            request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
        }
        else {
            System.out.println("Não é adm 💔");
            String regexFuncionario = "^[a-zA-Z]+\\.[a-zA-Z]+$";
            boolean professor = email.matches(regexFuncionario);
            UsuarioDAO userDAO = new UsuarioDAO();
            List<Usuario> users = userDAO.listarComFiltro("email", email);
            if (!users.isEmpty()) {
                validarEmail = true;
                validarSenha = users.getFirst().getSenha().equals(senha);
                if (validarSenha) {
                    request.getSession().setAttribute("usuario_id", users.getFirst().getId());
                    if (professor) {
                        request.getRequestDispatcher("WEB-INF/.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("WEB-INF/.jsp").forward(request, response);
                    }
                }
            }
            request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
        }
    }
}