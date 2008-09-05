/*
 * LoginService.java
 *
 * Created on 26 luglio 2008, 10.45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.pep.client;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 *
 * @author user
 */
public interface LoginService extends RemoteService{
    public String isUtente(String utente, String password);
    public String registraUtente(String utente, String password);
    public String getSessionID();
}
