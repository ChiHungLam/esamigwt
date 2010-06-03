/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.maschere;

import it.pep.EsamiGWT.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;


import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.PasswordTextBox;
import it.pep.EsamiGWT.client.gwt.WaitDialog;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class SottoMascheraRegistrazioneUtente extends Composite {

    private Button registratiButton;
    private PasswordTextBox textBoxPassword;
    private TextBox textBoxUsername;
    private Label passwordLabel;
    private Label usernameLabel;
    private FlexTable flexTable;
    private Label registratiLabel;
    private VerticalPanel verticalPanel;
    private final WaitDialog wd = new WaitDialog();

    public SottoMascheraRegistrazioneUtente() {

        verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);

        registratiLabel = new Label("Registrazione nuovo utente");
        verticalPanel.add(registratiLabel);

        flexTable = new FlexTable();
        verticalPanel.add(flexTable);

        usernameLabel = new Label("Indirizzo email:");
        flexTable.setWidget(0, 0, usernameLabel);

        passwordLabel = new Label("Password:");
        flexTable.setWidget(1, 0, passwordLabel);

        textBoxUsername = new TextBox();
        flexTable.setWidget(0, 1, textBoxUsername);

        textBoxPassword = new PasswordTextBox();
        flexTable.setWidget(1, 1, textBoxPassword);


        registratiButton = new Button("registrati");
        flexTable.setWidget(3, 1, registratiButton);
        registratiButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent sender) {
                if (textBoxUsername.getText().length() == 0 ||
                        textBoxPassword.getText().length() == 0) {
                    Window.alert("Username or password is empty.");
                } else {
                    registraUtente(textBoxUsername.getText(), textBoxPassword.getText());
                }
            }
        });
//        registratiButton.setText("Sign In");
    }

    private void registraUtente(String utente, String password) {
        wd.show();
        // Create an asynchronous callback to handle the result.
        final AsyncCallback<String> callback = new AsyncCallback<String>() {

            public void onSuccess(String result) {
                wd.hide();
                if (result.equalsIgnoreCase("preesistente")) {
                    Window.alert("Utente già presente, \nimpossibile registrarlo nuovamente.");
                } else  if (result.equalsIgnoreCase("true")) {
                    Window.alert("Grazie per la registrazione.\nTi è stata inviata una mail con il link alla pagina internet \nda aprire per confermare l'iscrizione ed attivare il tuo account.");
                } else {
                    Window.alert("registrazione fallita");
                }
            }

            public void onFailure(Throwable caught) {
                wd.hide();
                Window.alert("fallimento");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        getService().registraUtente(utente, password, GWT.getHostPageBaseURL(), callback);
    }

    public static LoginServiceAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        LoginServiceAsync service = (LoginServiceAsync) GWT.create(LoginService.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "loginservice";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}