//package com.sistema.estudiantes.servlet;
//
//import java.io.*;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//
//
//@WebServlet(name = "servletLoginADM", value = "/servletLoginADM")
//public class ServletADM extends HttpServlet {
//    public void init() {
//    }
////ALTERAR TUDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        AdmDAO admDAO = new AdmDAO();
//        Adm adm = admDAO.listar();
////        Pegando os Parâmetros
//        String email = request.getParameter("usuario").strip();
//        String senha = request.getParameter("senha").strip();
//
//        if (email.equals(adm.getEmail())) {
//            if (senha.equals(adm.getSenha())) {
//                request.getRequestDispatcher("views/telaADM.jsp").forward(request, response);
//            }
//        }
//    }
//}
