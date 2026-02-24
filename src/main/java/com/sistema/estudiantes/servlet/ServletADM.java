package com.sistema.estudiantes.servlet;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet(name = "servletLogin", value = "/servletLogin")
public class ServletADM extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        Pegando os Parâmetros
        String email = request.getParameter("usuario").strip();
        String senha = request.getParameter("senha").strip();

//        if (email.equals()) {}
    }
}
