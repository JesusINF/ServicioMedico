/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Empleado;
import modelos.EmpleadoDAO;
import modelos.Medico;
import modelos.MedicoDAO;
import modelos.Usuario;
import modelos.Vacacion;
import modelos.VacacionDAO;

/**
 *
 * @author jesus
 */
@WebServlet(name = "MenuVacaciones", urlPatterns = {"/MenuVacaciones"})
public class MenuVacaciones extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MenuVacaciones</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MenuVacaciones at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion;
        RequestDispatcher dispatcher = null;
        HttpSession sesion = request.getSession();
        Usuario user = (Usuario) sesion.getAttribute("login");

        MedicoDAO controladorMed = new MedicoDAO();
        EmpleadoDAO controladorEmp = new EmpleadoDAO();
        VacacionDAO controladorVac = new VacacionDAO();

        accion = request.getParameter("accion");

        if (user == null) {
            dispatcher = request.getRequestDispatcher("error.jsp");
        } else {
            request.setAttribute("Nivel", user.getTipo());
            if (accion == null || accion.isEmpty()) {
                if (user.getTipo().equalsIgnoreCase("Administrador") || user.getTipo().equalsIgnoreCase("Programador")) {
                    dispatcher = request.getRequestDispatcher("Ventanas/Vacaciones.jsp");
                } else if (user.getTipo().equalsIgnoreCase("Medico")) {
                    dispatcher = request.getRequestDispatcher("error.jsp");
                } else {
                    dispatcher = request.getRequestDispatcher("error.jsp");
                }

            } else if ("Logout".equals(accion)) {
                sesion.invalidate();
                response.sendRedirect("Login");
                return;
            } else if ("Registrar".equals(accion)) {

                ArrayList<String> nombres1 = null;
                nombres1 = controladorMed.listaMedicos();

                ArrayList<String> nombres2 = null;
                nombres2 = controladorEmp.listaEmpleados();

                ArrayList<String> nombresAll = new ArrayList<>();
                nombresAll.addAll(nombres1);
                nombresAll.addAll(nombres2);

                request.setAttribute("Lista", nombresAll);
                dispatcher = request.getRequestDispatcher("Ventanas/VacacionesRegistro.jsp");
            } else if ("Insert".equals(accion)) {
                try {

                    String NssPersonal = request.getParameter("personal");
                    String fecha = request.getParameter("fecha");

                    String[] partes = NssPersonal.split(" - ");

                    NssPersonal = partes[partes.length - 1];

                    boolean validar = false;
                    validar = controladorVac.registrarVacacion(NssPersonal, fecha);

                    request.setAttribute("pop", "popOpen");
                    if (validar) {
                        dispatcher = request.getRequestDispatcher("Ventanas/Vacaciones.jsp");
                    } else {
                        ArrayList<String> nombres1 = null;
                        nombres1 = controladorMed.listaMedicos();

                        ArrayList<String> nombres2 = null;
                        nombres2 = controladorEmp.listaEmpleados();

                        ArrayList<String> nombresAll = new ArrayList<>();
                        nombresAll.addAll(nombres1);
                        nombresAll.addAll(nombres2);

                        request.setAttribute("Lista", nombresAll);
                        dispatcher = request.getRequestDispatcher("Ventanas/VacacionesRegistro.jsp");
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop", "popOpen");
                    ArrayList<String> nombres1 = null;
                    nombres1 = controladorMed.listaMedicos();

                    ArrayList<String> nombres2 = null;
                    nombres2 = controladorEmp.listaEmpleados();

                    ArrayList<String> nombresAll = new ArrayList<>();
                    nombresAll.addAll(nombres1);
                    nombresAll.addAll(nombres2);

                    request.setAttribute("Lista", nombresAll);
                    dispatcher = request.getRequestDispatcher("Ventanas/VacacionesRegistro.jsp");
                }

            } else if ("Eliminar".equals(accion)) {
                request.setAttribute("Busca", "Busca");

                ArrayList<String> nombres = new ArrayList<>();
                nombres.addAll(controladorVac.listaVacacionesEmp());
                nombres.addAll(controladorVac.listaVacacionesMed());
                request.setAttribute("Lista", nombres);
                dispatcher = request.getRequestDispatcher("Ventanas/VacacionesEliminacion.jsp");
            } else if ("BuscaElimina".equalsIgnoreCase(accion)) {
                try {
                    String NssPersonal = request.getParameter("browsers");
                    String Fecha = null;

                    String[] partes = NssPersonal.split(" - ");
                    String[] partes2 = NssPersonal.split(": ");

                    if (partes.length == 1) {
                        NssPersonal = " ";
                    } else {
                        NssPersonal = partes[1];
                    }

                    Fecha = partes2[partes2.length - 1];

                    Empleado empleado = null;
                    empleado = controladorEmp.obtenerInfo(NssPersonal);

                    Medico medico = null;
                    medico = controladorMed.obtenerInfo(NssPersonal);

                    Vacacion vacacion = null;

                    if (empleado != null) {
                        vacacion = controladorVac.obtenerVacacionEmp(empleado.getId(), Fecha);
                    } else if (medico != null) {
                        vacacion = controladorVac.obtenerVacacionMed(medico.getId(), Fecha);
                    } else {
                        vacacion = null;
                    }

                    if (vacacion == null) {
                        request.setAttribute("pop1", "popOpen");
                        request.setAttribute("Busca", "Busca");

                        ArrayList<String> nombres = new ArrayList<>();
                        nombres.addAll(controladorVac.listaVacacionesEmp());
                        nombres.addAll(controladorVac.listaVacacionesMed());
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/VacacionesEliminacion.jsp");
                    } else {

                        request.setAttribute("Emp", vacacion);

                        if (medico != null) {
                            request.setAttribute("DatoPersonal", medico.getNombre() + " - " + medico.getNss());
                        } else if (empleado != null) {
                            request.setAttribute("DatoPersonal", empleado.getNombre() + " - " + empleado.getNss());
                        }

                        ArrayList<String> nombres = new ArrayList<>();
                        nombres.addAll(controladorVac.listaVacacionesEmp());
                        nombres.addAll(controladorVac.listaVacacionesMed());
                        request.setAttribute("Lista", nombres);
                        request.setAttribute("Actualiza", "Actualiza");
                        dispatcher = request.getRequestDispatcher("Ventanas/VacacionesEliminacion.jsp");
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop1", "popOpen");
                    request.setAttribute("Busca", "Busca");
                    ArrayList<String> nombres = new ArrayList<>();
                    nombres.addAll(controladorVac.listaVacacionesEmp());
                    nombres.addAll(controladorVac.listaVacacionesMed());
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/VacacionesEliminacion.jsp");
                }
            } else if ("Elimina".equals(accion)) {
                try {
                    String NssPersonal = request.getParameter("personal");
                    String fecha = request.getParameter("fecha");

                    String[] partes = NssPersonal.split(" - ");

                    NssPersonal = partes[partes.length - 1];

                    Empleado empleado = null;
                    empleado = controladorEmp.obtenerInfo(NssPersonal);

                    Medico medico = null;
                    medico = controladorMed.obtenerInfo(NssPersonal);

                    boolean validar = false;

                    if (empleado != null) {
                        validar = controladorVac.eliminarVacacionEmp(empleado.getId(), fecha);
                    } else if (medico != null) {
                        validar = controladorVac.eliminarVacacionMed(medico.getId(), fecha);
                    } else {
                        validar = false;
                    }

                    request.setAttribute("pop3", "popOpen");
                    if (validar) {
                        dispatcher = request.getRequestDispatcher("Ventanas/Vacaciones.jsp");
                    } else {
                        request.setAttribute("Busca", "Busca");

                        ArrayList<String> nombres = new ArrayList<>();
                        nombres.addAll(controladorVac.listaVacacionesEmp());
                        nombres.addAll(controladorVac.listaVacacionesMed());
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/VacacionesEliminacion.jsp");
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop3", "popOpen");
                    request.setAttribute("Busca", "Busca");

                    ArrayList<String> nombres = new ArrayList<>();
                    nombres.addAll(controladorVac.listaVacacionesEmp());
                    nombres.addAll(controladorVac.listaVacacionesMed());
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/VacacionesEliminacion.jsp");
                }
            }
        }
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
