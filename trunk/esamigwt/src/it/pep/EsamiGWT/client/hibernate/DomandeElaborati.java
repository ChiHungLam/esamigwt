/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.hibernate;

import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.dbBrowse.BrowseElement;
import java.io.Serializable;

/**
 *
 * @author Francesco
 */
public class DomandeElaborati implements Serializable, BrowseElement {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3936635019540541291L;
	private int ID;
    private String domanda;
    private String risp1;
    private String risp2;
    private String risp3;
    private String risp4;
    private String risp5;
    private int idDomanda;
    private int idElaborato;
    private int esatta;
    private int fornita;
    private String esito;
    static private String[] headersBrowse = {"id", "domanda", "esatta", "fornita", "esito"};
    static private String[] headersEdit = {"id", "domanda", "risposta 1", "risposta 2", "risposta 3", "risposta 4", "risposta 5", "esatta", "fornita", "esito"};

    public DomandeElaborati(
            int ID,
            String domanda,
            String risp1,
            String risp2,
            String risp3,
            String risp4,
            String risp5,
            int idDomanda,
            int idElaborato,
            int esatta,
            int fornita,
            String esito) {
        this.ID = ID;
        this.domanda = domanda;
        this.risp1 = risp1;
        this.risp2 = risp2;
        this.risp3 = risp3;
        this.risp4 = risp4;
        this.risp5 = risp5;
        this.idDomanda = idDomanda;
        this.idElaborato = idElaborato;
        this.esatta = esatta;
        this.fornita = fornita;
        this.esito = esito;
    }

    public DomandeElaborati() {
    }

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
        return getHeadersBrowse().length + 1;
    }

    public int getNumberFieldEdit() {
        return getHeadersEdit().length + 1;
    }

    public Widget getColumnBrowse(int column) {
        //"id", "domanda", "esatta", "fornita", "esito"
        Widget retVal = null;
        switch (column) {
            case 0:
            case 1:
                retVal = new Label("" + ID);
                break;
            case 2:
                retVal = new HTML(getDomanda());
                break;
            case 3:
                retVal = new HTML("" + getEsatta());
                break;
            case 4:
                retVal = new HTML("" + getFornita());
                break;
            case 5:
                retVal = new HTML("" + getEsito());
                break;
        }
        return retVal;
    }

    public Widget getFieldEdit(
            int column, boolean isEditable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getFieldTitleEdit(
            int column) {
        return getHeadersEdit()[column];
    }

    public String[] getHeaders() {
        return getHeadersBrowse();
    }

    public void setFieldValueFromEdit(int column, Widget widget) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDomanda() {
        return domanda;
    }

    public void setDomanda(String domanda) {
        this.domanda = domanda;
    }

    public String getRisp1() {
        return risp1;
    }

    public void setRisp1(String risp1) {
        this.risp1 = risp1;
    }

    public String getRisp2() {
        return risp2;
    }

    public void setRisp2(String risp2) {
        this.risp2 = risp2;
    }

    public String getRisp3() {
        return risp3;
    }

    public void setRisp3(String risp3) {
        this.risp3 = risp3;
    }

    public String getRisp4() {
        return risp4;
    }

    public void setRisp4(String risp4) {
        this.risp4 = risp4;
    }

    public String getRisp5() {
        return risp5;
    }

    public void setRisp5(String risp5) {
        this.risp5 = risp5;
    }

    public int getIdDomanda() {
        return idDomanda;
    }

    public void setIdDomanda(int idDomanda) {
        this.idDomanda = idDomanda;
    }

    public int getIdElaborato() {
        return idElaborato;
    }

    public void setIdElaborato(int idElaborato) {
        this.idElaborato = idElaborato;
    }

    public int getEsatta() {
        return esatta;
    }

    public void setEsatta(int esatta) {
        this.esatta = esatta;
    }

    public int getFornita() {
        return fornita;
    }

    public void setFornita(int fornita) {
        this.fornita = fornita;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }
}
