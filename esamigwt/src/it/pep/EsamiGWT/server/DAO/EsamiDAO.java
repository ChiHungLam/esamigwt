/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.server.DAO;

import it.pep.EsamiGWT.client.hibernate.*;
import it.pep.EsamiGWT.server.HibernateUtil;
import it.pep.EsamiGWT.server.LoggerApache;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Francesco
 */
public class EsamiDAO {

    public static List<Argomenti> trovaArgomentiPerUtente(String codUtente) {
        List<Argomenti> ArgomentiList = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Argomenti a where a.pubblico=true or a.codUtente=:codUtente");
            q.setParameter("codUtente", codUtente);
            ArgomentiList = (List<Argomenti>) q.list();
            LoggerApache.debug("argomenti trovati:" + q.list().size());
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ArgomentiList;
    }

    public static List<DomandeElaborati> trovaDomandeElaborato(int idElaborato) {
        List<DomandeElaborati> DomandeElaboratiList = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from DomandeElaborati de where de.idElaborato=:idElaborato order by de.ID");
            q.setParameter("idElaborato", idElaborato);
            DomandeElaboratiList = (List<DomandeElaborati>) q.list();
            LoggerApache.debug("Domande Elaborati trovate:" + q.list().size());
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DomandeElaboratiList;
    }

