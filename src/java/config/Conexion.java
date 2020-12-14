
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public Connection getConexion(){
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://172.16.42.78:3306/serviciomedico", "Naranjo", "#r%^OJy#Eo8d");
            return conexion;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
