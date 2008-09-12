/*
 * classe per rappresentare gli argomenti delle domande
 */
package it.pep.EsamiGWT.client.hibernate;

import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.dbBrowse.BrowseElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Francesco
 */
public class Appelli implements Serializable, BrowseElement {

    static private String[] headers = {"data", "descrizione"};
    private int ID;
    private Date dataAppello;
    private String descrizione;
    private String codUtente;

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
        return 3;   // codice + data + descrizione
    }

    public Widget getColumnBrowse(int column) {
        if (column == 0) {
            return new Label("" + ID);
        } else if (column == 1) {
            return new HTML("" + dataAppello);
        }
        return new HTML(descrizione);
    }

    public String getCodUtente() {
        return codUtente;
    }

    public void setCodUtente(String codUtente) {
        this.codUtente = codUtente;
    }

    public String[] getHeaders() {
        return headers;
    }

    public int length() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Date getDataAppello() {
        return dataAppello;
    }

    public void setDataAppello(Date data) {
        this.dataAppello = data;
    }
}
