/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.server.servlet;

import it.pep.EsamiGWT.server.DAO.EsamiDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco
 */
public class ConfermaIscrizione extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4619683694835733001L;

	/** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String activationCode = request.getParameter("id");
        boolean attivato = EsamiDAO.attivaUtente(activationCode);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Conferma Iscrizione</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Conferma Iscrizione</h1>");
            if (attivato) {
                out.println("Complimenti, ti sei correttamente registrato sul sito, adesso puoi incominciare ad inserire i tuoi test/esami.<p>");
                out.println("<a href="+request.getContextPath()+">Clicca qui</a>");
            } else {
                out.println("Conferma registrazione fallita.");
            }
            out.println("</body>");
            out.println("</html>");
        }  finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
