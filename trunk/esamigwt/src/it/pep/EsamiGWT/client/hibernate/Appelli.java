/*
 * classe per rappresentare gli argomenti delle domande
 */
package it.pep.EsamiGWT.client.hibernate;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import it.pep.EsamiGWT.client.dbBrowse.BrowseElement;
import java.io.Serializable;
import java.util.Date;
//import org.zenika.widget.client.datePicker.DatePicker;

/**
 *
 * @author Francesco
 */
public class Appelli implements Serializable, BrowseElement {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2761111640604216694L;
	static private String[] headersBrowse = {"data", "descrizione"};
    static private String[] headersEdit = {"id", "data", "descrizione"};
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
            return new HTML(DateTimeFormat.getShortDateFormat().format(dataAppello));
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
        return headersBrowse;
    }

    public Date getDataAppello() {
        return dataAppello;
    }

    public void setDataAppello(Date data) {
        this.dataAppello = data;
    }

    public int getNumberFieldEdit() {
        return 3;
    }

    public Widget getFieldEdit(int column, boolean isEditable) {
        Widget retVal = null;
        switch (column) {
            case 0: // id
                TextBox tmp0 = new TextBox();
                tmp0.setText("" + getID());
                tmp0.setEnabled(false);
                retVal = tmp0;
                break;
            case 1: // data
                DatePicker tmp1 = new DatePicker();
                tmp1.setValue(getDataAppello());
                tmp1.setVisible(isEditable);
                retVal = tmp1;
                break;
            case 2: // descrizione
                TextBox tmp2 = new TextBox();
                tmp2.setText(getDescrizione());
                tmp2.setEnabled(isEditable);
                retVal = tmp2;
                break;
        }
        return retVal;
    }

    public String getFieldTitleEdit(int column) {
        return headersEdit[column];
    }

    public void setFieldValueFromEdit(int column, Widget widget) {
        switch (column) {
            case 0: // id in sola lettura
                break;
            case 1: // data
                setDataAppello(((DatePicker) widget).getValue());
                break;
            case 2: // descrizione
                setDescrizione(((TextBox) widget).getText());
                break;
        }
    }
}
