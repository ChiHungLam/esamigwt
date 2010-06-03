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
public class MascheraLogin extends Composite {

    private Label welcomeToMyLabel;
    private VerticalPanel verticalPanelLogin;
    private HorizontalPanel horizontalPanel;
    private Button registratiButton;
    private boolean registraVisibile = true;
    private SottoMascheraLeggiUtente login;
    private SottoMascheraRegistrazioneUtente rlu;

    public MascheraLogin() {
        horizontalPanel = new HorizontalPanel();
        initWidget(horizontalPanel);
        horizontalPanel.setSize("460px", "182px");

        verticalPanelLogin = new VerticalPanel();
        horizontalPanel.add(verticalPanelLogin);

        welcomeToMyLabel = new Label("Welcome to my login page.");
        verticalPanelLogin.add(welcomeToMyLabel);

        login = new SottoMascheraLeggiUtente();
        horizontalPanel.add(login);



        registratiButton = new Button();
        registratiButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent e) {
                cambiaVisibilita();
            }
        });
        verticalPanelLogin.add(registratiButton);

        rlu = new SottoMascheraRegistrazioneUtente();
        horizontalPanel.add(rlu);
        cambiaVisibilita();
    }

    private void cambiaVisibilita() {
        registraVisibile = !registraVisibile;
        registratiButton.setText((registraVisibile ? "login" : "nuovo utente"));
        rlu.setVisible(registraVisibile);
        login.setVisible(!registraVisibile);

    }
}
