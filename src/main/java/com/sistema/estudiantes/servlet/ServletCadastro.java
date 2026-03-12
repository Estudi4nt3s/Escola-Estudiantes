package com.sistema.estudiantes.servlet;

import java.io.*;
import java.util.List;

import com.sistema.estudiantes.dao.*;
import com.sistema.estudiantes.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "servletCadastro", value = "/servletCadastro")
public class ServletCadastro extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        AlunoDAO alunoDAO = new AlunoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Aluno> alunos = alunoDAO.listar();

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        System.out.println("email: " + email);
        System.out.println("senha: " + senha);
        System.out.println("nome: " +  request.getParameter("nome"));
        System.out.println("sobrenome: " +  request.getParameter("sobrenome"));

        int matricula = Integer.parseInt(request.getParameter("matricula"));
        String cpf = request.getParameter("cpf").replace(".", "").replace("-", "");
        List<Aluno> alunos1 = alunoDAO.listarMatricula(matricula);
        List<Usuario> usuarios = usuarioDAO.listarComFiltro("email = ?", email);

        if (!usuarios.isEmpty()) {
            request.getSession().setAttribute("mensagem", "Email já cadastrado, tente inserir um novo email.");
            request.getRequestDispatcher("views/cadastro.jsp").forward(request, response);
            return;
        }

        System.out.println(alunos1.getFirst().getUsuarioId().getId());

        if (alunos1.getFirst().getUsuarioId().getId() != null){
            request.getSession().setAttribute("mensagem", "Aluno já cadastrado");
            request.getRequestDispatcher("views/cadastro.jsp").forward(request, response);
            return;
        }


        if (!alunos1.isEmpty()) {

            if (alunos1.getFirst().getCpf().equals(cpf)) {

                Usuario usuario = new Usuario(email, senha);
                usuarioDAO.inserir(usuario);
                alunos1.getFirst().setUsuarioId(usuarioDAO.listarComFiltro("email = ?", email).getFirst());
                alunoDAO.atualizar(alunos1.getFirst());
                request.getRequestDispatcher("index.jsp").forward(request, response);

            } else {
                request.getSession().setAttribute("mensagem", "CPF não encontrado, verifique se inseriu corretamente e tente novamente");
                request.getRequestDispatcher("views/cadastro.jsp").forward(request, response);
            }

        } else {
            request.getSession().setAttribute("mensagem", "Matricula não encontrado, verifique se inseriu corretamente e tente novamente");
            request.getRequestDispatcher("views/cadastro.jsp").forward(request, response);
        }
    }
}

