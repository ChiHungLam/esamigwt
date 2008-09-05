/*
 * LoginServiceAsync.java
 *
 * Created on 26 luglio 2008, 10.45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.pep.client;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 *
 * @author user
 */
public interface LoginServiceAsync {
    public void isUtente(String utente, String password, AsyncCallback callback);

    public void registraUtente(String utente, String password, AsyncCallback callback);

    public void getSessionID(AsyncCallback callback);
}
