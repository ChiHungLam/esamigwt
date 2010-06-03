/*
 * LoginService.java
 *
 * Created on 26 luglio 2008, 10.45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.pep.EsamiGWT.client;
import com.google.gwt.user.client.rpc.RemoteService;
import it.pep.EsamiGWT.client.hibernate.Utenti;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author user
 */
@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService{
    public Utenti isUtente(String utente, String password);
    public String registraUtente(String utente, String password, String urlBase);
    public String getSessionID();
}
