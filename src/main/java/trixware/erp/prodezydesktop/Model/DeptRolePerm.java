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
public class DeptRolePerm {

    private String department;
    private String resource ;
    private String role ;
    
    private int canRead ;
    private int canEdit ;
    private int canDisable ;
    private int canExport ;
    private int canCreate ;
    private int canImport ;
    
    public DeptRolePerm () {
    }

    public DeptRolePerm ( String department , String resource , String role , int canRead , int canEdit , int canDisable , int canExport , int canCreate , int canImport ) {
        this.department = department;
        this.resource = resource;
        this.role = role;
        this.canRead = canRead;
        this.canEdit = canEdit;
        this.canDisable = canDisable;
        this.canExport = canExport;
        this.canCreate = canCreate;
        this.canImport = canImport;
    }

    
    
    public String getDepartment () {
        return department;
    }

    public void setDepartment ( String department ) {
        this.department = department;
    }

    public String getResource () {
        return resource;
    }

    public void setResource ( String resource ) {
        this.resource = resource;
    }

    public String getRole () {
        return role;
    }

    public void setRole ( String role ) {
        this.role = role;
    }

    public int getCanRead () {
        return canRead;
    }

    public void setCanRead ( int canRead ) {
        this.canRead = canRead;
    }

    public int getCanEdit () {
        return canEdit;
    }

    public void setCanEdit ( int canEdit ) {
        this.canEdit = canEdit;
    }

    public int getCanExport () {
        return canExport;
    }

    public void setCanExport ( int canExport ) {
        this.canExport = canExport;
    }

    public int getCanImport () {
        return canImport;
    }

    public void setCanImport ( int canImport ) {
        this.canImport = canImport;
    }

    public int getCanDisable () {
        return canDisable;
    }

    public void setCanDisable ( int canDisable ) {
        this.canDisable = canDisable;
    }

    public int getCanCreate () {
        return canCreate;
    }

    public void setCanCreate ( int canCreate ) {
        this.canCreate = canCreate;
    }
    
    
    
    
}
