/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.maschere;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.gwt.WaitDialog;

/**
 *
 * @author Francesco
 */
class DialogParametriStampaEtichette extends DialogBox {

    private VerticalPanel dialogContents = new VerticalPanel();
    private FlexTable ft = new FlexTable();
    private Label lr = new Label("Righe");
    private Label lc = new Label("Colonne");
    private TextBox tbRighe = new TextBox();
    private TextBox tbCol = new TextBox();
    private Button btnOk = new Button("Ok");
    private Button btnAnnulla = new Button("Annulla");
//    private BusinessServiceAsync businessSvc = GWT.create(BusinessService.class);
    private final WaitDialog wd = new WaitDialog();

    public DialogParametriStampaEtichette(SottoMascheraElaborati aThis) {
        btnOk.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                hide();
                wd.show();
                stampa();
            }
        });
        btnAnnulla.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                hide();
            }
        });

        add(dialogContents);
        dialogContents.add(ft);
        ft.setWidget(0, 0, lr);
        ft.setWidget(0, 1, tbRighe);
        ft.setWidget(1, 0, lc);
        ft.setWidget(1, 1, tbCol);
        ft.setWidget(6, 0, btnOk);
        ft.setWidget(6, 1, btnAnnulla);

        setAnimationEnabled(true);
        center();
    }

    private void stampa() {
        int nRighe,nCol;
        nRighe=Integer.parseInt(tbRighe.getText());
        nCol=Integer.parseInt(tbCol.getText());
        // todo: riempire
    }
}
