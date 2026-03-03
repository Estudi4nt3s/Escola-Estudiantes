package com.sistema.estudiantes.servlet;

import java.io.IOException;
import java.util.List;

import com.sistema.estudiantes.dao.AdministradorDAO;
import com.sistema.estudiantes.model.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "servletLoginAdmin", value = "/servletLoginAdmin")
public class ServletLoginAdmin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        usuario = usuario != null ? usuario.strip() : "";
        senha = senha != null ? senha.strip() : "";

        AdministradorDAO adminDAO = new AdministradorDAO();
        List<Admin> admins = adminDAO.buscarLogin(usuario);

        if (!admins.isEmpty()) {

            boolean senhaValida = admins.getFirst().getSenha().equals(senha);

            if (senhaValida) {

                request.getSession().setAttribute("admin", admins.getFirst());

                request.getRequestDispatcher("views/inicio_a.jsp")
                        .forward(request, response);

            } else {
                request.getSession().setAttribute("erroAdmin", "Senha incorreta");
                request.getRequestDispatcher("index.jsp")
                        .forward(request, response);
            }

        } else {
            request.getSession().setAttribute("erroAdmin", "Usuário não encontrado");
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
        }
    }
}