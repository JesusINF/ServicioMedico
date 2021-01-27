/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import config.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jesus
 */
public class MedicoDAO {

    private Connection conexion;
    private Conexion con;

    private void MedicoDAO() {
        con = new Conexion();
        conexion = con.getConexion();
    }

    public Medico obtenerInfoSesion(int idUsuario) {
        MedicoDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `medicos`.`idmedicos`,\n"
                    + "    `medicos`.`nombre`,\n"
                    + "    `medicos`.`direccion`,\n"
                    + "    `medicos`.`telefono`,\n"
                    + "    `medicos`.`cp`,\n"
                    + "    `medicos`.`curp`,\n"
                    + "    `medicos`.`nss`,\n"
                    + "    `medicos`.`cedula`,\n"
                    + "    `medicos`.`tipo`,\n"
                    + "    `medicos`.`alta`,\n"
                    + "    `medicos`.`baja`,\n"
                    + "    `medicos`.`usuario_idusuario`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`medicos`\n"
                    + "WHERE `medicos`.`usuario_idusuario` = ?;");
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            Medico medico = null;

            while (rs.next()) {
                int id = rs.getInt("idmedicos");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String cp = rs.getString("cp");
                String curp = rs.getString("curp");
                String nss = rs.getString("nss");
                String cedula = rs.getString("cedula");
                String tipo = rs.getString("tipo");
                Date alta = rs.getDate("alta");
                Date baja = rs.getDate("baja");

                medico = new Medico(id, nombre, direccion, telefono, cp, curp, nss, cedula, tipo, alta, baja, idUsuario);
            }

