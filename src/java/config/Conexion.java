
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    public Connection getConexion(){
        try {
            Class.forName ("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://172.16.42.78:3306/serviciomedico?serverTimezone=UTC&", "Naranjo", "#r%^OJy#Eo8d");
            System.out.println("Conexion Exitosa");
            return conexion;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}
