/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pep.EsamiGWT.server;

import org.apache.log4j.Logger;

/**
 *
 * @author Francesco
 */
public class LoggerApache {

    private static org.apache.log4j.Logger log = Logger.getLogger(LoggerApache.class);

    public static void debug(String messaggio) {
        log.debug("********"+messaggio+"********");
    }
    public static void error(String messaggio) {
        log.error(messaggio);
    }
    public static void info(String messaggio) {
        log.info(messaggio);
    }
}
