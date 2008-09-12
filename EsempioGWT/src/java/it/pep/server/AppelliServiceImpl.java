/*
 * AppelliServiceImpl.java
 *
 * Created on 12 settembre 2008, 15.01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import it.pep.EsamiGWT.client.AppelliService;
import it.pep.EsamiGWT.client.hibernate.*;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Francesco
 */
public class AppelliServiceImpl extends RemoteServiceServlet implements
        AppelliService {

    public List<Appelli> trovaAppelliPerUtente(String codUtente) {
        List<Appelli> AppelliList = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Appelli a");
//            q.setParameter("utente", utente);
            AppelliList = (List<Appelli>) q.list();
            LoggerApache.debug("appelli trovati:" + q.list().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppelliList;
    }
}
