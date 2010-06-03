/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pep.EsamiGWT.client.hibernate;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author user
 */
public class Utenti implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2350131750077649509L;
	private String utente,password;
    private boolean abilitato;
    private Date scadenzaSottoscrizione;
    private String activationCode;

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

    /**
     * @return the abilitato
     */
    public boolean isAbilitato() {
        return abilitato;
    }

    /**
     * @param abilitato the abilitato to set
     */
    public void setAbilitato(boolean abilitato) {
        this.abilitato = abilitato;
    }

    /**
     * @return the scadenzaSottoscrizione
     */
    public Date getScadenzaSottoscrizione() {
        return scadenzaSottoscrizione;
    }

    /**
     * @param scadenzaSottoscrizione the scadenzaSottoscrizione to set
     */
    public void setScadenzaSottoscrizione(Date scadenzaSottoscrizione) {
        this.scadenzaSottoscrizione = scadenzaSottoscrizione;
    }

    /**
     * @return the activation_code
     */
    public String getActivationCode() {
        return activationCode;
    }

    /**
     * @param activation_code the activation_code to set
     */
    public void setActivationCode(String activation_code) {
        this.activationCode = activation_code;
    }

}
