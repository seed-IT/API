package eu.seed.it;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        Connection connection = null;

        try {   //begin connection
            connection = ConnectionConfig.mariaDBConnection();
            if(connection != null){
                System.out.println("Connexion établie...");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {  //close connection
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
