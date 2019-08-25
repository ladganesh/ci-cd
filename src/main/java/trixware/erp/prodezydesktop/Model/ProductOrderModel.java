/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package trixware.erp.prodezydesktop.Model;

import java.util.ArrayList;
import trixware.erp.prodezydesktop.Model.DeliveryScheduleModel ;
/**
 *
 * @author Rajesh
 */
public class ProductOrderModel {


    private int part_id ;
    private String partName_Code , partQty, edd, rate, unit ;
    ArrayList<  ArrayList<DeliveryScheduleModel>  > shcdListForOrder = null ;
    
    
    public ProductOrderModel () {
        shcdListForOrder  = new ArrayList<  ArrayList<DeliveryScheduleModel>  >() ;
    }

    public ProductOrderModel ( int part_id , String partName_Code , String partQty , String edd , String rate , String unit ) {
        this.part_id = part_id;
        this.partName_Code = partName_Code;
        this.partQty = partQty;
        this.edd = edd;
        this.rate = rate;
        this.unit = unit;
        shcdListForOrder  = new ArrayList<  ArrayList<DeliveryScheduleModel>  >() ;
    }

    public int getPart_id () {
        return this.part_id;
    }

    public void setPart_id ( int part_id ) {
        
        this.part_id = part_id;
    }

    public String getPartName_Code () {
        return this.partName_Code;
    }

    public void setPartName_Code ( String partName_Code ) {
        this.partName_Code = partName_Code;
    }

    public String getPartQty () {
        return partQty;
    }

    public void setPartQty ( String partQty ) {
        this.partQty = partQty;
    }

    public String getEdd () {
        return edd;
    }

    public void setEdd ( String edd ) {
        this.edd = edd;
    }

    public String getRate () {
        return rate;
    }

    public void setRate ( String rate ) {
        this.rate = rate;
    }

    public String getUnit () {
        return unit;
    }

    public void setUnit ( String unit ) {
        this.unit = unit;
    }
    
    public void setOrderSchedule( ArrayList< DeliveryScheduleModel  > schedule  ){
        shcdListForOrder.add( schedule ) ;
    }
    
    public ArrayList< DeliveryScheduleModel  > getOrderScheduleForItem( int index  ){
        return shcdListForOrder.get( index  ) ;
    }

    public ArrayList<ArrayList<DeliveryScheduleModel>> getOrderFullSchedule(  ){
        return shcdListForOrder ;
    }    
    
   public String getPartOrderValue(){
       
        int qty = Integer.parseInt(  getPartQty () )  ;
        double rate = Double.parseDouble(  getRate ())  ;
       
        
       
       return String.valueOf(  qty*rate ) ;
   }
    
    
    
}
