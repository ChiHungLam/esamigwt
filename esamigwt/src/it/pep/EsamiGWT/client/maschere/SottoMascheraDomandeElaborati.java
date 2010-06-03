/*
 * implementa listaElaborati finestra che mostra l'elenco degli elaborati creati
 */
package it.pep.EsamiGWT.client.maschere;

import it.pep.EsamiGWT.client.gwt.MioComposite;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.dbBrowse.BrowseListener;
import it.pep.EsamiGWT.client.dbBrowse.DbBrowse;
import it.pep.EsamiGWT.client.hibernate.DomandeElaborati;
import it.pep.EsamiGWT.client.staticData.DatiStatici;
import java.util.List;

/**
 *
 * @author user
 */
public class SottoMascheraDomandeElaborati extends MioComposite implements BrowseListener {

    private List<DomandeElaborati> listaDomandeElaborati = null;
    private DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    private FlowPanel panel = new FlowPanel();  
    private DbBrowse<DomandeElaborati> dbDomandeElaborati = null;
    private String strIdElaborato;
    private String descrElaborato;
    private int posDomandeElaborato = 0;
    private boolean[] bottoniBrowseVisibili = {false, false, false,false};
    private boolean finito = false;
    private final DialogBox dialogDettaglio = new DialogBox();

    public SottoMascheraDomandeElaborati(String strIdElaborato, String descrElaborato) {
        this.strIdElaborato = strIdElaborato;
        this.descrElaborato = descrElaborato;
        initWidget(decPanel);
        decPanel.setWidget(panel);
        panel.setSize("580px", "500px");
        trovaDomandeElaborati();
        onVisible();
    }

    public void trovaDomandeElaborati() {
        final String titolo = "Domande dell'elaborato '" + descrElaborato + "'";

        // Create an asynchronous callback to handle the result.
        final AsyncCallback<List<DomandeElaborati>> callback = new AsyncCallback<List<DomandeElaborati>>() {

            public void onSuccess(List<DomandeElaborati> result) {
                listaDomandeElaborati = result;
                dbDomandeElaborati = new DbBrowse<DomandeElaborati>(getListaDomandeElaborati(), SottoMascheraDomandeElaborati.this, new DomandeElaborati(), titolo);
                getDbDomandeElaborati().setBottoniAttivi(bottoniBrowseVisibili);
                panel.add(getDbDomandeElaborati());
//                mostraDettaglioDomandeElaborato();
                dialogDettaglio.hide();
            }

            public void onFailure(Throwable caught) {
                Window.alert("Errore nella ricerca delle domandeElaborati");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        DatiStatici.getDBService().trovaDomandeElaborato(Integer.parseInt(strIdElaborato), callback);
    }


    @Override
    public void onVisible() {
    }

    /** 
     * metodi di BrowseListener per gestire le operazioni di browse
     */
    public int onSel(String key) {
//        posDomandeElaborato = dbDomandeElaborati.getSelectedRow() - 1;
//        mostraDettaglioDomandeElaborato();
        return 0;
    }

    public int onAdd() {
        return 0;
    }

    public int onDel(String key) {
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

    void aggiornaRigaCorrente(DomandeElaborati domandeElaborato) {
        getDbDomandeElaborati().aggiornaRigaCorrente(domandeElaborato);
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

    /**
     * @return the dbDomandeElaborati
     */
    public DbBrowse<DomandeElaborati> getDbDomandeElaborati() {
        return dbDomandeElaborati;
    }

    /**
     * @return the listaDomandeElaborati
     */
    public List<DomandeElaborati> getListaDomandeElaborati() {
        return listaDomandeElaborati;
    }
}
