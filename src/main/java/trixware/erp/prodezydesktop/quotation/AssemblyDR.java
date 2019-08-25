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
public class AssemblyDR {
    private String assId;
    private String assNo;
    private String assDesc;
    private String quant;
    private String drwgNo;
    private String drwgRevNo;
    private String drwgDate;
    
    public AssemblyDR(String assId,String assNo,String assDesc,String quant,String drwgNo,String drwgRevNo,String drwgDate)
    {
        this.assId=assId;
        this.assNo=assNo;
        this.assDesc=assDesc;
        this.quant=quant;
        this.drwgNo=drwgNo;
        this.drwgRevNo=drwgRevNo;
        this.drwgDate=drwgDate;
    }

    public void setAssId(String assId) {
        this.assId = assId;
    }

    public void setAssNo(String assNo) {
        this.assNo = assNo;
    }

    public void setAssDesc(String assDesc) {
        this.assDesc = assDesc;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }

    public void setDrwgNo(String drwgNo) {
        this.drwgNo = drwgNo;
    }

    public void setDrwgRevNo(String drwgRevNo) {
        this.drwgRevNo = drwgRevNo;
    }

    public void setDrwgDate(String drwgDate) {
        this.drwgDate = drwgDate;
    }

    public String getAssId() {
        return assId;
    }

    public String getAssNo() {
        return assNo;
    }

    public String getAssDesc() {
        return assDesc;
    }

    public String getQuant() {
        return quant;
    }

    public String getDrwgNo() {
        return drwgNo;
    }

    public String getDrwgRevNo() {
        return drwgRevNo;
    }

    public String getDrwgDate() {
        return drwgDate;
    }
    
}
