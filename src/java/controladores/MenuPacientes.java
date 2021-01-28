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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Medico;
import modelos.MedicoDAO;
import modelos.Paciente;
import modelos.PacienteDAO;
import modelos.Usuario;

/**
 *
 * @author jesus
 */
@WebServlet(name = "MenuPacientes", urlPatterns = {"/MenuPacientes"})
public class MenuPacientes extends HttpServlet {

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
            out.println("<title>Servlet MenuPacientes</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MenuPacientes at " + request.getContextPath() + "</h1>");
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

        accion = request.getParameter("accion");

        if (user == null) {
            dispatcher = request.getRequestDispatcher("error.jsp");
        } else {
            request.setAttribute("Nivel", user.getTipo());
            if (accion == null || accion.isEmpty()) {
                if (user.getTipo().equalsIgnoreCase("Administrador") || user.getTipo().equalsIgnoreCase("Programador")) {
                    dispatcher = request.getRequestDispatcher("Ventanas/Pacientes.jsp");
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
                MedicoDAO controladorMed = new MedicoDAO();

                ArrayList<String> nombres = null;
                nombres = controladorMed.listaMedicos();
                request.setAttribute("Lista", nombres);
                dispatcher = request.getRequestDispatcher("Ventanas/PacientesRegistro.jsp");
            } else if ("Insert".equals(accion)) {
                MedicoDAO controladorMed = new MedicoDAO();

                String Nombre = request.getParameter("Nombre");
                String Direccion = request.getParameter("Direccion");
                String Telefono = request.getParameter("Telefono");
                String Cp = request.getParameter("Cp");
                String Curp = request.getParameter("Curp");
                String Nss = request.getParameter("Nss");
                String Padecimientos = request.getParameter("Padecimiento");
                String NssMed = request.getParameter("browsers");

                String[] partes = NssMed.split(" - ");

                NssMed = partes[partes.length - 1];

                Medico medico = null;

                medico = controladorMed.obtenerInfo(NssMed);

                Paciente paciente = null;
                paciente = new Paciente(0, Nombre, Direccion, Telefono, Cp, Curp, Nss, Padecimientos, medico.getId());

                PacienteDAO controladorPac = new PacienteDAO();

                boolean validar;
                validar = controladorPac.registroPaciente(paciente);
                request.setAttribute("pop", "popOpen");
                if (validar) {
                    dispatcher = request.getRequestDispatcher("Ventanas/Pacientes.jsp");
                } else {
                    ArrayList<String> nombres = null;
                    nombres = controladorMed.listaMedicos();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/PacientesRegistro.jsp");
                }

            } else if ("Actualizar".equals(accion)) {
                request.setAttribute("Busca", "Busca");
                PacienteDAO controladorPac = new PacienteDAO();

                ArrayList<String> nombres = null;
                nombres = controladorPac.listaPacientes();
                request.setAttribute("Lista", nombres);
                dispatcher = request.getRequestDispatcher("Ventanas/PacientesActualizacion.jsp");
            } else if ("Busca".equals(accion)) {
                PacienteDAO controladorPac = new PacienteDAO();
                try {
                    String Nss = request.getParameter("browsers");

                    String[] partes = Nss.split(" - ");

                    Nss = partes[partes.length - 1];

                    Paciente paciente = null;

                    paciente = controladorPac.obtenerInfo(Nss);

                    if (paciente == null) {
                        request.setAttribute("pop1", "popOpen");
                        request.setAttribute("Busca", "Busca");
                        ArrayList<String> nombres = null;
                        nombres = controladorPac.listaPacientes();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/PacientesActualizacion.jsp");
                    } else {

                        request.setAttribute("Emp", paciente);

                        Cookie galletaPaciente = new Cookie("Paciente", paciente.getNss());
                        galletaPaciente.setMaxAge(1000);
                        response.addCookie(galletaPaciente);

                        MedicoDAO controladorMed = new MedicoDAO();

                        Medico medico = null;
                        medico = controladorMed.obtenerInfo(paciente.getIdMedico());

                        request.setAttribute("DatoMedico", medico.getNombre() + " - " + medico.getNss());

                        ArrayList<String> nombres = null;
                        nombres = controladorMed.listaMedicos();
                        request.setAttribute("Lista", nombres);
                        request.setAttribute("Actualiza", "Actualiza");
                        dispatcher = request.getRequestDispatcher("Ventanas/PacientesActualizacion.jsp");

                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop1", "popOpen");
                    request.setAttribute("Busca", "Busca");
                    ArrayList<String> nombres = null;
                    nombres = controladorPac.listaPacientes();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/PacientesActualizacion.jsp");
                }

            } else if ("Actualiza".equals(accion)) {
                MedicoDAO controladorMed = new MedicoDAO();
                PacienteDAO controladorPac = new PacienteDAO();

                try {
                    String Nombre = request.getParameter("Nombre");
                    String Direccion = request.getParameter("Direccion");
                    String Telefono = request.getParameter("Telefono");
                    String Cp = request.getParameter("Cp");
                    String Curp = request.getParameter("Curp");
                    String Nss = request.getParameter("Nss");
                    String Padecimientos = request.getParameter("Padecimiento");
                    String NssMed = request.getParameter("browsers");

                    String[] partes = NssMed.split(" - ");

                    NssMed = partes[partes.length - 1];

                    Medico medico = null;

                    medico = controladorMed.obtenerInfo(NssMed);

                    Paciente paciente = null;

                    paciente = new Paciente(0, Nombre, Direccion, Telefono, Cp, Curp, Nss, Padecimientos, medico.getId());

                    Cookie[] galletaId = request.getCookies();
                    String NssAntiguo = null;
                    for (Cookie cookie : galletaId) {
                        if (cookie.getName().equalsIgnoreCase("Paciente")) {
                            NssAntiguo = cookie.getValue();
                            cookie.setMaxAge(0);
                        }
                    }

                    boolean validar;
                    validar = controladorPac.ActualizarPaciente(paciente, NssAntiguo);

                    request.setAttribute("pop2", "popOpen");
                    if (validar) {
                        dispatcher = request.getRequestDispatcher("Ventanas/Pacientes.jsp");
                    } else {

                        request.setAttribute("Busca", "Busca");
                        ArrayList<String> nombres = null;
                        nombres = controladorPac.listaPacientes();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/PacientesActualizacion.jsp");
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop2", "popOpen");
                    request.setAttribute("Busca", "Busca");
                    ArrayList<String> nombres = null;
                    nombres = controladorPac.listaPacientes();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/PacientesActualizacion.jsp");
                }
            } else if ("Eliminar".equals(accion)) {
                request.setAttribute("Busca", "Busca");
                PacienteDAO controladorPac = new PacienteDAO();

                ArrayList<String> nombres = null;
                nombres = controladorPac.listaPacientes();
                request.setAttribute("Lista", nombres);
                dispatcher = request.getRequestDispatcher("Ventanas/PacientesEliminacion.jsp");
            } else if ("BuscaElimina".equalsIgnoreCase(accion)) {
                MedicoDAO controladorMed = new MedicoDAO();
                PacienteDAO controladorPac = new PacienteDAO();

                try {
                    String Nss = request.getParameter("browsers");

                    String[] partes = Nss.split(" - ");

                    Nss = partes[partes.length - 1];

                    Paciente paciente = null;
                    paciente = controladorPac.obtenerInfo(Nss);

                    Medico medico = null;

                    medico = controladorMed.obtenerInfo(paciente.getIdMedico());

                    if (paciente == null) {
                        request.setAttribute("pop1", "popOpen");
                        request.setAttribute("Busca", "Busca");

                        ArrayList<String> nombres = null;
                        nombres = controladorPac.listaPacientes();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/PacientesEliminacion.jsp");
                    } else {

                        request.setAttribute("Emp", paciente);
                        request.setAttribute("DatoMedico", medico.getNombre() + " - " + medico.getNss());

                        request.setAttribute("Actualiza", "Actualiza");
                        dispatcher = request.getRequestDispatcher("Ventanas/PacientesEliminacion.jsp");
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop1", "popOpen");
                    request.setAttribute("Busca", "Busca");

                    ArrayList<String> nombres = null;
                    nombres = controladorPac.listaPacientes();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/PacientesEliminacion.jsp");
                }
            } else if ("Elimina".equals(accion)) {
                PacienteDAO controladorPac = new PacienteDAO();
                try {
                    String Nss = request.getParameter("Nss");

                    boolean validar;
                    validar = controladorPac.EliminarPaciente(Nss);

                    request.setAttribute("pop3", "popOpen");
                    if (validar) {
                        dispatcher = request.getRequestDispatcher("Ventanas/Pacientes.jsp");
                    } else {
                        request.setAttribute("Busca", "Busca");

                        ArrayList<String> nombres = null;
                        nombres = controladorPac.listaPacientes();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/PacientesEliminacion.jsp");
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop3", "popOpen");
                    request.setAttribute("Busca", "Busca");

                    ArrayList<String> nombres = null;
                    nombres = controladorPac.listaPacientes();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/PacientesEliminacion.jsp");
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
