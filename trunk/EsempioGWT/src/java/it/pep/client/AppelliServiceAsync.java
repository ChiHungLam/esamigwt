/*
 * AppelliServiceAsync.java
 *
 * Created on 12 settembre 2008, 15.01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.pep.EsamiGWT.client;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 *
 * @author Francesco
 */
public interface AppelliServiceAsync {
    public void trovaAppelliPerUtente(String codUtente,AsyncCallback callback);
}
