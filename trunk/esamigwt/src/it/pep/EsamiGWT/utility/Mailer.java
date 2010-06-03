/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.utility;

import java.util.Properties;
import javax.mail.*;

public class Mailer {

    /** Username to use for authentication. */
    protected String username;
    /** Password to use for authentication. */
    protected String password;
    /**
     * Authenticator to be used with servers requiring authentication. If server
     * does not require authentication, this object will be ignored, but it is,
     * nevertheless, required.
     */
    protected Authenticator authenticator = new BasicAuthenticator();

    // ...
    public void sendMessage(final String host, final String from, final String to,
            final String subject, final String text) throws MessagingException {
        // Create a new session
// Create empty properties
        Properties props = new Properties();
        props.put("mail.host", host);

// Setup authentication, get session
        final Session session = Session.getDefaultInstance(props, authenticator);

// Get the store
        Store store = session.getStore("pop3");
        store.connect();
    }

    /**
     * Authenticates component using configured username and password.
     */
    public class BasicAuthenticator extends Authenticator {

        /**
         * Password authentication, returning username and password specified
         * in the implementation.
         */
        protected PasswordAuthentication passwordAuthentication = new PasswordAuthentication(username, password);

        /**
         * Returns object performing password-based authentication.
         * @return Password authentication object.
         */
        protected PasswordAuthentication getPasswordAuthentication() {
            return passwordAuthentication;
        }
    }
}