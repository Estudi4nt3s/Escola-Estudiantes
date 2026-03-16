package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.UsuarioDAO;
import com.sistema.estudiantes.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/resetSenha")
public class ServletResetSenha extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorToken(token);

        if (usuario == null) {
            request.setAttribute("msg", "Token inválido.");
            request.getRequestDispatcher("/views/resetSenha.jsp").forward(request, response);
            return;
        }

        Timestamp expiraNoBanco = usuarioDAO.obterDataExpiracaoDoBanco(token);

        if (expiraNoBanco == null || expiraNoBanco.before(new java.util.Date())) {
            request.setAttribute("msg", "Token expirado.");
            request.getRequestDispatcher("/views/resetSenha.jsp").forward(request, response);
            return;
        }

        request.setAttribute("token", token);
        request.getRequestDispatcher("/views/resetSenha.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        String senha = request.getParameter("senha");

        Usuario usuario = usuarioDAO.buscarPorToken(token);

        if (usuario == null) {
            request.setAttribute("msg", "Token inválido.");
            request.getRequestDispatcher("/views/resetSenha.jsp").forward(request, response);
            return;
        }

        usuario.setSenha(senha);
        usuarioDAO.atualizar(usuario);

        usuarioDAO.limparToken(usuario.getId());

        request.setAttribute("msg", "Senha alterada com sucesso!");
        request.getRequestDispatcher("/views/resetSenha.jsp").forward(request, response);
    }
}