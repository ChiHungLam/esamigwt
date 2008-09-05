/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.client.maschere;

import it.pep.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import java.util.Date;
import org.apache.catalina.Session;

public class MascheraLogin extends Composite {

    private Button signInButton;
    private CheckBox rememberMeOnCheckBox;
    private PasswordTextBox textBoxPassword;
    private TextBox textBoxUsername;
    private Label passwordLabel;
    private Label usernameLabel;
    private FlexTable flexTable;
    private Label signInToLabel;
    private VerticalPanel verticalPanel;

    public MascheraLogin() {

        verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);

        signInToLabel = new Label("Sign in to your account.");
        verticalPanel.add(signInToLabel);

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

        rememberMeOnCheckBox = new CheckBox();
        flexTable.setWidget(2, 1, rememberMeOnCheckBox);
        rememberMeOnCheckBox.setText("Remember me on this computer.");

        signInButton = new Button();
        flexTable.setWidget(3, 1, signInButton);
        signInButton.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                if (textBoxUsername.getText().length() == 0 ||
                        textBoxPassword.getText().length() == 0) {
                    Window.alert("Username or password is empty.");
                } else {
                    controllaUtente(textBoxUsername.getText(), textBoxPassword.getText());
                }
            }
        });
        signInButton.setText("Sign In");

        /** per saltare il login - DA TOGLIERE*/
        apriSchermataPrincipale();
    }

    private void controllaUtente(String utente, String password) {
        // Create an asynchronous callback to handle the result.
        final AsyncCallback callback = new AsyncCallback() {

            public void onSuccess(Object result) {
                boolean esito = ((String) result).equalsIgnoreCase("true");
//                Window.alert("accesso:" + esito);
                if (esito) {
                    if (rememberMeOnCheckBox.isChecked()) {
                        salvaUtente();
                    }
                    apriSchermataPrincipale();
                }
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
        final AsyncCallback callback = new AsyncCallback() {

            public void onSuccess(Object result) {
                String sessionID = (String) result;
                final long DURATION = 1000 * 60 * 60 * 24 * 14; //duration remembering login. 2 weeks in this example.
                Date expires = new Date(System.currentTimeMillis() + DURATION);
                Cookies.setCookie("sid", sessionID, expires, null, "/", false);
                Window.alert("cookie creato");
            }

            public void onFailure(Throwable caught) {
                Window.alert("creazione cookie fallita");
            }
        };

        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        getService().getSessionID(callback);
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

    private void apriSchermataPrincipale() {
        RootPanel rootPanel = RootPanel.get();
        rootPanel.clear();
        rootPanel.add(new MascheraPrincipale());
    }
}