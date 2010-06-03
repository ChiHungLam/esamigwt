/*
 * BusinessServiceAsync.java
 *
 * Created on 8 settembre 2008, 18.34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import it.pep.EsamiGWT.client.hibernate.Appelli;
import it.pep.EsamiGWT.client.hibernate.Argomenti;
import it.pep.EsamiGWT.client.hibernate.Domande;
import it.pep.EsamiGWT.client.hibernate.DomandeElaborati;
import it.pep.EsamiGWT.client.hibernate.Elaborati;
import java.util.List;

/**
 *
 * @author Francesco
 */
public interface BusinessServiceAsync {

    public void correggiElaborato(Elaborati el, List<DomandeElaborati> listaDomandeElaborati, AsyncCallback<String> callback);

    public void trovaArgomentiPerUtente(String codUtente, AsyncCallback<List<Argomenti>> callback);

    public void trovaDomandeElaborato(int idElaborato, AsyncCallback<List<DomandeElaborati>> callback);

    public void trovaDomandePerArgomento(int idArgomento, AsyncCallback<List<Domande>> callback);

    public void trovaAppelliPerUtente(String codUtente, AsyncCallback<List<Appelli>> callback);

    public void salvaDomanda(Domande dom, boolean insert, AsyncCallback<String> callback);

    public void cancDomanda(Domande dom, AsyncCallback<String> asyncCallback);

    public void salvaArgomento(Argomenti arg, boolean insert, AsyncCallback<String> callback);

    public void cancArgomento(Argomenti arg, AsyncCallback<String> asyncCallback);

    public void salvaAppello(Appelli arg, boolean insert, AsyncCallback<String> callback);

    public void cancAppello(Appelli arg, AsyncCallback<String> asyncCallback);

    public void creaAppello(Appelli appello, int numEsaminandi, int numCompitiDiff, List<Argomenti> listaArgomenti, AsyncCallback<String> callback);

    public void trovaElaboratiPerAppello(int idAppello, AsyncCallback<List<Elaborati>> callback);

    public void creaStampaAppello(Appelli a, String url, boolean stampaDomande, boolean stampaRisposte, boolean anonimo, int numColonne, AsyncCallback<String> asyncCallback);

    public void creaStampaCorrezione(Appelli appello, AsyncCallback<String> callback);

    public void creaStampaRisultati(Appelli appello, AsyncCallback<String> callback);

    public void bloccaDomandePerUtente(String codUtente, boolean tutte, Argomenti argomento, boolean blocco, AsyncCallback<Boolean> callback);

    public void importaRisultati(String codUtente, String filename, AsyncCallback<Boolean> callback);
}
