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

    Connection conexion;

    public UsuarioDAO() {
        Conexion con = new Conexion();
        conexion = con.getConexion();
    }

    public boolean login(String usuario, String password) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conexion.prepareStatement("SELECT * FROM `serviciomedico`.`usuario` WHERE `usuario`.`usuario` = ? AND `usuario`.`password` = ?;");
            ps.setString(1, usuario);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if(rs.next()){
                return true;
            } else{
                return false;
            }
        } catch (SQLException e) {
               System.out.println(e.toString());
               return false;
        }
    }
}
