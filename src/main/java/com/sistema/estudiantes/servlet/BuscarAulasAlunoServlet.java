package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AulaDAO;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.Aula;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/BuscarAulasAlunoServlet")
public class BuscarAulasAlunoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String data = request.getParameter("data");
        Aluno aluno = (Aluno) request.getSession().getAttribute("aluno");

        if (aluno == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String diaSemana = getDiaSemana(data);

        AulaDAO aulaDAO = new AulaDAO();
        List<Aula> aulas = aulaDAO.listarPorTurma(diaSemana, aluno.getTurmaId());

        request.setAttribute("aulas", aulas);
        request.getRequestDispatcher("/views/aulaDia.jsp").forward(request, response);
    }

    private static String getDiaSemana(String data) {
        DayOfWeek dia = LocalDate.parse(data).getDayOfWeek();
        return switch (dia) {
            case MONDAY -> "SEG";
            case TUESDAY -> "TER";
            case WEDNESDAY -> "QUA";
            case THURSDAY -> "QUI";
            case FRIDAY -> "SEX";
            case SATURDAY -> "SÁB";
            case SUNDAY -> "DOM";
        };
    }
}