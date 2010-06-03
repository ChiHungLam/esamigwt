/*
 * implementa listaElaborati finestra che mostra l'elenco degli elaborati creati
 */
package it.pep.EsamiGWT.client.maschere;

import com.google.gwt.core.client.GWT;
import it.pep.EsamiGWT.client.gwt.MioComposite;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.dbBrowse.BrowseListener;
import it.pep.EsamiGWT.client.dbBrowse.DbBrowse;
import it.pep.EsamiGWT.client.dbBrowse.Print;
import it.pep.EsamiGWT.client.gwt.WaitDialog;
import it.pep.EsamiGWT.client.hibernate.Appelli;
import it.pep.EsamiGWT.client.hibernate.Elaborati;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import java.util.List;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 *
 * @author user
 */
public class SottoMascheraElaborati extends MioComposite implements BrowseListener {

    private List<Elaborati> listaElaborati = null;
    private DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    private VerticalSplitPanel vSplit = new VerticalSplitPanel();   // un pannello con parte sinistra e destra
    private DbBrowse<Elaborati> dbElaborati = null;
    private String strIdAppello;
    private String descrAppello;
    private Appelli appello;
    private int posElaborato = 0;
    private boolean[] bottoniBrowseVisibili = {false, false, true, true};
    private boolean finito = false;
    private final DialogBox dialogDettaglio = new DialogBox();
    final WaitDialog wd = new WaitDialog();
    SottoMascheraDomandeElaborati smde = null;

    public SottoMascheraElaborati(Appelli appello) {
        this.strIdAppello = "" + appello.getID();
        this.descrAppello = appello.getDescrizione();
//        this.dataAppello = appello.getDataAppello();
        this.appello = appello;
        initWidget(decPanel);
        decPanel.setWidget(vSplit);
        vSplit.setSize("580px", "500px");
        trovaElaborati();
        onVisible();
    }

    public void trovaElaborati() {
        final String titolo = "Elaborati di '" + descrAppello + "'";

        // Create an asynchronous callback to handle the result.
        final AsyncCallback<List<Elaborati>> callback = new AsyncCallback<List<Elaborati>>() {

            public void onSuccess(List<Elaborati> result) {
                listaElaborati = result;
                dbElaborati = new DbBrowse<Elaborati>(listaElaborati, SottoMascheraElaborati.this, new Elaborati(), titolo);
                dbElaborati.setBottoniAttivi(bottoniBrowseVisibili);
                vSplit.setTopWidget(dbElaborati);
                mostraDettaglioElaborato();
                dialogDettaglio.hide();
            }

            public void onFailure(Throwable caught) {
                Window.alert("Errore nella ricerca degli elaborati");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        DatiStatici.getDBService().trovaElaboratiPerAppello(Integer.parseInt(strIdAppello), callback);
    }

    public void mostraDettaglioElaborato() {
        if (!(listaElaborati == null || listaElaborati.isEmpty())) {
            Elaborati el = listaElaborati.get(posElaborato);
            mostraDettaglioElaborato(el, false);
        }
    }

    public int printCorrezione() {
        wd.show();
        // Create an asynchronous callback to handle the result.
        final AsyncCallback<String> callback = new AsyncCallback<String>() {

            public void onSuccess(String result) {
                String daStampare = result;
                wd.hide();
                //               Window.alert(daStampare);
                Print.it(daStampare);
            }

            public void onFailure(Throwable caught) {
                wd.hide();
                Window.alert("Errore nella stampa degli elaborati");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        DatiStatici.getDBService().creaStampaCorrezione(this.appello, callback);
        return 1;
    }

    public int printRisultati() {
        String url = GWT.getHostPageBaseURL() + "RisultatiServlet" + "?appID=" + appello.getID();
        String strWindowName = "_blank";
        String strWindowFeatures = "menubar=no,location=no,resizable=yes,scrollbars=yes,status=yes";

        Window.open(url, strWindowName, strWindowFeatures);
        /*
        wd.show();
        // Create an asynchronous callback to handle the result.
        final AsyncCallback callback = new AsyncCallback() {

        public void onSuccess(Object result) {
        String daStampare = (String) result;
        wd.hide();
        //               Window.alert(daStampare);
        Print.it(daStampare);
        }

        public void onFailure(Throwable caught) {
        wd.hide();
        Window.alert("Errore nella stampa degli elaborati");
        }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        DatiStatici.getDBService().creaStampaRisultati(this.appello, callback);
         */
        return 1;
    }

    void menuEtichette() {
        DialogParametriStampaEtichette dpse = new DialogParametriStampaEtichette(this);
        dpse.show();
    }

    private void mostraDettaglioElaborato(Elaborati el, boolean rileggiLista) {
        smde = new SottoMascheraDomandeElaborati("" + el.getID(), "" + el.getID());
//        smde.setUpdate(rileggiLista);
        vSplit.setBottomWidget(smde);
    }

    @Override
    public void onVisible() {
        vSplit.setSplitPosition("40%");
    }

    /** 
     * metodi di BrowseListener per gestire le operazioni di browse
     */
    public int onSel(String key) {
        posElaborato = dbElaborati.getSelectedRow() - 1;
        mostraDettaglioElaborato();
        return 0;
    }

    public int onAdd() {
        return 0;
    }

    public int onDel(String key) {
        return 0;
    }

    public int onEdit(String key) {
        if (!listaElaborati.isEmpty()) {
            Elaborati arg = listaElaborati.get(posElaborato);
            return editElaborato(arg, "Correzione elaborato", true);
        }
        return 0;
    }

    public int editElaborato(Elaborati el, String titolo, boolean update) {
        Widget smce = null;
        smce = new SottoMascheraCorreggiElaborato(el, true, this, smde.getListaDomandeElaborati());
//        smda.setUpdate(update);

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

        dialogContents.add(smce);

        dialogDettaglio.center();

        dialogDettaglio.show();
        return 0;
    }

    public void chiudiDialog() {
        dialogDettaglio.hide();
    }

    public int onSearch() {
        return 0;
    }

    public int onClose() {
        return 0;
    }

    void aggiornaRigaCorrente(Elaborati elaborato) {
        dbElaborati.aggiornaRigaCorrente(elaborato);
    }

    public boolean isFinito() {
        return finito;
    }

    public void setFinito(boolean finito) {
        this.finito = finito;
    }

    public int onPrint(String selectedKey) {
        MenuStampaAppello msa = new MenuStampaAppello(this);
        msa.show();
        return 1;
    }

    public void menuElaborati() {
        DialogParametriStampaElaborati dpse = new DialogParametriStampaElaborati(this);
        dpse.show();
    }

    public int printElaborati(boolean stampaDomande, boolean stampaRisposte, boolean anonimo, int numColonne) {
        wd.show();
        // Create an asynchronous callback to handle the result.
        final AsyncCallback<String> callback = new AsyncCallback<String>() {

            public void onSuccess(String result) {
                String daStampare = result;
                wd.hide();
                //               Window.alert(daStampare);
                Print.it(daStampare);
            }

            public void onFailure(Throwable caught) {
                wd.hide();
                Window.alert("Errore nella stampa degli elaborati");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        DatiStatici.getDBService().creaStampaAppello(this.appello, GWT.getHostPageBaseURL(), stampaDomande, stampaRisposte, anonimo, numColonne, callback);
        return 1;
    }
}
