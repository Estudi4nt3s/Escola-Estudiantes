package com.sistema.estudiantes.servlet;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

import java.util.Date;
import java.util.Properties;

public class EmailService {
    Dotenv dotenv = Dotenv.load();

    private final String email = dotenv.get("ENV_USER");
    private final String password = dotenv.get("ENV_PASS");



    public void enviarRecuperacao(String emailDestino, String token) {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        System.out.println("EMAIL: " + email);
        System.out.println("PASSWORD: " + password);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        new Thread(() -> {
            try {

                Message message = new MimeMessage(session);

                message.setFrom(new InternetAddress(email));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(emailDestino)
                );

                message.setSubject("Recuperação de senha - Estudiantes");

                message.setSentDate(new Date());

                String link = "http://localhost:8080/Estudiantes/resetSenha?token=" + token;

                String htmlContent = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 8px; overflow: hidden;'>" +
                        "<div style='background-color: #d9534f; padding: 20px; text-align: center;'>" +
                        "<h1 style='color: white; margin: 0; font-size: 24px;'>Colégio Estudiantes</h1>" +
                        "</div>" +

                        "<div style='padding: 30px;'>" +
                        "<h2 style='color: #333; margin-top: 0; text-align: center;'>Recuperação de Senha</h2>" +
                        "<p style='color: #555; font-size: 16px;'>Olá,</p>" +
                        "<p style='color: #555; font-size: 16px; line-height: 1.5;'>" +
                        "Recebemos uma solicitação para redefinir sua senha no portal do colégio. " +
                        "Se foi você, clique no botão abaixo para prosseguir:" +
                        "</p>" +

                        "<div style='text-align: center; margin: 30px 0;'>" +
                        "<a href='" + link + "' style='background-color: #d9534f; color: white; padding: 14px 28px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;'>" +
                        "Redefinir Minha Senha" +
                        "</a>" +
                        "</div>" +

                        "<p style='color: #888; font-size: 12px; margin-top: 30px;'>" +
                        "Se o botão não funcionar, copie e cole este link no seu navegador: <br>" +
                        "<span style='color: #007bff;'>" + link + "</span>" +
                        "</p>" +
                        "<hr style='border: 0; border-top: 1px solid #eee; margin: 20px 0;'>" +
                        "<p style='color: #999; font-size: 12px; text-align: center;'>Este é um e-mail automático, por favor não responda.</p>" +
                        "</div>" +
                        "</div>";

                message.setContent(htmlContent, "text/html; charset=UTF-8");

                Transport.send(message);

                System.out.println("Email enviado para: " + emailDestino);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }
}