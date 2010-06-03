/*
 * LoginServiceImpl.java
 *
 * Created on 26 luglio 2008, 10.45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import it.pep.EsamiGWT.client.LoginService;
import java.util.List;
import it.pep.EsamiGWT.client.hibernate.*;
import it.pep.EsamiGWT.utility.*;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.*;

/**
 *
 * @author user
 */
public class LoginServiceImpl extends RemoteServiceServlet implements
        LoginService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8802194097127648389L;
	private final String fraseSegreta = "finchèlabarcava";
    private String from = "info@pep.it";
    private String host = "localhost";

    @SuppressWarnings("unchecked")
	public Utenti isUtente(String utente, String password) {
        List<Utenti> utentiList = null;
        boolean trovato = false;
        Utenti retVal = null;
        try {
            LoggerApache.debug("LoginService");
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Utenti u where u.utente=:utente and u.abilitato=true");
            q.setParameter("utente", utente);
            utentiList = (List<Utenti>) q.list();
            if (utentiList.size() != 0) {
                retVal = utentiList.get(0);
                trovato = BCrypt.checkpw(password, retVal.getPassword().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (trovato ? retVal : null);
    }

    public String registraUtente(String utente, String password, String urlBase) {
        Session session = null;
        boolean inserito = false;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            Utenti u = new Utenti();
            u.setUtente(utente);
            String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            u.setPassword(hashPassword);
            if (u.getActivationCode() == null) {
                Timestamp ts = new Timestamp(new java.util.Date().getTime());
                String activation_code = BCrypt.hashpw(ts + password + fraseSegreta, BCrypt.gensalt());
                u.setActivationCode(activation_code);
            }
            session.save(u);
            inserito = inviaMailConferma(u, urlBase);
        } catch (NonUniqueObjectException e) {
            return "preesistente";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inserito) {
                tx.commit();
            } else {
                tx.rollback();
            }
        }
        return "" + inserito;
    }

    public String getSessionID() {
        HttpServletRequest httpReq = getThreadLocalRequest();
        HttpSession httpSess = httpReq.getSession();
        return httpSess.getId();
    }

    private boolean inviaMailConferma(Utenti u, String urlBase) {
        boolean retVal;
        HttpServletRequest httpReq = getThreadLocalRequest();
        String confirmationUrl = urlBase + "ConfermaIscrizione?id=" + u.getActivationCode();
        String msg = "Clicca sul link sotto per attivare la tua registrazione sul sito " + httpReq.getServerName() + ".\n" + confirmationUrl;
        retVal = SendMail.inviaEmail(u.getUtente(), this.from, this.host, msg, "registrazione utente", "");
        return retVal;
    }
}
