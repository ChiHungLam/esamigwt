/*
 * this class realizes a widget to show a list of records and manipulate 
 * them (add, delete, edit, ecc.)
 */
package it.pep.EsamiGWT.client.dbBrowse;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;
import java.util.List;

/**
 *
 * @author user
 */
public class DbBrowse<T extends BrowseElement> extends Composite {

    private List<T> dataElements = null;
    private String[] headers = null;
//    private DockPanel dp = new DockPanel();
    private VerticalPanel dp = new VerticalPanel();
    private FlowPanel toolBarPanel = new FlowPanel();
    private FlowPanel titleBarPanel = new FlowPanel();
    private HorizontalPanel dataPanel = new HorizontalPanel();
    private ScrollPanel scrollDataPanel=new ScrollPanel(dataPanel);
    private PushButton delButton = null;
    private PushButton editButton = null;
    private PushButton insButton = null;
    private PushButton printButton = null;
    private FlexTable dataTable = new FlexTable();
    private BrowseListener handler = null;
    private String selectedKey = null;
    private int selectedRow = -1; // no row selected
    @SuppressWarnings("unused")
	private boolean[] bottoniAttivi = {true, true, true, true};
    private final String radioGroup = "" + ((int) (Math.random() * 1000));  // serve per raggruppare i radio button di questo browse
    private final String idTabDati = "tab" + ((int) (Math.random() * 1000));  // serve per identificare la tabella dati
//    private final String RUN_REPORT_SERVLET_PATH = "StampaReport";

    public DbBrowse(List<T> data, BrowseListener handler, T dummyElement, String title) {
        this.headers = dummyElement.getHeaders(); //we getColumnBrowse the header from class T
        this.dataElements = data;
        this.handler = handler;
        initWidget(dp);

//        dp.add(titleBarPanel, DockPanel.NORTH);
//        dp.add(toolBarPanel, DockPanel.NORTH);
//        dp.add(dataPanel, DockPanel.CENTER);
        dp.add(titleBarPanel);
        dp.add(toolBarPanel);
        dp.add(scrollDataPanel);

        createTitleBar(title);
        createToolBar();
        initTable();
        createRows(DATA_INDEX_ZERO);
        applyDataRowStyles();
    }

    public void aggiornaRigaCorrente(T cellObjects) {
        for (int cell = FIRST_COLUMN;
                cell < cellObjects.getNumberColumnBrowse(); cell++) {
            addCell(getSelectedRow(), cell, cellObjects.getColumnBrowse(cell));
        }
    }

    private void addBodyHTML(FlexTable tabella, StringBuffer retVal) {
        retVal.append(HtmlCodeForPrint.getInitBody());
        for (int r = 1; r < tabella.getRowCount(); r++) {
            retVal.append(HtmlCodeForPrint.getInitRow());
            for (int c = 1; c <= headers.length; c++) {
                retVal.append(HtmlCodeForPrint.getInitCol());
                retVal.append(tabella.getHTML(r, c));
                retVal.append(HtmlCodeForPrint.getEndCol());
            }
            retVal.append(HtmlCodeForPrint.getEndRow());
        }
        retVal.append(HtmlCodeForPrint.getEndBody());
    }

    private void addHeaderHTML(FlexTable tabella, StringBuffer retVal) {
        retVal.append(HtmlCodeForPrint.getInitHeader());
        for (int c = 1; c <= headers.length; c++) {
            retVal.append(HtmlCodeForPrint.getInitColHeader());
            retVal.append(tabella.getHTML(0, c));
            retVal.append(HtmlCodeForPrint.getEndColHeader());
        }
        retVal.append(HtmlCodeForPrint.getEndHeader());
    }

    private void createTitleBar(String title) {
        Label titolo = new Label(title);
        titleBarPanel.add(titolo);
        titleBarPanel.setStyleName("gwtsolutions-EasyFlexTable-Title");
    }

    private void initTable() {
        for (int col = 0; col < headers.length; col++) {
            dataTable.setWidget(0, col + 1, new HTML(headers[col]));
        }
        DOM.setElementProperty(dataTable.getElement(), "id", idTabDati);


        dataPanel.add(dataTable);
    }

    private void addColumns() {
        for (String s : headers) {
            addColumn(s);
        }
    }

