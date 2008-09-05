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
package it.pep.client.maschere;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class MascheraPrincipale extends Composite {

    MenuBar menu = new MenuBar();
    DecoratedTabPanel tabPanel = new DecoratedTabPanel();
    DockPanel dp=new DockPanel();
    VerticalPanel menuPanel=new VerticalPanel();
    
    public MascheraPrincipale() {
        initWidget(dp);
        
        dp.add(tabPanel,DockPanel.CENTER);
        dp.add(menuPanel,DockPanel.NORTH);

        tabPanel.setWidth("400px");

        // Enable the deck panel animation
        tabPanel.getDeckPanel().setAnimationEnabled(true);

        creaMenu();

        // Primo tab
        String[] tabTitles = {"Appelli", "Domande"};
        MascheraAppelli ma = new MascheraAppelli();
        tabPanel.add(ma, tabTitles[0]);

        // Secondo tab
        MascheraDomande md = new MascheraDomande();
        tabPanel.add(md, tabTitles[1]);


        tabPanel.selectTab(0);
    }

    private MenuBar creaMenu() {
        // Create a command that will execute on menu item selection
        Command menuCommand = new Command() {

            public void execute() {
                Window.alert("menu");
            }
        };

        // Create a menu bar
        menu.setAutoOpen(true);
        menu.setWidth("500px");
        menu.setAnimationEnabled(true);

        // Create the file menu
        MenuBar fileMenu = new MenuBar(true);
        fileMenu.setAnimationEnabled(true);
        fileMenu.addItem("Logout", true, menuCommand);
        menu.addItem(new MenuItem("File", fileMenu));
        menuPanel.add(menu);
        return menu;
    }
}

