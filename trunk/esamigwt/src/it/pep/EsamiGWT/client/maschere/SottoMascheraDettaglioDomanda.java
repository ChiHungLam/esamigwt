/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.maschere;

import it.pep.EsamiGWT.client.gwt.MioComposite;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.hibernate.Domande;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 *
 * @author Francesco
 */
public class SottoMascheraDettaglioDomanda extends MioComposite {

    private DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    private ScrollPanel sp = new ScrollPanel();
    private FlexTable ft = new FlexTable();
    private boolean isEditable = false;
    private Domande domanda = null;
    private Widget[] inputFields = null;
    private SottoMascheraDomande parent = null;
    private boolean update = false;

    public SottoMascheraDettaglioDomanda(Domande dom, boolean isEditable, SottoMascheraDomande par) {
        this.domanda = dom;
        this.isEditable = isEditable;
        this.parent = par;

        initWidget(decPanel);
        decPanel.setWidget(sp);
        sp.add(ft);
        int numCampi = dom.getNumberFieldEdit();
        inputFields = new Widget[numCampi];
        for (int k = 0; k < numCampi; k++) {
            Label l = new Label(dom.getFieldTitleEdit(k), true);
            l.addStyleName("etichetta");
            ft.setWidget(k, 0, l);
            inputFields[k] = dom.getFieldEdit(k, isEditable);
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
                                parent.trovaDomande();
                            } else {
                                Window.alert("modifiche salvate");
                                parent.aggiornaRigaCorrente(domanda);
                            }
                        } else {
                            Window.alert("salvataggio fallito");
                        }
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nel salvataggio della domanda");
                    }
                };

                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                DatiStatici.getDBService().salvaDomanda(domanda, update, callback);
            }
        });
        ft.setWidget(numCampi, 1, salvaButton);
    }

    private void apportaModifiche() {
        int numCampi = domanda.getNumberFieldEdit();
        for (int k = 0; k < numCampi; k++) {
            domanda.setFieldValueFromEdit(k, inputFields[k]);
        }
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
