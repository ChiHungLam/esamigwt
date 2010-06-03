/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pep.EsamiGWT.client.dbBrowse;

/**
 *
 * @author Francesco
 */
public class HtmlCodeForPrint {
//    static private final String initHtml = "<head><link type=\"text/css\" rel='stylesheet' href='EsamiGWT.css'></head><body>";
//    static private final String endHtml = "</body>";
//    static private final String initHtml = "<head><style type=\"text/css\">@import url(EsamiGWT.css);</style></head><body>";
//    static private final String endHtml = "</body>";
//    static private final String initHtml = "<STYLE TYPE=\"text/css\">table#StampaElenco {    border: solid #000 1px;}table#StampaElenco td {    border: solid #000 1px;}table#StampaElenco th {    font-size: 12px;    font-weight: bold;    color: blue;    text-align: center;    border: solid #000 1px;}table#StampaEsame {    border: none;}table#StampaEsame td {  font-size: 12px;}table#StampaEsame th {  font-size: 12px;  font-weight: bold;}</STYLE>";

    static private final String initHtml = "<STYLE TYPE=\"text/css\">table#StampaElenco {    border: solid #000 1px;}table#StampaElenco td {    border: solid #000 1px;}table#StampaElenco th {    font-size: 12px;    font-weight: bold;    color: blue;    text-align: center;    border: solid #000 1px;}table#StampaEsame {    border: none;}table#StampaEsame td {  font-size: 12px;}table#StampaEsame th {  font-size: 12px;  font-weight: bold; text-align: left;}</STYLE>";
    static private final String endHtml = "";
    static private final String initTableEsami = "<table id=\"StampaEsame\">";
    static private final String initTable = "<table id=\"StampaElenco\">";
    static private final String endTable = "</table>";
    static private final String initHeader = "<thead>";
    static private final String endHeader = "</thead>";
    static private final String initFooter = "<tfoot>";
    static private final String endFooter = "</tfoot>";
    static private final String initColHeader = "<th>";
    static private final String endColHeader = "</th>";
    static private final String initBody = "<tbody>";
    static private final String endBody = "</tbody>";
    static private final String initRow = "<tr>";
    static private final String endRow = "</tr>";
    static private final String initCol = "<td>";
    static private final String endCol = "</td>";
    static private final String initBorderedCol = "<td style=\"border:solid 1px\">";

    static public String getInitTable() {
        return initTable;
    }

    static public String getEndTable() {
        return endTable;
    }

    static public String getInitHeader() {
        return initHeader;
    }

    static public String getEndHeader() {
        return endHeader;
    }

    static public String getInitColHeader() {
        return initColHeader;
    }

    static public String getEndColHeader() {
        return endColHeader;
    }

    static public String getInitBody() {
        return initBody;
    }

    static public String getEndBody() {
        return endBody;
    }

    static public String getInitRow() {
        return initRow;
    }

    static public String getEndRow() {
        return endRow;
    }

    static public String getInitCol() {
        return initCol;
    }

    static public String getEndCol() {
        return endCol;
    }

    public static String getInitFooter() {
        return initFooter;
    }

    public static String getEndFooter() {
        return endFooter;
    }

    public static String getInitHtml() {
        return initHtml;
    }

    public static String getInitTableEsami() {
        return initTableEsami;
    }

    public static String getEndHtml() {
        return endHtml;
    }

    public static String creaColonna(String cosa) {
        StringBuffer retVal = new StringBuffer();
        retVal.append(HtmlCodeForPrint.getInitCol());
        retVal.append(cosa);
        retVal.append(HtmlCodeForPrint.getEndCol());
        return retVal.toString();
    }

    public static String creaColonnaConBordo(String cosa) {
        StringBuffer retVal = new StringBuffer();
        retVal.append(HtmlCodeForPrint.getInitBorderedCol());
        retVal.append(cosa);
        retVal.append(HtmlCodeForPrint.getEndCol());
        return retVal.toString();
    }

    public static String creaRiga(String cosa) {
        StringBuffer retVal = new StringBuffer();
        retVal.append(HtmlCodeForPrint.getInitRow());
        retVal.append(cosa);
        retVal.append(HtmlCodeForPrint.getEndRow());
        return retVal.toString();
    }

    public static String creaTabella(String cosa) {
        StringBuffer retVal = new StringBuffer();
        retVal.append(HtmlCodeForPrint.getInitTable());
        retVal.append(cosa);
        retVal.append(HtmlCodeForPrint.getEndTable());
        return retVal.toString();
    }

    public static String creaTabellaEsami(String cosa) {
        StringBuffer retVal = new StringBuffer();
        retVal.append(HtmlCodeForPrint.getInitTableEsami());
        retVal.append(cosa);
        retVal.append(HtmlCodeForPrint.getEndTable());
        return retVal.toString();
    }

    /**
     * @return the initBorderedCol
     */
    public static String getInitBorderedCol() {
        return initBorderedCol;
    }

    public static String creaBarcode(String codice, String url) {
        return "<img height=\"70\" src=\"" + url + "MioBarcodeServlet?type=code128&height=7&hrsize=4pt&fmt=jpeg&msg=" + codice + "\">";
    }
}
