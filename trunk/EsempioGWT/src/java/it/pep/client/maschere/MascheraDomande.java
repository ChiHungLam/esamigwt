/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pep.client.maschere;

import com.google.gwt.user.client.ui.*;

/**
 *
 * @author user
 */
public class MascheraDomande extends Composite{

    private VerticalPanel vp=new VerticalPanel();
    public MascheraDomande() {
        initWidget(vp);
        vp.add(new HTML("domande"));
    }

}
