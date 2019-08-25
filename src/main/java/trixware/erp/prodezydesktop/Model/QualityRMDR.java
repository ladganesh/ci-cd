/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

/**
 *
 * @author WIN7
 */
public class QualityRMDR {
    public int RMQP_ID;
    public String RMQP_Name;

    public int getRMQP_ID() {
        return RMQP_ID;
    }

    public void setRMQP_ID(int RMQP_ID) {
        this.RMQP_ID = RMQP_ID;
    }

    public String getRMQP_Name() {
        return RMQP_Name;
    }

    public void setRMQP_Name(String RMQP_Name) {
        this.RMQP_Name = RMQP_Name;
    }

    public QualityRMDR() {
    }
    public QualityRMDR(int rmqpId,String rmqpName)
    {
        this.RMQP_ID=rmqpId;
        this.RMQP_Name=rmqpName;
    }
}
