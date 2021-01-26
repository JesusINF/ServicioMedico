/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import config.Conexion;
import config.EncriptadorAES;
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

    private void UsuarioDAO() {
        con = new Conexion();
        conexion = con.getConexion();
    }

    public boolean login(String usuario, String password) {
        UsuarioDAO();

        EncriptadorAES encriptadorAES = new EncriptadorAES();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conexion.prepareStatement("SELECT * FROM `bawuh1cvaadk7k8ml9wu`.`usuario` WHERE `usuario`.`usuario` = ? AND `usuario`.`password` = ?;");
            ps.setString(1, usuario);
            ps.setString(2, encriptadorAES.encriptar(password));
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
        
        EncriptadorAES encriptadorAES = new EncriptadorAES();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conexion.prepareStatement("SELECT `usuario`.`idusuario`, `usuario`.`tipo` FROM `bawuh1cvaadk7k8ml9wu`.`usuario` WHERE `usuario`.`usuario` = ? AND `usuario`.`password` = ?;");
            ps.setString(1, usuario);
            ps.setString(2, encriptadorAES.encriptar(password));
            rs = ps.executeQuery();

            Usuario user = null;

            while (rs.next()) {
                int id = rs.getInt("idusuario");
                String tipo = rs.getString("tipo");

                user = new Usuario(id, usuario, password, tipo);
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
    
    public Usuario obtenerUsuario(int id) {
        UsuarioDAO();
        
        EncriptadorAES encriptadorAES = new EncriptadorAES();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conexion.prepareStatement("SELECT `usuario`.`idusuario`, `usuario`.`usuario`, `usuario`.`password`,  `usuario`.`tipo` FROM `bawuh1cvaadk7k8ml9wu`.`usuario` WHERE `usuario`.`idusuario` = ?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            Usuario user = null;

            while (rs.next()) {
                String usuario = rs.getString("usuario");
                String password = encriptadorAES.desencriptar(rs.getString("password"));
                String tipo = rs.getString("tipo");

                user = new Usuario(id, usuario, password, tipo);
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

    public boolean crearUsuario(Usuario usuario) {
        UsuarioDAO();

        EncriptadorAES encriptadorAES = new EncriptadorAES();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("INSERT INTO `bawuh1cvaadk7k8ml9wu`.`usuario`\n"
                    + "(`usuario`,\n"
                    + "`password`,\n"
                    + "`tipo`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?);");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, encriptadorAES.encriptar(usuario.getPassword()));
            ps.setString(3, usuario.getTipo());

            if (encriptadorAES.encriptar(usuario.getPassword()) == null) {
                ps.close();
                conexion.close();
                con.cerrarConexion();
                return false;
            }

            boolean validacion; 
            int i;
            i = ps.executeUpdate();
            
            if(i == 0){
                validacion = false;
            } else{
                validacion = true;
            }

            ps.close();
            conexion.close();
            con.cerrarConexion();
            return validacion;
        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return false;
        }
    }

    public boolean actualizarUsuario(int usuarioAntiguo, Usuario usuario) {
        UsuarioDAO();

        EncriptadorAES encriptadorAES = new EncriptadorAES();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("UPDATE `bawuh1cvaadk7k8ml9wu`.`usuario`\n"
                    + "SET\n"
                    + "`usuario` = ?,\n"
                    + "`password` = ?,\n"
                    + "`tipo` = ?"
                    + "WHERE `idusuario` = ?;");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, encriptadorAES.encriptar(usuario.getPassword()));
            ps.setString(3, usuario.getTipo());
            ps.setInt(4, usuarioAntiguo);

            if (encriptadorAES.encriptar(usuario.getPassword()) == null) {
                ps.close();
                conexion.close();
                con.cerrarConexion();
                return false;
            }

            boolean validacion; 
            int i;
            i = ps.executeUpdate();
            
            if(i == 0){
                validacion = false;
            } else{
                validacion = true;
            }

            ps.close();
            conexion.close();
            con.cerrarConexion();
            return validacion;
        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return false;
        }
    }

    public boolean eliminarUsuario(String usuario) {
        UsuarioDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("DELETE FROM `bawuh1cvaadk7k8ml9wu`.`usuario`\n"
                    + "WHERE `usuario` = ?;");
            ps.setString(1, usuario);

            boolean validacion;
            
            int i;
            i = ps.executeUpdate();
            
            if(i == 0){
                validacion = false;
            } else{
                validacion = true;
            }

            ps.close();
            conexion.close();
            con.cerrarConexion();
            return validacion;
        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return false;
        }
    }

}
