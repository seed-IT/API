package eu.seed.it;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConfig {
    public static Connection mariaDBConnection(){
        Connection connection = null;

        try{
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Connexion à la base de données...");
            connection = DriverManager.getConnection("jdbc:mariadb://51.91.100.245:3306/DB?user=seeds&password=tortue");
            System.out.println("Connexion réussie...");

        }catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
