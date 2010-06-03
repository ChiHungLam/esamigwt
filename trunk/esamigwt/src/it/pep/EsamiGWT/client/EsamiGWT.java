package it.pep.EsamiGWT.client;

import it.pep.EsamiGWT.client.maschere.MascheraLogin;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EsamiGWT implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
        if (!validoSessionID()) {
            RootPanel rootPanel = RootPanel.get();
            rootPanel.clear();
            rootPanel.add(new MascheraLogin());
        }
    }

    private boolean validoSessionID() {
        boolean retVal = false;
        String sessionID = Cookies.getCookie("sid");
        if (sessionID != null) {
            //checkWithServerIfSessionIdIsStillLegal();
        }
        return retVal;
	}
}
