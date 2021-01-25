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
import java.util.ArrayList;

/**
 *
 * @author jesus
 */
public class EmpleadoDAO {

    private Connection conexion;
    private Conexion con;

    private void EmpleadoDAO() {
        con = new Conexion();
        conexion = con.getConexion();
    }

    public Empleado obtenerInfoSesion(int idUsuario) {
        EmpleadoDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `empleados`.`idempleados`,\n"
                    + "    `empleados`.`nombre`,\n"
                    + "    `empleados`.`direccion`,\n"
                    + "    `empleados`.`telefono`,\n"
                    + "    `empleados`.`cp`,\n"
                    + "    `empleados`.`curp`,\n"
                    + "    `empleados`.`nss`,\n"
                    + "    `empleados`.`tipo`,\n"
                    + "    `empleados`.`usuario_idusuario`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`empleados`\n"
                    + "WHERE `empleados`.`usuario_idusuario` = ?;");
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            Empleado empleado = null;

            while (rs.next()) {
                int id = rs.getInt("idempleados");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String cp = rs.getString("cp");
                String curp = rs.getString("curp");
                String nss = rs.getString("nss");
                String tipo = rs.getString("tipo");

                empleado = new Empleado(id, nombre, direccion, telefono, cp, curp, nss, tipo, idUsuario);
            }

            rs.close();
            conexion.close();
            con.cerrarConexion();
            return empleado;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }

    public Empleado obtenerInfo(String nombre) {
        EmpleadoDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `empleados`.`idempleados`,\n"
                    + "    `empleados`.`nombre`,\n"
                    + "    `empleados`.`direccion`,\n"
                    + "    `empleados`.`telefono`,\n"
                    + "    `empleados`.`cp`,\n"
                    + "    `empleados`.`curp`,\n"
                    + "    `empleados`.`nss`,\n"
                    + "    `empleados`.`tipo`,\n"
                    + "    `empleados`.`usuario_idusuario`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`empleados`\n"
                    + "WHERE `empleados`.`nombre` = ?;");
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            Empleado empleado = null;

            while (rs.next()) {
                int id = rs.getInt("idempleados");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String cp = rs.getString("cp");
                String curp = rs.getString("curp");
                String nss = rs.getString("nss");
                String tipo = rs.getString("tipo");
                int idUsuario = rs.getInt("usuario_idusuario");

                empleado = new Empleado(id, nombre, direccion, telefono, cp, curp, nss, tipo, idUsuario);
            }

            rs.close();
            conexion.close();
            con.cerrarConexion();
            return empleado;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }

    public ArrayList<String> listaEmpleados() {
        EmpleadoDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `empleados`.`nombre`"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`empleados`;");
            rs = ps.executeQuery();

            Empleado empleado = null;

            ArrayList<String> nombre = new ArrayList<>();

            while (rs.next()) {
                nombre.add(rs.getString("nombre"));
            }

            rs.close();
            conexion.close();
            con.cerrarConexion();
            return nombre;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }

    public boolean registroEmpleado(Empleado empleado, Usuario usuario) {

        UsuarioDAO controlador = new UsuarioDAO();
        boolean insertoUsuario;
        insertoUsuario = controlador.crearUsuario(usuario);
        
        Usuario usuarioId = null;
        usuarioId = controlador.obtenerSesion(usuario.getUsuario(), usuario.getPassword());

        EmpleadoDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("INSERT INTO `bawuh1cvaadk7k8ml9wu`.`empleados`\n"
                    + "(`nombre`,\n"
                    + "`direccion`,\n"
                    + "`telefono`,\n"
                    + "`cp`,\n"
                    + "`curp`,\n"
                    + "`nss`,\n"
                    + "`tipo`,\n"
                    + "`usuario_idusuario`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);");
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getDireccion());
            ps.setString(3, empleado.getTelefono());
            ps.setString(4, empleado.getCp());
            ps.setString(5, empleado.getCurp());
            ps.setString(6, empleado.getNss());
            ps.setString(7, empleado.getTipo());
            ps.setInt(8, usuarioId.getId());

            boolean validacion;
            
            int i;
            i = ps.executeUpdate();
            
            if(i == 0){
                validacion = false;
            } else{
                validacion = true;
            }

            if (!insertoUsuario || !validacion) {
                if (insertoUsuario) {
                    controlador.eliminarUsuario(usuarioId.getUsuario());
                }
                ps.close();
                conexion.close();
                con.cerrarConexion();
                return false;
            } else {
                ps.close();
                conexion.close();
                con.cerrarConexion();
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return false;
        }
    }
}