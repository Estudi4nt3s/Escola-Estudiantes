package com.sistema.estudiantes.servlet;

import java.io.*;
import java.util.List;

import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Professor;
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
        boolean prioridade = false;
        boolean validarEmpresa = false;

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
//            Declarando diversos objetos
            if(!professor){
                AlunoDAO alunoDAO = new AlunoDAO();
                List<Aluno> aluno = alunoDAO.select();

//            Vendo se o e-mail da empresa está presente no banco de dados
                for (int i = 0; i < aluno.size(); i++) {
                    if (aluno.get(i).getUsuario().equals(email)) {
                        posicao = i;
                        request.getSession().setAttribute("idusuario", aluno.get(i).getId());
                        System.out.println(email);
                        break;
                    }
                }
            }
            else{
                ProfessorDAO profDAO = new ProfessorDAO();
                List<Professor> prof = profDAO.select();

//            Vendo se o e-mail da empresa está presente no banco de dados
                for (int i = 0; i < prof.size(); i++) {
                    if (prof.get(i).getUsuario().equals(email)) {
                        posicao = i;
                        request.getSession().setAttribute("idusuario", prof.get(i).getId());
                        System.out.println(email);
                        break;
                    }
                }
            }


//            Vendo se o e-mail do funcionário está presente no banco de dados
            if (!validarEmail) {
                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).getEmail().equals(email)) {
                        validarEmail = true;
                        prioridade = usuarios.get(i).isPrioridade();
                        posicao = i;
                        request.getSession().setAttribute("empresaid", usuarios.get(i).getEmpresaId());
                        request.getSession().setAttribute("funcionarioid", usuarios.get(i).getId());
                        break;
                    }
                }
            }

//            Vendo se a senha corresponde ao e-mail do banco de dados
            if (posicao != -1) {
                if (validarEmpresa) {
                    if (senha.equals(empresas.get(posicao).getSenha())) {
                        validarSenha = true;
                    }
                } else {
                    if (senha.equals(usuarios.get(posicao).getSenha())) {
                        validarSenha = true;
                    }
                }
            }
            System.out.println("chegou");
//            Encaminhando para a página correspondente
            if (validarSenha) {
                System.out.println("Senha valida");
                if (validarEmpresa) {
                    System.out.println("chegouuuu");
                    request.getRequestDispatcher("WEB-INF/views/PaginaAposLogin/crudAdm.jsp").forward(request, response);
                } else {
                    if (prioridade) {
                        request.getRequestDispatcher("WEB-INF/views/PaginaAposLogin/crudRH.jsp").forward(request, response);
                    } else {
                        System.out.println("erro");
                        request.getRequestDispatcher("WEB-INF/error.jsp").forward(request, response);
                    }
                }
            }
//            Voltando para login caso a senha esteja errada
            else {
                request.getRequestDispatcher("WEB-INF/views/LoginSignUp/login.jsp").forward(request, response);
            }
        }
    }
}