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

        int matricula = Integer.parseInt(request.getParameter("matricula"));
        String cpf = request.getParameter("cpf").replace(".", "").replace("-", "");
        List<Aluno> alunos1 = alunoDAO.listarComFiltro(matricula);

        if (alunos1.getFirst().getMatricula() == matricula){
            if(alunos1.getFirst().getCpf().equals(cpf)){
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                Usuario usuario = new Usuario(email, senha);
                usuarioDAO.inserir(usuario);
            }
            //CPF não compativel
        }
        else{
            //Matrícula não encontrada
        }
    }
}

