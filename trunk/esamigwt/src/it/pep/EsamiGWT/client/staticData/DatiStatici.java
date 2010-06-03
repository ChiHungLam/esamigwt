/*
 * data to mantain during session
 */

package it.pep.EsamiGWT.client.staticData;

import com.google.gwt.core.client.GWT;
import it.pep.EsamiGWT.client.*;
import it.pep.EsamiGWT.client.hibernate.Appelli;
import it.pep.EsamiGWT.client.hibernate.Argomenti;
import java.util.List;

/**
 *
 * @author Francesco
 */
public class DatiStatici {
	private static BusinessServiceAsync service = (BusinessServiceAsync) GWT.create(BusinessService.class);

//    private static String codUtente;
    private static List<Argomenti> listaArgomenti=null;
    @SuppressWarnings("unused")
	private static List<Appelli> listaAppelli=null;

    public static void apportaModificheArgomenti(List<Integer> listaNumDomande) {
        int pos=0;
        for(Argomenti argomento:listaArgomenti){
            argomento.setNumDomande(listaNumDomande.get(pos++));
        }
    }

//    public static String getCodUtente() {
//        return codUtente;
//    }

//    public static void setCodUtente(String codUtente) {
//        DatiStatici.codUtente = codUtente;
//    }

    public static BusinessServiceAsync getDBService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
//        BusinessServiceAsync service = (BusinessServiceAsync) GWT.create(BusinessService.class);
//        // Specify the URL at which our service implementation is running.
//        // Note that the target URL must reside on the same domain and port from
//        // which the host page was served.
//        //
//        ServiceDefTarget endpoint = (ServiceDefTarget) service;
//        String moduleRelativeURL = GWT.getModuleBaseURL() + "dbservice";
//        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }

    public static BusinessServiceAsync getBusinessService() {
//        // Create the client proxy. Note that although you are creating the
//        // service interface proper, you cast the result to the asynchronous
//        // version of
//        // the interface. The cast is always safe because the generated proxy
//        // implements the asynchronous interface automatically.
//        BusinessServiceAsync service = (BusinessServiceAsync) GWT.create(BusinessService.class);
//        // Specify the URL at which our service implementation is running.
//        // Note that the target URL must reside on the same domain and port from
//        // which the host page was served.
//        //
//        ServiceDefTarget endpoint = (ServiceDefTarget) service;
//        String moduleRelativeURL = GWT.getModuleBaseURL() + "businessservice";
//        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }

    public static List<Argomenti> getListaArgomenti() {
        return listaArgomenti;
    }

    public static void setListaArgomenti(List<Argomenti> listaArgomenti) {
        DatiStatici.listaArgomenti = listaArgomenti;
    }
    public static void setListaAppelli(List<Appelli> listaAppelli) {
        DatiStatici.listaAppelli = listaAppelli;
    }
}
