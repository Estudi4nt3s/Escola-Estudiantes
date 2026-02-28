package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AulaDAO;
import com.sistema.estudiantes.model.Aula;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
    @WebServlet("/BuscarAulasServlet")
    public class BuscarAulasServlet extends HttpServlet {

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            String data = request.getParameter("data");

            List<Aula> aulas = AulaDAO.buscarPorData(data);

            request.setAttribute("aulas", aulas);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/views/aulaDia.jsp");

            dispatcher.forward(request, response);
        }}

