//import jakarta.mail.*;
//import jakarta.mail.internet.*;
//import javax.mail.Authenticator;
//import javax.mail.PasswordAuthentication;
//import java.util.Properties;
//
//public class EmailService {
//
//    private final String email = System.getenv("ENV_USER");
//    private final String password = System.getenv("ENV_PASS");
//
//    public void enviarRecuperacao(String emailDestino, String token) {
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(email, password);
//            }
//        });
//
//        new Thread(() -> {
//            try {
//
//                Message message = new MimeMessage(session);
//                message.setFrom(new InternetAddress(email));
//                message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse(emailDestino));
//
//                message.setSubject("Recuperação de Senha");
//
//                message.setText(
//                        "Clique neste link para redefinir sua senha:\n" +
//                                "https://seusite.com/reset?token=" + token
//                );
//
//                Transport.send(message);
//
//                System.out.println("Email enviado para " + emailDestino);
//
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//}