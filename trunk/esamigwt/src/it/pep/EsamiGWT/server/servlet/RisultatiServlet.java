package it.pep.EsamiGWT.server.servlet;

import it.pep.EsamiGWT.client.Utility;
import it.pep.EsamiGWT.client.hibernate.Elaborati;
import it.pep.EsamiGWT.server.DAO.EsamiDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;

/**
 *
 * @author Francesco
 */
public class RisultatiServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8600739527613920524L;
	static private final String FIELD_CONTAINER = "\""; //racchiude i valori da inserire nel file CSV
    static private final String FIELD_SEPARATOR = ";";    //separatore di campo

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAppello = Integer.parseInt(request.getParameter("appID"));
        List<Elaborati> elencoElaborati = EsamiDAO.trovaElaboratiPerAppello(idAppello);
        response.setContentType("application/csv");
        response.setHeader("Content-Disposition", "filename=\"graduatoria.csv\"");
        PrintWriter out = response.getWriter();
        try {
            out.println(costruisciIntestazioneCSV());
            for (Elaborati el : elencoElaborati) {
                out.print(costruisciCampoCSV("" + el.getID()));
                out.print(costruisciCampoCSV(el.getMatricolaStudente()));
                out.print(costruisciCampoCSV(el.getCognomeStudente()));
                out.print(costruisciCampoCSV(el.getNomeStudente()));
                out.print(costruisciCampoCSV(el.getNote()));
                out.print(costruisciCampoCSV(NumberFormat.getNumberInstance().format(el.getPunteggio())));
                out.println();
            }
        } finally {
            out.close();
        }
    }

    /**
     * costruisce la riga di intestazione con i nomi dei campi per il file CSV
     * @return la stringa con i campi
     */
    private String costruisciIntestazioneCSV() {
        String[] nomiCampi = {"ID", "Matricola", "Cognome", "Nome", "Note", "Punteggio"};
        String retVal = "";
        for (String s : nomiCampi) {
            retVal += costruisciCampoCSV(s);
        }
        return retVal;
    }

    /**
     * prepara il campo da inserire nel file CSV con gli opportuni delimitatori
     * @param valore
     * @return
     */
    private String costruisciCampoCSV(String valore) {
        return FIELD_CONTAINER + Utility.stringaNonNulla(valore) + FIELD_CONTAINER + FIELD_SEPARATOR;
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
