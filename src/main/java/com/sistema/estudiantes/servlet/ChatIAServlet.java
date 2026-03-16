package com.sistema.estudiantes.servlet;

import com.sistema.estudiantes.dao.AdministradorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/ChatIAServlet")
public class ChatIAServlet extends HttpServlet {
    private AdministradorDAO dao = new AdministradorDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pergunta = request.getParameter("pergunta");
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) apiKey = "AIzaSyAVjMHlL-go7KDt4kx3D2eMUQAfrjr777M";

        String contexto = dao.obterResumoEstatisticas() + " " + dao.obterRankingParaIA();
        String jsonInput = "{\"contents\":[{\"parts\":[{\"text\":\"Você é um assistente administrativo escolar. Dados: "
                + contexto + ". Pergunta: " + pergunta + "\"}]}]}";

        // MODELO CORRETO: gemini-1.5-flash
        URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try(OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes("utf-8"));
        }

        if (conn.getResponseCode() == 200) {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);

                String rawJson = sb.toString();
                // Extração simples: busca o conteúdo entre o "text": " e o próximo campo de controle
                String texto = "Erro ao processar conteúdo.";
                if (rawJson.contains("\"text\": \"")) {
                    texto = rawJson.split("\"text\": \"")[1];
                    // Remove o fechamento do JSON (tudo depois do último "finishReason")
                    texto = texto.substring(0, texto.lastIndexOf("\"finishReason\":") - 3);
                    // Corrige escapes do próprio JSON
                    texto = texto.replace("\\n", "\n").replace("\\\"", "\"");
                }

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(texto);
            }
        } else {
            response.getWriter().write("Erro " + conn.getResponseCode() + ": Verifique o modelo e a chave.");
        }
    }
}