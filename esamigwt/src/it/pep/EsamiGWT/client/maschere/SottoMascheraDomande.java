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
import it.pep.EsamiGWT.client.hibernate.Domande;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import java.util.List;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 *
 * @author user
 */
public class SottoMascheraDomande extends MioComposite implements BrowseListener {

    private List<Domande> listaDomande = null;
    private DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    private VerticalSplitPanel vSplit = new VerticalSplitPanel();   // un pannello con parte sinistra e destra
    private DbBrowse<Domande> dbDomande = null;
    private String strIdArgomento;
    private String descrArgomento;
    private int posDomanda = 0;
    private boolean[] bottoniBrowseVisibili = {true, true, false,true};
    private boolean finito = false;
    private final DialogBox dialogDettaglio = new DialogBox();

    public SottoMascheraDomande(String strIdArgomento, String descrArgomento) {
        this.strIdArgomento = strIdArgomento;
        this.descrArgomento = descrArgomento;
        initWidget(decPanel);
        decPanel.setWidget(vSplit);
        vSplit.setSize("500px", "500px");
        trovaDomande();
        onVisible();
    }

    public void trovaDomande() {
        final String titolo = "Domande per '" + descrArgomento + "'";

        // Create an asynchronous callback to handle the result.
        final AsyncCallback<List<Domande>> callback = new AsyncCallback<List<Domande>>() {

            public void onSuccess(List<Domande> result) {
                listaDomande = result;
                dbDomande = new DbBrowse<Domande>(listaDomande, SottoMascheraDomande.this, new Domande(), titolo);
                dbDomande.setBottoniAttivi(bottoniBrowseVisibili);
                vSplit.setTopWidget(dbDomande);
                mostraDettaglioDomanda();
                dialogDettaglio.hide();
            }

            public void onFailure(Throwable caught) {
                Window.alert("Errore nella ricerca delle domande");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        DatiStatici.getDBService().trovaDomandePerArgomento(Integer.parseInt(strIdArgomento), callback);
    }

    private void mostraDettaglioDomanda() {
        if (!listaDomande.isEmpty()) {
            Domande dom = listaDomande.get(posDomanda);
            mostraDettaglioDomanda(dom, false);
        }
    }

    private void mostraDettaglioDomanda(Domande dom, boolean rileggiLista) {
        SottoMascheraDettaglioDomanda smdd = new SottoMascheraDettaglioDomanda(dom, true, this);
        smdd.setUpdate(rileggiLista);
        vSplit.setBottomWidget(smdd);
    }

    @Override
    public void onVisible() {
        vSplit.setSplitPosition("40%");
    }

    /** 
     * metodi di BrowseListener per gestire le operazioni di browse
     */
    public int onSel(String key) {
        posDomanda = dbDomande.getSelectedRow() - 1;
        mostraDettaglioDomanda();
        return 0;
    }

    public int onAdd() {
        Domande nuova = new Domande();
        nuova.setIdArgomento(Integer.parseInt(strIdArgomento));

        SottoMascheraDettaglioDomanda smdd = new SottoMascheraDettaglioDomanda(nuova, true, this);
        smdd.setUpdate(true);

        dialogDettaglio.setText("Nuova domanda");
        dialogDettaglio.setAnimationEnabled(true);
        VerticalPanel dialogContents = new VerticalPanel();
        dialogDettaglio.setWidget(dialogContents);

        // Add a close button at the top of the dialog
        PushButton closeButton = new PushButton(new Image("images/exit.png"),
                new ClickHandler() {

                    public void onClick(ClickEvent sender) {
                        dialogDettaglio.hide();
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
        if (!listaDomande.isEmpty()) {
            boolean confirm = Window.confirm("Vuoi veramente cancellare il record selezionato?");


            if (confirm) {
                Domande dom = listaDomande.get(posDomanda);

                // Create an asynchronous callback to handle the result.
                final AsyncCallback<String> callback = new AsyncCallback<String>() {

                    public void onSuccess(String result) {
                        Window.alert("cancellazione effettuata");
                        trovaDomande();
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Errore nella cancellazione delle domande");
                    }
                };

                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                DatiStatici.getDBService().cancDomanda(dom, callback);
            }

        }
        return 0;
    }

    public int onEdit(String key) {
        return 0;
    }

    public int onSearch() {
        return 0;
    }

    public int onClose() {
        return 0;
    }

    void aggiornaRigaCorrente(Domande domanda) {
        dbDomande.aggiornaRigaCorrente(domanda);
    }

    public boolean isFinito() {
        return finito;
    }

    public void setFinito(boolean finito) {
        this.finito = finito;
    }

    public int onPrint(String selectedKey) {
        return 0;
    }
}
