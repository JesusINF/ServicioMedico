/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author jesus
 */
public class Vacacion {
    private int id;
    private String fecha;
    private int idEmpMed;

    public Vacacion(int id, String fecha, int idEmpMed) {
        this.id = id;
        this.fecha = fecha;
        this.idEmpMed = idEmpMed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdEmpMed() {
        return idEmpMed;
    }

    public void setIdEmpMed(int idEmpMed) {
        this.idEmpMed = idEmpMed;
    }
    
    
}
