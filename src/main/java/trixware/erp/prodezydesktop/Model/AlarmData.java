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
public class AlarmData{    
  String alarmName;
    int alarmRefreshRate;
    boolean alarmState ;
    public AlarmData ( String alarmName , int alarmRefreshRate , boolean alarmState ) {
        this.alarmName = alarmName;
        this.alarmRefreshRate = alarmRefreshRate;
        this.alarmState = alarmState;
    }

    public String getAlarmName () {
        return alarmName;
    }

    public void setAlarmName ( String alarmName ) {
        this.alarmName = alarmName;
    }

    public int getAlarmRefreshRate () {
        return alarmRefreshRate;
    }

    public void setAlarmRefreshRate ( int alarmRefreshRate ) {
        this.alarmRefreshRate = alarmRefreshRate;
    }

    public boolean isAlarmState () {
        return alarmState;
    }

    public void setAlarmState ( boolean alarmState ) {
        this.alarmState = alarmState;
    }
    
}
