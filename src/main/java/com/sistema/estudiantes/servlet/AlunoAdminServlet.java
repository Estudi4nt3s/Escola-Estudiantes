    package com.sistema.estudiantes.servlet;

    import com.sistema.estudiantes.dao.AlunoAdminDAO;
    import com.sistema.estudiantes.dao.TurmaAdmDAO; // Adicione este import
    import com.sistema.estudiantes.model.Aluno;
    import com.sistema.estudiantes.model.TurmaAdm;
    import jakarta.servlet.*;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.*;
    import jakarta.mail.*;
    import jakarta.mail.internet.*;
    import java.io.IOException;
    import java.time.LocalDate;
    import java.util.List;
    import java.util.Properties;

    @WebServlet("/AlunoAdminServlet")
    public class AlunoAdminServlet extends HttpServlet {
        private AlunoAdminDAO dao = new AlunoAdminDAO();
        private TurmaAdmDAO turmaDao = new TurmaAdmDAO(); // Instancie o DAO de turmas

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // 1. Busca todos os alunos
            List<Aluno> lista = dao.listarTodos();
            request.setAttribute("listaAlunos", lista);

            // 2. BUSCA TODAS AS TURMAS (Isso resolve o N/A)
            List<TurmaAdm> listaTurmas = turmaDao.listarTodas();
            request.setAttribute("listaTurmas", listaTurmas);

            // 3. Lógica para carregar aluno em caso de edição ou exclusão
            String acao = request.getParameter("acao");
            if ("editar".equals(acao) || "pre-excluir".equals(acao)) {
                String matriculaStr = request.getParameter("matricula");
                if (matriculaStr != null) {
                    int matricula = Integer.parseInt(matriculaStr);
                    Aluno aluno = dao.buscarPorMatricula(matricula);
                    request.setAttribute("alunoEditar", aluno);
                }
            }

            request.getRequestDispatcher("/views/aluno_a.jsp").forward(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("UTF-8");
            String acao = request.getParameter("acao");

            if ("excluir".equals(acao)) {
                int matricula = Integer.parseInt(request.getParameter("matricula"));
                dao.excluir(matricula);
            }
            else if ("novo".equals(acao) || "editar".equals(acao)) {
                Aluno aluno = new Aluno();
                if ("editar".equals(acao)) {
                    aluno.setMatricula(Integer.parseInt(request.getParameter("matricula")));
                }
                aluno.setNome(request.getParameter("nome"));
                aluno.setCpf(request.getParameter("cpf"));
                aluno.setDataNascimento(LocalDate.parse(request.getParameter("dataNascimento")));
                aluno.setTelefonePai(request.getParameter("telefonePai"));
                aluno.setEmailResponsavel(request.getParameter("emailResponsavel"));

                String tId = request.getParameter("turmaId");
                aluno.setTurmaId((tId != null && !tId.isEmpty()) ? Integer.parseInt(tId) : 1);

                dao.salvar(aluno, acao);

                if ("novo".equals(acao)) {
                    Aluno salvo = dao.buscarPorCpf(aluno.getCpf());
                    if (salvo != null && salvo.getEmailResponsavel() != null) {
                        enviarEmailAutomatico(salvo);
                    }
                }
            }
            response.sendRedirect(request.getContextPath() + "/AlunoAdminServlet");
        }

        private void enviarEmailAutomatico(Aluno a) {
            final String usuario = "secretaria.estudiantes01@gmail.com";
            // REMOVIDO: \n e @. A senha deve ter exatamente 16 letras.
            final String senha = "cydolspaaiifdgzo";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            // Tempo limite para não travar o sistema se o Google demorar
            props.put("mail.smtp.connectiontimeout", "5000");
            props.put("mail.smtp.timeout", "5000");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuario, senha);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(usuario));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(a.getEmailResponsavel()));
                message.setSubject("Bem-vindo à Escola Universo - Seus Dados de Acesso");

                String conteudoHtml = "<html><body style='font-family: Arial, sans-serif; color: #333;'>"
                        + "<div style='background-color: #e74c3c; padding: 20px; text-align: center; color: white;'>"
                        + "<h1>Bem-vindo à Escola Universo!</h1>"
                        + "</div>"
                        + "<div style='padding: 20px; border: 1px solid #ddd;'>"
                        + "<p>Olá, <strong>" + a.getNome() + "</strong>!</p>"
                        + "<p>Sua matrícula foi realizada com sucesso. Use os dados abaixo para criar seu perfil:</p>"
                        + "<table style='width: 100%; border-collapse: collapse;'>"
                        + "<tr><td style='padding: 8px; border: 1px solid #ddd;'><strong>Matrícula:</strong></td>"
                        + "<td style='padding: 8px; border: 1px solid #ddd;'>" + a.getMatricula() + "</td></tr>"
                        + "<tr><td style='padding: 8px; border: 1px solid #ddd;'><strong>CPF:</strong></td>"
                        + "<td style='padding: 8px; border: 1px solid #ddd;'>" + a.getCpf() + "</td></tr>"
                        + "</table>"
                        + "<br><p>Acesse o link abaixo para criar sua senha:</p>"
                        + "<p style='text-align: center;'>"
                        + "<a href='http://localhost:8080/Estudiantes/views/cadastro.jsp' style='background-color: #f1c40f; color: #000; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold;'>CADASTRAR MEU USUÁRIO</a>"
                        + "</p>"
                        + "</div>"
                        + "<footer style='margin-top: 20px; font-size: 12px; color: #777;'>Atenciosamente,<br>Secretaria Escola Universo</footer>"
                        + "</body></html>";

                message.setContent(conteudoHtml, "text/html; charset=UTF-8");
                Transport.send(message);

                // Log para você ver no IntelliJ que deu certo
                System.out.println(">>> E-mail enviado com sucesso para: " + a.getEmailResponsavel());

            } catch (MessagingException e) {
                System.err.println(">>> ERRO AO ENVIAR EMAIL: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }