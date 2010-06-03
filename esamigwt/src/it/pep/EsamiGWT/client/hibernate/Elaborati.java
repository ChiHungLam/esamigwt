/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pep.EsamiGWT.client.hibernate;

import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.dbBrowse.BrowseElement;
import it.pep.EsamiGWT.client.Utility;
import java.io.Serializable;

/**
 *
 * @author Francesco
 */
public class Elaborati  implements Serializable, BrowseElement {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1579608788658143883L;
	private int ID;
    private int idAppello;
    private int gruppo;
    private float punteggio;
    private String matricolaStudente;
    private String nomeStudente;
    private String cognomeStudente;
    private String note;

    static private String[] headersBrowse = {"id", "cognome","nome","matricola","punteggio","gruppo","note"};
    static private String[] headersEdit = {"id", "cognome","nome","matricola","note"};

    public static String[] getHeadersBrowse() {
        return headersBrowse;
    }

    public static void setHeadersBrowse(String[] aHeadersBrowse) {
        headersBrowse = aHeadersBrowse;
    }

    public static String[] getHeadersEdit() {
        return headersEdit;
    }

    public static void setHeadersEdit(String[] aHeadersEdit) {
        headersEdit = aHeadersEdit;
    }
    public int getNumberColumnBrowse() {
        return getHeadersBrowse().length+1;
    }

    public int getNumberFieldEdit() {
        return getHeadersEdit().length;
    }

    public Widget getColumnBrowse(int column) {
        Widget retVal = null;
        switch (column) {
            case 0:
            case 1:
                retVal = new Label("" + ID);
                break;
            case 2:
                retVal = new HTML(getCognomeStudente());
                break;
            case 3:
                retVal = new HTML(getNomeStudente());
                break;
            case 4:
                retVal = new HTML(getMatricolaStudente());
                break;
            case 5:
                retVal = new HTML("" + getPunteggio());
                break;
            case 6:
                retVal = new HTML("" + getGruppo());
                break;
            case 7:
                retVal = new HTML(getNote());
                break;
        }
        return retVal;
    }

    public Widget getFieldEdit(int column, boolean isEditable) {
        Widget retVal = null;
        switch (column) {
            case 0:
                TextBox tmp0 = new TextBox();
                tmp0.setText("" + getID());
                tmp0.setEnabled(false);
                retVal = tmp0;
                break;
            case 1:
                TextBox tmp2 = new TextBox();
                tmp2.setText(Utility.stringaNonNulla(getCognomeStudente()));
                tmp2.setEnabled(isEditable);
                retVal = tmp2;
                break;
            case 2:
                TextBox tmp3 = new TextBox();
                tmp3.setText(Utility.stringaNonNulla(getNomeStudente()));
                tmp3.setEnabled(isEditable);
                retVal = tmp3;
                break;
            case 3:
                TextBox tmp4 = new TextBox();
                tmp4.setText(Utility.stringaNonNulla(getMatricolaStudente()));
                tmp4.setEnabled(isEditable);
                retVal = tmp4;
                break;
            case 4:
                TextBox tmp5 = new TextBox();
                tmp5.setText(Utility.stringaNonNulla(getNote()));
                tmp5.setEnabled(isEditable);
                retVal = tmp5;
                break;
        }
        return retVal;
    }

    public String getFieldTitleEdit(int column) {
        return getHeadersEdit()[column];
    }

    public String[] getHeaders() {
        return getHeadersBrowse();
    }

    public void setFieldValueFromEdit(int column, Widget widget) {
        switch (column) {
            case 0:
                setID(Integer.parseInt(((TextBox)widget).getText()));
                break;
            case 1:
                setCognomeStudente(((TextBox)widget).getText());
                break;
            case 2:
                setNomeStudente(((TextBox)widget).getText());
                break;
            case 3:
                setMatricolaStudente(((TextBox)widget).getText());
                break;
            case 4:
                setNote(((TextBox)widget).getText());
                break;
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIdAppello() {
        return idAppello;
    }

    public void setIdAppello(int idAppello) {
        this.idAppello = idAppello;
    }

    public int getGruppo() {
        return gruppo;
    }

    public void setGruppo(int gruppo) {
        this.gruppo = gruppo;
    }

    public float getPunteggio() {
        long punteggioTmp=(long) Math.round(punteggio*100);
        return punteggioTmp/100.0f;
    }

    public void setPunteggio(float punteggio) {
        long punteggioTmp=(long) Math.round(punteggio*100);
        this.punteggio = punteggioTmp/100.0f;
    }

    public String getMatricolaStudente() {
        return matricolaStudente;
    }

    public void setMatricolaStudente(String matricolaStudente) {
        this.matricolaStudente = matricolaStudente;
    }

    public String getNomeStudente() {
        return nomeStudente;
    }

    public void setNomeStudente(String nomeStudente) {
        this.nomeStudente = nomeStudente;
    }

    public String getCognomeStudente() {
        return cognomeStudente;
    }

    public void setCognomeStudente(String cognomeStudente) {
        this.cognomeStudente = cognomeStudente;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
