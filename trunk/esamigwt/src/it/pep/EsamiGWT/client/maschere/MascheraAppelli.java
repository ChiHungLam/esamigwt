/*
 * classe per rappresentare gli appelli delle domande
 */
/*
 * implementa listaAppelli finestra che mostra l'elenco degli appelli creati
 */
package it.pep.EsamiGWT.client.maschere;

import it.pep.EsamiGWT.client.gwt.MioComposite;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.dbBrowse.BrowseListener;
import it.pep.EsamiGWT.client.dbBrowse.DbBrowse;
import it.pep.EsamiGWT.client.hibernate.Appelli;
import it.pep.EsamiGWT.client.hibernate.Utenti;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import java.util.List;

/**
 *
 * @author user
 */
public class MascheraAppelli extends MioComposite implements BrowseListener {

    private List<Appelli> listaAppelli = null;
    private DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    private HorizontalSplitPanel hSplit = new HorizontalSplitPanel();   // un pannello con parte sinistra e destra
    private DbBrowse<Appelli> dbAppelli = null;
    private SottoMascheraElaborati sme = null;
    private final DialogBox dialogDettaglio = new DialogBox();
    private int posAppello = 0;
    private Utenti utenteLoggato = null;

    public MascheraAppelli(Utenti utenteLoggato) {
        if (utenteLoggato != null) {
            this.utenteLoggato = utenteLoggato;
            initWidget(decPanel);
            decPanel.setWidget(hSplit);
            hSplit.setSize("850px", "500px");
            trovaAppelli();
            onVisible();
        }
    }

    public void chiudiDialog() {
        dialogDettaglio.hide();
    }

    public void trovaAppelli() {
        // Create an asynchronous callback to handle the result.
        final AsyncCallback<List<Appelli>> callback = new AsyncCallback<List<Appelli>>() {

            public void onSuccess(List<Appelli> result) {
                listaAppelli = result;
                DatiStatici.setListaAppelli(listaAppelli);
                dbAppelli = new DbBrowse<Appelli>(listaAppelli, MascheraAppelli.this, new Appelli(), "Appelli");
                hSplit.setLeftWidget(dbAppelli);
                trovaElaborati();
                chiudiDialog();
            }

            public void onFailure(Throwable caught) {
                Window.alert("Errore nella ricerca degli appelli");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        DatiStatici.getDBService().trovaAppelliPerUtente(getUtenteLoggato().getUtente(), callback);
    }

    private void trovaElaborati() {
//        String strIdAppello = dbAppelli.getSelectedKey();
//        String descrAppello = (listaAppelli.get(dbAppelli.getSelectedRow() - 1)).getDescrizione();
//        sme = new SottoMascheraElaborati(strIdAppello, descrAppello, dataAppello);
        int rigaSel = dbAppelli.getSelectedRow() - 1;
        if (rigaSel >= 0) {
            Appelli appello = listaAppelli.get(rigaSel);
            sme = new SottoMascheraElaborati(appello);
            hSplit.setRightWidget(sme);
            sme.onVisible();
        }
    }

    void aggiornaRigaCorrente(Appelli appello) {
        dbAppelli.aggiornaRigaCorrente(appello);
        chiudiDialog();
    }

    @Override
    public void onVisible() {
        hSplit.setSplitPosition("30%");
//        if (md != null) {
//            md.onVisible();
//        }
    }

    /** 
     * metodi di BrowseListener per gestire le operazioni di browse
     */
    public int onSel(String key) {
        posAppello = dbAppelli.getSelectedRow() - 1;
        trovaElaborati();
        return 0;
    }

    public int onAdd() {
        Appelli nuova = new Appelli();
//        nuova.setCodUtente("da mettere");
        return editAppello(nuova, "Nuovo appello", true);
    }

    public int editAppello(Appelli arg, String titolo, boolean update) {
        Widget smda = null;
        if (update) {
            smda = new SottoMascheraCreaAppello(arg, true, this);
        } else {
            smda = new SottoMascheraDettaglioAppello(arg, true, this);
//        smda.setUpdate(update);
        }
        dialogDettaglio.setText(titolo);
        dialogDettaglio.setAnimationEnabled(true);
        VerticalPanel dialogContents = new VerticalPanel();
        dialogDettaglio.setWidget(dialogContents);

        // Add a close button at the top of the dialog
        PushButton closeButton = new PushButton(new Image("images/exit.png"),
                new ClickHandler() {

                    public void onClick(ClickEvent sender) {
                        chiudiDialog();
                    }
                });
        closeButton.addStyleName("uscita");
        dialogContents.add(closeButton);

        dialogContents.add(smda);


        dialogDettaglio.center();
        dialogDettaglio.show();
        return 0;
    }

    public int onDel(String key) {
        if (!listaAppelli.isEmpty()) {
            boolean confirm = Window.confirm("Vuoi veramente cancellare il record selezionato?");


            if (confirm) {
                Appelli dom = listaAppelli.get(posAppello);

                // Create an asynchronous callback to handle the result.
                final AsyncCallback<String> callback = new AsyncCallback<String>() {

                    public void onSuccess(String result) {
                        Window.alert("cancellazione effettuata");
                        trovaAppelli();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nella cancellazione delle appelli");
                    }
                };

                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                DatiStatici.getDBService().cancAppello(dom, callback);
            }

        }
        return 0;
    }

    public int onEdit(String key) {
        if (!listaAppelli.isEmpty()) {
            Appelli arg = listaAppelli.get(posAppello);
            return editAppello(arg, "Modifica appello", false);
        }
        return 0;
    }

    public int onSearch() {
        return 0;
    }

    public int onClose() {
        return 0;
    }

    public int onPrint(String selectedKey) {
        return 0;
    }

    /**
     * @return the utenteLoggato
     */
    public Utenti getUtenteLoggato() {
        return utenteLoggato;
    }
}
