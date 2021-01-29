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
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author jesus
 */
public class ConsultaDAO {

    private Connection conexion;
    private Conexion con;

    private void ConsultaDAO() {
        con = new Conexion();
        conexion = con.getConexion();
    }

    public boolean registrarConsulta(Consulta consulta) {
        ConsultaDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("INSERT INTO `bawuh1cvaadk7k8ml9wu`.`consultas`\n"
                    + "(`medicos_idmedicos`,\n"
                    + "`pacientes_idpacientes`,\n"
                    + "`inicio`,\n"
                    + "`fin`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);");
            ps.setInt(1, consulta.getIdMedico());
            ps.setInt(2, consulta.getIdPaciente());
            ps.setTimestamp(3, Timestamp.valueOf(consulta.getInicio()));
            ps.setTimestamp(4, Timestamp.valueOf(consulta.getFin()));

            int i = 0;

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

    public ArrayList<String> listaConsulta() {
        ConsultaDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `pacientes`.`nombre`,\n"
                    + "	`pacientes`.`nss`,\n"
                    + "	`consultas`.`inicio`,\n"
                    + "    `consultas`.`fin`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`consultas`\n"
                    + "INNER JOIN `bawuh1cvaadk7k8ml9wu`.`pacientes`\n"
                    + "ON `consultas`.`pacientes_idpacientes` = `pacientes`.`idpacientes`;");
            rs = ps.executeQuery();

            ArrayList<String> nombre = new ArrayList<>();

            while (rs.next()) {
                nombre.add(rs.getString("nombre") + " - " + rs.getString("nss") + " - Fecha de Cita: " + rs.getTimestamp("inicio").toString().replace(".0", ""));
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

    public Consulta obtenerInfo(int idPaciente, String Inicio) {
        ConsultaDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `consultas`.`idconsultas`,\n"
                    + "    `consultas`.`medicos_idmedicos`,\n"
                    + "    `consultas`.`pacientes_idpacientes`,\n"
                    + "    `consultas`.`inicio`,\n"
                    + "    `consultas`.`fin`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`consultas`\n"
                    + "WHERE `consultas`.`pacientes_idpacientes` = ? AND `consultas`.`inicio` = ?;");
            ps.setInt(1, idPaciente);
            ps.setTimestamp(2, Timestamp.valueOf(Inicio));
            rs = ps.executeQuery();

            Consulta consulta = null;

            while (rs.next()) {
                int id = rs.getInt("idconsultas");
                int idMedico = rs.getInt("medicos_idmedicos");
                Timestamp inicio = rs.getTimestamp("inicio");
                Timestamp fin = rs.getTimestamp("fin");

                consulta = new Consulta(id, idMedico, idPaciente, inicio.toString().replace(' ', 'T').replace(":00.0", ""), fin.toString().replace(' ', 'T').replace(":00.0", ""));
            }

            rs.close();
            conexion.close();
            con.cerrarConexion();
            return consulta;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }

    public boolean ActualizarConsulta(Consulta consulta, int idAntiguo) {
        ConsultaDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("UPDATE `bawuh1cvaadk7k8ml9wu`.`consultas`\n"
                    + "SET\n"
                    + "`medicos_idmedicos` = ?,\n"
                    + "`pacientes_idpacientes` = ?,\n"
                    + "`inicio` = ?,\n"
                    + "`fin` = ?\n"
                    + "WHERE `idconsultas` = ?;");
            ps.setInt(1, consulta.getIdMedico());
            ps.setInt(2, consulta.getIdPaciente());
            ps.setTimestamp(3, Timestamp.valueOf(consulta.getInicio()));
            ps.setTimestamp(4, Timestamp.valueOf(consulta.getFin()));
            ps.setInt(5, idAntiguo);

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

    public boolean eliminarConsulta(Consulta consulta) {
        ConsultaDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("DELETE FROM `bawuh1cvaadk7k8ml9wu`.`consultas`\n"
                    + "WHERE `medicos_idmedicos` = ? AND\n"
                    + "`pacientes_idpacientes` = ? AND\n"
                    + "`inicio` = ? AND\n"
                    + "`fin` = ?;");
            ps.setInt(1, consulta.getIdMedico());
            ps.setInt(2, consulta.getIdPaciente());
            ps.setTimestamp(3, Timestamp.valueOf(consulta.getInicio()));
            ps.setTimestamp(4, Timestamp.valueOf(consulta.getFin()));

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
}
