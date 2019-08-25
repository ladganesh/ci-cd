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
public class ENQModelDR {
    private String enqId;
    private String enqNo;
    private String asslyId;
    private String customerId;
    private String customerName;
    private String enqLatestVersion;
    
    public ENQModelDR(String enqId,String enqNo,String asslyId,String customerId,String custName,String enqLatestVer)
    {
        this.enqId=enqId;
        this.enqNo=enqNo;
        this.asslyId=enqId;
        this.asslyId=asslyId;
        this.customerId=customerId;
        this.enqLatestVersion=enqLatestVer;
        this.customerName=custName;
    }

    public void setEnqId(String enqId) {
        this.enqId = enqId;
    }

    public void setEnqNo(String enqNo) {
        this.enqNo = enqNo;
    }

    public String getEnqNo() {
        return enqNo;
    }

    public void setAsslyId(String asslyId) {
        this.asslyId = asslyId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setEnqLatestVersion(String enqLatestVersion) {
        this.enqLatestVersion = enqLatestVersion;
    }

    public String getEnqId() {
        return enqId;
    }

    public String getAsslyId() {
        return asslyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEnqLatestVersion() {
        return enqLatestVersion;
    }    
}
