/*
 * AppelliService.java
 *
 * Created on 12 settembre 2008, 15.01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.pep.EsamiGWT.client;
import com.google.gwt.user.client.rpc.RemoteService;
import it.pep.EsamiGWT.client.hibernate.Appelli;
import java.util.List;

/**
 *
 * @author Francesco
 */
public interface AppelliService extends RemoteService{
    public List<Appelli> trovaAppelliPerUtente(String codUtente);
}
