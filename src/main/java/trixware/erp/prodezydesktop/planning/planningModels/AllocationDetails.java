/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planning.planningModels;

import java.util.Date;

/**
 *
 * @author Rajesh
 */
public class AllocationDetails {
        
        private double assigned_qty = 0.0,     bookedtime ;
        private Date fromdatetime   ,     todateime   ;
        private int shiftid      ;

        public double getAssigned_qty () {
            return assigned_qty;
        }

        public void setAssigned_qty ( double assigned_qty ) {
            this.assigned_qty = assigned_qty;
        }

        public double getBookedtime () {
            return bookedtime;
        }

        public void setBookedtime ( double bookedtime ) {
            this.bookedtime = bookedtime;
        }

        public Date getFromdatetime () {
            return fromdatetime;
        }

        public void setFromdatetime ( Date fromdatetime ) {
            this.fromdatetime = fromdatetime;
        }

        public Date getTodateime () {
            return todateime;
        }

        public void setTodateime ( Date todateime ) {
            this.todateime = todateime;
        }

        public int getShiftid () {
            return shiftid;
        }

        public void setShiftid ( int shiftid ) {
            this.shiftid = shiftid;
        }
    
}
