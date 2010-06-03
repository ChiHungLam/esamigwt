/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pep.EsamiGWT.client.maschere;

import it.pep.EsamiGWT.client.gwt.MioComposite;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.hibernate.Appelli;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 *
 * @author Francesco
 */
public class SottoMascheraDettaglioAppello extends MioComposite{
    private DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    private ScrollPanel sp = new ScrollPanel();
    private FlexTable ft = new FlexTable();
    private boolean isEditable = false;
    private Appelli appello = null;
    private Widget[] inputFields = null;
    private MascheraAppelli parent = null;
    private boolean update = false;

    public SottoMascheraDettaglioAppello(Appelli app, boolean isEditable, MascheraAppelli par) {
        this.appello = app;
        this.isEditable = isEditable;
        this.parent = par;

        initWidget(decPanel);
        decPanel.setWidget(sp);
        sp.add(ft);
        int numCampi = app.getNumberFieldEdit();
        inputFields = new Widget[numCampi];
        for (int k = 0; k < numCampi; k++) {
            Label l = new Label(app.getFieldTitleEdit(k), true);
            l.addStyleName("etichetta");
            ft.setWidget(k, 0, l);
            inputFields[k] = app.getFieldEdit(k, isEditable);
            inputFields[k].addStyleName("campo");
            ft.setWidget(k, 1, inputFields[k]);
        }
        Button salvaButton = new Button("salva modifiche");
        salvaButton.setEnabled(isEditable);
        salvaButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent arg0) {
                apportaModifiche();
                // Create an asynchronous callback to handle the result.
                final AsyncCallback<String> callback = new AsyncCallback<String>() {

                    public void onSuccess(String result) {
                        String strRes = result;
                        if (strRes.equalsIgnoreCase("true")) {
                            if (update) {
                                parent.trovaAppelli();
                            } else {
                                parent.aggiornaRigaCorrente(appello);
                            }
                        } else {
                            Window.alert("salvataggio fallito");
                        }
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel salvataggio della Appello");
                    }
                };

                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                DatiStatici.getDBService().salvaAppello(appello,update, callback);
            }
        });
        ft.setWidget(numCampi, 1, salvaButton);
    }

    private void apportaModifiche() {
        int numCampi = appello.getNumberFieldEdit();
        for (int k = 0; k < numCampi; k++) {
            appello.setFieldValueFromEdit(k, inputFields[k]);
        }
        appello.setCodUtente(parent.getUtenteLoggato().getUtente());
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean rileggiLista) {
        this.update = rileggiLista;
    }
}
