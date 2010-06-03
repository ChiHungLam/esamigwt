/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.gwt;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NumberBox
        extends TextBox implements FocusListener {

    final boolean integersOnly;
    boolean emptyAllowed = false;

    public NumberBox() {
        this(false);
        addFocusListener(this);
    }

    public NumberBox(boolean integersOnly) {
        this.integersOnly = integersOnly;
    }

    public int getInt() {
        return Integer.parseInt(getText());
    }

    public int getIntOrZero() {
        int retVal = 0;
        try {
            retVal = Integer.parseInt(getText());
        } catch (Exception e) {
        }
        return retVal;
    }

    public double getDouble() {
        NumberFormat fmt = NumberFormat.getDecimalFormat();
        return fmt.parse(getText());
    }

    public boolean validate() {
        try {
            if (emptyAllowed && isEmpty()); else if (integersOnly) {
                getInt();
            } else {
                getDouble();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setNumber(int value) {
        setText(Integer.toString(value));
    }

    public void setNumber(double value) {
        NumberFormat fmt = NumberFormat.getDecimalFormat();
        setText(fmt.format(value));
    }

    public void setEmptyAllowed(boolean allowed) {
        this.emptyAllowed = allowed;
    }

    public boolean isEmpty() {
        return getText() == null || getText().length() == 0;
    }

    public void clear() {
        setText("");
    }

    public void onFocus(Widget arg0) {
    }

    public void onLostFocus(Widget arg0) {
        if (!validate()) {
            Window.alert("valore non ammissibile");
            setFocus(true);
        }

    }
}