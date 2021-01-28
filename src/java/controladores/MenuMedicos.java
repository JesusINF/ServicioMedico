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
import modelos.UsuarioDAO;
import modelos.Usuario;

/**
 *
 * @author jesus
 */
@WebServlet(name = "MenuMedicos", urlPatterns = {"/MenuMedicos"})
public class MenuMedicos extends HttpServlet {

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
            out.println("<title>Servlet MenuMedicos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MenuMedicos at " + request.getContextPath() + "</h1>");
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
                    dispatcher = request.getRequestDispatcher("Ventanas/Medicos.jsp");
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
                dispatcher = request.getRequestDispatcher("Ventanas/MedicosRegistro.jsp");
            } else if ("Insert".equals(accion)) {
                String Nombre = request.getParameter("Nombre");
                String Direccion = request.getParameter("Direccion");
                String Telefono = request.getParameter("Telefono");
                String Cp = request.getParameter("Cp");
                String Curp = request.getParameter("Curp");
                String Nss = request.getParameter("Nss");
                String Cedula = request.getParameter("Cedula");
                String Tipo = request.getParameter("Tipo");
                String Usuario = request.getParameter("Usuario");
                String Password = request.getParameter("Password");

                Medico medico = new Medico(0, Nombre, Direccion, Telefono, Cp, Curp, Nss, Cedula, Tipo, null, null, 0);
                Usuario usuario = new Usuario(0, Usuario, Password, "Medico");

                MedicoDAO controlador = new MedicoDAO();

                boolean validar;
                validar = controlador.registroMedico(medico, usuario);
                request.setAttribute("pop", "popOpen");
                if (validar) {
                    dispatcher = request.getRequestDispatcher("Ventanas/Medicos.jsp");
                } else {
                    dispatcher = request.getRequestDispatcher("Ventanas/MedicosRegistro.jsp");
                }

            } else if ("Actualizar".equals(accion)) {
                request.setAttribute("Busca", "Busca");
                MedicoDAO controladorMed = new MedicoDAO();

                ArrayList<String> nombres = null;
                nombres = controladorMed.listaMedicos();
                request.setAttribute("Lista", nombres);
                dispatcher = request.getRequestDispatcher("Ventanas/MedicosActualizacion.jsp");
            } else if ("Busca".equals(accion)) {
                MedicoDAO controladorMed = new MedicoDAO();

                try {
                    String Nss = request.getParameter("browsers");

                    String[] partes = Nss.split(" - ");

                    Nss = partes[partes.length - 1];

                    Medico medico = null;

                    medico = controladorMed.obtenerInfo(Nss);

                    if (medico == null) {
                        request.setAttribute("pop1", "popOpen");
                        request.setAttribute("Busca", "Busca");
                        ArrayList<String> nombres = null;
                        nombres = controladorMed.listaMedicos();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/MedicosActualizacion.jsp");
                    } else {
                        UsuarioDAO usuarioDAO = new UsuarioDAO();

                        Usuario usuario = null;
                        usuario = usuarioDAO.obtenerUsuario(medico.getIdUsuario());

                        if (usuario == null) {
                            request.setAttribute("pop1", "popOpen");
                            ArrayList<String> nombres = null;
                            nombres = controladorMed.listaMedicos();
                            request.setAttribute("Lista", nombres);
                            request.setAttribute("Busca", "Busca");
                            dispatcher = request.getRequestDispatcher("Ventanas/MedicosActualizacion.jsp");
                        } else {
                            request.setAttribute("Emp", medico);
                            request.setAttribute("User", usuario);

                            Cookie galletaUsuario = new Cookie("User", Integer.toString(usuario.getId()));
                            galletaUsuario.setMaxAge(1000);
                            response.addCookie(galletaUsuario);

                            request.setAttribute("Actualiza", "Actualiza");
                            dispatcher = request.getRequestDispatcher("Ventanas/MedicosActualizacion.jsp");
                        }
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop1", "popOpen");
                    ArrayList<String> nombres = null;
                    nombres = controladorMed.listaMedicos();
                    request.setAttribute("Lista", nombres);
                    request.setAttribute("Busca", "Busca");
                    dispatcher = request.getRequestDispatcher("Ventanas/MedicosActualizacion.jsp");
                }
            } else if ("Actualiza".equals(accion)) {
                String Nombre = request.getParameter("Nombre");
                String Direccion = request.getParameter("Direccion");
                String Telefono = request.getParameter("Telefono");
                String Cp = request.getParameter("Cp");
                String Curp = request.getParameter("Curp");
                String Nss = request.getParameter("Nss");
                String Cedula = request.getParameter("Cedula");
                String Tipo = request.getParameter("Tipo");
                String Usuario = request.getParameter("Usuario");
                String Password = request.getParameter("Password");

                Medico medico = new Medico(0, Nombre, Direccion, Telefono, Cp, Curp, Nss, Cedula, Tipo, null, null, 0);
                Usuario usuario = new Usuario(0, Usuario, Password, "Medico");

                Usuario usuarioAntiguo = null;

                Cookie[] galletaId = request.getCookies();
                int id = 0;
                for (Cookie cookie : galletaId) {
                    if (cookie.getName().equalsIgnoreCase("User")) {
                        id = Integer.parseInt(cookie.getValue());
                        break;
                    }
                }

                UsuarioDAO controladorUsuario = new UsuarioDAO();
                MedicoDAO ControladorMedico = new MedicoDAO();
                try {
                    usuarioAntiguo = controladorUsuario.obtenerUsuario(id);

                    boolean validar;
                    validar = ControladorMedico.ActualizaMedico(medico, usuario, usuarioAntiguo);

                    request.setAttribute("pop2", "popOpen");
                    if (validar) {
                        dispatcher = request.getRequestDispatcher("Ventanas/Medicos.jsp");
                    } else {

                        request.setAttribute("Busca", "Busca");
                        ArrayList<String> nombres = null;
                        nombres = ControladorMedico.listaMedicos();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/MedicosActualizacion.jsp");
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop2", "popOpen");
                    request.setAttribute("Busca", "Busca");
                    ArrayList<String> nombres = null;
                    nombres = ControladorMedico.listaMedicos();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/MedicosActualizacion.jsp");
                }
            } else if ("Eliminar".equals(accion)) {
                request.setAttribute("Busca", "Busca");
                MedicoDAO controladorMed = new MedicoDAO();

                ArrayList<String> nombres = null;
                nombres = controladorMed.listaMedicos();
                request.setAttribute("Lista", nombres);
                dispatcher = request.getRequestDispatcher("Ventanas/MedicosEliminacion.jsp");
            } else if ("BuscaElimina".equalsIgnoreCase(accion)) {
                MedicoDAO controladorMed = new MedicoDAO();

                try {

                    String Nss = request.getParameter("browsers");

                    String[] partes = Nss.split(" - ");

                    Nss = partes[partes.length - 1];

                    Medico medico = null;

                    medico = controladorMed.obtenerInfo(Nss);

                    if (medico == null) {
                        request.setAttribute("pop1", "popOpen");
                        request.setAttribute("Busca", "Busca");
                        ArrayList<String> nombres = null;
                        nombres = controladorMed.listaMedicos();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/MedicosEliminacion.jsp");
                    } else {
                        UsuarioDAO usuarioDAO = new UsuarioDAO();

                        Usuario usuario = null;
                        usuario = usuarioDAO.obtenerUsuario(medico.getIdUsuario());

                        if (usuario == null) {
                            request.setAttribute("pop1", "popOpen");
                            ArrayList<String> nombres = null;
                            nombres = controladorMed.listaMedicos();
                            request.setAttribute("Lista", nombres);
                            request.setAttribute("Busca", "Busca");
                            dispatcher = request.getRequestDispatcher("Ventanas/MedicosEliminacion.jsp");
                        } else {
                            request.setAttribute("Emp", medico);
                            request.setAttribute("User", usuario);

                            request.setAttribute("Actualiza", "Actualiza");
                            dispatcher = request.getRequestDispatcher("Ventanas/MedicosEliminacion.jsp");
                        }
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop1", "popOpen");
                    ArrayList<String> nombres = null;
                    nombres = controladorMed.listaMedicos();
                    request.setAttribute("Lista", nombres);
                    request.setAttribute("Busca", "Busca");
                    dispatcher = request.getRequestDispatcher("Ventanas/MedicosEliminacion.jsp");
                }
            } else if ("Elimina".equals(accion)) {
                MedicoDAO controladorMed = new MedicoDAO();
                try {
                    String Nss = request.getParameter("Nss");
                    String Usuario = request.getParameter("Usuario");

                    boolean validar;
                    validar = controladorMed.EliminarMedico(Nss, Usuario);
                    request.setAttribute("pop3", "popOpen");
                    if (validar) {
                        dispatcher = request.getRequestDispatcher("Ventanas/Medicos.jsp");
                    } else {

                        request.setAttribute("Busca", "Busca");
                        ArrayList<String> nombres = null;
                        nombres = controladorMed.listaMedicos();
                        request.setAttribute("Lista", nombres);
                        dispatcher = request.getRequestDispatcher("Ventanas/MedicosEliminacion.jsp");
                    }
                } catch (NullPointerException ex) {
                    request.setAttribute("pop3", "popOpen");
                    request.setAttribute("Busca", "Busca");
                    ArrayList<String> nombres = null;
                    nombres = controladorMed.listaMedicos();
                    request.setAttribute("Lista", nombres);
                    dispatcher = request.getRequestDispatcher("Ventanas/MedicosEliminacion.jsp");
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
