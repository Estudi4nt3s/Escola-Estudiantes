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

                String link = "http://localhost:8080/estudiantes/resetSenha?token=" + token;

                message.setText(
                        "Olá,\n\n" +
                                "Recebemos uma solicitação para redefinir sua senha.\n\n" +
                                "Clique no link abaixo para criar uma nova senha:\n\n" +
                                link +
                                "\n\nSe você não solicitou isso, ignore este email."
                );

                Transport.send(message);

                System.out.println("Email enviado para: " + emailDestino);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }
}