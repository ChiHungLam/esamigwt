/*
 * implementa la finestra che mostra l'elenco degli appelli creati
 */
package it.pep.client.maschere;

import com.google.gwt.user.client.ui.*;
import it.pep.client.dbBrowse.DbBrowse;
import it.pep.client.dbBrowse.ProvaBrowseElement;
import it.pep.client.dbBrowse.ProvaBrowseListener;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author user
 */
public class MascheraAppelli extends Composite {

    DecoratorPanel decPanel = new DecoratorPanel(); //un contenitore con i bordi arrotondati
    HorizontalSplitPanel hSplit = new HorizontalSplitPanel();   // un pannello con parte sinistra e destra

    public MascheraAppelli() {
        initWidget(decPanel);
        decPanel.setWidget(hSplit);
        hSplit.ensureDebugId("cwHorizontalSplitPanel");
        hSplit.setSize("500px", "350px");
        hSplit.setSplitPosition("30%");
        hSplit.setRightWidget(new HTML("compiti"));
        hSplit.setLeftWidget(creaEsempio());
    }

    private Widget creaEsempio() {
        ArrayList<ProvaBrowseElement> elenco=new ArrayList();
        for(int i=0; i<10; i++)
            elenco.add(new ProvaBrowseElement());
        String [] intestazioni={"nome","cognome"};
        return new DbBrowse(elenco, intestazioni,new ProvaBrowseListener());
    }
}
class ElencoAppelli extends Composite{
    ScrollPanel sp=new ScrollPanel();
    ElencoAppelli(){
        initWidget(sp);
        sp.add(new HTML("appelli con scroll"));
    }
}