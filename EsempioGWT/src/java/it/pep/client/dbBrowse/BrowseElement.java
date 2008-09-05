/**
 * this interface describe an element that is shown in a row of DbBrowse
 */
package it.pep.client.dbBrowse;

import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author user
 */
public interface BrowseElement {
    /**
     * 
     * @return thr number of fields in this class
     */
    public int length();
    
    /**
     * 
     * @param column - the number of column in browse (0 for key)
     * @return a Widget for display the field
     */
    public Widget get(int column);

}
