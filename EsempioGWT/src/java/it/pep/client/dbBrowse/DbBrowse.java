/*
 * this class realizes a widget to show a list of records and manipulate 
 * them (add, delete, edit, ecc.)
 */
package it.pep.client.dbBrowse;

import com.google.gwt.user.client.ui.*;
import java.util.List;

/**
 *
 * @author user
 */
public class DbBrowse<T extends BrowseElement> extends Composite{

    private List<T> dataElements = null;
    private String[] headers = null;
    private DockPanel dp = new DockPanel();
    private FlowPanel toolBarPanel = new FlowPanel();
    private HorizontalPanel dataPanel = new HorizontalPanel();
    private Button insButton = new Button("+");
    private Button delButton = new Button("-");
    private Button editButton = new Button("edit");
    private FlexTable dataTable = new FlexTable();
    private BrowseListener handler = null;
    private String selectedKey = null;

    public DbBrowse(List<T> data, String[] headers, BrowseListener handler) {
        this.dataElements = data;
        this.headers = headers;
        this.handler = handler;
        initWidget(dp);
        dp.add(toolBarPanel, DockPanel.NORTH);
        dp.add(dataPanel, DockPanel.CENTER);
        createToolBar();
        initTable();
        createRows(DATA_INDEX_ZERO);
        applyDataRowStyles();
    }

    private void initTable() {
        for (int col = 0; col < headers.length; col++) {
            dataTable.setWidget(0, col + 1, new HTML(headers[col]));
        }
        dataPanel.add(dataTable);
    }

    private void addColumns() {
        for (String s : headers) {
            addColumn(s);
        }
    }

    private void createToolBar() {
        insButton.setWidth("20px");
        toolBarPanel.add(insButton);
        delButton.setWidth("20px");
        toolBarPanel.add(delButton);
        editButton.setWidth("100px");
        toolBarPanel.add(editButton);
    }
    /*********************************************************/
    protected static final int HEADER_ROW = 0;
    protected static final int FIRST_DATA_ROW = 1;
    protected static final int FIRST_COLUMN = 1;    // 0 means key
    protected static final int DATA_INDEX_ZERO = 0;
    protected static final int DEFAULT_TABLE_CELL_SPACING = 0;
    private int nextRow = 1;

    protected DbBrowse() {
        dataTable.insertRow(HEADER_ROW);
        dataTable.getRowFormatter().addStyleName(HEADER_ROW,
                "gwtsolutions-EasyFlexTable-Header");
        dataTable.setCellSpacing(DEFAULT_TABLE_CELL_SPACING);
        addStyleName("gwtsolutions-EasyFlexTable");
        addColumns();
    }

    public void createRows(int rowIndex) {
        if (dataElements == null) {
            return;
        }
        boolean firstRow = true;
        for (T elemento : dataElements) {
            addRow(elemento, firstRow);
            firstRow = false;
        }
    }

    private void addRow(T cellObjects, boolean firstRow) {
        addCell(nextRow, 0, new RowSelection(cellObjects.get(0), firstRow));
        for (int cell = FIRST_COLUMN;
                cell < cellObjects.length(); cell++) {
            addCell(nextRow, cell, cellObjects.get(cell));
        }
        nextRow++;
    }

    public void addCell(int row, int cell, Object cellObject) {
        Widget widget = createCellWidget(cellObject);
        dataTable.setWidget(row, cell, widget);
        dataTable.getCellFormatter().addStyleName(row,
                cell,
                cell == 0 ? "gwtsolutions-EasyFlexTable-Cell0"
                : "gwtsolutions-EasyFlexTable-Cell");
    }

    public void applyDataRowStyles() {
        HTMLTable.RowFormatter rf = dataTable.getRowFormatter();

        for (int row = FIRST_DATA_ROW; row < dataTable.getRowCount(); ++row) {
            if ((row % 2) != 0) {
                // Remove the even row style, just in case this used
                // to be an even row
                rf.removeStyleName(row,
                        "gwtsolutions-EasyFlexTable-EvenRow");
                rf.addStyleName(row,
                        "gwtsolutions-EasyFlexTable-OddRow");
            } else {
                // Remove the odd row style, just in case this used
                // to be an odd row
                rf.removeStyleName(row,
                        "gwtsolutions-EasyFlexTable-OddRow");
                rf.addStyleName(row,
                        "gwtsolutions-EasyFlexTable-EvenRow");
            }
        }
    }

    public void addColumn(Object columnHeading) {
        Widget widget = createCellWidget(columnHeading);
        int columnIndex = getColumnCount();

        widget.setWidth("100%");
        widget.addStyleName(
                "gwtsolutions-EasyFlexTable-ColumnLabel");

        dataTable.setWidget(HEADER_ROW, columnIndex, widget);

        dataTable.getCellFormatter().addStyleName(HEADER_ROW, columnIndex,
                "gwtsolutions-EasyFlexTable-ColumnLabelCell");
    }

    public int getColumnCount() {
        return dataTable.getCellCount(HEADER_ROW);
    }

    protected Widget createCellWidget(Object cellObject) {
        Widget widget = null;

        if (cellObject instanceof Widget) {
            widget = (Widget) cellObject;
        } else {
            widget = new Label(cellObject.toString());
        }
        return widget;
    }

    public String getSelectedKey() {
        return selectedKey;
    }

    public void setSelectedKey(String selectedKey) {
        this.selectedKey = selectedKey;
    }

    private class RowSelection extends RadioButton {

        private String rowKey;

        public RowSelection(Widget get, boolean selected) {
            super("");
            rowKey = get.getTitle();
            setChecked(selected);
            if(selected)
                setSelectedKey(getRowKey());
            addClickListener(new ClickListener() {

                public void onClick(Widget arg0) {
                    setSelectedKey(getRowKey());
                    if (handler != null) {
                        handler.onSel(getSelectedKey());
                    }
                }
            });
        }

        public String getRowKey() {
            return rowKey;
        }
    }
}