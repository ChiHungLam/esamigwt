/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.maschere;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.*;
import it.pep.EsamiGWT.client.gwt.WaitDialog;
import it.pep.EsamiGWT.client.hibernate.Argomenti;
import it.pep.EsamiGWT.client.hibernate.Utenti;
import it.pep.EsamiGWT.client.staticData.DatiStatici;

/**
 *
 * @author Francesco
 */
public class DialogSBloccoDomande extends DialogBox {

    private VerticalPanel dialogContents = new VerticalPanel();
    private FlexTable ft = new FlexTable();
    private CheckBox cbTuttiArgomenti = new CheckBox("Tutti gli argomenti");
    private Label lb1 = new Label("Argomento");
    private ListBox lbArgomento = new ListBox();
    private Button btnOk = new Button("Ok");
    private Button btnAnnulla = new Button("Annulla");
    private boolean bloccare;
    private BusinessServiceAsync businessSvc = GWT.create(BusinessService.class);
    private Utenti utenteLoggato = null;
    private final WaitDialog wd = new WaitDialog();
    private SottoMascheraDomande smd=null;

    public DialogSBloccoDomande(Utenti utenteLoggato, boolean blocco, SottoMascheraDomande md) {
        this.utenteLoggato = utenteLoggato;
        this.bloccare = blocco;
        this.smd=md;
        lbArgomento.setEnabled(false);
        for (Argomenti a : DatiStatici.getListaArgomenti()) {
            lbArgomento.addItem(a.getDescrizione());
        }
        cbTuttiArgomenti.setValue(true);
        cbTuttiArgomenti.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

            public void onValueChange(ValueChangeEvent<Boolean> event) {
                lbArgomento.setEnabled(!event.getValue());
            }
        });

        btnOk.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
//                int codArgSel = DatiStatici.getListaArgomenti().get(lbArgomento.getSelectedIndex()).getID();
                hide();
                wd.show();
                sBlocca();
            }
        });
        btnAnnulla.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                hide();
            }
        });

        add(dialogContents);
        dialogContents.add(ft);
        ft.setWidget(0, 0, cbTuttiArgomenti);
        ft.setWidget(1, 0, lb1);
        ft.setWidget(1, 1, lbArgomento);
        ft.setWidget(6, 0, btnOk);
        ft.setWidget(6, 1, btnAnnulla);

        setText((bloccare ? "Blocco" : "Sblocco") + " domande");
        setAnimationEnabled(true);
        center();
    }

    private void sBlocca() {
        final Argomenti argSel=DatiStatici.getListaArgomenti().get(lbArgomento.getSelectedIndex());
        // Initialize the service proxy.
        if (businessSvc == null) {
            businessSvc = GWT.create(BusinessService.class);
        }

        // Set up the callback object.
        AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

            public void onFailure(Throwable caught) {
                wd.hide();
                Window.alert("Errore nello (s)blocco delle domande");
                caught.printStackTrace();
            }

            public void onSuccess(Boolean result) {
                smd.trovaDomande();     // serve per aggiornare la visualizzazione delle domande
                wd.hide();
                Window.alert("Domande "+(bloccare ? "bloccate" : "sbloccate"));
            }
        };

        // Make the call to the service.
        businessSvc.bloccaDomandePerUtente(
                utenteLoggato.getUtente(),
                cbTuttiArgomenti.getValue(),
                argSel,
                bloccare,
                callback);
    }
}
