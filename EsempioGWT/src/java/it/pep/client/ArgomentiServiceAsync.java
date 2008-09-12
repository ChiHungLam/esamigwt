/*
 * ArgomentiServiceAsync.java
 *
 * Created on 8 settembre 2008, 18.34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.pep.EsamiGWT.client;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import it.pep.EsamiGWT.client.hibernate.*;


/**
 *
 * @author Francesco
 */
public interface ArgomentiServiceAsync {
    public void trovaArgomentiPerUtente(String codUtente,AsyncCallback callback);
}
