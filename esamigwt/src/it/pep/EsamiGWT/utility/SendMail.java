/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.utility;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Francesco
 */
public class SendMail {

    /**
     * Inserire qui la descrizione del metodo.
     * Data di creazione: (05/11/2002 11.50.17)
     * @return boolean
     * @param to java.lang.String
     * @param from java.lang.String
     * @param host java.lang.String
     * @param msgText java.lang.String
     * @param subject java.lang.String
     */
    public static boolean inviaEmail(
            String to,
            String from,
            String host,
            String msgText,
            String subject,
            String cc) {

        //imposto debug a true se voglio che sia effettuato
        boolean debug = false;

        // Crea alcune proprieta e prende la sessione di default
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host); 
        if (debug) {
            props.put("mail.debug", "true"); 
        }
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(debug);

        try {
            // crea un messaggio
            Message msg = new MimeMessage(session);
            // setta il mittente
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            // setta il destinatario
            msg.setRecipients(Message.RecipientType.TO, address);
            // setta il cc
            if (cc != null && !cc.equals("")) {
                InternetAddress[] ccAddress = {new InternetAddress(cc)};
                msg.setRecipients(Message.RecipientType.CC, ccAddress);
            }
            // setta il subject
            msg.setSubject(subject);
            // setta la data
            msg.setSentDate(new java.util.Date());
            // setta il corpo del messaggio
            msg.setText(msgText);
            // manda il messaggio
            Transport.send(msg);

            return true;

        } catch (MessagingException mex) {

            mex.printStackTrace();
            return false;
        }

    }
}