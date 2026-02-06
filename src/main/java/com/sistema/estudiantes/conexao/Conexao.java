package com.sistema.estudiantes.conexao;


//Importação Dotenv
import io.github.cdimascio.dotenv.Dotenv;

//Importações SQL
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Conexao {

    public static Connection createConnection() {
        //Definindo variavel conn como null para poder tratar erros
        Connection conn = null;

        //Criando um objeto Dotenv para a passagem de valores
        Dotenv dotenv = Dotenv.load();

        //Atribuindo os valores do arquivo .env para criar a conexão

        final String caminho = dotenv.get("DB_URL");
        final String usuario = dotenv.get("DB_USUARIO");
        final String password = dotenv.get("DB_SENHA");

        try {
            Class.forName("org.postgresql.Driver");
            //Passagem dos atributos para efetuar a conexão
            conn = DriverManager.getConnection(caminho, usuario, password);
        }catch (SQLException e){
            //printando erros
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        //Retorna a conexão

        return conn;
    }


    //Criando um método para fechar a conexão
    public static void fecharConnection(Connection conn){

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