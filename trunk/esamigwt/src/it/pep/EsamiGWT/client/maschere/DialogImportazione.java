/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.maschere;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import gwtupload.client.SingleUploader;
import it.pep.EsamiGWT.client.BusinessService;
import it.pep.EsamiGWT.client.BusinessServiceAsync;
import it.pep.EsamiGWT.client.gwt.WaitDialog;
import it.pep.EsamiGWT.client.hibernate.Utenti;

/**
 *
 * @author Francesco
 */
class DialogImportazione extends DialogBox {

    private VerticalPanel dialogContents = new VerticalPanel();
    private FlexTable ft = new FlexTable();
    private Label lb1 = new Label("File CSV da importare");
    private FileUpload fu = new FileUpload();
    private Button btnOk = new Button("Ok");
    private Button btnAnnulla = new Button("Annulla");

    private SingleUploader su=new SingleUploader();

    private BusinessServiceAsync businessSvc = GWT.create(BusinessService.class);
    private Utenti utenteLoggato = null;
    private final WaitDialog wd = new WaitDialog();

    public DialogImportazione(Utenti utenteLoggato) {
        this.utenteLoggato = utenteLoggato;
        btnOk.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
//                int codArgSel = DatiStatici.getListaArgomenti().get(lbArgomento.getSelectedIndex()).getID();
                hide();
                wd.show();
                importa();
            }
        });
        btnAnnulla.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                hide();
            }
        });

        add(dialogContents);
        dialogContents.add(ft);
        ft.setWidget(0, 0, null);
        ft.setWidget(1, 0, lb1);
        ft.setWidget(1, 1, fu);
        ft.setWidget(6, 0, btnOk);
        ft.setWidget(6, 1, btnAnnulla);
        ft.setWidget(7, 0, su);

        setAnimationEnabled(true);
        center();
    }

    private void importa() {
        if (fu.getFilename().length() == 0) {
            Window.alert("E' necessario selezionare un file");
        } else {
            // Initialize the service proxy.
            if (businessSvc == null) {
                businessSvc = GWT.create(BusinessService.class);
            }

            // Set up the callback object.
            AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

                public void onFailure(Throwable caught) {
                    wd.hide();
                    Window.alert("Errore nell'importazione");
                    caught.printStackTrace();
                }

                public void onSuccess(Boolean result) {
//                smd.trovaDomande();     // serve per aggiornare la visualizzazione delle domande
                    wd.hide();
                    Window.alert("Importazione eseguita");
                }
            };

            // Make the call to the service.
            businessSvc.importaRisultati(
                    utenteLoggato.getUtente(),
                    fu.getFilename(),
                    callback);
        }
    }
}
