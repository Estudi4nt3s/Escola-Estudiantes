package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AdministradorDAO;
import com.sistema.estudiantes.model.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/servletLoginAdmin")
public class ServletLoginAdmin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        usuario = usuario != null ? usuario.strip() : "";
        senha = senha != null ? senha.strip() : "";

        AdministradorDAO dao = new AdministradorDAO();
        List<Admin> admins = dao.buscarLogin(usuario);

        if (!admins.isEmpty()) {

            Admin admin = admins.get(0);

            if (admin.getSenha().equals(senha)) {

                // cria sessão
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);

                response.sendRedirect("views/inicio_a.jsp");

            } else {
                response.sendRedirect("index.jsp?erroAdmin=senha");
            }

        } else {
            response.sendRedirect("index.jsp?erroAdmin=usuario");
        }
    }
}