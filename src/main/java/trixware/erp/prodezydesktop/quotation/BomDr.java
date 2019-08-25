/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation;

import java.util.ArrayList;

/**
 *
 * @author Akshay
 */
public class BomDr {
    private String srNo;
    private String partNo;
    private String drwNo;
    private String drwRevNo;
    private String drwRevDate;
    private String category;
    private String scope;
    private String qtyPerAssembly;
    private String materialAsPerDrg;
    private String equvaentGrade;
    private String spec;
    private String diameter;
    private String csArea;
    private String length;
    private String density;
    private String wt;
    private String basicRate;
    private String transport;
    private String packaging;
    private String inspection;
    private String totalRmLanded;
    private String totalRmCost;
    private String stdBO;    
    private String totalBomCost;
    private String icc;
    private String iccMonth;
    private String iccCost;
    private String thickness;
    private String width;
    private String inDiam;
    private String outDiam;
    private String volume;
    private String rateFluctPercent;
    private String rateFluctCost;
    private String otherExp;
    private String taxesPercent;
    private String taxesCost;
    private String scrapRate;
    private String fcWeight;
    private String scStatus;
    private String scRecovery;
    private String scRecoveryPercent;
    private String scWeight;
    private String isPurchasedMaster;
    private String purchasedCategory;
    private String purchasedSupplier;
    
    private ArrayList<BomProcessDr> bomProcess;

    public void setIccMonth(String iccMonth) {
        this.iccMonth = iccMonth;
    }

    public void setIccCost(String iccCost) {
        this.iccCost = iccCost;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setInDiam(String inDiam) {
        this.inDiam = inDiam;
    }

    public void setOutDiam(String outDiam) {
        this.outDiam = outDiam;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setRateFluctPercent(String rateFluctPercent) {
        this.rateFluctPercent = rateFluctPercent;
    }

    public void setRateFluctCost(String rateFluctCost) {
        this.rateFluctCost = rateFluctCost;
    }

    public void setOtherExp(String otherExp) {
        this.otherExp = otherExp;
    }

    public void setTaxesPercent(String taxesPercent) {
        this.taxesPercent = taxesPercent;
    }

    public void setTaxesCost(String taxesCost) {
        this.taxesCost = taxesCost;
    }

    public void setScrapRate(String scrapRate) {
        this.scrapRate = scrapRate;
    }

    public void setFcWeight(String fcWeight) {
        this.fcWeight = fcWeight;
    }

    public void setScStatus(String scStatus) {
        this.scStatus = scStatus;
    }

    public void setScRecovery(String scRecovery) {
        this.scRecovery = scRecovery;
    }

    public void setScRecoveryPercent(String scRecoveryPercent) {
        this.scRecoveryPercent = scRecoveryPercent;
    }

    public void setScWeight(String scWeight) {
        this.scWeight = scWeight;
    }

    public void setIsPurchasedMaster(String isPurchasedMaster) {
        this.isPurchasedMaster = isPurchasedMaster;
    }

    public void setPurchasedCategory(String purchasedCategory) {
        this.purchasedCategory = purchasedCategory;
    }

    public void setPurchasedSupplier(String purchasedSupplier) {
        this.purchasedSupplier = purchasedSupplier;
    }

    public String getIccMonth() {
        return iccMonth;
    }

    public String getIccCost() {
        return iccCost;
    }

    public String getThickness() {
        return thickness;
    }

    public String getWidth() {
        return width;
    }

    public String getInDiam() {
        return inDiam;
    }

    public String getOutDiam() {
        return outDiam;
    }

    public String getVolume() {
        return volume;
    }

    public String getRateFluctPercent() {
        return rateFluctPercent;
    }

    public String getRateFluctCost() {
        return rateFluctCost;
    }

    public String getOtherExp() {
        return otherExp;
    }

    public String getTaxesPercent() {
        return taxesPercent;
    }

    public String getTaxesCost() {
        return taxesCost;
    }

    public String getScrapRate() {
        return scrapRate;
    }

    public String getFcWeight() {
        return fcWeight;
    }

    public String getScStatus() {
        return scStatus;
    }

    public String getScRecovery() {
        return scRecovery;
    }

    public String getScRecoveryPercent() {
        return scRecoveryPercent;
    }

    public String getScWeight() {
        return scWeight;
    }

    public String getIsPurchasedMaster() {
        return isPurchasedMaster;
    }

    public String getPurchasedCategory() {
        return purchasedCategory;
    }

    public String getPurchasedSupplier() {
        return purchasedSupplier;
    }

    public void setStdBO(String stdBO) {
        this.stdBO = stdBO;
    }    

    public void setTotalBomCost(String totalBomCost) {
        this.totalBomCost = totalBomCost;
    }

    public String getStdBO() {
        return stdBO;
    }

    public String getTotalBomCost() {
        return totalBomCost;
    }
    
    public void setBomProcess(ArrayList<BomProcessDr> bomProcess) {
        this.bomProcess = bomProcess;
    }

    public ArrayList<BomProcessDr> getBomProcess() {
        return bomProcess;
    }
    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public void setDrwNo(String drwNo) {
        this.drwNo = drwNo;
    }

    public void setDrwRevNo(String drwRevNo) {
        this.drwRevNo = drwRevNo;
    }

    public void setDrwRevDate(String drwRevDate) {
        this.drwRevDate = drwRevDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setQtyPerAssembly(String qtyPerAssembly) {
        this.qtyPerAssembly = qtyPerAssembly;
    }

    public void setMaterialAsPerDrg(String materialAsPerDrg) {
        this.materialAsPerDrg = materialAsPerDrg;
    }

    public void setEquvaentGrade(String equvaentGrade) {
        this.equvaentGrade = equvaentGrade;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public void setCsArea(String csArea) {
        this.csArea = csArea;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public void setWt(String wt) {
        this.wt = wt;
    }

    public void setBasicRate(String basicRate) {
        this.basicRate = basicRate;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public void setTotalRmLanded(String totalRmLanded) {
        this.totalRmLanded = totalRmLanded;
    }

    public void setTotalRmCost(String totalRmCost) {
        this.totalRmCost = totalRmCost;
    }

    public void setIcc(String icc) {
        this.icc = icc;
    }

    public String getSrNo() {
        return srNo;
    }

    public String getPartNo() {
        return partNo;
    }

    public String getDrwNo() {
        return drwNo;
    }

    public String getDrwRevNo() {
        return drwRevNo;
    }

    public String getDrwRevDate() {
        return drwRevDate;
    }

    public String getCategory() {
        return category;
    }

    public String getScope() {
        return scope;
    }

    public String getQtyPerAssembly() {
        return qtyPerAssembly;
    }

    public String getMaterialAsPerDrg() {
        return materialAsPerDrg;
    }

    public String getEquvaentGrade() {
        return equvaentGrade;
    }

    public String getSpec() {
        return spec;
    }

    public String getDiameter() {
        return diameter;
    }

    public String getCsArea() {
        return csArea;
    }

    public String getLength() {
        return length;
    }

    public String getDensity() {
        return density;
    }

    public String getWt() {
        return wt;
    }

    public String getBasicRate() {
        return basicRate;
    }

    public String getTransport() {
        return transport;
    }

    public String getPackaging() {
        return packaging;
    }

    public String getInspection() {
        return inspection;
    }

    public String getTotalRmLanded() {
        return totalRmLanded;
    }

    public String getTotalRmCost() {
        return totalRmCost;
    }

    public String getIcc() {
        return icc;
    }    
}
