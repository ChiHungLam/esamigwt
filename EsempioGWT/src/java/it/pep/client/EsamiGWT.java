/*
 * EsamiGWT.java
 *
 * Created on 21 luglio 2008, 13.11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package it.pep.client;

import it.pep.client.maschere.MascheraLogin;
import it.pep.client.maschere.MascheraRegistrazioneUtente;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.*;

/**
 *
 * @author user
 */
public class EsamiGWT implements EntryPoint {

    /** Creates a new instance of EsamiGWT */
    public EsamiGWT() {
    }
    private MascheraLogin login;
    private MascheraRegistrazioneUtente rlu;
    private Label welcomeToMyLabel;
    private VerticalPanel verticalPanelLogin;
    private HorizontalPanel horizontalPanel;
    private Button registratiButton;
    private boolean registraVisibile = true;

    public void onModuleLoad() {
        if (!validoSessionID()) {
            RootPanel rootPanel = RootPanel.get();

            horizontalPanel = new HorizontalPanel();
            rootPanel.add(horizontalPanel, 0, 5);
            horizontalPanel.setSize("460px", "182px");

            verticalPanelLogin = new VerticalPanel();
            horizontalPanel.add(verticalPanelLogin);

            welcomeToMyLabel = new Label("Welcome to my login page.");
            verticalPanelLogin.add(welcomeToMyLabel);

            login = new MascheraLogin();
            horizontalPanel.add(login);



            registratiButton = new Button();
            registratiButton.addClickListener(new ClickListener() {

                public void onClick(Widget sender) {
                    cambiaVisibilita();
                }
            });
            verticalPanelLogin.add(registratiButton);

            rlu = new MascheraRegistrazioneUtente();
            horizontalPanel.add(rlu);
            cambiaVisibilita();
        }
    }

    private void cambiaVisibilita() {
        registraVisibile = !registraVisibile;
        registratiButton.setText((registraVisibile ? "login" : "nuovo utente"));
        rlu.setVisible(registraVisibile);
        login.setVisible(!registraVisibile);

    }

    private boolean validoSessionID() {
        boolean retVal=false;
        String sessionID = Cookies.getCookie("sid");
        if (sessionID != null) {
            //checkWithServerIfSessionIdIsStillLegal();
        }
        return retVal;
    }
}
