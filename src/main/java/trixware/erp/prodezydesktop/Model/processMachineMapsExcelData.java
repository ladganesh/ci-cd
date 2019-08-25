/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

/**
 *
 * @author dell
 */
public class processMachineMapsExcelData {
    
    private String FG_PART_NO;
    private String PROCESS_NAME;
    private String mcname;
    private String die_tool;
    private String SETUP_TIME;
    private String IDEAL_PROCESS_TIME;
    private String TG_OPT_HR;
    private String TG_OPT_SHT;
    private String TG_OPT_DAY;
    private String VAL_AT_PROC;
    private String MAX_REJ_PERM;
    private String REMARKS;

    public processMachineMapsExcelData(String FG_PART_NO, String PROCESS_NAME, String mcname, String die_tool, String SETUP_TIME, String IDEAL_PROCESS_TIME, String TG_OPT_HR, String TG_OPT_SHT, String TG_OPT_DAY, String VAL_AT_PROC, String MAX_REJ_PERM, String REMARKS) {
        this.FG_PART_NO = FG_PART_NO;
        this.PROCESS_NAME = PROCESS_NAME;
        this.mcname = mcname;
        this.die_tool = die_tool;
        this.SETUP_TIME = SETUP_TIME;
        this.IDEAL_PROCESS_TIME = IDEAL_PROCESS_TIME;
        this.TG_OPT_HR = TG_OPT_HR;
        this.TG_OPT_SHT = TG_OPT_SHT;
        this.TG_OPT_DAY = TG_OPT_DAY;
        this.VAL_AT_PROC = VAL_AT_PROC;
        this.MAX_REJ_PERM = MAX_REJ_PERM;
        this.REMARKS = REMARKS;
    }

    public String getFG_PART_NO() {
        return FG_PART_NO;
    }

    public void setFG_PART_NO(String FG_PART_NO) {
        this.FG_PART_NO = FG_PART_NO;
    }

    public String getPROCESS_NAME() {
        return PROCESS_NAME;
    }

    public void setPROCESS_NAME(String PROCESS_NAME) {
        this.PROCESS_NAME = PROCESS_NAME;
    }

    public String getMcname() {
        return mcname;
    }

    public void setMcname(String mcname) {
        this.mcname = mcname;
    }

    public String getDie_tool() {
        return die_tool;
    }

    public void setDie_tool(String die_tool) {
        this.die_tool = die_tool;
    }

    public String getSETUP_TIME() {
        return SETUP_TIME;
    }

    public void setSETUP_TIME(String SETUP_TIME) {
        this.SETUP_TIME = SETUP_TIME;
    }

    public String getIDEAL_PROCESS_TIME() {
        return IDEAL_PROCESS_TIME;
    }

    public void setIDEAL_PROCESS_TIME(String IDEAL_PROCESS_TIME) {
        this.IDEAL_PROCESS_TIME = IDEAL_PROCESS_TIME;
    }

    public String getTG_OPT_HR() {
        return TG_OPT_HR;
    }

    public void setTG_OPT_HR(String TG_OPT_HR) {
        this.TG_OPT_HR = TG_OPT_HR;
    }

    public String getTG_OPT_SHT() {
        return TG_OPT_SHT;
    }

    public void setTG_OPT_SHT(String TG_OPT_SHT) {
        this.TG_OPT_SHT = TG_OPT_SHT;
    }

    public String getTG_OPT_DAY() {
        return TG_OPT_DAY;
    }

    public void setTG_OPT_DAY(String TG_OPT_DAY) {
        this.TG_OPT_DAY = TG_OPT_DAY;
    }

    public String getVAL_AT_PROC() {
        return VAL_AT_PROC;
    }

    public void setVAL_AT_PROC(String VAL_AT_PROC) {
        this.VAL_AT_PROC = VAL_AT_PROC;
    }

    public String getMAX_REJ_PERM() {
        return MAX_REJ_PERM;
    }

    public void setMAX_REJ_PERM(String MAX_REJ_PERM) {
        this.MAX_REJ_PERM = MAX_REJ_PERM;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }
    
    
}
