/*
 * LoginServiceImpl.java
 *
 * Created on 26 luglio 2008, 10.45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package it.pep.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import it.pep.client.LoginService;
import java.util.List;
import it.pep.hibernate.*;
import it.pep.utility.*;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.*;

/**
 *
 * @author user
 */
public class LoginServiceImpl extends RemoteServiceServlet implements
        LoginService {

    public String isUtente(String utente, String password) {
        List<Utenti> utentiList = null;
        boolean trovato = false;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Utenti u where u.utente=:utente");
            q.setParameter("utente", utente);
            utentiList = (List<Utenti>) q.list();
            Utenti u = utentiList.get(0);
            trovato = BCrypt.checkpw(password, u.getPassword().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "" + trovato;
    }

    public String registraUtente(String utente, String password) {
        Session session = null;
        boolean inserito = false;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Utenti u = new Utenti();
            u.setUtente(utente);
            String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            u.setPassword(hashPassword);
            session.save(u);
            tx.commit();
            inserito = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "" + inserito;
    }

    public String getSessionID() {
        HttpServletRequest httpReq = getThreadLocalRequest();
        HttpSession httpSess = httpReq.getSession();
        return httpSess.getId();
    }
}
