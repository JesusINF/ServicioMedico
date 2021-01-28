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
public class PacienteDAO {

    private Connection conexion;
    private Conexion con;

    private void PacientesDAO() {
        con = new Conexion();
        conexion = con.getConexion();
    }

    public Paciente obtenerInfo(String nss) {
        PacientesDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `pacientes`.`idpacientes`,\n"
                    + "    `pacientes`.`nombre`,\n"
                    + "    `pacientes`.`direccion`,\n"
                    + "    `pacientes`.`telefono`,\n"
                    + "    `pacientes`.`cp`,\n"
                    + "    `pacientes`.`curp`,\n"
                    + "    `pacientes`.`nss`,\n"
                    + "    `pacientes`.`padecimiento`,\n"
                    + "    `pacientes`.`medicos_idmedicos`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`pacientes`"
                    + "WHERE `pacientes`.`nss` = ?;");
            ps.setString(1, nss);
            rs = ps.executeQuery();

            Paciente paciente = null;

            while (rs.next()) {
                int id = rs.getInt("idpacientes");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String cp = rs.getString("cp");
                String curp = rs.getString("curp");
                String nombre = rs.getString("nombre");
                String padecimiento = rs.getString("padecimiento");
                int idMedico = rs.getInt("medicos_idmedicos");

                paciente = new Paciente(id, nombre, direccion, telefono, cp, curp, nss, padecimiento, idMedico);
            }

            rs.close();
            conexion.close();
            con.cerrarConexion();
            return paciente;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }

    public ArrayList<String> listaPacientes() {
        PacientesDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `pacientes`.`nombre`, `pacientes`.`nss`"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`pacientes`;");
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

    public boolean registroPaciente(Paciente paciente) {
        PacientesDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("INSERT INTO `bawuh1cvaadk7k8ml9wu`.`pacientes`\n"
                    + "(`nombre`,\n"
                    + "`direccion`,\n"
                    + "`telefono`,\n"
                    + "`cp`,\n"
                    + "`curp`,\n"
                    + "`nss`,\n"
                    + "`padecimiento`,\n"
                    + "`medicos_idmedicos`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);");
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getDireccion());
            ps.setString(3, paciente.getTelefono());
            ps.setString(4, paciente.getCp());
            ps.setString(5, paciente.getCurp());
            ps.setString(6, paciente.getNss());
            ps.setString(7, paciente.getPadecimiento());
            ps.setInt(8, paciente.getIdMedico());

            int i;
            i = ps.executeUpdate();

            ps.close();
            conexion.close();
            con.cerrarConexion();

            return i != 0;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return false;
        }
    }

    public boolean ActualizarPaciente(Paciente paciente, String nss) {
        PacientesDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("UPDATE `bawuh1cvaadk7k8ml9wu`.`pacientes`\n"
                    + "SET\n"
                    + "`nombre` = ?,\n"
                    + "`direccion` = ?,\n"
                    + "`telefono` = ?,\n"
                    + "`cp` = ?,\n"
                    + "`curp` = ?,\n"
                    + "`nss` = ?,\n"
                    + "`padecimiento` = ?,\n"
                    + "`medicos_idmedicos` = ?\n"
                    + "WHERE `nss` = ?;");
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getDireccion());
            ps.setString(3, paciente.getTelefono());
            ps.setString(4, paciente.getCp());
            ps.setString(5, paciente.getCurp());
            ps.setString(6, paciente.getNss());
            ps.setString(7, paciente.getPadecimiento());
            ps.setInt(8, paciente.getIdMedico());
            ps.setString(9, nss);

            int i;
            i = ps.executeUpdate();

            ps.close();
            conexion.close();
            con.cerrarConexion();
            return i != 0;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return false;
        }
    }

    public boolean EliminarPaciente(String nss) {
        PacientesDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("DELETE FROM `bawuh1cvaadk7k8ml9wu`.`pacientes`\n"
                    + "WHERE `nss` = ?;");
            ps.setString(1, nss);

            int i;
            i = ps.executeUpdate();

            ps.close();
            conexion.close();
            con.cerrarConexion();

            if (i == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return false;
        }
    }
}
