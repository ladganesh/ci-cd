/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

/**
 *
 * @author Rajesh
 */
public class RMBatchDR {

    public RMBatchDR () {
    }

    public RMBatchDR ( int MC_ID ,  int _RMID , String MC_NAME , int _FGQty , String avlQty  , String rmCode) {
        this.BTC_ID = MC_ID;
        this.BTC_NAME = MC_NAME;
        this.RMID = _RMID ;
        this.FGQty = _FGQty ;
        this.availbleQty = avlQty ;
        this.RMCODE = rmCode ;
        
    }
    
     public RMBatchDR ( int MC_ID ,   String MC_NAME ,   int _RMID , int _FGQty , String avlQty , double _convertRate , int FGQtyCnt ) {
        this.BTC_ID = MC_ID;
        this.BTC_NAME = MC_NAME;
        this.RMID = _RMID ;
        this.FGQty = _FGQty ;
        this.availbleQty = avlQty ;
        this._convertRate = _convertRate;
        this.FGQtyCnt = FGQtyCnt;
        
    }
      
      public RMBatchDR ( int MC_ID ,   String MC_NAME ,   int _RMID , int _FGQty , String avlQty ) {
        this.BTC_ID = MC_ID;
        this.BTC_NAME = MC_NAME;
        this.RMID = _RMID ;
        this.FGQty = _FGQty ;
        this.availbleQty = avlQty ;
      }
    public RMBatchDR ( int MC_ID ,   String MC_NAME ,   int _RMID ,  String avlQty  ) {
        this.BTC_ID = MC_ID;
        this.BTC_NAME = MC_NAME;
        this.RMID = _RMID ;
        this.availbleQty = avlQty ;
        
    }

    public int getBTC_ID () {
        return BTC_ID;
    }

    public void setBTC_ID ( int BTC_ID ) {
        this.BTC_ID = BTC_ID;
    }

    public String getBTC_NAME () {
        return BTC_NAME;
    }

    public void setBTC_NAME ( String BTC_NAME ) {
        this.BTC_NAME = BTC_NAME;
    }

    public int getRMID () {
        return RMID;
    }

    public void setRMID ( int RMID ) {
        this.RMID = RMID;
    }

    public String getRMCODE () {
        return RMCODE;
    }

    public void setRMCODE ( String RMCODE ) {
        this.RMCODE = RMCODE;
    }

    public int getFGQty () {
        return FGQty;
    }

    public void setFGQty ( int FGQty ) {
        this.FGQty = FGQty;
    }

    public String getAvailbleQty () {
        return availbleQty;
    }

    public void setAvailbleQty ( String availbleQty ) {
        this.availbleQty = availbleQty;
    }

    public int BTC_ID;
    public String BTC_NAME;
    public int RMID ;
    public String RMCODE ;
    public int FGQty ;
    public String availbleQty ;
    public Double _convertRate;
    public int FGQtyCnt;

    public Double getConversion_value() {
        return _convertRate;
    }

    public void setConversion_value(Double _convertRate) {
        this._convertRate = _convertRate;
    }

    public int getBatch_qty_cnt() {
        return FGQtyCnt;
    }

    public void setBatch_qty_cnt(int FGQtyCnt) {
        this.FGQtyCnt = FGQtyCnt;
    }
}

