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
public class VacacionDAO {

    private Connection conexion;
    private Conexion con;

    private void VacacionesDAO() {
        con = new Conexion();
        conexion = con.getConexion();
    }

    public boolean registrarVacacion(String nssPersonal, String fecha) {
        EmpleadoDAO controladorEmp = new EmpleadoDAO();
        MedicoDAO controladorMed = new MedicoDAO();

        Empleado empleado = controladorEmp.obtenerInfo(nssPersonal);
        Medico medico = controladorMed.obtenerInfo(nssPersonal);

        VacacionesDAO();

        PreparedStatement ps = null;

        try {
            if (medico != null) {
                ps = conexion.prepareStatement("INSERT INTO `bawuh1cvaadk7k8ml9wu`.`vacacionesm`\n"
                        + "(`fecha`,\n"
                        + "`medicos_idmedicos`)\n"
                        + "VALUES\n"
                        + "(?,\n"
                        + "?);");
                ps.setDate(1, Date.valueOf(fecha));
                ps.setInt(2, medico.getId());

                int i = 0;
                i = ps.executeUpdate();

                ps.close();
                conexion.close();
                con.cerrarConexion();

                return i != 0;

            } else if (empleado != null) {
                ps = conexion.prepareStatement("INSERT INTO `bawuh1cvaadk7k8ml9wu`.`vacacionese`\n"
                        + "(`fecha`,\n"
                        + "`empleados_idempleados`)\n"
                        + "VALUES\n"
                        + "(?,\n"
                        + "?);");
                ps.setDate(1, Date.valueOf(fecha));
                ps.setInt(2, empleado.getId());

                int i = 1;
                i = ps.executeUpdate();

                ps.close();
                conexion.close();
                con.cerrarConexion();

                return i != 0;
            } else {
                con.cerrarConexion();
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return false;
        }

    }

    public ArrayList<String> listaVacacionesEmp() {
        VacacionesDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `vacacionese`.`fecha`,\n"
                    + "    `empleados`.`nombre`,\n"
                    + "    `empleados`.`nss`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`vacacionese`\n"
                    + "INNER JOIN `bawuh1cvaadk7k8ml9wu`.`empleados`\n"
                    + "ON `vacacionese`.`empleados_idempleados` = `empleados`.`idempleados`;");
            rs = ps.executeQuery();

            ArrayList<String> nombre = new ArrayList<>();

            while (rs.next()) {
                nombre.add(rs.getString("nombre") + " - " + rs.getString("nss") + " - Fecha de Vacación: " + rs.getDate("fecha").toString());
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

    public ArrayList<String> listaVacacionesMed() {
        VacacionesDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `vacacionesm`.`fecha`,\n"
                    + "    `medicos`.`nombre`,\n"
                    + "    `medicos`.`nss`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`vacacionesm`\n"
                    + "INNER JOIN `bawuh1cvaadk7k8ml9wu`.`medicos`\n"
                    + "ON `vacacionesm`.`medicos_idmedicos` = `medicos`.`idmedicos`;");
            rs = ps.executeQuery();

            ArrayList<String> nombre = new ArrayList<>();

            while (rs.next()) {
                nombre.add(rs.getString("nombre") + " - " + rs.getString("nss") + " - Fecha de Vacación: " + rs.getDate("fecha").toString());
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

    public Vacacion obtenerVacacionEmp(int idPersonal, String fecha) {
        VacacionesDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `vacacionese`.`idvacacionese`,\n"
                    + "    `vacacionese`.`fecha`,\n"
                    + "    `vacacionese`.`empleados_idempleados`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`vacacionese`\n"
                    + "WHERE `vacacionese`.`empleados_idempleados` = ? AND `vacacionese`.`fecha` = ?;");
            ps.setInt(1, idPersonal);
            ps.setDate(2, Date.valueOf(fecha));
            rs = ps.executeQuery();

            Vacacion vacacion = null;

            while (rs.next()) {
                int id = rs.getInt("idvacacionese");
                int idEmp = rs.getInt("empleados_idempleados");

                vacacion = new Vacacion(id, fecha, idEmp);
            }

            rs.close();
            conexion.close();
            con.cerrarConexion();
            return vacacion;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }

    public Vacacion obtenerVacacionMed(int idPersonal, String fecha) {
        VacacionesDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT `vacacionesm`.`idvacaciones`,\n"
                    + "    `vacacionesm`.`fecha`,\n"
                    + "    `vacacionesm`.`medicos_idmedicos`\n"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`vacacionesm`\n"
                    + "WHERE `vacacionesm`.`medicos_idmedicos` = ? AND `vacacionesm`.`fecha` = ?;");
            ps.setInt(1, idPersonal);
            ps.setDate(2, Date.valueOf(fecha));
            rs = ps.executeQuery();

            Vacacion vacacion = null;

            while (rs.next()) {
                int id = rs.getInt("idvacaciones");
                int idEmp = rs.getInt("medicos_idmedicos");

                vacacion = new Vacacion(id, fecha, idEmp);
            }

            rs.close();
            conexion.close();
            con.cerrarConexion();
            return vacacion;

        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }

    public boolean eliminarVacacionEmp(int idPersonal, String Fecha) {
        VacacionesDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("DELETE FROM `bawuh1cvaadk7k8ml9wu`.`vacacionese`\n"
                    + "WHERE `vacacionese`.`empleados_idempleados` = ? AND `vacacionese`.`fecha` = ?;");
            ps.setInt(1, idPersonal);
            ps.setDate(2, Date.valueOf(Fecha));

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

    public boolean eliminarVacacionMed(int idPersonal, String Fecha) {
        VacacionesDAO();

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("DELETE FROM `bawuh1cvaadk7k8ml9wu`.`vacacionesm`\n"
                    + "WHERE `vacacionesm`.`medicos_idmedicos` = ? AND `vacacionesm`.`fecha` = ?;");
            ps.setInt(1, idPersonal);
            ps.setDate(2, Date.valueOf(Fecha));

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
