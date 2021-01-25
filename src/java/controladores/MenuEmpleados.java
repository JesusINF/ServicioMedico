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
@WebServlet(name = "MenuEmpleados", urlPatterns = {"/MenuEmpleados"})
public class MenuEmpleados extends HttpServlet {

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
            out.println("<title>Servlet MenuEmpledos</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MenuEmpledos at " + request.getContextPath() + "</h1>");
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

            if (accion == null || accion.isEmpty()) {
                if (user.getTipo().equalsIgnoreCase("Administrador") || user.getTipo().equalsIgnoreCase("Programador")){
                    dispatcher = request.getRequestDispatcher("Ventanas/EmpleadosAdmin.jsp");
                } else if (user.getTipo().equalsIgnoreCase("Medico")){
                    dispatcher = request.getRequestDispatcher("error.jsp");
                } else {
                    dispatcher = request.getRequestDispatcher("error.jsp");
                }
                
            } else if ("Logout".equals(accion)) {
                sesion.invalidate();
                response.sendRedirect("Login");
                return;
            } else if("Registrar".equals(accion)){
                dispatcher = request.getRequestDispatcher("Ventanas/EmpleadosAdmiRegis.jsp");
            } else if("Insert".equals(accion)){
                String Nombre = request.getParameter("Nombre");
                String Direccion = request.getParameter("Direccion");
                String Telefono = request.getParameter("Telefono");
                String Cp = request.getParameter("Cp");
                String Curp = request.getParameter("Curp");
                String Nss = request.getParameter("Nss");
                String Tipo = request.getParameter("Tipo");
                String Usuario = request.getParameter("Usuario");
                String Password = request.getParameter("Password");
                
                Empleado empleado = new Empleado(0, Nombre, Direccion, Telefono, Cp, Curp, Nss, Tipo, 0);
                Usuario usuario = new Usuario(0, Usuario, Password, Tipo);
                
                EmpleadoDAO controlador = new EmpleadoDAO();
                
                boolean validar;
                validar = controlador.registroEmpleado(empleado, usuario);
                
                if(!validar){
                    dispatcher = request.getRequestDispatcher("Ventanas/EmpleadosAdmin.jsp");
                }else {
                    dispatcher = request.getRequestDispatcher("Ventanas/EmpleadosAdmiRegis.jsp");
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
