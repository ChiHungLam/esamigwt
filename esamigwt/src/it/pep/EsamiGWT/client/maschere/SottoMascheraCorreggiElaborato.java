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
import it.pep.EsamiGWT.client.hibernate.DomandeElaborati;
import it.pep.EsamiGWT.client.hibernate.Elaborati;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import java.util.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 *
 * @author Francesco
 */
public class SottoMascheraCorreggiElaborato extends MioComposite {
    private static final String ERRATA = "errata";
    private static final String CORRETTA = "corretta";
    private static final String NULLA = "nulla";
    private static final float PUNTEGGIO_CORRETTA = 1f;
    private static final float PUNTEGGIO_NULLA = 0f;
    private static final float PUNTEGGIO_ERRATA = -0.2f;

    private DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    private ScrollPanel sp = new ScrollPanel();
    private FlexTable ft = new FlexTable();
    private boolean isEditable = false;
    private Elaborati el = null;
    private Widget[] inputFields = null;
    private SottoMascheraElaborati parent = null;
    private boolean update = false;
    private int numCampiElaborato,  numDomandeElaborato;
    private final WaitDialog wd = new WaitDialog();
    private List<DomandeElaborati> listaDomandeElaborati = null;

    public SottoMascheraCorreggiElaborato(Elaborati elPar, boolean isEditable, SottoMascheraElaborati par, List<DomandeElaborati> listaDomandeElaboratiPar) {
        this.listaDomandeElaborati = listaDomandeElaboratiPar;
        this.el = elPar;
        this.isEditable = isEditable;
        this.parent = par;

        initWidget(decPanel);
        decPanel.setWidget(sp);
        sp.add(ft);

        numCampiElaborato = elPar.getNumberFieldEdit();
        numDomandeElaborato = listaDomandeElaboratiPar.size();
        inputFields = new Widget[numCampiElaborato + numDomandeElaborato];

        int numCampi = aggiungiCampiElaborato();
        numCampi += aggiungiDomandeElaborato(numCampi);

        Button salvaButton = new Button("salva");
        salvaButton.setEnabled(isEditable);
        salvaButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent arg0) {
                wd.show();
                apportaModificheElaborato();
                apportaModificheDomandeElaborato();

                // Create an asynchronous callback to handle the result.
                final AsyncCallback<String> callback = new AsyncCallback<String>() {

                    public void onSuccess(String result) {
                        chiudiWait();
                        String strRes = result;
                        if (strRes.equalsIgnoreCase("ok")) {
                            parent.chiudiDialog();
                            Window.alert("compito corretto");
                            parent.aggiornaRigaCorrente(el);
                            parent.mostraDettaglioElaborato();
                        } else {
                            Window.alert(strRes);
                        }
                    }

                    public void onFailure(Throwable caught) {
                        chiudiWait();
//                        parent.chiudiDialog();
                        Window.alert("Errore nella correzione dell'elaborato");
                    }
                };

                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                DatiStatici.getBusinessService().correggiElaborato(el, listaDomandeElaborati, callback);
            }
        });
        ft.setWidget(numCampi, 1, salvaButton);
    }

    private void apportaModificheDomandeElaborato() {
        float punteggioTot = 0;
        for (int k = 0; k < numDomandeElaborato; k++) {
            float punteggio = 0;
            String esito = "";
            DomandeElaborati de = listaDomandeElaborati.get(k);
            de.setFornita(
                    ((NumberBox) inputFields[k + numCampiElaborato]).getIntOrZero());
            if (de.getFornita() == de.getEsatta()) { // risposta esatta
                punteggio = PUNTEGGIO_CORRETTA;
                esito = CORRETTA;
            } else if (de.getFornita() == 0) { // risposta nulla
                punteggio = PUNTEGGIO_NULLA;
                esito = NULLA;
            } else {  // risposta errata
                punteggio = PUNTEGGIO_ERRATA;
                esito = ERRATA;
            }
            de.setEsito(esito);
            punteggioTot += punteggio;
        }
        el.setPunteggio(punteggioTot);
    }

    void chiudiWait() {
        wd.hide();
//        parent.chiudiDialog();
    }

    private int aggiungiCampiElaborato() {
        int numCampi = el.getNumberFieldEdit();
        for (int k = 0; k < numCampi; k++) {
            Label l = new Label(el.getFieldTitleEdit(k), true);
            l.addStyleName("etichetta");
            ft.setWidget(k, 0, l);
            inputFields[k] = el.getFieldEdit(k, isEditable);
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

    private int aggiungiDomandeElaborato(int numCampiPrecedenti) {
        int riga = numCampiPrecedenti;
        int righe = 1;

        aggiungiLinea(riga);

        for (DomandeElaborati de : listaDomandeElaborati) {
            riga++;
            righe++;
            int col = 0;

            HTML t0 = new HTML("" + de.getID());
            t0.addStyleName("etichetta");
            ft.setWidget(riga, col++, t0);

            HTML t1 = new HTML(de.getDomanda());
            t1.addStyleName("etichetta");
            ft.setWidget(riga, col++, t1);

            HTML t2 = new HTML("" + de.getEsatta());
            t2.addStyleName("etichetta");
            ft.setWidget(riga, col++, t2);
            int posInput = riga - 1; /* siccome nella tabella visualizzata c'è una
            riga contenente una linea di separazione,
            devo scalare la posizione di inputFields
            di 1 ed usare quindi [riga-1]*/
            inputFields[posInput] = new NumberBox(true);
            ((NumberBox) inputFields[posInput]).setNumber(
                    de.getFornita());
            inputFields[posInput].addStyleName("campo");
            ft.setWidget(riga, col++, inputFields[posInput]);
        }
        return righe;
    }

    private void apportaModificheElaborato() {
        int numCampi = el.getNumberFieldEdit();
        for (int k = 0; k < numCampi; k++) {
            el.setFieldValueFromEdit(k, inputFields[k]);
        }
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean rileggiLista) {
        this.update = rileggiLista;
    }
}
