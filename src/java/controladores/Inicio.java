/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author jesus
 */
@WebServlet(name = "Inicio", urlPatterns = {"/Inicio"})
public class Inicio extends HttpServlet {

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
            out.println("<title>Servlet Inicio</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Inicio at " + request.getContextPath() + "</h1>");
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
                if (user.getTipo().equalsIgnoreCase("Administrador") || user.getTipo().equalsIgnoreCase("Programador")){
                    EmpleadoDAO controlador = new EmpleadoDAO();
                    Empleado empleado = controlador.obtenerInfoSesion(user.getId());
                    request.setAttribute("Usuario", empleado.getNombre());
                } else if (user.getTipo().equalsIgnoreCase("Medico")){
                    MedicoDAO controlador = new MedicoDAO();
                    Medico medico = controlador.obtenerInfoSesion(user.getId());
                    request.setAttribute("Usuario", medico.getNombre());
                } else {
                    EmpleadoDAO controlador = new EmpleadoDAO();
                    Empleado empleado = controlador.obtenerInfoSesion(user.getId());
                   request.setAttribute("Usuario", empleado.getNombre());
                }
                dispatcher = request.getRequestDispatcher("Ventanas/Inicio.jsp");
            } else if ("Logout".equals(accion)) {
                sesion.invalidate();
                response.sendRedirect("Login");
                return;
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
