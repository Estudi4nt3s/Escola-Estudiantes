package com.sistema.estudiantes.servlet;

import java.io.*;
import java.util.List;

import com.sistema.estudiantes.dao.UsuarioDAO;
import com.sistema.estudiantes.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet(name = "servletLoginADM", value = "/servletLoginADM")
public class ServletADM extends HttpServlet {
    public void init() {
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String email = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        senha = senha!=null?senha.strip():"";
        email = email!=null?email.strip():"";

        List<Usuario> adm = usuarioDAO.listarFiltros("isadm = ? AND email = ?",true,email);

        if (email.equals(adm.getFirst().getEmail())) {
            if (senha.equals(adm.getFirst().getSenha())) {
                request.getRequestDispatcher("views/inicio_a.jsp").forward(request, response);
            }
            else {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
        else{
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
