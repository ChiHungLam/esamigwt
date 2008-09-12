/*
 * classe per rappresentare gli argomenti delle domande
 */
package it.pep.EsamiGWT.client.hibernate;

import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.dbBrowse.BrowseElement;
import java.io.Serializable;

/**
 *
 * @author Francesco
 */
public class Argomenti implements Serializable, BrowseElement {

    static private String[] headers={"descrizione"};

    private int ID;
    private String descrizione;
    private String codUtente;
    private boolean isPubblico;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getNumberColumnBrowse() {
        return 2;   // il codice + la descrizione
    }

    public Widget getColumnBrowse(int column) {
        if (column == 0) {
            return new Label("" + ID);
        }
        return new HTML(descrizione);
    }

    public String getCodUtente() {
        return codUtente;
    }

    public void setCodUtente(String codUtente) {
        this.codUtente = codUtente;
    }

    public boolean isIsPubblico() {
        return isPubblico;
    }

    public void setIsPubblico(boolean isPubblico) {
        this.isPubblico = isPubblico;
    }

    public String[] getHeaders() {
        return headers;
    }

    public int length() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
