/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package it.pep.EsamiGWT.client.maschere;

import it.pep.EsamiGWT.client.gwt.MioComposite;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import it.pep.EsamiGWT.client.hibernate.Utenti;

public class MascheraPrincipale extends Composite {

    private MenuBar menu = new MenuBar();
    private DecoratedTabPanel tabPanel = new DecoratedTabPanel();
    private DockPanel dp = new DockPanel();
    private VerticalPanel menuPanel = new VerticalPanel();
    private MioComposite[] formPanels = new MioComposite[2];
    private String[] tabTitles = {"Appelli", "Domande"};
    private Utenti utenteLoggato = null;

    /**
     * è la classe che si occupa della visualizzazione del programma vero e proprio
     * @param utenteLoggato è il codice dell'utente che ha superato la fase di login
     */
    public MascheraPrincipale(Utenti utenteLoggato) {
        if (utenteLoggato != null) {
            this.utenteLoggato = utenteLoggato;
            formPanels[0] = new MascheraAppelli(utenteLoggato);
            formPanels[1] = new MascheraArgomenti(utenteLoggato);

            initWidget(dp);

            dp.add(tabPanel, DockPanel.CENTER);
            dp.add(menuPanel, DockPanel.NORTH);

            tabPanel.setWidth("100%");

            // Enable the deck panel animation
            tabPanel.getDeckPanel().setAnimationEnabled(true);

            creaMenu();
            creaPannelli();
        }
    }

    private MenuBar creaMenu() {
        // Create a menu bar
        menu.setAutoOpen(true);
        menu.setWidth("100%");
        menu.setAnimationEnabled(true);

        // Create the file menu
        MenuBar fileMenu = new MenuBar(true);
        fileMenu.setAnimationEnabled(true);
        fileMenu.addItem("Impostazioni", true, new Command() {

            public void execute() {
                Window.alert("scelto Impostazioni");
            }
        });

        fileMenu.addItem("Logout", true, new Command() {

            public void execute() {
//                Window.alert("scelto logout");
                if (Window.confirm("Vuoi terminare la sessione di lavoro?")) {
                    tornaAMascheraLogin();
                }
            }
        });
        menu.addItem(new MenuItem("File", fileMenu));

        // Create the file menu
        MenuBar domandeMenu = new MenuBar(true);
        domandeMenu.setAnimationEnabled(true);
        domandeMenu.addItem("blocca tutte", true, new Command() {

            public void execute() {
                eseguiSBlocco(true);
            }
        });
        domandeMenu.addItem("sblocca tutte", true, new Command() {

            public void execute() {
                eseguiSBlocco(false);
            }
        });
        menu.addItem(new MenuItem("Domande", domandeMenu));

        // Create the file menu
        MenuBar elaboratiMenu = new MenuBar(true);
        elaboratiMenu.setAnimationEnabled(true);
        elaboratiMenu.addItem("importa correzione", true, new Command() {

            public void execute() {
                eseguiImportazione();
            }
        });
        menu.addItem(new MenuItem("Elaborati", elaboratiMenu));

        menuPanel.add(menu);
        return menu;
    }

    private void eseguiSBlocco(boolean blocco) {
        DialogSBloccoDomande dBlocca = new DialogSBloccoDomande(utenteLoggato,blocco,((MascheraArgomenti)formPanels[1]).getMd());
        dBlocca.show();
    }

    private void eseguiImportazione() {
        DialogImportazione dImporta = new DialogImportazione(utenteLoggato);
        dImporta.show();
    }
    private void creaPannelli() {
        int pos = 0;
        for (MioComposite mc : formPanels) {
            tabPanel.add(mc, tabTitles[pos++]);
        }
        tabPanel.selectTab(0);
        tabPanel.addTabListener(new TabListener() {

            public boolean onBeforeTabSelected(SourcesTabEvents arg0, int arg1) {
                return true;
            }

            public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
                tabPanel.getDeckPanel().showWidget(tabIndex);
                formPanels[tabIndex].onVisible();
            }
        });
    }

    private void tornaAMascheraLogin() {
        utenteLoggato = null;
        RootPanel rootPanel = RootPanel.get();
        rootPanel.clear();
        rootPanel.add(new MascheraLogin());
    }
}

