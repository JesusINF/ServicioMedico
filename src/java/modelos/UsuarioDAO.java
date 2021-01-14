/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jesus
 */
public class UsuarioDAO {

    private Connection conexion;
    private Conexion con;

    public void UsuarioDAO() {
        con = new Conexion();
        conexion = con.getConexion();
    }

    public boolean login(String usuario, String password) {
        UsuarioDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conexion.prepareStatement("SELECT * FROM `bawuh1cvaadk7k8ml9wu`.`usuario` WHERE `usuario`.`usuario` = ? AND `usuario`.`password` = ?;");
            ps.setString(1, usuario);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                ps.close();
                rs.close();
                conexion.close();
                con.cerrarConexion();
                return true;
            } else {
                ps.close();
                rs.close();
                conexion.close();
                con.cerrarConexion();
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public Usuario obtenerSesion(String usuario, String password) {
        UsuarioDAO();
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conexion.prepareStatement("SELECT `usuario`.`tipo` FROM `bawuh1cvaadk7k8ml9wu`.`usuario` WHERE `usuario`.`usuario` = ? AND `usuario`.`password` = ?;");
            ps.setString(1, usuario);
            ps.setString(2, password);
            rs = ps.executeQuery();

            Usuario user = null;

            while (rs.next()) {
                String tipo = rs.getString("tipo");

                user = new Usuario(0, usuario, password, tipo);
            }

            ps.close();
            rs.close();
            conexion.close();
            con.cerrarConexion();
            return user;
        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }
    
}
