/*
 * aggiunge la funzione onVisible
 */
package it.pep.EsamiGWT.client.gwt;

import com.google.gwt.user.client.ui.Composite;

/**
 *
 * @author Francesco
 */
public class MioComposite extends Composite {

    /**
     * viene richiamata quando il widget diventa visibile
     * serve se ci sono splitPanel dentro un tabPanel
     * perchè devono essere ridimensionati ogni volta
     * con un comando del tipo
     * hSplit.setSplitPosition("30%");
     */
    public void onVisible() {
    }
}
