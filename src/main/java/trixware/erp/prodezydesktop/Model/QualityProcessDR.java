/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author WIN7
 */
public class QualityProcessDR {
    public int PROC_ID;
    public String PROC_NAME;

    public int getPROC_ID() {
        return PROC_ID;
    }

    public void setPROC_ID(int PROC_ID) {
        this.PROC_ID = PROC_ID;
    }

    public String getPROC_NAME() {
        return PROC_NAME;
    }

    public void setPROC_NAME(String PROC_NAME) {
        this.PROC_NAME = PROC_NAME;
    }
    
    public QualityProcessDR(int procid,String procname)
    {
        this.PROC_ID=procid;
        this.PROC_NAME=procname;
    }
    public QualityProcessDR()
    {
        
    }
}
