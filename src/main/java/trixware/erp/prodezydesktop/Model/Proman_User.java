/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

/**
 *
 * @author Rajesh
 */
public class Proman_User {
    
    private static int EID = -1;
    private static  String  username = "";
    private static String  password = "";
    private static boolean  isActivelyLoggedIn = false;
    
    private static String role = "";
    private static String dept ;
    
    private static   boolean canRead = false, canEdit= false, canDisable= false, canCreate= false, canImport= false, canExport= false, canExportPDF = false , canSeeReports = false  ;

    public static boolean isCanRead () {
        return canRead;
    }

    public static void setCanRead ( boolean canRead ) {
        Proman_User.canRead = canRead;
    }

    public static boolean isCanEdit () {
        return canEdit;
    }

    public static void setCanEdit ( boolean canEdit ) {
        Proman_User.canEdit = canEdit;
    }

    public static boolean isCanDisable () {
        return canDisable;
    }

    public static void setCanDisable ( boolean canDisable ) {
        Proman_User.canDisable = canDisable;
    }

    public static boolean isCanCreate () {
        return canCreate;
    }

    public static void setCanCreate ( boolean canCreate ) {
        Proman_User.canCreate = canCreate;
    }

    public static boolean isCanImport () {
        return canImport;
    }

    public static void setCanImport ( boolean canImport ) {
        Proman_User.canImport = canImport;
    }

    public static boolean isCanExport () {
        return canExport;
    }

    public static void setCanExport ( boolean canExport ) {
        Proman_User.canExport = canExport;
    }

    public static boolean isCanExportToPDF () {
        return canExportPDF;
    }

    public static void setCanExportToPDF ( boolean canRead ) {
        Proman_User.canExportPDF = canRead;
    }

    public static boolean isCanSeeReports () {
        return canSeeReports;
    }

    public static void setCanSeeReports ( boolean canRead ) {
        Proman_User.canSeeReports = canRead;
    }

    
    
    

    public static String getRole () {
        return role;
    }

    public static void setRole ( String role ) {
        Proman_User.role = role;
    }

    public static String getDeptIds () {
        return dept;
    }

    public static void setDeptIds ( String dept ) {
        Proman_User.dept = dept;
    }
    
    

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String _password) {
        password = _password;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String _username) {
        username = _username;
    }

    public static boolean isActivelyLoggedIn() {
        return isActivelyLoggedIn;
    }

    public static void setIsActivelyLoggedIn(boolean _isActivelyLoggedIn) {
        isActivelyLoggedIn = _isActivelyLoggedIn;
    }

    public static void setEID(int eid) {
        EID = eid;
    }

    public static int getEID() {
        return EID;
    }

    
    
}
