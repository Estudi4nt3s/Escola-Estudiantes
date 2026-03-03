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

        int matricula = Integer.parseInt(request.getParameter("matricula"));
        String cpf = request.getParameter("cpf").replace(".", "").replace("-", "");
        List<Aluno> alunos1 = alunoDAO.listarComFiltro(matricula);
        List<Usuario> usuarios = usuarioDAO.listarComFiltro("email = ?", email);
        System.out.println(alunos1.isEmpty());
        if(!alunos1.isEmpty()) {
                if (alunos1.getFirst().getCpf().equals(cpf)) {
                    //Alterar dps
                    Usuario usuario = new Usuario(100, email, senha);
                    usuarioDAO.inserir(usuario);
                    alunos1.getFirst().setUsuarioId(usuarios.getFirst());
                    alunoDAO.atualizar(alunos1.getFirst());
                }
                //CPF não compativel
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

