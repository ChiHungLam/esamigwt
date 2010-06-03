/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.maschere;

import it.pep.EsamiGWT.client.gwt.MioComposite;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.gwt.WaitDialog;
import it.pep.EsamiGWT.client.hibernate.Argomenti;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import java.util.Date;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 *
 * @author Francesco
 */
public class SottoMascheraDettaglioArgomento extends MioComposite {

    private DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    private ScrollPanel sp = new ScrollPanel();
    private FlexTable ft = new FlexTable();
    private boolean isEditable = false;
    private Argomenti argomento = null;
    private Widget[] inputFields = null;
    private MascheraArgomenti parent = null;
    private boolean update = false;
    private WaitDialog wd = new WaitDialog();

    public SottoMascheraDettaglioArgomento(Argomenti arg, boolean isEditable, MascheraArgomenti par) {
        this.argomento = arg;
        this.isEditable = isEditable;
        this.parent = par;

        initWidget(decPanel);
        decPanel.setWidget(sp);
        sp.add(ft);
        int numCampi = arg.getNumberFieldEdit();
        inputFields = new Widget[numCampi];
        for (int k = 0; k < numCampi; k++) {
            Label l = new Label(arg.getFieldTitleEdit(k), true);
            l.addStyleName("etichetta");
            ft.setWidget(k, 0, l);
            inputFields[k] = arg.getFieldEdit(k, isEditable);
            inputFields[k].addStyleName("campo");
            ft.setWidget(k, 1, inputFields[k]);
        }
        Button salvaButton = new Button("salva modifiche");
        salvaButton.setEnabled(isEditable);
        salvaButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent arg0) {
                wd.show();
                apportaModifiche();
                // Create an asynchronous callback to handle the result.
                final AsyncCallback<String> callback = new AsyncCallback<String>() {

                    public void onSuccess(String result) {
                        parent.chiudiDialog();
                        String strRes = result;
                        if (strRes.equalsIgnoreCase("true")) {
                            wd.hide();
                            Window.alert("salvataggio effettuato");
                            if (update) {
                                parent.trovaArgomenti();
                            } else {
                                parent.aggiornaRigaCorrente(argomento);
                            }
                        } else {
                            wd.hide();
                            Window.alert("salvataggio fallito");
                        }
                    }

                    public void onFailure(Throwable caught) {
                        parent.chiudiDialog();
                        Window.alert("Errore nel salvataggio della argomento");
                    }
                };

                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                DatiStatici.getDBService().salvaArgomento(argomento, update, callback);
            }
        });
        ft.setWidget(numCampi, 1, salvaButton);
    }

    private void apportaModifiche() {
        int numCampi = argomento.getNumberFieldEdit();
        for (int k = 0; k < numCampi; k++) {
            argomento.setFieldValueFromEdit(k, inputFields[k]);
        }
        /**
         * se l'utente non ha pagato la sottoscrizione è costretto a creare argomenti pubblici
         */
        if (!argomento.isPubblico() && !utenteSottoscrittore(parent.getUtenteLoggato().getScadenzaSottoscrizione())) {
            Window.alert("Solo gli utenti paganti possono creare argomenti privati");
            argomento.setPubblico(true);
        }
//                parent.getUtenteLoggato().getScadenzaSottoscrizione().after(oggi()));
        argomento.setCodUtente(parent.getUtenteLoggato().getUtente());
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean rileggiLista) {
        this.update = rileggiLista;
    }

    private boolean utenteSottoscrittore(Date scadenzaSottoscrizione) {
        boolean retVal = false;
        if (scadenzaSottoscrizione != null) {
            java.util.Date today = new java.util.Date();
            retVal = (!scadenzaSottoscrizione.before(today));
        }
        return retVal;
    }
}
