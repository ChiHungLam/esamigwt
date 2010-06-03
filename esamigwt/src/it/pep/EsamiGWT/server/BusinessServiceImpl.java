/*
 * BusinessServiceImpl.java
 *
 * Created on 8 settembre 2008, 18.34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import it.pep.EsamiGWT.client.BusinessService;
import it.pep.EsamiGWT.client.hibernate.*;
import it.pep.EsamiGWT.server.DAO.EsamiDAO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Francesco
 */
public class BusinessServiceImpl extends RemoteServiceServlet implements
        BusinessService {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9102907477533858056L;
	public List<Argomenti> trovaArgomentiPerUtente(String codUtente) {
        return EsamiDAO.trovaArgomentiPerUtente(codUtente);
    }

    public List<Domande> trovaDomandePerArgomento(int idArgomento) {
        return EsamiDAO.trovaDomandePerArgomento(idArgomento);
    }

    public List<Appelli> trovaAppelliPerUtente(String codUtente) {
        return EsamiDAO.trovaAppelliPerUtente(codUtente);
    }

    public String salvaDomanda(Domande dom, boolean insert) {
        return "" + EsamiDAO.salvaDomanda(dom, insert);
    }

    public String cancDomanda(Domande dom) {
        return "" + EsamiDAO.cancDomanda(dom);
    }

    public String salvaArgomento(Argomenti arg, boolean insert) {
        return "" + EsamiDAO.salvaArgomento(arg, insert);
    }

    public String cancArgomento(Argomenti arg) {
        return "" + EsamiDAO.cancArgomento(arg);
    }

    public String salvaAppello(Appelli arg, boolean insert) {
        return "" + EsamiDAO.salvaAppello(arg, insert);
    }

    public String cancAppello(Appelli arg) {
        return "" + EsamiDAO.cancAppello(arg);
    }

    /**
     * crea l'appello con n compiti diversi
     * @param appello
     * @param numEsaminandi
     * @param numCompitiDiff
     * @param listaArgomenti
     * @return
     */
    public String creaAppello(Appelli appello, int numEsaminandi, int numCompitiDiff, List<Argomenti> listaArgomenti) {
        return GestioneAppello.creaAppello(appello, numEsaminandi, numCompitiDiff, listaArgomenti);
    }

    public List<DomandeElaborati> trovaDomandeElaborato(int idElaborato) {
        return EsamiDAO.trovaDomandeElaborato(idElaborato);
    }

    public List<Elaborati> trovaElaboratiPerAppello(int idAppello) {
        return EsamiDAO.trovaElaboratiPerAppello(idAppello);
    }

    public String creaStampaAppello(Appelli a, String url, boolean stampaDomande, boolean stampaRisposte, boolean anonimo, int numColonne) {
        HttpServletRequest httpReq = getThreadLocalRequest();
        HttpSession httpSess = httpReq.getSession();
        return GestioneStampaAppello.creaStampa(httpSess,a, url, stampaDomande, stampaRisposte, anonimo, numColonne);
    }

    public String creaStampaCorrezione(Appelli a) {
        return GestioneStampaCorrezione.creaStampa(a);
    }

    public String creaStampaRisultati(Appelli a) {
        return GestioneStampaRisultati.creaStampa(a);
    }

    public String correggiElaborato(Elaborati el, List<DomandeElaborati> listaDomandeElaborati) {
        return GestioneCorrezioneElaborato.correggiElaborato(el, listaDomandeElaborati);
    }
    public Boolean bloccaDomandePerUtente(String codUtente, boolean tutte, Argomenti argomento, boolean blocco){
        return new Boolean(EsamiDAO.sBloccaDomande(codUtente, tutte, argomento, blocco));
    }
    public Boolean importaRisultati(String codUtente, String filename){
        return new Boolean(GestioneCorrezioneElaborato.importaRisultati(codUtente, filename));
    }
}
