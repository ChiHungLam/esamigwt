/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.server.DAO;

import it.pep.EsamiGWT.client.hibernate.*;
import it.pep.EsamiGWT.server.HibernateUtil;
import it.pep.EsamiGWT.server.LoggerApache;
import org.hibernate.*;

/**
 *
 * @author Francesco
 */
public class CreazioneElaboratiDAO {

    public static Session initSession() {
            return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public static Transaction initTransazione(Session session) {
            return session.beginTransaction();
    }

    public static int creaAppello(Appelli appello, Session session) {
        try {
            LoggerApache.debug("creazione appello");
            session.save(appello);
            LoggerApache.debug("id appello salvato: " + appello.getID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appello.getID();
    }

    public static int creaAggiornaElaborato(Elaborati el,Session session, boolean insert) {
        try {
            LoggerApache.debug("creazione elaborato");
//            session.saveOrUpdate(el);
            if (insert) {
                session.save(el);
            } else {
                session.update(el);
            }
            LoggerApache.debug("id elaborato salvato: " + el.getID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return el.getID();
    }

    public static int creaAggiornaDomandaElaborato(DomandeElaborati de, Session session, boolean insert) {
        try {
            LoggerApache.debug("creazione domande elaborato");
//            session.saveOrUpdate(de);
            if (insert) {
                session.save(de);
            } else {
                session.update(de);
            }
            LoggerApache.debug("id domande elaborato salvato: " + de.getID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return de.getID();
    }
        public static long contaDomandeNonUtilizzatePerArgomento(Argomenti argomento, Session session) {
        Long count = 0L;
        try {
//            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("select count(*) from Domande d where d.idArgomento=:idargomento and d.utilizzato=:utilizzato");
            q.setParameter("idargomento", argomento.getID());
            q.setParameter("utilizzato", false);
            Object result = q.uniqueResult();
            if (result != null) {
                count = (Long) result;
            } else {
                LoggerApache.error("conteggio domande non eseguito");
            }
//            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
