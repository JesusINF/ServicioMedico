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
    
    public Medico obtenerInfo(String nombre) {
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
                    + "WHERE `medicos`.`nombre` = ?;");
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            Medico medico = null;

            while (rs.next()) {
                int id = rs.getInt("idmedicos");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String cp = rs.getString("cp");
                String curp = rs.getString("curp");
                String nss = rs.getString("nss");
                String cedula = rs.getString("cedula");
                String tipo = rs.getString("tipo");
                Date alta = rs.getDate("alta");
                Date baja = rs.getDate("baja");
                int idUsuario = rs.getInt("usuario_idusuario");
                
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
    
    public ArrayList<String> nombresMedicos(){
        MedicoDAO();

        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList<String> nombres = new ArrayList<>();
        
        try {
            ps = conexion.prepareStatement("SELECT `medicos`.`nombre`"
                    + "FROM `bawuh1cvaadk7k8ml9wu`.`medicos`;");
            
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                nombres.add(rs.getString("nombre"));
            }
            
            rs.close();
            conexion.close();
            con.cerrarConexion();
            return nombres;
        } catch (SQLException e) {
            System.out.println(e.toString());
            con.cerrarConexion();
            return null;
        }
    }
}
