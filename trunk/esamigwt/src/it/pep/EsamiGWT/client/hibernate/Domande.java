/*
 * classe per rappresentare gli argomenti delle domande
 */
package it.pep.EsamiGWT.client.hibernate;

import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.dbBrowse.BrowseElement;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import java.io.Serializable;

/**
 *
 * @author Francesco
 */
public class Domande implements Serializable, BrowseElement {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2026854906340120298L;
	static private String[] headersBrowse = {"domanda", "esatta", "errata1", "errata2", "errata3", "errata4", "utilizzato"};
    static private String[] headersEdit = {"id", "domanda", "esatta", "errata1", "errata2", "errata3", "errata4", "utilizzato", "argomento (visibile solo dopo il refresh)"};
    private int ID;
    private String domanda;
    private String risp1;
    private String risp2;
    private String risp3;
    private String risp4;
    private String risp5;
    private int idArgomento;
    private boolean utilizzato;

    public int getNumberColumnBrowse() {
        return 8;   // il codice + GLI ALTRI
    }

    public Widget getColumnBrowse(int column) {
        Widget retVal = null;
        switch (column) {
            case 0: // id
                retVal = new Label("" + getID());
                break;
            case 1: // domanda
                retVal = new HTML(getDomanda());
                break;
            case 2: // risposta 1
                retVal = new HTML(getRisp1());
                break;
            case 3: // risposta 2
                retVal = new HTML(getRisp2());
                break;
            case 4: // risposta 3
                retVal = new HTML(getRisp3());
                break;
            case 5: // risposta 4
                retVal = new HTML(getRisp4());
                break;
            case 6: // risposta 5
                retVal = new HTML(getRisp5());
                break;
            case 7: // utilizzato
                retVal = new CheckBox();
                ((CheckBox) retVal).setValue(isUtilizzato());
                ((CheckBox) retVal).setEnabled(false);
                break;
        }
        return retVal;
    }

    public String[] getHeaders() {
        return headersBrowse;
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

    public int getIdArgomento() {
        return idArgomento;
    }

    public void setIdArgomento(int idArgomento) {
        this.idArgomento = idArgomento;
    }

    public boolean isUtilizzato() {
        return utilizzato;
    }

    public void setUtilizzato(boolean utilizzato) {
        this.utilizzato = utilizzato;
    }

    public int getNumberFieldEdit() {
        return 9;
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
            case 1: // domanda
                TextArea tmp1 = new TextArea();
                tmp1.setText(getDomanda());
                tmp1.setEnabled(isEditable);
                tmp1.setSize(largTextArea, altTextArea);
                retVal = tmp1;
                break;
            case 2: // risposta 1
                TextArea tmp2 = new TextArea();
                tmp2.setText(getRisp1());
                tmp2.setEnabled(isEditable);
                tmp2.setSize(largTextArea, altTextArea);
                retVal = tmp2;
                break;
            case 3: // risposta 2
                TextArea tmp3 = new TextArea();
                tmp3.setText(getRisp2());
                tmp3.setEnabled(isEditable);
                tmp3.setSize(largTextArea, altTextArea);
                retVal = tmp3;
                break;
            case 4: // risposta 3
                TextArea tmp4 = new TextArea();
                tmp4.setText(getRisp3());
                tmp4.setEnabled(isEditable);
                tmp4.setSize(largTextArea, altTextArea);
                retVal = tmp4;
                break;
            case 5: // risposta 4
                TextArea tmp5 = new TextArea();
                tmp5.setText(getRisp4());
                tmp5.setEnabled(isEditable);
                tmp5.setSize(largTextArea, altTextArea);
                retVal = tmp5;
                break;
            case 6: // risposta 5
                TextArea tmp6 = new TextArea();
                tmp6.setText(getRisp5());
                tmp6.setEnabled(isEditable);
                tmp6.setSize(largTextArea, altTextArea);
                retVal = tmp6;
                break;
            case 7: // utilizzato
                retVal = new CheckBox();
                ((CheckBox) retVal).setValue(isUtilizzato());
                ((CheckBox) retVal).setEnabled(isEditable);
                break;
            case 8: // id argomento
                ListBox lb = new ListBox();
                lb.setEnabled(isEditable);
                int caricati = 0;
                for (Argomenti a : DatiStatici.getListaArgomenti()) {
                    lb.addItem(a.getDescrizione());
                    if (a.getID() == getIdArgomento()) {
                        lb.setItemSelected(caricati, true);
                    }
                    caricati++;
                }
                retVal = lb;
        }
        return retVal;
    }

    public void setFieldValueFromEdit(int column, Widget widget) {
        switch (column) {
            case 0: // id   
                // è in sola lettura
                break;
            case 1: // domanda
                setDomanda(((TextArea) widget).getText());
                break;
            case 2: // risposta 1
                setRisp1(((TextArea) widget).getText());
                break;
            case 3: // risposta 2
                setRisp2(((TextArea) widget).getText());
                break;
            case 4: // risposta 3
                setRisp3(((TextArea) widget).getText());
                break;
            case 5: // risposta 4
                setRisp4(((TextArea) widget).getText());
                break;
            case 6: // risposta 5
                setRisp5(((TextArea) widget).getText());
                break;
            case 7: // utilizzato
                boolean checked = ((CheckBox) widget).getValue();
                setUtilizzato(checked);
                break;
            case 8: // id argomento
                ListBox lb = (ListBox) widget;
                setIdArgomento(DatiStatici.getListaArgomenti().get(lb.getSelectedIndex()).getID());
                break;
        }
    }

    public String getFieldTitleEdit(int column) {
        return headersEdit[column];
    }

    public String getRisp(int numRisp) {
        String retVal=null;
        switch(numRisp){
            case 1:
                retVal=getRisp1();
                break;
            case 2:
                retVal=getRisp2();
                break;
            case 3:
                retVal=getRisp3();
                break;
            case 4:
                retVal=getRisp4();
                break;
            case 5:
                retVal=getRisp5();
                break;
        }
        return retVal;
    }
}