    private void createToolBar() {
        FlexTable ftBar = new FlexTable();
        toolBarPanel.add(ftBar);
        String dimBottoni = "20px";
        Image imgIns = new Image("images/add.png");
        insButton = new PushButton(imgIns);
        insButton.setWidth(dimBottoni);
        insButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent source) {
                handler.onAdd();
            }
        });
        ftBar.setWidget(0, 0, insButton);

        Image imgDel = new Image("images/del.png");
        delButton = new PushButton(imgDel);
        delButton.setWidth(dimBottoni);
        delButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent source) {
                handler.onDel(getSelectedKey());
            }
        });
        ftBar.setWidget(0, 1, delButton);

        Image imgEdit = new Image("images/edit.png");
        editButton = new PushButton(imgEdit);
        editButton.setWidth(dimBottoni);
        editButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent source) {
                handler.onEdit(getSelectedKey());
            }
        });
        ftBar.setWidget(0, 2, editButton);

        Image printEdit = new Image("images/print.png");
        printButton = new PushButton(printEdit);
        printButton.setWidth(dimBottoni);
        printButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent source) {
                if (handler.onPrint(getSelectedKey()) == 0) // start the default handling
                {
                    defaultPrint();
                }
            }
        });
        ftBar.setWidget(0, 3, printButton);
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

//        for (T elemento : dataElements) {
        for (int n = 0; n < dataElements.size(); n++) {
            T elemento = dataElements.get(n);
            addRow(elemento, firstRow);
            firstRow = false;
        }
    }

    private void addRow(T cellObjects, boolean firstRow) {
        addCell(nextRow, 0, new RowSelection(nextRow, (Label) cellObjects.getColumnBrowse(0), firstRow)); // la cella con la chiave
        for (int cell = FIRST_COLUMN;
                cell < cellObjects.getNumberColumnBrowse(); cell++) {
            addCell(nextRow, cell, cellObjects.getColumnBrowse(cell));
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
        rf.removeStyleName(HEADER_ROW,
                "gwtsolutions-EasyFlexTable-ColumnLabelCell");
        rf.removeStyleName(HEADER_ROW,
                "gwtsolutions-EasyFlexTable-ColumnLabel");
        rf.addStyleName(HEADER_ROW,
                "gwtsolutions-EasyFlexTable-ColumnLabel");
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

    public int getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedItem(int selectedRow, String selectedKey) {
        this.selectedRow = selectedRow;
        this.selectedKey = selectedKey;
    }

    public void setBottoniAttivi(boolean[] bottoniAttivi) {
        this.bottoniAttivi = bottoniAttivi;
        insButton.setVisible(bottoniAttivi[0]);
        delButton.setVisible(bottoniAttivi[1]);
        editButton.setVisible(bottoniAttivi[2]);
        printButton.setVisible(bottoniAttivi[3]);
    }

    private void defaultPrint() {
        /*Window.open(RUN_REPORT_SERVLET_PATH, "Report", "");*/
//        Print.it(/*DOM.getElementById(idTabDati)*/); 
        Print.it(getTableHtml(dataTable));
    }
    private String getTableHtml(FlexTable tabella) {
        StringBuffer retVal = new StringBuffer(HtmlCodeForPrint.getInitHtml()+HtmlCodeForPrint.getInitTable());
        addHeaderHTML(tabella, retVal);
        addBodyHTML(tabella, retVal);
        retVal.append(HtmlCodeForPrint.getEndTable());
        retVal.append(HtmlCodeForPrint.getEndHtml());
//        System.out.println(retVal.toString());
        return retVal.toString();
    }

    /*******************/
    private class RowSelection extends RadioButton {

        private String rowKey;
        private int row;

        public RowSelection(int row, Label get, boolean selected) {
            super(radioGroup);
            this.rowKey = get.getText();
            this.row = row;
//            Log.info(rowKey);
            setValue(selected);
            if (selected) {
                setSelectedItem(row, getRowKey());
            }
            addClickHandler(new ClickHandler() {

                public void onClick(ClickEvent arg0) {
                    int rigaPrecedente = getSelectedRow();
                    if (rigaPrecedente != -1) // uncheck the previous row
                    {
                        ((CheckBox) dataTable.getWidget(rigaPrecedente, 0)).setValue(false);
                    }
                    setValue(true); // check the current row

                    setSelectedItem(getRow(), getRowKey()); // set new row and key in table

                    if (handler != null) {  // there is an handler for table events
                        handler.onSel(getSelectedKey());
                    }
                }
            });
        }

        public String getRowKey() {
            return rowKey;
        }

        public int getRow() {
            return row;
        }
    }
}