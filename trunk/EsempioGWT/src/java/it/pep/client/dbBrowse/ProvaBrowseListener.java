/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pep.client.dbBrowse;

import com.google.gwt.user.client.Window;

/**
 *
 * @author user
 */
public class ProvaBrowseListener implements BrowseListener{

    public int onSel(String key) {
        Window.alert(key);
        return 0;
    }

    public int onAdd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int onDel(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int onEdit(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int onSearch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int onClose() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
