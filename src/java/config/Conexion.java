
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public Connection getConexion() throws ClassNotFoundException{
        try {
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conexion = DriverManager.getConnection("jdbc:mysql://172.16.42.78:3306/serviciomedico?serverTimezone=UTC", "Naranjo", "#r%^OJy#Eo8d");
            System.out.println("Conexion Exitosa");
            return conexion;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
