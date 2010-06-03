/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.server;

import it.pep.EsamiGWT.client.hibernate.*;
import it.pep.EsamiGWT.server.DAO.CreazioneElaboratiDAO;
import it.pep.EsamiGWT.server.DAO.EsamiDAO;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Francesco
 */
public class GestioneAppello {

    /**
     * crea l'appello con n compiti diversi
     * @param appello
     * @param numEsaminandi
     * @param numCompitiDiff
     * @param listaArgomenti
     * @return
     */
    static public String creaAppello(Appelli appello, int numEsaminandi, int numCompitiDiff, List<Argomenti> listaArgomenti) {
        EsamiDAO.salvaArgomenti(listaArgomenti);
        String retVal = null;
        Session session = null;
        Transaction tx = null;
        session = CreazioneElaboratiDAO.initSession();
        tx = CreazioneElaboratiDAO.initTransazione(session);
        retVal = procediCreazionecreaAppello(appello, numEsaminandi, numCompitiDiff, listaArgomenti, session);
        if (retVal.equalsIgnoreCase("ok")) {
            LoggerApache.debug("esame ok");
            tx.commit();
        } else {
            LoggerApache.debug("esame ko");
            tx.rollback();
        }
        return retVal;
    }

    static public String procediCreazionecreaAppello(Appelli appello, int numEsaminandi, int numCompitiDiff, List<Argomenti> listaArgomenti, Session session) {
//        LoggerApache.debug("esaminandi:" + numEsaminandi);
//        LoggerApache.debug("compiti differenti:" + numCompitiDiff);
//        int conta = 0;
//        for (Argomenti argomento : listaArgomenti) {
//            LoggerApache.debug("domanda " + (conta++) + ": " + argomento.getNumDomande());
//        }
        String messaggio = controllaNumeroDomandeSufficienti(numCompitiDiff, listaArgomenti, session);
        if (!messaggio.equalsIgnoreCase("ok")) {
            return messaggio;
        }
        inserisciAppello(appello, session);
        for (int k = 0; k < numCompitiDiff; k++) {
            if (!creaCompito(appello, numEsaminandi, numCompitiDiff, listaArgomenti, k, session)) {
                return "operazione fallita";
            }
        }
//        EsamiDAO.salvaArgomenti(listaArgomenti);
//        EsamiDAO.salvaAppello(appello, true);
        return "ok";
    }

    /**
     * calcola "quantiNumeri" numeri casuali compresi tra 0 e "MaxValAmmissibile"
     * @param quantiNumeri
     * @param MaxValAmmissibile
     * @return
     */
    static private int[] calcNumeriCasuali(int quantiNumeri, int MaxValAmmissibile) {
        int numeriEstratti[] = new int[quantiNumeri];
        for (int k = 0; k < quantiNumeri; k++) {
            boolean presente;
            do {
                presente = false;
                numeriEstratti[k] = (int) Math.round(Math.random() * (MaxValAmmissibile + 0.5));   // calcolo una posizione random all'interno della lista delle domande
                for (int m = 0; m < k && !presente; m++) {  // controllo se la posizione era già uscita
                    presente = (numeriEstratti[m] == numeriEstratti[k]);
                }
            } while (presente);
        }
        return numeriEstratti;
    }

    /**
     * controlla che il numero di domande per ogni argomento siano sufficienti 
     * per soddisfare tutti i compiti diversi da creare
     * @param numCompitiDiff
     * @param listaNumDomande
     * @return
     */
    static private String controllaNumeroDomandeSufficienti(int numCompitiDiff, List<Argomenti> listaArgomenti, Session session) {
        StringBuffer messaggio = new StringBuffer("Domande insufficienti per l'argomento ");
        String mess;
        for (Argomenti argomento : listaArgomenti) {
            long domandeRichieste = argomento.getNumDomande() * numCompitiDiff;
            if (domandeRichieste != 0) {
                long domandeDisponibili = CreazioneElaboratiDAO.contaDomandeNonUtilizzatePerArgomento(argomento,session);
                if (domandeDisponibili < domandeRichieste) {
                    messaggio.append(argomento.getDescrizione() + ".");
                    messaggio.append("\nDisponibili: " + domandeDisponibili);
                    messaggio.append("\nRichieste: " + domandeRichieste);
                    return messaggio.toString();
                }
            }
        }
        return "ok";
    }

