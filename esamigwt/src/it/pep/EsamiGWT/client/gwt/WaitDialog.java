/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.gwt;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;

/**
 *
 * @author Francesco
 */
public class WaitDialog extends DialogBox {

    private static final Image img = new Image("images/clessidra.gif");

    public WaitDialog() {
        setText("Attendere prego...");
        img.setStyleName("centrato");
        add(img);
        center();
        hide();
    }

//    @Override
//    public void show() {
//        center();
//        super.show();
//        int finto=0;
//    }

}
