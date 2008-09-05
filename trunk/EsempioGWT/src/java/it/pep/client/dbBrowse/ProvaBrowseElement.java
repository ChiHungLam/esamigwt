/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.client.dbBrowse;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import it.pep.client.dbBrowse.BrowseElement;

/**
 *
 * @author user
 */
public class ProvaBrowseElement implements BrowseElement {

    private String nome,  cognome;
    private int chiave = (int) (Math.random() * 10000000);

    public int length() {
        return 3;
    }

    public Widget get(int posizione) {
        if (posizione == 0) {
            return new HTML("" + chiave);
        } else if (posizione == 1) {
            return new HTML("nome"+chiave);
        } else {
            return new HTML("un cognome"+chiave);
        }
    }
}