    public static long trovaQuanteDomandeElaborato(int idElaborato) {
        Long count = 0L;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("select count(*) from DomandeElaborati de where de.idElaborato=:idElaborato");
            q.setParameter("idElaborato", idElaborato);
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

    public static List<Domande> trovaDomandePerArgomento(int idArgomento) {
        return trovaDomandePerArgomento(idArgomento, false);
    }

    public static List<Domande> trovaDomandePerArgomento(int idArgomento, boolean soloNonUtilizzate) {
        return trovaDomandePerArgomento(idArgomento, soloNonUtilizzate, null);
    }

    public static List<Domande> trovaDomandePerArgomento(int idArgomento, boolean soloNonUtilizzate, Session session) {
        boolean gestisciSession = session == null;
        List<Domande> DomandeList = null;
        try {
            if (gestisciSession) {
                session = HibernateUtil.getSessionFactory().getCurrentSession();
                Transaction tx = session.beginTransaction();
            }
            String query = "from Domande d where d.idArgomento=:idArgomento";
            if (soloNonUtilizzate) {
                query += " and utilizzato=:utilizzato";
            }
            Query q = session.createQuery(query);
            q.setParameter("idArgomento", idArgomento);
            if (soloNonUtilizzate) {
                q.setParameter("utilizzato", false);
            }
            DomandeList = (List<Domande>) q.list();
            LoggerApache.debug("domande trovate:" + q.list().size());
            if (gestisciSession) {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DomandeList;
    }

    static public List<Appelli> trovaAppelliPerUtente(String codUtente) {
        List<Appelli> AppelliList = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Appelli a where a.codUtente=:codUtente order by a.dataAppello");
            q.setParameter("codUtente", codUtente);
            AppelliList = (List<Appelli>) q.list();
            LoggerApache.debug("appelli trovati:" + q.list().size());
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppelliList;
    }

    static public boolean salvaDomanda(Domande dom, boolean insert) {
        return salvaDomanda(dom, insert, null);
    }

    static public boolean salvaDomanda(Domande dom, boolean insert, Session session) {
        boolean salvato = false;
        boolean gestisciSession = (session == null);
        Transaction tx = null;
        try {
            if (gestisciSession) {
                session = HibernateUtil.getSessionFactory().getCurrentSession();
                tx = session.beginTransaction();
            }
            LoggerApache.debug("salvataggio domanda");
//            session = HibernateUtil.getSessionFactory().getCurrentSession();
//            Transaction tx = session.beginTransaction();
            if (insert) {
                session.save(dom);
            } else {
                session.update(dom);
            }
            if (gestisciSession) {
                tx.commit();
            }
            LoggerApache.debug("id domanda salvata: " + dom.getID());
            salvato = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salvato;
    }

    static public boolean cancDomanda(Domande dom) {
        Session session = null;
        boolean salvato = false;
        try {
            LoggerApache.debug("salvataggio domanda");
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            session.delete(dom);
            tx.commit();
            salvato = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salvato;
    }

    static public boolean salvaArgomenti(List<Argomenti> listaArgomenti) {
        for (Argomenti arg : listaArgomenti) {
            if (!salvaArgomento(arg, false)) {
                return false;
            }
        }
        return true;
    }

    static public boolean salvaArgomento(Argomenti arg, boolean insert) {
        Session session = null;
        boolean salvato = false;
        try {
            LoggerApache.debug("salvataggio argomento");
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            if (insert) {
                session.save(arg);
            } else {
                session.update(arg);
            }
            tx.commit();
            LoggerApache.debug("id argomento salvato: " + arg.getID());
            salvato = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salvato;
    }

    static public boolean cancArgomento(Argomenti arg) {
        Session session = null;
        boolean salvato = false;
        try {
            LoggerApache.debug("salvataggio argomento");
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            session.delete(arg);
            tx.commit();
            salvato = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salvato;
    }

    static public boolean salvaAppello(Appelli arg, boolean insert) {
        Session session = null;
        boolean salvato = false;
        try {
            LoggerApache.debug("salvataggio appello");
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            if (insert) {
                session.save(arg);
            } else {
                session.update(arg);
            }
            tx.commit();
            LoggerApache.debug("id appello salvato: " + arg.getID());
            salvato = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salvato;
    }

    static public boolean cancAppello(Appelli arg) {
        Session session = null;
        boolean salvato = false;
        try {
            LoggerApache.debug("salvataggio appello");
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            session.delete(arg);
            tx.commit();
            salvato = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salvato;
    }

    public static List<Elaborati> trovaElaboratiPerAppello(int idAppello) {
        List<Elaborati> ElaboratiList = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Elaborati e where e.idAppello=:idAppello order by e.ID");
            q.setParameter("idAppello", idAppello);
            ElaboratiList = (List<Elaborati>) q.list();
            LoggerApache.debug("Elaborati trovati:" + q.list().size());
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ElaboratiList;
    }

    public static boolean attivaUtente(String activationCode) {
        boolean attivato = false;
        Utenti ut = null;
        try {
            LoggerApache.debug("LoginService");
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Utenti u where u.activationCode=:activationCode");
            q.setParameter("activationCode", activationCode);
            List<Utenti> utentiList = (List<Utenti>) q.list();
            if (utentiList.size() == 1) {
                attivato = true;
                ut = utentiList.get(0);
                ut.setAbilitato(true);
                session.update(ut);
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attivato;
    }

    public static boolean sBloccaDomande(String codUtente, boolean tutte, Argomenti argomento, boolean blocco) {
        List<Argomenti> listaArgomenti = null;
        String hql = "update Domande set utilizzato = :blocco where idArgomento = :codArg";
        if (tutte) {
            listaArgomenti = trovaArgomentiPerUtente(codUtente);
        } else {
            listaArgomenti = new ArrayList<Argomenti>();
            listaArgomenti.add(argomento);
        }
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(hql);
        query.setBoolean("blocco", blocco);
        for (Argomenti a : listaArgomenti) {
//            List<Domande> listaDomande=trovaDomandePerArgomento(a.getID());
//            for (Domande d : listaDomande) {
//                d.setUtilizzato(blocco);
//                salvaDomanda(d, false);
//            }
            query.setInteger("codArg", a.getID());
            int rowCount = query.executeUpdate();
            System.out.println("Rows (s)bloccate: " + rowCount + " per argomento:" + a.getID());
        }
        tx.commit();
        return true;
    }

//    static public boolean salvaDomandaElaborato(DomandeElaborati de, boolean insert) {
//        Session session = null;
//        boolean salvato = false;
//        try {
//            LoggerApache.debug("salvataggio domanda di elaborato");
//            session = HibernateUtil.getSessionFactory().getCurrentSession();
//            Transaction tx = session.beginTransaction();
//            if (insert) {
//                session.save(de);
//            } else {
//                session.update(de);
//            }
//            tx.commit();
//            LoggerApache.debug("id domanda elaborato salvata: " + de.getID());
//            salvato = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return salvato;
//    }
}
