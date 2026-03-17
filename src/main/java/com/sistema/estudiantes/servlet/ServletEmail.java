package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.UsuarioDAO;
import com.sistema.estudiantes.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@WebServlet("/email")
public class ServletEmail extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    protected void doPost (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String subAcao = request.getParameter("sub_acao");

        if ("procurar".equals(subAcao)) {
            procurarEmail(request, response);
        }
    }

    public void procurarEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        List<Usuario> usuarios = usuarioDAO.listarComFiltro("email = ?", email);

        if (!usuarios.isEmpty()) {
            Usuario usuario = usuarios.get(0);

            String token = UUID.randomUUID().toString();
            LocalDateTime expira = LocalDateTime.now().plusHours(24);

            usuario.setTokenRecuperacao(token);
            usuarioDAO.atualizarToken(usuario.getId(), token, expira);

            EmailService emailService = new EmailService();
            emailService.enviarRecuperacao(email, token);

            request.setAttribute("msg", "Email enviado!");
        } else {
            request.setAttribute("msg", "Email não encontrado");
        }

        request.getRequestDispatcher("/views/senha.jsp").forward(request, response);
    }
}
