/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.maschere;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 *
 * @author Francesco
 */
public class MenuStampaAppello extends DialogBox {

    private int selezione = -1;
    private SottoMascheraElaborati genitore;

    public MenuStampaAppello(SottoMascheraElaborati sme) {
        genitore=sme;
        this.setText("Cosa vuoi stampare?");
        FlowPanel pannello = new FlowPanel();
        add(pannello);

        Button stampaAppello = new Button("Elaborati", new ClickHandler() {

            public void onClick(ClickEvent sender) {
                selezione = 0;
                hide();
                genitore.menuElaborati();
            }
        });
        pannello.add(stampaAppello);

        Button stampaEtichette = new Button("Etichette", new ClickHandler() {

            public void onClick(ClickEvent sender) {
                selezione = 0;
                hide();
                genitore.menuEtichette();
            }
        });
        pannello.add(stampaEtichette);

        Button stampaCorrezione = new Button("Correzione", new ClickHandler() {

            public void onClick(ClickEvent sender) {
                selezione = 1;
                hide();
                genitore.printCorrezione();
            }
        });
        pannello.add(stampaCorrezione);

        Button stampaRisultati = new Button("Risultati", new ClickHandler() {

            public void onClick(ClickEvent sender) {
                selezione = 2;
                hide();
                if(Window.confirm("Verrà ora creato un file CSV con i risultati\nche potrai aprire con Excel, Openoffice o similari.\nVuoi continuare?"))
                    genitore.printRisultati();
            }
        });
        pannello.add(stampaRisultati);

        Button stampaAnnulla = new Button("Annulla", new ClickHandler() {

            public void onClick(ClickEvent sender) {
                selezione = -1;
                hide();
            }
        });
        pannello.add(stampaAnnulla);

        center();
    }

    /**
     * @return the selezione
     */
    public int getSelezione() {
        return selezione;
    }
}
