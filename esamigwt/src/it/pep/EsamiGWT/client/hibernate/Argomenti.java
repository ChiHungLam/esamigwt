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

    /**
	 * 
	 */
	private static final long serialVersionUID = 1073778157733419212L;
	static private String[] headersBrowse = {"descrizione"};
    static private String[] headersEdit = {"id", "descrizione", "pubblico"};
    private int ID;
    private String descrizione;
    private String codUtente;
    private boolean pubblico;
    private int numDomande;
    
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

    public boolean isPubblico() {
        return pubblico;
    }

    public void setPubblico(boolean pubblico) {
        this.pubblico = pubblico;
    }

    public String[] getHeaders() {
        return headersBrowse;
    }

    public int getNumberFieldEdit() {
        return 3;
    }

    public Widget getFieldEdit(int column, boolean isEditable) {
        Widget retVal = null;
        String largTextArea = "350px";
        String altTextArea = "100px";
        switch (column) {
            case 0: // id
                TextBox tmp0 = new TextBox();
                tmp0.setText("" + getID());
                tmp0.setEnabled(false);
                retVal = tmp0;
                break;
            case 1: // descrizione
                TextArea tmp1 = new TextArea();
                tmp1.setText(getDescrizione());
                tmp1.setEnabled(isEditable);
                tmp1.setSize(largTextArea, altTextArea);
                retVal = tmp1;
                break;
            case 2: // pubblico
                retVal = new CheckBox();
                ((CheckBox) retVal).setValue(isPubblico());
                ((CheckBox) retVal).setEnabled(isEditable);
                break;
        }
        return retVal;
    }

    public String getFieldTitleEdit(int column) {
        return headersEdit[column];
    }

    public void setFieldValueFromEdit(int column, Widget widget) {
        switch (column) {
            case 0: // id   
                // è in sola lettura
                break;
            case 1: // domanda
                setDescrizione(((TextArea) widget).getText());
                break;
            case 2: // utilizzato
                boolean checked = ((CheckBox) widget).getValue();
                setPubblico(checked);
                break;
        }
    }

    public int getNumDomande() {
        return numDomande;
    }

    public void setNumDomande(int numDomande) {
        this.numDomande = numDomande;
    }
}
