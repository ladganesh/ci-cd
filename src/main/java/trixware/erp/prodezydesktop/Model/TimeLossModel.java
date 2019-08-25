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
public class TimeLossModel {
    
    private int Id;
     private String reason ;
     private int minutes ;
     private int timelossForReason ;

    public TimeLossModel ( int Id , String reason , int minutes , int timelossForReason ) {
        this.Id = Id;
        this.reason = reason;
        this.minutes = minutes;
       this.timelossForReason = timelossForReason ;
    }

    public TimeLossModel ( ) {
        
    }
    
    public int getId () {
        return Id;
    }

    public void setId ( int Id ) {
        this.Id = Id;
    }

    public String getReason () {
        return reason;
    }

    public void setReason ( String reason ) {
        this.reason = reason;
    }

    public int getMinutes () {
        return minutes;
    }

    public void setMinutes ( int minutes ) {
        this.minutes = minutes;
    }

    public int getTimelossForReason () {
        return timelossForReason;
    }

    public void setTimelossForReason ( int timelossForReason ) {
        this.timelossForReason = timelossForReason;
    }
    
    
}