            rs.close();
            conexion.close();
            con.cerrarConexion();
            return medico;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }

    public Medico obtenerInfo(String nss) {
        MedicoDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `medicos`.`idmedicos`,\n"
                    + "    `medicos`.`nombre`,\n"
                    + "    `medicos`.`direccion`,\n"
                    + "    `medicos`.`telefono`,\n"
                    + "    `medicos`.`cp`,\n"
                    + "    `medicos`.`curp`,\n"
                    + "    `medicos`.`nss`,\n"
                    + "    `medicos`.`cedula`,\n"
                    + "    `medicos`.`tipo`,\n"
                    + "    `medicos`.`alta`,\n"
                    + "    `medicos`.`baja`,\n"
                    + "    `medicos`.`usuario_idusuario`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`medicos`\n"
                    + "WHERE `medicos`.`nss` = ?;");
            ps.setString(1, nss);
            rs = ps.executeQuery();

            Medico medico = null;

            while (rs.next()) {
                int id = rs.getInt("idmedicos");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String cp = rs.getString("cp");
                String curp = rs.getString("curp");
                String cedula = rs.getString("cedula");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                int idUsuario = rs.getInt("usuario_idusuario");

                medico = new Medico(id, nombre, direccion, telefono, cp, curp, nss, cedula, tipo, null, null, idUsuario);
            }

            rs.close();
            conexion.close();
            con.cerrarConexion();
            return medico;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }

    public ArrayList<String> listaMedicos() {
        MedicoDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `medicos`.`nombre`, `medicos`.`nss`"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`medicos`;");
            rs = ps.executeQuery();

            ArrayList<String> nombre = new ArrayList<>();

            while (rs.next()) {
                nombre.add(rs.getString("nombre") + " - " + rs.getString("nss"));
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

    public boolean registroMedico(Medico medico, Usuario usuario) {

        UsuarioDAO controlador = new UsuarioDAO();
        boolean insertoUsuario;
        insertoUsuario = controlador.crearUsuario(usuario);

        if (!insertoUsuario) {
            return false;
        }

        Usuario usuarioId = null;
        usuarioId = controlador.obtenerSesion(usuario.getUsuario(), usuario.getPassword());

        if (usuarioId == null) {
            return false;
        }

        MedicoDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("INSERT INTO `bawuh1cvaadk7k8ml9wu`.`medicos`\n"
                    + "(`nombre`,\n"
                    + "`direccion`,\n"
                    + "`telefono`,\n"
                    + "`cp`,\n"
                    + "`curp`,\n"
                    + "`nss`,\n"
                    + "`cedula`,\n"
                    + "`tipo`,\n"
                    + "`alta`,\n"
                    + "`baja`,\n"
                    + "`usuario_idusuario`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);");
            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getDireccion());
            ps.setString(3, medico.getTelefono());
            ps.setString(4, medico.getCp());
            ps.setString(5, medico.getCurp());
            ps.setString(6, medico.getNss());
            ps.setString(7, medico.getCedula());
            ps.setString(8, medico.getTipo());
            ps.setDate(9, medico.getAlta());
            ps.setDate(10, medico.getBaja());
            ps.setInt(11, usuarioId.getId());

            boolean validacion;

            int i;
            i = ps.executeUpdate();

            if (i == 0) {
                validacion = false;
            } else {
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

    public boolean restaurarMedico(Medico medico, Usuario usuario) {

        MedicoDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("INSERT INTO `bawuh1cvaadk7k8ml9wu`.`medicos`\n"
                    + "(`nombre`,\n"
                    + "`direccion`,\n"
                    + "`telefono`,\n"
                    + "`cp`,\n"
                    + "`curp`,\n"
                    + "`nss`,\n"
                    + "`cedula`,\n"
                    + "`tipo`,\n"
                    + "`alta`,\n"
                    + "`baja`,\n"
                    + "`usuario_idusuario`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);");
            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getDireccion());
            ps.setString(3, medico.getTelefono());
            ps.setString(4, medico.getCp());
            ps.setString(5, medico.getCurp());
            ps.setString(6, medico.getNss());
            ps.setString(7, medico.getCedula());
            ps.setString(8, medico.getTipo());
            ps.setDate(9, medico.getAlta());
            ps.setDate(10, medico.getBaja());
            ps.setInt(11, usuario.getId());

            boolean validacion;

            int i;
            i = ps.executeUpdate();

            if (i == 0) {
                validacion = false;
            } else {
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

    public boolean ActualizaMedico(Medico medico, Usuario usuario, Usuario usuarioAntiguo) {

        UsuarioDAO controlador = new UsuarioDAO();
        boolean actualizoUsuario;
        actualizoUsuario = controlador.actualizarUsuario(usuarioAntiguo.getId(), usuario);

        if (!actualizoUsuario) {
            return false;
        }

        Usuario usuarioId = null;
        usuarioId = controlador.obtenerUsuario(usuarioAntiguo.getId());

        MedicoDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("UPDATE `bawuh1cvaadk7k8ml9wu`.`medicos`\n"
                    + "SET\n"
                    + "`nombre` = ?,\n"
                    + "`direccion` = ?,\n"
                    + "`telefono` = ?,\n"
                    + "`cp` = ?,\n"
                    + "`curp` = ?,\n"
                    + "`nss` = ?,\n"
                    + "`cedula` = ?,\n"
                    + "`tipo` = ?\n"
                    + "WHERE `usuario_idusuario` = ?;");
            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getDireccion());
            ps.setString(3, medico.getTelefono());
            ps.setString(4, medico.getCp());
            ps.setString(5, medico.getCurp());
            ps.setString(6, medico.getNss());
            ps.setString(7, medico.getCedula());
            ps.setString(8, medico.getTipo());
            ps.setInt(9, usuarioId.getId());

            boolean validacion;

            int i;
            i = ps.executeUpdate();

            if (i == 0) {
                validacion = false;
            } else {
                validacion = true;
            }

            if (!actualizoUsuario || !validacion) {
                if (actualizoUsuario) {
                    controlador.actualizarUsuario(usuarioAntiguo.getId(), usuarioAntiguo);
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

    public boolean EliminarMedico(String nss, String usuario) {

        UsuarioDAO controlador = new UsuarioDAO();

        Medico medicoResp;
        medicoResp = obtenerInfo(nss);

        if (medicoResp == null) {
            return false;
        }

        Usuario usuarioResp;
        usuarioResp = controlador.obtenerUsuario(medicoResp.getIdUsuario());

        if (usuarioResp == null) {
            return false;
        }

        MedicoDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("DELETE FROM `bawuh1cvaadk7k8ml9wu`.`medicos`\n"
                    + "WHERE `nss` = ?;");
            ps.setString(1, nss);

            boolean validacion;

            int i;
            i = ps.executeUpdate();

            if (i == 0) {
                validacion = false;
            } else {
                validacion = true;
            }

            boolean eliminoUsuario;
            eliminoUsuario = controlador.eliminarUsuario(usuario);

            if (eliminoUsuario) {
                ps.close();
                conexion.close();
                con.cerrarConexion();
                return true;
            } else {
                if (validacion) {
                    restaurarMedico(medicoResp, usuarioResp);
                }
                ps.close();
                conexion.close();
                con.cerrarConexion();
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return false;
        }
    }
}
