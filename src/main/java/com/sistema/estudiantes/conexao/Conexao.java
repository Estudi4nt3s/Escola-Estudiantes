package com.sistema.estudiantes.conexao;


//Importação Dotenv
import io.github.cdimascio.dotenv.Dotenv;

//Importações SQL
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Conexao {

    public static Connection conectar() {
        Connection conn = null;
        try {
            Dotenv dotenv = Dotenv.load();
            String caminho = dotenv.get("DB_URL");
            String usuario = dotenv.get("DB_USUARIO");
            String password = dotenv.get("DB_SENHA");

            // Log para conferir se os dados estão chegando (Cuidado em produção!)

            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(caminho, usuario, password);

        } catch (Exception e) {
            System.err.println("ERRO NA CONEXÃO: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    //Criando um método para fechar a conexão
    public static void desconectar(Connection conn){

        try{
            //Verifica se a conexão esta aberta, se sim, a conexão é fechada
            if (conn != null && !conn.isClosed()){
                conn.close();
            }
        }catch (Exception e){

            //Printando erros
            e.printStackTrace();
        }
    }
}