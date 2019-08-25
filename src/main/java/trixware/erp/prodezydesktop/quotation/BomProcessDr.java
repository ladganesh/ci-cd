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
public class BomProcessDr {

    private int processId;
    private String processName;
    private String processCost;
    private String isOtherExp;
    private String batchQuantity;
    private String processUnit;    
    private String cycleCount;
    private String basicRate;
    private String setupTime;
    private String setupCost;
    private String fixturingCost;
    private String inspectionCost;
    private String localTaransCost;
    private String handlingCost;
    private String rejPercentage;
    private String rejPercentageCost;

    public String getRejPercentageCost() {
        return rejPercentageCost;
    }

    public void setRejPercentageCost(String rejPercentageCost) {
        this.rejPercentageCost = rejPercentageCost;
    }
    
    public void setIsOtherExp(String isOtherExp) {
        this.isOtherExp = isOtherExp;
    }

    public void setBatchQuantity(String batchQuantity) {
        this.batchQuantity = batchQuantity;
    }

    public void setProcessUnit(String processUnit) {
        this.processUnit = processUnit;
    }

    public void setCycleCount(String cycleCount) {
        this.cycleCount = cycleCount;
    }

    public void setBasicRate(String basicRate) {
        this.basicRate = basicRate;
    }

    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    public void setSetupCost(String setupCost) {
        this.setupCost = setupCost;
    }

    public void setFixturingCost(String fixturingCost) {
        this.fixturingCost = fixturingCost;
    }

    public void setInspectionCost(String inspectionCost) {
        this.inspectionCost = inspectionCost;
    }

    public void setLocalTaransCost(String localTaransCost) {
        this.localTaransCost = localTaransCost;
    }

    public void setHandlingCost(String handlingCost) {
        this.handlingCost = handlingCost;
    }

    public void setRejPercentage(String rejPercentage) {
        this.rejPercentage = rejPercentage;
    }
 

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getIsOtherExp() {
        return isOtherExp;
    }

    public String getBatchQuantity() {
        return batchQuantity;
    }

    public String getProcessUnit() {
        return processUnit;
    }

    public String getCycleCount() {
        return cycleCount;
    }

    public String getBasicRate() {
        return basicRate;
    }

    public String getSetupTime() {
        return setupTime;
    }

    public String getSetupCost() {
        return setupCost;
    }

    public String getFixturingCost() {
        return fixturingCost;
    }

    public String getInspectionCost() {
        return inspectionCost;
    }

    public String getLocalTaransCost() {
        return localTaransCost;
    }

    public String getHandlingCost() {
        return handlingCost;
    }

    public String getRejPercentage() {
        return rejPercentage;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public void setProcessCost(String processCost) {
        this.processCost = processCost;
    }

    public int getProcessId() {
        return processId;
    }

    public String getProcessName() {
        return processName;
    }

    public String getProcessCost() {
        return processCost;
    }
    
}
