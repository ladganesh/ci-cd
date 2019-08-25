/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation;

/**
 *
 * @author Akshay
 */
public class RejectionDR {

    private int id;    
    private int from;    
    private int to;    
    private double recoveryPercent;
    public RejectionDR(int id,int from,int to,double recovery)
    {
        this.id=id;
        this.from=from;
        this.to=to;
        this.recoveryPercent=recovery;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setRecoveryPercent(double recoveryPercent) {
        this.recoveryPercent = recoveryPercent;
    }

    public int getId() {
        return id;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public double getRecoveryPercent() {
        return recoveryPercent;
    }

}
