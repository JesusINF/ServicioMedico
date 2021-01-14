package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    private Connection conexion = null;
    public Connection getConexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://uhff8twgju9qdcnv:2gOqmLpR5bq1bm9h1o1M@bawuh1cvaadk7k8ml9wu-mysql.services.clever-cloud.com:3306/bawuh1cvaadk7k8ml9wu?serverTimezone=UTC&", "uhff8twgju9qdcnv", "2gOqmLpR5bq1bm9h1o1M");
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

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
