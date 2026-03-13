package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.TurmaDAO;
import com.sistema.estudiantes.model.Turma;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/BuscarTurmasServlet")
class BuscarTurmasServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TurmaDAO turmaDAO = new TurmaDAO();
        List<Turma> turmas = turmaDAO.listar();

        request.setAttribute("turmas", turmas);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/views/turmaDia.jsp");

        dispatcher.forward(request, response);
    }
}