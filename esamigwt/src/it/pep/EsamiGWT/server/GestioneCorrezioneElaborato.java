/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pep.EsamiGWT.server;

import it.pep.EsamiGWT.client.hibernate.DomandeElaborati;
import it.pep.EsamiGWT.client.hibernate.Elaborati;
import it.pep.EsamiGWT.server.DAO.CreazioneElaboratiDAO;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Francesco
 */
public class GestioneCorrezioneElaborato {

    static String correggiElaborato(Elaborati el, List<DomandeElaborati> listaDomandeElaborati) {
        String retVal = "ok";
        Session session = null;
        Transaction tx = null;
 //       calcolaPunteggio();
        session = CreazioneElaboratiDAO.initSession();
        tx = CreazioneElaboratiDAO.initTransazione(session);
        CreazioneElaboratiDAO.creaAggiornaElaborato(el, session, false);
        for(DomandeElaborati de : listaDomandeElaborati){
                    CreazioneElaboratiDAO.creaAggiornaDomandaElaborato(de,session, false);
        }
        if (retVal.equalsIgnoreCase("ok")) {
            LoggerApache.debug("correzione ok");
            tx.commit();
        } else {
            LoggerApache.debug("correzione ko");
            tx.rollback();
        }
        return retVal;
    }
    static boolean importaRisultati(String codUtente, String filename){
        // todo: upload, parse del file e scrittura nel db
        return true;
    }

}
