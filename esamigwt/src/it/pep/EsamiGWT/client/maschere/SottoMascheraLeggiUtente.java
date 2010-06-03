/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.maschere;

import it.pep.EsamiGWT.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import it.pep.EsamiGWT.client.gwt.WaitDialog;
import it.pep.EsamiGWT.client.hibernate.Utenti;
import java.util.Date;
//import org.apache.catalina.Session;

public class SottoMascheraLeggiUtente extends Composite {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private static LoginServiceAsync service = (LoginServiceAsync) GWT.create(LoginService.class);

    private Button signInButton;
    private CheckBox rememberMeOnCheckBox;
    private PasswordTextBox textBoxPassword;
    private TextBox textBoxUsername;
    private Label passwordLabel;
    private Label usernameLabel;
    private FlexTable flexTable;
    private Label signInToLabel;
    private VerticalPanel verticalPanel;
    private final WaitDialog wd = new WaitDialog();
    private Utenti utenteLoggato=null;

    public SottoMascheraLeggiUtente() {

        verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);

        signInToLabel = new Label("Inserisci i tuoi dati");
        verticalPanel.add(signInToLabel);

        flexTable = new FlexTable();
        flexTable.setStyleName("pulsante");
        verticalPanel.add(flexTable);

        usernameLabel = new Label("Utente:");
        flexTable.setWidget(0, 0, usernameLabel);

        passwordLabel = new Label("Password:");
        flexTable.setWidget(1, 0, passwordLabel);

        textBoxUsername = new TextBox();
        flexTable.setWidget(0, 1, textBoxUsername);

        textBoxPassword = new PasswordTextBox();
        flexTable.setWidget(1, 1, textBoxPassword);

        rememberMeOnCheckBox = new CheckBox();
        flexTable.setWidget(2, 1, rememberMeOnCheckBox);
        rememberMeOnCheckBox.setText("Ricordami su questo computer");

        signInButton = new Button();
        flexTable.setWidget(3, 1, signInButton);
        signInButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent e) {
                if (textBoxUsername.getText().length() == 0 ||
                        textBoxPassword.getText().length() == 0) {
                    Window.alert("Utente o password vuoti");
                } else {
                    controllaUtente(textBoxUsername.getText(), textBoxPassword.getText());
                }
            }
        });
        signInButton.setText("Accedi");

    /** per saltare il login - DA TOGLIERE
    apriMascheraPrincipale();*/
    }

    private void controllaUtente(String utente, String password) {
        wd.show();
        // Create an asynchronous callback to handle the result.
        final AsyncCallback<Utenti> callback = new AsyncCallback<Utenti>() {

            public void onSuccess(Utenti result) {
                Utenti utTmp=result;
                boolean esito = utTmp!=null;
//                Window.alert("accesso:" + esito);
                if (esito) {
                    utenteLoggato=utTmp;
                    if (rememberMeOnCheckBox.getValue()) {
                        salvaUtente();
                    }
                    apriMascheraPrincipale();
                } else {
                    Window.alert("utente e/o password errati");
                }
                wd.hide();
            }

            public void onFailure(Throwable caught) {
                Window.alert("accesso fallito");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        getService().isUtente(utente, password, callback);
    }

    private void salvaUtente() {
        // Create an asynchronous callback to handle the result.
        final AsyncCallback<String> callback = new AsyncCallback<String>() {

            public void onSuccess(String result) {
                String sessionID = result;
                final long DURATION = 1000 * 60 * 60 * 24 * 14; //duration remembering login. 2 weeks in this example.

                Date expires = new Date(System.currentTimeMillis() + DURATION);
                Cookies.setCookie("sid", sessionID, expires, null, "/", false);
                wd.hide();

//                Window.alert("cookie creato");
            }

            public void onFailure(Throwable caught) {
                wd.hide();

                Window.alert("creazione cookie fallita");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        getService().getSessionID(callback);
    }

    public static LoginServiceAsync getService() {
//        // Create the client proxy. Note that although you are creating the
//        // service interface proper, you cast the result to the asynchronous
//        // version of
//        // the interface. The cast is always safe because the generated proxy
//        // implements the asynchronous interface automatically.
//        LoginServiceAsync service = (LoginServiceAsync) GWT.create(LoginService.class);
//        // Specify the URL at which our service implementation is running.
//        // Note that the target URL must reside on the same domain and port from
//        // which the host page was served.
//        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "loginservice";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }

    private void apriMascheraPrincipale() {
        RootPanel rootPanel = RootPanel.get();
        rootPanel.clear();
        rootPanel.add(new MascheraPrincipale(utenteLoggato));
    }
}