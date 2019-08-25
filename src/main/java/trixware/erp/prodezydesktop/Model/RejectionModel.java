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
public class RejectionModel {
    
    
    int totalRejection ;
    int rejectionForReason ;
    int rejectionReasonId ;
    String rejectionReasonStr ;

  

    public int getTotalRejection () {
        return totalRejection;
    }

    public void setTotalRejection ( int totalRejection ) {
        this.totalRejection = totalRejection;
    }

    public int getRejectionForReason () {
        return rejectionForReason;
    }

    public void setRejectionForReason ( int rejectionForReason ) {
        this.rejectionForReason = rejectionForReason;
    }

    public int getRejectionReasonId () {
        return rejectionReasonId;
    }

    public void setRejectionReasonId ( int rejectionReasonId ) {
        this.rejectionReasonId = rejectionReasonId;
    }

    public String getRejectionReasonStr () {
        return rejectionReasonStr;
    }

    public void setRejectionReasonStr ( String rejectionReasonStr ) {
        this.rejectionReasonStr = rejectionReasonStr;
    }
    
    
    
}
