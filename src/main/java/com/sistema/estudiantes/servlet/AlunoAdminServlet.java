package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AlunoAdminDAO;
import com.sistema.estudiantes.dao.TurmaAdmDAO;
import com.sistema.estudiantes.model.Aluno;
import com.sistema.estudiantes.model.TurmaAdm;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@WebServlet("/AlunoAdminServlet")
@MultipartConfig
public class AlunoAdminServlet extends HttpServlet {
    private AlunoAdminDAO dao = new AlunoAdminDAO();
    private TurmaAdmDAO turmaDao = new TurmaAdmDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Aluno> lista = dao.listarTodos();
        request.setAttribute("listaAlunos", lista);

        List<TurmaAdm> listaTurmas = turmaDao.listarTodas();
        request.setAttribute("listaTurmas", listaTurmas);

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
        HttpSession session = request.getSession();

        if ("excluir".equals(acao)) {
            String matStr = request.getParameter("matricula");
            if (matStr != null && !matStr.isEmpty()) {
                dao.excluir(Integer.parseInt(matStr));
                session.setAttribute("mensagemSucesso", "Aluno excluído com sucesso!");
            }
        }
        else if ("novo".equals(acao) || "editar".equals(acao)) {
            Aluno aluno = new Aluno();
            if ("editar".equals(acao)) {
                String matStr = request.getParameter("matricula");
                if (matStr != null && !matStr.isEmpty()) {
                    aluno.setMatricula(Integer.parseInt(matStr));
                }
            }

            aluno.setNome(request.getParameter("nome"));
            aluno.setCpf(limparCpf(request.getParameter("cpf")));
            String dataStr = request.getParameter("dataNascimento");
            if (dataStr != null && !dataStr.isEmpty()) {
                aluno.setDataNascimento(LocalDate.parse(dataStr));
            }

            aluno.setTelefonePai(request.getParameter("telefonePai"));
            aluno.setEmailResponsavel(request.getParameter("emailResponsavel"));

            String tId = request.getParameter("turmaId");
            aluno.setTurmaId((tId != null && !tId.isEmpty()) ? Integer.parseInt(tId) : 1);

            if (dao.salvar(aluno, acao)) {
                session.setAttribute("mensagemSucesso", "Operação realizada com sucesso!");
                if ("novo".equals(acao)) {
                    Aluno salvo = dao.buscarPorCpf(aluno.getCpf());
                    if (salvo != null && salvo.getEmailResponsavel() != null) {
                        enviarEmailAutomatico(salvo);
                    }
                }
            } else {
                session.setAttribute("mensagemErro", "Erro ao salvar o registro.");
            }
        }
        else if ("importar".equals(acao)) {
            Part filePart = request.getPart("file");
            int contagem = 0;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), "UTF-8"))) {
                String linha;
                reader.readLine();
                while ((linha = reader.readLine()) != null) {
                    if (linha.trim().isEmpty() || linha.startsWith(";;")) continue;

                    String[] dados = linha.split("[;,]");

                    if (dados.length >= 6) {
                        try {
                            Aluno a = new Aluno();
                            a.setNome(dados[0]);
                            a.setCpf(limparCpf(dados[1]));
                            a.setDataNascimento(LocalDate.parse(dados[2]));
                            a.setTelefonePai(dados[3]);
                            a.setEmailResponsavel(dados[4]);
                            a.setTurmaId(Integer.parseInt(dados[5]));

                            if (dao.salvar(a, "novo")) {
                                Aluno salvo = dao.buscarPorCpf(a.getCpf());
                                if (salvo != null && salvo.getEmailResponsavel() != null) {
                                    enviarEmailAutomatico(salvo);
                                    // Delay de 1.5s entre disparos para não ser marcado como spam
                                    Thread.sleep(1500);
                                }
                                contagem++;
                            }
                        } catch (Exception e) {
                            System.err.println("Erro ao processar linha: " + linha);
                        }
                    }
                }
                session.setAttribute("mensagemSucesso", "Importação concluída! " + contagem + " alunos adicionados e e-mails enviados.");
            } catch (Exception e) {
                session.setAttribute("mensagemErro", "Erro ao processar arquivo: " + e.getMessage());
            }
        }
        response.sendRedirect(request.getContextPath() + "/AlunoAdminServlet");
    }

    private String limparCpf(String cpf) {
        if (cpf == null) return "";
        return cpf.replaceAll("[^0-9]", "");
    }

    private void enviarEmailAutomatico(Aluno a) {
        final String usuario = "secretaria.estudiantes01@gmail.com";
        final String senha = "cydolspaaiifdgzo";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
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
            message.setSubject("Bem-vindo à Escola Estudiantes - Seus Dados de Acesso");

            String conteudoHtml = "<html><body style='font-family: Arial, sans-serif; color: #333;'>"
                    + "<div style='background-color: #e74c3c; padding: 20px; text-align: center; color: white;'>"
                    + "<h1>Bem-vindo à Escola Estudiantes!</h1>"
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
                    + "<footer style='margin-top: 20px; font-size: 12px; color: #777;'>Atenciosamente,<br>Secretaria Escola Estudiantes</footer>"
                    + "</body></html>";

            message.setContent(conteudoHtml, "text/html; charset=UTF-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}