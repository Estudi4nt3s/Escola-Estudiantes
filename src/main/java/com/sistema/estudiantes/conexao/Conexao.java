package com.sistema.estudiantes.conexao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.DriverManager;
import java.sql.Connection;

public class Conexao {

    public static Connection conectar() {
        Connection conn = null;
        try {
            String caminho = System.getenv("DB_URL");
            String usuario = System.getenv("DB_USUARIO");
            String password = System.getenv("DB_SENHA");

            if (caminho == null || caminho.isEmpty()) {
                Dotenv dotenv = Dotenv.load();
                caminho = dotenv.get("DB_URL");
                usuario = dotenv.get("DB_USUARIO");
                password = dotenv.get("DB_SENHA");
            }

            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(caminho, usuario, password);

        } catch (Exception e) {
            System.err.println("ERRO NA CONEXÃO: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public static void desconectar(Connection conn){
        try{
            if (conn != null && !conn.isClosed()){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}