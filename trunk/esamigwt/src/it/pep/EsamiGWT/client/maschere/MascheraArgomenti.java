/*
 * implementa listaArgomenti finestra che mostra l'elenco degli appelli creati
 */
package it.pep.EsamiGWT.client.maschere;

import it.pep.EsamiGWT.client.gwt.MioComposite;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.dbBrowse.BrowseListener;
import it.pep.EsamiGWT.client.dbBrowse.DbBrowse;
import it.pep.EsamiGWT.client.hibernate.Argomenti;
import it.pep.EsamiGWT.client.hibernate.Utenti;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import java.util.List;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 *
 * @author user
 */
public class MascheraArgomenti extends MioComposite implements BrowseListener {

    private List<Argomenti> listaArgomenti = null;
    private DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    private HorizontalSplitPanel hSplit = new HorizontalSplitPanel();   // un pannello con parte sinistra e destra
    private DbBrowse<Argomenti> dbArgomenti = null;
    private SottoMascheraDomande md = null;
    private final DialogBox dialogDettaglio = new DialogBox();
    private int posArgomento = 0;
    private Utenti utenteLoggato;

    public MascheraArgomenti(Utenti utenteLoggato) {
        if (utenteLoggato != null) {
            this.utenteLoggato = utenteLoggato;
            initWidget(decPanel);
            decPanel.setWidget(hSplit);
            hSplit.setSize("850px", "500px");
            trovaArgomenti();
            onVisible();
        }
    }

    public void trovaArgomenti() {
        // Create an asynchronous callback to handle the result.
        final AsyncCallback<List<Argomenti>> callback = new AsyncCallback<List<Argomenti>>() {

            public void onSuccess(List<Argomenti> result) {
                listaArgomenti = result;
                DatiStatici.setListaArgomenti(listaArgomenti);
                dbArgomenti = new DbBrowse<Argomenti>(listaArgomenti, MascheraArgomenti.this, new Argomenti(), "Argomenti");
                hSplit.setLeftWidget(dbArgomenti);
                trovaDomande();
                chiudiDialog();
            }

            public void onFailure(Throwable caught) {
                Window.alert("Errore nella ricerca degli argomenti");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        DatiStatici.getDBService().trovaArgomentiPerUtente(getUtenteLoggato().getUtente(), callback);
    }

    void chiudiDialog() {
        dialogDettaglio.hide();
    }

    private void trovaDomande() {
        String strIdArgomento = dbArgomenti.getSelectedKey();
        int rigaSel = dbArgomenti.getSelectedRow() - 1;
        if (rigaSel >= 0) {
            String descrArgomento = (listaArgomenti.get(rigaSel)).getDescrizione();
            md = new SottoMascheraDomande(strIdArgomento, descrArgomento);
            hSplit.setRightWidget(getMd());
            getMd().onVisible();
        }
    }

    void aggiornaRigaCorrente(Argomenti argomento) {
        dbArgomenti.aggiornaRigaCorrente(argomento);
        chiudiDialog();
    }

    @Override
    public void onVisible() {
        hSplit.setSplitPosition("40%");
        if (getMd() != null) {
            getMd().onVisible();
        }
    }

    /** 
     * metodi di BrowseListener per gestire le operazioni di browse
     */
    public int onSel(String key) {
        posArgomento = dbArgomenti.getSelectedRow() - 1;
        trovaDomande();
        return 0;
    }

    public int onAdd() {
        Argomenti nuova = new Argomenti();
//        nuova.setCodUtente("da mettere");
        return editArgomento(nuova, "Nuovo argomento", true);
    }

    public int editArgomento(Argomenti arg, String titolo, boolean update) {
        SottoMascheraDettaglioArgomento smdd = new SottoMascheraDettaglioArgomento(arg, true, this);
        smdd.setUpdate(update);

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

        dialogContents.add(smdd);


        dialogDettaglio.center();
        dialogDettaglio.show();
        return 0;
    }

    public int onDel(String key) {
        if (!listaArgomenti.isEmpty()) {
            boolean confirm = Window.confirm("Vuoi veramente cancellare il record selezionato?");


            if (confirm) {
                Argomenti dom = listaArgomenti.get(posArgomento);

                // Create an asynchronous callback to handle the result.
                final AsyncCallback<String> callback = new AsyncCallback<String>() {

                    public void onSuccess(String result) {
                        Window.alert("cancellazione effettuata");
                        trovaArgomenti();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nella cancellazione delle argomenti");
                    }
                };

                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                DatiStatici.getDBService().cancArgomento(dom, callback);
            }

        }
        return 0;
    }

    public int onEdit(String key) {
        if (!listaArgomenti.isEmpty()) {
            Argomenti arg = listaArgomenti.get(posArgomento);
            return editArgomento(arg, "Modifica argomento", false);
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

    /**
     * @return the md
     */
    public SottoMascheraDomande getMd() {
        return md;
    }
}
