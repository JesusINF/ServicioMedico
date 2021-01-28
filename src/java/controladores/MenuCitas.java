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
import modelos.Consulta;
import modelos.ConsultaDAO;
import modelos.Medico;
import modelos.MedicoDAO;
import modelos.Paciente;
import modelos.PacienteDAO;
import modelos.Usuario;

/**
 *
 * @author jesus
 */
@WebServlet(name = "MenuCitas", urlPatterns = {"/MenuCitas"})
public class MenuCitas extends HttpServlet {

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
            out.println("<title>Servlet MenuCitas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MenuCitas at " + request.getContextPath() + "</h1>");
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
                    dispatcher = request.getRequestDispatcher("Ventanas/Citas.jsp");
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
                request.setAttribute("Lista1", nombres);

                PacienteDAO controladorPac = new PacienteDAO();

                ArrayList<String> nombres2 = null;
                nombres2 = controladorPac.listaPacientes();
                request.setAttribute("Lista2", nombres2);
                dispatcher = request.getRequestDispatcher("Ventanas/CitasRegistro.jsp");
            } else if ("Insert".equals(accion)) {
                try {
                    MedicoDAO controladorMed = new MedicoDAO();
                    PacienteDAO controladorPac = new PacienteDAO();
                    ConsultaDAO controladorCon = new ConsultaDAO();

                    String NssMed = request.getParameter("medico");
                    String NSSPac = request.getParameter("paciente");
                    String Inicio = request.getParameter("inicio");
                    String Fin = request.getParameter("fin");

                    String[] partes = NssMed.split(" - ");

                    NssMed = partes[partes.length - 1];

                    partes = NSSPac.split(" - ");

                    NSSPac = partes[partes.length - 1];

                    Medico medico = controladorMed.obtenerInfo(NssMed);
                    Paciente paciente = controladorPac.obtenerInfo(NSSPac);

                    Inicio = Inicio.replace('T', ' ') + ":00";
                    Fin = Fin.replace('T', ' ') + ":00";

                    Consulta consulta = new Consulta(0, medico.getId(), paciente.getId(), Inicio, Fin);

                    boolean validar = false;
                    validar = controladorCon.registrarConsulta(consulta);
                    request.setAttribute("pop", "popOpen");
                    if (validar) {
                        dispatcher = request.getRequestDispatcher("Ventanas/Citas.jsp");
                    } else {
                        ArrayList<String> nombres = null;
                        nombres = controladorMed.listaMedicos();
                        request.setAttribute("Lista1", nombres);

                        ArrayList<String> nombres2 = null;
                        nombres2 = controladorPac.listaPacientes();
                        request.setAttribute("Lista2", nombres2);
                        dispatcher = request.getRequestDispatcher("Ventanas/CitasRegistro.jsp");
                    }
                } catch (NullPointerException ex) {
                    MedicoDAO controladorMed = new MedicoDAO();
                    PacienteDAO controladorPac = new PacienteDAO();
                    request.setAttribute("pop", "popOpen");
                    ArrayList<String> nombres = null;
                    nombres = controladorMed.listaMedicos();
                    request.setAttribute("Lista1", nombres);

                    ArrayList<String> nombres2 = null;
                    nombres2 = controladorPac.listaPacientes();
                    request.setAttribute("Lista2", nombres2);
                    dispatcher = request.getRequestDispatcher("Ventanas/CitasRegistro.jsp");
                }

            } else if ("Actualizar".equals(accion)) {
                ConsultaDAO controladorCon = new ConsultaDAO();

                request.setAttribute("Busca", "Busca");

                ArrayList<String> nombres = null;
                nombres = controladorCon.listaConsulta();
                request.setAttribute("Lista", nombres);
                dispatcher = request.getRequestDispatcher("Ventanas/CitasActualizacion.jsp");
            } else if ("Busca".equals(accion)) {
                try {
                    PacienteDAO controladorPac = new PacienteDAO();
                    MedicoDAO controladorMed = new MedicoDAO();
                    ConsultaDAO controladorCon = new ConsultaDAO();

                    String NssPaciente = request.getParameter("browsers");
                    String Inicio = null;

                    String[] partes = NssPaciente.split(" - ");
                    String[] partes2 = NssPaciente.split(": ");

                    if (partes.length == 1) {
                        NssPaciente = " ";
                    } else {
                        NssPaciente = partes[1];
                    }

                    Inicio = partes2[partes2.length - 1];

                    Paciente paciente = null;

                    paciente = controladorPac.obtenerInfo(NssPaciente);

                    Consulta consulta = null;

                    consulta = controladorCon.obtenerInfo(paciente.getId(), Inicio);

                    Medico medico = null;
                    medico = controladorMed.obtenerInfo(consulta.getIdMedico());

                    if (consulta == null) {
                        request.setAttribute("pop1", "popOpen");
                        request.setAttribute("Busca", "Busca");
                        ArrayList<String> nombres = null;
                        nombres = controladorCon.listaConsulta();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/CitasActualizacion.jsp");
                    } else {

                        request.setAttribute("Emp", consulta);

                        Cookie galletaCita = new Cookie("Cita", Integer.toString(consulta.getId()));
                        galletaCita.setMaxAge(1000);
                        response.addCookie(galletaCita);

                        request.setAttribute("DatoMedico", medico.getNombre() + " - " + medico.getNss());
                        request.setAttribute("DatoPaciente", paciente.getNombre() + " - " + paciente.getNss());

                        ArrayList<String> nombres = null;
                        nombres = controladorMed.listaMedicos();
                        request.setAttribute("Lista1", nombres);

                        ArrayList<String> nombres2 = null;
                        nombres2 = controladorPac.listaPacientes();
                        request.setAttribute("Lista2", nombres2);
                        request.setAttribute("Actualiza", "Actualiza");
                        dispatcher = request.getRequestDispatcher("Ventanas/CitasActualizacion.jsp");

                    }
                } catch (NullPointerException ex) {
                    ConsultaDAO controladorCon = new ConsultaDAO();
                    request.setAttribute("pop1", "popOpen");
                    request.setAttribute("Busca", "Busca");
                    ArrayList<String> nombres = null;
                    nombres = controladorCon.listaConsulta();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/CitasActualizacion.jsp");
                }

            } else if ("Actualiza".equals(accion)) {
                try {
                    MedicoDAO controladorMed = new MedicoDAO();
                    PacienteDAO controladorPac = new PacienteDAO();
                    ConsultaDAO controladorCon = new ConsultaDAO();

                    String NssMed = request.getParameter("medico");
                    String NSSPac = request.getParameter("paciente");
                    String Inicio = request.getParameter("inicio");
                    String Fin = request.getParameter("fin");

                    String[] partes = NssMed.split(" - ");

                    NssMed = partes[partes.length - 1];

                    partes = NSSPac.split(" - ");

                    NSSPac = partes[partes.length - 1];

                    Medico medico = controladorMed.obtenerInfo(NssMed);
                    Paciente paciente = controladorPac.obtenerInfo(NSSPac);

                    Inicio = Inicio.replace('T', ' ') + ":00";
                    Fin = Fin.replace('T', ' ') + ":00";

                    Consulta consulta = new Consulta(0, medico.getId(), paciente.getId(), Inicio, Fin);

                    Cookie[] galletaId = request.getCookies();
                    int idAntiguo = 0;
                    for (Cookie cookie : galletaId) {
                        if (cookie.getName().equalsIgnoreCase("Cita")) {
                            idAntiguo = Integer.parseInt(cookie.getValue());
                            cookie.setMaxAge(0);
                        }
                    }

                    boolean validar;
                    validar = controladorCon.ActualizarConsulta(consulta, idAntiguo);

                    request.setAttribute("pop2", "popOpen");
                    if (validar) {
                        dispatcher = request.getRequestDispatcher("Ventanas/Citas.jsp");
                    } else {

                        request.setAttribute("Busca", "Busca");
                        ArrayList<String> nombres = null;
                        nombres = controladorCon.listaConsulta();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/CitasActualizacion.jsp");
                    }
                } catch (NullPointerException ex) {
                    ConsultaDAO controladorCon = new ConsultaDAO();
                    request.setAttribute("pop2", "popOpen");
                    request.setAttribute("Busca", "Busca");
                    ArrayList<String> nombres = null;
                    nombres = controladorCon.listaConsulta();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/CitasActualizacion.jsp");
                }
            } else if ("Eliminar".equals(accion)) {
                ConsultaDAO controladorCon = new ConsultaDAO();

                request.setAttribute("Busca", "Busca");

                ArrayList<String> nombres = null;
                nombres = controladorCon.listaConsulta();
                request.setAttribute("Lista", nombres);
                dispatcher = request.getRequestDispatcher("Ventanas/CitasEliminacion.jsp");
            } else if ("BuscaElimina".equalsIgnoreCase(accion)) {
                try {
                    PacienteDAO controladorPac = new PacienteDAO();
                    MedicoDAO controladorMed = new MedicoDAO();
                    ConsultaDAO controladorCon = new ConsultaDAO();

                    String NssPaciente = request.getParameter("browsers");
                    String Inicio = null;

                    String[] partes = NssPaciente.split(" - ");
                    String[] partes2 = NssPaciente.split(": ");

                    if (partes.length == 1) {
                        NssPaciente = " ";
                    } else {
                        NssPaciente = partes[1];
                    }

                    Inicio = partes2[partes2.length - 1];

                    Paciente paciente = null;

                    paciente = controladorPac.obtenerInfo(NssPaciente);

                    Consulta consulta = null;

                    consulta = controladorCon.obtenerInfo(paciente.getId(), Inicio);

                    Medico medico = null;
                    medico = controladorMed.obtenerInfo(consulta.getIdMedico());

                    if (consulta == null) {
                        request.setAttribute("pop1", "popOpen");
                        request.setAttribute("Busca", "Busca");

                        ArrayList<String> nombres = null;
                        nombres = controladorCon.listaConsulta();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/CitasEliminacion.jsp");
                    } else {

                        request.setAttribute("Emp", consulta);
                        request.setAttribute("DatoMedico", medico.getNombre() + " - " + medico.getNss());
                        request.setAttribute("DatoPaciente", paciente.getNombre() + " - " + paciente.getNss());

                        ArrayList<String> nombres = null;
                        nombres = controladorMed.listaMedicos();
                        request.setAttribute("Lista1", nombres);

                        ArrayList<String> nombres2 = null;
                        nombres2 = controladorPac.listaPacientes();
                        request.setAttribute("Lista2", nombres2);
                        request.setAttribute("Actualiza", "Actualiza");
                        dispatcher = request.getRequestDispatcher("Ventanas/CitasEliminacion.jsp");
                    }
                } catch (NullPointerException ex) {
                    ConsultaDAO controladorCon = new ConsultaDAO();
                    request.setAttribute("pop1", "popOpen");
                    request.setAttribute("Busca", "Busca");

                    ArrayList<String> nombres = null;
                    nombres = controladorCon.listaConsulta();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/CitasEliminacion.jsp");
                }
            } else if ("Elimina".equals(accion)) {
                PacienteDAO controladorPac = new PacienteDAO();
                MedicoDAO controladorMed = new MedicoDAO();
                ConsultaDAO controladorCon = new ConsultaDAO();

                try {
                    String NssMed = request.getParameter("medico");
                    String NSSPac = request.getParameter("paciente");
                    String Inicio = request.getParameter("inicio");
                    String Fin = request.getParameter("fin");

                    String[] partes = NssMed.split(" - ");

                    NssMed = partes[partes.length - 1];

                    partes = NSSPac.split(" - ");

                    NSSPac = partes[partes.length - 1];

                    Medico medico = controladorMed.obtenerInfo(NssMed);
                    Paciente paciente = controladorPac.obtenerInfo(NSSPac);

                    Inicio = Inicio.replace('T', ' ');
                    Fin = Fin.replace('T', ' ');

                    Consulta consulta = new Consulta(0, medico.getId(), paciente.getId(), Inicio, Fin);

                    boolean validar;
                    validar = controladorCon.eliminarConsulta(consulta);

                    request.setAttribute("pop3", "popOpen");
                    if (validar) {
                        dispatcher = request.getRequestDispatcher("Ventanas/Citas.jsp");
                    } else {
                        request.setAttribute("Busca", "Busca");

                        ArrayList<String> nombres = null;
                        nombres = controladorCon.listaConsulta();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/CitasEliminacion.jsp");
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop3", "popOpen");
                    request.setAttribute("Busca", "Busca");

                    ArrayList<String> nombres = null;
                    nombres = controladorCon.listaConsulta();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/CitasEliminacion.jsp");
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
