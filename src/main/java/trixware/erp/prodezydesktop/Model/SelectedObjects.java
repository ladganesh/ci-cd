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
public class SelectedObjects {

    private int ID;
    private String VALUE;

    public String getValue () {
        return this.VALUE;
    }

    public void setValue ( String _value ) {
        this.VALUE = _value;
    }

    public int getId () {
        return this.ID;
    }

    public void setId ( int _id ) {
        this.ID = _id;
    }

}