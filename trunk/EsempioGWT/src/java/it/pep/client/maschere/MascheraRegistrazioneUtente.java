/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.client.maschere;

import it.pep.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;

import com.google.gwt.user.client.ui.CheckBox;

import com.google.gwt.user.client.ui.ClickListener;

import com.google.gwt.user.client.ui.Composite;

import com.google.gwt.user.client.ui.FlexTable;

import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class MascheraRegistrazioneUtente extends Composite {

    private Button registratiButton;
    private PasswordTextBox textBoxPassword;
    private TextBox textBoxUsername;
    private Label passwordLabel;
    private Label usernameLabel;
    private FlexTable flexTable;
    private Label registratiLabel;
    private VerticalPanel verticalPanel;

    public MascheraRegistrazioneUtente() {

        verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);

        registratiLabel = new Label("Registrazione nuovo utente");
        verticalPanel.add(registratiLabel);

        flexTable = new FlexTable();
        verticalPanel.add(flexTable);

        usernameLabel = new Label("Username:");
        flexTable.setWidget(0, 0, usernameLabel);

        passwordLabel = new Label("Password:");
        flexTable.setWidget(1, 0, passwordLabel);

        textBoxUsername = new TextBox();
        flexTable.setWidget(0, 1, textBoxUsername);

        textBoxPassword = new PasswordTextBox();
        flexTable.setWidget(1, 1, textBoxPassword);


        registratiButton = new Button("registrati");
        flexTable.setWidget(3, 1, registratiButton);
        registratiButton.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
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
        // Create an asynchronous callback to handle the result.
        final AsyncCallback callback = new AsyncCallback() {

            public void onSuccess(Object result) {
                Window.alert("registrato "+(String) result);
            }

            public void onFailure(Throwable caught) {
                Window.alert("fallimento");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        getService().registraUtente(utente, password, callback);
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