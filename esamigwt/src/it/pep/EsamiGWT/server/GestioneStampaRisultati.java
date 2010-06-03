/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.server;

import it.pep.EsamiGWT.client.dbBrowse.HtmlCodeForPrint;
import it.pep.EsamiGWT.client.hibernate.*;
import it.pep.EsamiGWT.server.DAO.EsamiDAO;
import java.text.DateFormat;
import java.util.List;

/**
 *
 * @author Francesco
 */
public class GestioneStampaRisultati {

    /** crea una tabella con i dati dell'elaborato e gli spazi per 
     *  scrivere i dati del candidato
     */
    static private void addDatiElaboratoHTML(String dataAppello, Elaborati el, StringBuffer retVal, boolean primaVolta) {
        final String spaziVuoti = "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";

//        retVal.append("<P CLASS=nuovaPaginaF>");
        retVal.append(primaVolta?"<hr>":"<p STYLE=\"page-break-before: always\"><hr></p>");
        retVal.append(HtmlCodeForPrint.getInitTableEsami());
        retVal.append(HtmlCodeForPrint.getInitBody());

        retVal.append(HtmlCodeForPrint.getInitRow());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append("Elaborato n. " + el.getID());
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append("Appello del " + dataAppello);
//        retVal.append("Appello del " + appello.getDataAppello());
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getEndRow());

        retVal.append(HtmlCodeForPrint.getInitRow());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append("Cognome");
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append(el.getCognomeStudente());
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append("Nome");
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append(el.getNomeStudente());
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getEndRow());

        retVal.append(HtmlCodeForPrint.getInitRow());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append("Matricola");
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append(el.getMatricolaStudente());
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getEndRow());

        retVal.append(HtmlCodeForPrint.getInitRow());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append("Punteggio");
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append(el.getPunteggio());
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getEndRow());

        retVal.append(HtmlCodeForPrint.getEndBody());
        retVal.append(HtmlCodeForPrint.getEndTable());

        retVal.append("<hr><p>");
    }

    static private void addDomandeHTML(Elaborati el, StringBuffer retVal) {
        final String cellaConBordo = "<td style=\"border:solid 1px\">";
        List<DomandeElaborati> listaDomandeElaborati = EsamiDAO.trovaDomandeElaborato(el.getID());
        retVal.append(HtmlCodeForPrint.getInitBody());
        for (DomandeElaborati de : listaDomandeElaborati) {
            retVal.append(HtmlCodeForPrint.getInitRow());
            retVal.append(HtmlCodeForPrint.getInitCol());
            retVal.append(de.getID());
            retVal.append(HtmlCodeForPrint.getEndCol());
            retVal.append(HtmlCodeForPrint.getInitCol());
            retVal.append("&nbsp");
            retVal.append(HtmlCodeForPrint.getEndCol());
            retVal.append(HtmlCodeForPrint.getInitCol());
            retVal.append(de.getEsito());
            retVal.append(HtmlCodeForPrint.getEndCol());
            retVal.append(HtmlCodeForPrint.getEndRow());
        }
        retVal.append(HtmlCodeForPrint.getEndBody());
    }

    private static void addFooterHTML(Elaborati el, StringBuffer retVal) {
        retVal.append(HtmlCodeForPrint.getInitFooter());

        retVal.append(HtmlCodeForPrint.getInitRow());

        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append("&nbsp");
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append("&nbsp");
        retVal.append(HtmlCodeForPrint.getEndColHeader());
        retVal.append(HtmlCodeForPrint.getInitColHeader());
        retVal.append("<hr>Elaborato n. " + el.getID());
        retVal.append(HtmlCodeForPrint.getEndColHeader());

        retVal.append(HtmlCodeForPrint.getEndRow());

        retVal.append(HtmlCodeForPrint.getEndFooter());
    }

    static public String creaStampa(Appelli appello) {
//        String dataAppello = DateTimeFormat.getShortDateFormat().format(appello.getDataAppello());
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        String dataAppello = df.format(appello.getDataAppello());

        List<Elaborati> listaElaborati = EsamiDAO.trovaElaboratiPerAppello(appello.getID());
        StringBuffer retVal = new StringBuffer(HtmlCodeForPrint.getInitHtml());
        boolean primaVolta=true;
        for (Elaborati el : listaElaborati) {
            addDatiElaboratoHTML(dataAppello, el, retVal,primaVolta);
            retVal.append(HtmlCodeForPrint.getInitTableEsami());
            addFooterHTML(el, retVal);
            addDomandeHTML(el, retVal);
            retVal.append(HtmlCodeForPrint.getEndTable());
            primaVolta=false;
        }
        retVal.append(HtmlCodeForPrint.getEndHtml());
        LoggerApache.debug(retVal.toString());
        return retVal.toString();
    }
}
