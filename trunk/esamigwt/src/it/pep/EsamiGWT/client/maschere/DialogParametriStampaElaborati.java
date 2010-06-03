/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.maschere;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

/**
 *
 * @author Francesco
 */
public class DialogParametriStampaElaborati extends DialogBox {

    private SottoMascheraElaborati parent = null;
    private VerticalPanel dialogContents = new VerticalPanel();
    private FlexTable ft = new FlexTable();
//    private ListBox lbTipoStampa = new ListBox();
    private CheckBox cbDomande = new CheckBox("foglio domande");
    private CheckBox cbRisposte = new CheckBox("foglio risposte");
    private CheckBox cbAnonimo = new CheckBox("anonimo");
    private ListBox lbNumColonne = new ListBox();
    private Button btnOk = new Button("Ok");
    private Button btnAnnulla = new Button("Annulla");

    public DialogParametriStampaElaborati(SottoMascheraElaborati chiamante) {
        this.parent = chiamante;
        setText("Parametri per la stampa degli elaborati");
//        lbTipoStampa.addItem("fogli separati domande/soluzioni");
//        lbTipoStampa.addItem("foglio unico domande/soluzioni");
        lbNumColonne.addItem("1");
        lbNumColonne.addItem("2");
        lbNumColonne.setSelectedIndex(1);
        cbDomande.setValue(true);
        cbRisposte.setValue(true);
        cbAnonimo.setValue(true);
        btnOk.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent sender) {
                hide();
//                Window.alert("" + getCbDomande().isChecked() + getCbRisposte().isChecked() + cbAnonimo.isChecked() + lbNumColonne.getSelectedIndex());
                parent.printElaborati(getCbDomande().getValue(), getCbRisposte().getValue(), cbAnonimo.getValue(), lbNumColonne.getSelectedIndex() + 1);
            }
        });
        btnAnnulla.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent sender) {
                hide();
            }
        });


        add(dialogContents);
        dialogContents.add(ft);
//        ft.setWidget(0, 0, new HTML("tipo stampa"));
//        ft.setWidget(0, 1, lbTipoStampa);
        ft.setWidget(1, 1, cbDomande);
        ft.setWidget(2, 1, cbRisposte);
        ft.setWidget(3, 1, cbAnonimo);
        ft.setWidget(4, 0, new HTML("num. colonne domande"));
        ft.setWidget(4, 1, lbNumColonne);
        ft.setWidget(6, 0, btnOk);
        ft.setWidget(6, 1, btnAnnulla);
        center();
    }

    /**
     * @return the cbAnonimo
     */
    public CheckBox getCbAnonimo() {
        return cbAnonimo;
    }

    /**
     * @return the tbNumColonne
     */
    public ListBox getLbNumColonne() {
        return lbNumColonne;
    }

    /**
     * @return the cbDomande
     */
    public CheckBox getCbDomande() {
        return cbDomande;
    }

    /**
     * @return the cbRisposte
     */
    public CheckBox getCbRisposte() {
        return cbRisposte;
    }
}
