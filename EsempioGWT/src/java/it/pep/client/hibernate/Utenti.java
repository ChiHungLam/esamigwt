/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pep.EsamiGWT.client.hibernate;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class Utenti implements Serializable{
    private String utente,password;

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