    /** 
     * crea uno degli n compiti diversi
     * @param dataAppello
     * @param numEsaminandi
     * @param numCompitiDiff
     * @param listaArgomenti
     * @return
     */
    static private boolean creaCompito(Appelli appello, int numEsaminandi, int numCompitiDiff, List<Argomenti> listaArgomenti, int gruppo, Session session) {
        int nTotDomande = 0;
        for (Argomenti argomento : listaArgomenti) {
            nTotDomande = nTotDomande + argomento.getNumDomande();
        }
        Domande arrayDomande[] = new Domande[nTotDomande];
        int nPosUltDomanda = -1;
        for (Argomenti argomento : listaArgomenti) {		// ciclo sugli argomenti
            if (argomento.getNumDomande() != 0) {
                LoggerApache.debug("argomento: " + argomento.getDescrizione());

//            List<Domande> listaDomandePerArgomento = (new BusinessServiceImpl()).trovaDomandePerArgomento(argomento.getID());
                List<Domande> listaDomandePerArgomento = EsamiDAO.trovaDomandePerArgomento(argomento.getID(), true, session);
                int domandeScelte[] = calcNumeriCasuali(argomento.getNumDomande(), listaDomandePerArgomento.size() - 1);
                for (int k = 0; k < domandeScelte.length; k++) {
                    nPosUltDomanda++;
                    Domande domSceltaTmp=listaDomandePerArgomento.get(domandeScelte[k]);
                    arrayDomande[nPosUltDomanda] = domSceltaTmp;
                    domSceltaTmp.setUtilizzato(true);
                    EsamiDAO.salvaDomanda(domSceltaTmp, false, session);
                    LoggerApache.debug("domanda scelta - pos: " + domandeScelte[k] + " testo: " + arrayDomande[nPosUltDomanda].getDomanda());
                }
            }
        }
//        inserisciElaborato(gruppo, appello.getID(), session);
        if (!creaElaboratiDiversi(nTotDomande, arrotondaIntSuperiore(numEsaminandi / numCompitiDiff), gruppo, appello, arrayDomande, session)) {
            return false;
        //	aggiornaFlagUtilizzato(arrayDomande,nTotDomande,oCon)
        }
        return true;
    }

    /**
     * arrotonda un numero reale per eccesso
     * @param numero
     * @return
     */
    static private int arrotondaIntSuperiore(double numero) {
        int retVal;
        retVal = (int) numero;
        if (retVal != numero) //decimali troncati
        {
            retVal = retVal + 1;
        }
        return retVal;
    }

    /**
     * crea il "gruppo" di "numElaborati" compiti mescolando "nTotDomande" 
     * domande prese da "arrayDomande" per l'appello del "dataAppello"
     * @param nTotDomande
     * @param numElaborati
     * @param gruppo
     * @param dataAppello
     * @param arrayDomande
     */
    static private boolean creaElaboratiDiversi(int nTotDomande, int numElaborati, int gruppo, Appelli appello, Domande[] arrayDomande, Session session) {
        LoggerApache.debug("numElaborati: "+numElaborati);
        int id;
        int[] posizioniDomScelte;
        for (int k = 1; k <= numElaborati; k++) {
            id = inserisciElaborato(gruppo, appello.getID(), session);
            if (id != -1) {
                posizioniDomScelte = calcNumeriCasuali(nTotDomande, nTotDomande - 1);
                for (int m = 0; m < nTotDomande; m++) {
                    insDomandeElaborati(id, arrayDomande[posizioniDomScelte[m]], session);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * inserisce la domanda dom nell'elaborato
     * con id "idElaborato"
     * @param idElaborato
     * @param dom
     */
    private static void insDomandeElaborati(int idElaborato, Domande dom, Session session) {
        int[] permutazioneRisposte = calcNumeriCasuali(5, 4);
        DomandeElaborati de = new DomandeElaborati(
                -1,
                dom.getDomanda(),
                dom.getRisp(permutazioneRisposte[0] + 1),
                dom.getRisp(permutazioneRisposte[1] + 1),
                dom.getRisp(permutazioneRisposte[2] + 1),
                dom.getRisp(permutazioneRisposte[3] + 1),
                dom.getRisp(permutazioneRisposte[4] + 1),
                dom.getID(),
                idElaborato,
                cercaGiusta(permutazioneRisposte),
                0,
                "");
//        CreazioneElaboratiDAO.creaAggiornaDomandaElaborato(de, true, session);
        CreazioneElaboratiDAO.creaAggiornaDomandaElaborato(de, session, true);
    }

    /**
     * cerca la posizione del valore 1 
     * (che indica la risposta giusta nella permutazione "permutazioneRisposte")
     * @param permutazioneRisposte
     */
    private static int cercaGiusta(int[] permutazioneRisposte) {
        int retVal = 0;
        for (int k = 1; k < permutazioneRisposte.length; k++) {
            if (permutazioneRisposte[k] == 1) {
                retVal = k;
            }
        }
        return retVal + 1;    // le risposte partono da 1, l'array da 0
    }

    private static int inserisciAppello(Appelli appello, Session session) {
        return CreazioneElaboratiDAO.creaAppello(appello, session);
    }

    private static int inserisciElaborato(int gruppo, int idAppello, Session session) {
        Elaborati nuovoElaborato = new Elaborati();
        nuovoElaborato.setGruppo(gruppo);
        nuovoElaborato.setIdAppello(idAppello);

        return CreazioneElaboratiDAO.creaAggiornaElaborato(nuovoElaborato, session, true);
    }
}
