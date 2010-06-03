/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.dbBrowse;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.UIObject;

public class Print {

    public static native void it() /*-{ 
    $wnd.print(); 
    }-*/;

    public static native void it(String html) /*-{ 
    var frame = $doc.getElementById('__printingFrame'); 
    if (!frame) { 
    $wnd.alert("Error: Can't find printing frame."); 
    return; 
    } 
    frame = frame.contentWindow; 
    var doc = frame.document; 
    doc.open(); 
    doc.write(html); 
    doc.close(); 
    frame.focus(); 
    frame.print(); 
    }-*/;

    public static void it(UIObject obj) {
        it("", obj.getElement().toString());
    }

    public static void it(Element element) {
        it("", element.toString());
    }

    public static void it(String style, String it) {
        it("<html><header>" + style + "</header><body>" + it +"</body></html>"); 
    
    }

    public static void it(String style, UIObject obj) {
        it(style, obj.getElement().toString());
    }

    public static void it(String style, Element element) {
        it(style, element.toString());
    }
}