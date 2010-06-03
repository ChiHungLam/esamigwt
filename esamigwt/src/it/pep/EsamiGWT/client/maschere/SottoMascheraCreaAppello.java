/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.maschere;

import it.pep.EsamiGWT.client.gwt.MioComposite;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.gwt.NumberBox;
import it.pep.EsamiGWT.client.gwt.WaitDialog;
import it.pep.EsamiGWT.client.hibernate.Appelli;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import java.util.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 *
 * @author Francesco
 */
public class SottoMascheraCreaAppello extends MioComposite {

    private DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    private ScrollPanel sp = new ScrollPanel();
    private FlexTable ft = new FlexTable();
    private boolean isEditable = false;
    private Appelli appello = null;
    private Widget[] inputFields = null;
    private MascheraAppelli parent = null;
    private boolean update = false;
    private int numCampiAppello,  numArgomenti;
    private final int numCampiCompiti = 2;
    final WaitDialog wd = new WaitDialog();
    private long start;

    public SottoMascheraCreaAppello(Appelli app, boolean isEditable, MascheraAppelli par) {
        this.appello = app;
        this.isEditable = isEditable;
        this.parent = par;

        initWidget(decPanel);
        decPanel.setWidget(sp);
        sp.add(ft);

        numCampiAppello = appello.getNumberFieldEdit();
        numArgomenti = DatiStatici.getListaArgomenti().size();
        inputFields = new Widget[numCampiAppello + numArgomenti + numCampiCompiti];

        int numCampi = aggiungiCampiAppello();
        numCampi += aggiungiNumeroCompiti(numCampi);
        numCampi += aggiungiNumeroDomande(numCampi);

        Button salvaButton = new Button("crea appello");
        salvaButton.setEnabled(isEditable);
        salvaButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent arg0) {
                // Get current time
                start = System.currentTimeMillis();

                wd.show();
                apportaModificheAppello();
                final Date dataAppello = appello.getDataAppello();
                final int numEsaminandi = ((NumberBox) inputFields[numCampiAppello]).getIntOrZero();
                final int numCompitiDiff = ((NumberBox) inputFields[numCampiAppello + 1]).getIntOrZero();
                final List<Integer> listaNumDomande = new ArrayList<Integer>();
                int inizio = numCampiAppello + numCampiCompiti;
                for (int k = inizio; k < inizio + numArgomenti; k++) {
                    listaNumDomande.add(new Integer(((NumberBox) inputFields[k]).getIntOrZero()));
                }
                apportaModificheArgomenti(listaNumDomande);

                // Create an asynchronous callback to handle the result.
                final AsyncCallback<String> callback = new AsyncCallback<String>() {

                    public void onSuccess(String result) {
                        chiudiWait();
                        String strRes = result;
                        if (strRes.equalsIgnoreCase("ok")) {
                            parent.chiudiDialog();

                            long elapsedTimeMillis = System.currentTimeMillis() - start;// Get elapsed time in milliseconds
                            float elapsedTimeMin = elapsedTimeMillis / (60 * 1000F);// Get elapsed time in minutes

                            Window.alert("appello creato in " + elapsedTimeMin + " minuti");
                            parent.trovaAppelli();
                        } else {
                            Window.alert(strRes);
                        }
                    }

                    public void onFailure(Throwable caught) {
                        chiudiWait();
//                        parent.chiudiDialog();
                        Window.alert("Errore nella creazione dell'appello");
                    }
                };

                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                DatiStatici.getBusinessService().creaAppello(appello, numEsaminandi, numCompitiDiff, DatiStatici.getListaArgomenti(), callback);
            }
        });
        ft.setWidget(numCampi, 1, salvaButton);
    }

    void chiudiWait() {
        wd.hide();
//        parent.chiudiDialog();
    }

    private int aggiungiCampiAppello() {
        int numCampi = appello.getNumberFieldEdit();
        for (int k = 0; k < numCampi; k++) {
            Label l = new Label(appello.getFieldTitleEdit(k), true);
            l.addStyleName("etichetta");
            ft.setWidget(k, 0, l);
            inputFields[k] = appello.getFieldEdit(k, isEditable);
            inputFields[k].addStyleName("campo");
            ft.setWidget(k, 1, inputFields[k]);
        }
        return numCampi;
    }

    private void aggiungiLinea(int riga) {
        ft.setWidget(riga, 0, new HTML("<hr>"));
        ft.setWidget(riga, 1, new HTML("<hr>"));
        ft.setWidget(riga, 2, new HTML("<hr>"));
        ft.setWidget(riga, 3, new HTML("<hr>"));
    }

    private int aggiungiNumeroCompiti(int numCampiPrecedenti) {
        int riga = numCampiPrecedenti;

        aggiungiLinea(riga);

        riga++;
        Label l0 = new Label("N° esaminandi", true);
        l0.addStyleName("etichetta");
        ft.setWidget(riga, 0, l0);
        inputFields[numCampiPrecedenti] = new NumberBox(true);
        inputFields[numCampiPrecedenti].addStyleName("campo");
        ft.setWidget(riga, 1, inputFields[numCampiPrecedenti]);

        Label l1 = new Label("N° compiti differenti", true);
        l1.addStyleName("etichetta");
        ft.setWidget(riga, 2, l1);
        inputFields[numCampiPrecedenti + 1] = new NumberBox(true);
        inputFields[numCampiPrecedenti + 1].addStyleName("campo");
        ft.setWidget(riga, 3, inputFields[numCampiPrecedenti + 1]);

        return 2;
    }

    private int aggiungiNumeroDomande(int numCampiPrecedenti) {
        int riga = numCampiPrecedenti;
        int col = 0;

        aggiungiLinea(riga);

        int numCampi = DatiStatici.getListaArgomenti().size();
        for (int k = 0; k < numCampi; k++) {
            if (k % 2 == 0) { // se k è pari scriviamo nella nuova riga e nella colonna sinistra
                riga++;
                col = 0;
            } else {    // altrimenti scriviamo nella colonna a destra
                col = 2;
            }
            String nomeArgomento = DatiStatici.getListaArgomenti().get(k).getDescrizione();
            Label l = new Label(nomeArgomento, true);
            l.addStyleName("etichetta");
            ft.setWidget(riga, col, l);
            inputFields[k + numCampiPrecedenti] = new NumberBox(true);
            ((NumberBox) inputFields[k + numCampiPrecedenti]).setNumber(
                    DatiStatici.getListaArgomenti().get(k).getNumDomande());
            inputFields[k + numCampiPrecedenti].addStyleName("campo");
            ft.setWidget(riga, col + 1, inputFields[k + numCampiPrecedenti]);
        }
        return numCampi;
    }

    private void apportaModificheAppello() {
        int numCampi = appello.getNumberFieldEdit();
        for (int k = 0; k < numCampi; k++) {
            appello.setFieldValueFromEdit(k, inputFields[k]);
        }
        appello.setCodUtente(parent.getUtenteLoggato().getUtente());
    }

    private void apportaModificheArgomenti(List<Integer> listaNumDomande) {
        DatiStatici.apportaModificheArgomenti(listaNumDomande);
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean rileggiLista) {
        this.update = rileggiLista;
    }
}
