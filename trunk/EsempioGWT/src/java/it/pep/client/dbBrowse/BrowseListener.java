/*
 * this interface listen for an event in db browse
 */

package it.pep.client.dbBrowse;

/**
 *
 * @author user
 */
public interface BrowseListener {
    /**
     * fired when a row is selected
     * @param key - the key of record
     * @return
     */
    public int onSel(String key);
    /**
     * fired when "add" button is pressed
     * @return
     */
    public int onAdd();
    /**
     * fired when "delete" button is pressed
     * @param key - the key of record
     * @return
     */
    public int onDel(String key);
    /**
     * fired when "edit" button is pressed
     * @param key - the key of record
     * @return
     */
    public int onEdit(String key);
    /**
     * fired when "search" button is pressed
     * @return
     */
    public int onSearch();
    /**
     * fired when "close" button is pressed
     * @return
     */
    public int onClose();
}
