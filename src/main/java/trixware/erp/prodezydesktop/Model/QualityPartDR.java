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
public class QualityPartDR 
{
    public String partName;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }
    public int partId;

    public QualityPartDR(int partid,String partName){
        this.partId=partid;
        this.partName=partName;
    }
     public QualityPartDR(){}
}
