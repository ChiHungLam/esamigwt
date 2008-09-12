/*
 * ArgomentiServiceImpl.java
 *
 * Created on 8 settembre 2008, 18.34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import it.pep.EsamiGWT.client.ArgomentiService;
import it.pep.EsamiGWT.client.hibernate.*;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Francesco
 */
public class ArgomentiServiceImpl extends RemoteServiceServlet implements
        ArgomentiService {

    public List<Argomenti> trovaArgomentiPerUtente(String codUtente) {
        List<Argomenti> ArgomentiList = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Argomenti a");
//            q.setParameter("utente", utente);
            ArgomentiList = (List<Argomenti>) q.list();
            LoggerApache.debug("argomenti trovati:" + q.list().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ArgomentiList;
    }
}
