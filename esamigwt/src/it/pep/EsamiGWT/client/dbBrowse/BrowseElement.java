/**
 * this interface describe an element that is shown in a row of DbBrowse
 */
package it.pep.EsamiGWT.client.dbBrowse;

import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author user
 */
public interface BrowseElement {
    /**
     * 
     * @return the number of fields that are visible in browse
     */
    public int getNumberColumnBrowse();
    
    /**
     * 
     * @return the number of fields that are visible in editing
     */
    public int getNumberFieldEdit();
    
    /**
     * 
     * @param column - the number of column in browse (0 for key)
     * @return a Widget for display the field (Label for key)
     */
    public Widget getColumnBrowse(int column);
    /**
     * 
     * @param column - the number of column in edit (0 for key)
     * @return a Widget for display the field 
     */
    public Widget getFieldEdit(int column, boolean isEditable);
    /**
     * 
     * @param column - the number of column in edit (0 for key)
     * @return a Widget for display the field 
     */
    public String getFieldTitleEdit(int column);
    /**
     * 
     * @return an array with the headers of every field
     */
    public String[] getHeaders();
    /**
     * update the class fields from the values contained into the widget
     * 
     * @param column - the number of column in edit (0 for key)
     * widget - the control with the new value in it
     * 
     */
    public void setFieldValueFromEdit(int column, Widget widget);
}
