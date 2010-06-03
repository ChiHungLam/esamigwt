/*
 * BusinessService.java
 *
 * Created on 8 settembre 2008, 18.34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import it.pep.EsamiGWT.client.hibernate.*;

/**
 *
 * @author Francesco
 */
@RemoteServiceRelativePath("businessservice")
public interface BusinessService extends RemoteService {

    public List<Argomenti> trovaArgomentiPerUtente(String codUtente);

    public List<Domande> trovaDomandePerArgomento(int idArgomento);

    public List<Appelli> trovaAppelliPerUtente(String codUtente);

    public String salvaDomanda(Domande dom, boolean insert);

    public String cancDomanda(Domande dom);

    public String salvaArgomento(Argomenti arg, boolean insert);

    public String cancArgomento(Argomenti arg);

    public String salvaAppello(Appelli arg, boolean insert);

    public String cancAppello(Appelli arg);

    public String creaAppello(Appelli appello, int numEsaminandi, int numCompitiDiff, List<Argomenti> listaArgomenti);

    public List<DomandeElaborati> trovaDomandeElaborato(int idElaborato);

    public List<Elaborati> trovaElaboratiPerAppello(int idAppello);

    public String creaStampaAppello(Appelli a, String url, boolean stampaDomande, boolean stampaRisposte, boolean anonimo, int numColonne);

    public String creaStampaCorrezione(Appelli a);

    public String creaStampaRisultati(Appelli a);

    public String correggiElaborato(Elaborati el, List<DomandeElaborati> listaDomandeElaborati);

    public Boolean bloccaDomandePerUtente(String codUtente, boolean tutte, Argomenti argomento, boolean blocco);

    public Boolean importaRisultati(String codUtente, String filename);
}
