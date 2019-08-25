/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planning.planningModels;

/**
 *
 * @author Rajesh
 */
public class ProcDetails {


           private  int processid ;
            private String processname ;
            private double cycletime,   maxtime,      bookedtime,      availabletime  ;

        public int getProcessid () {
            return processid;
        }

        public void setProcessid ( int processid ) {
            this.processid = processid;
        }

        public String getProcessname () {
            return processname;
        }

        public void setProcessname ( String processname ) {
            this.processname = processname;
        }

        public double getCycletime () {
            return cycletime;
        }

        public void setCycletime ( double cycletime ) {
            this.cycletime = cycletime;
        }

        public double getMaxtime () {
            return maxtime;
        }

        public void setMaxtime ( double maxtime ) {
            this.maxtime = maxtime;
        }

        public double getBookedtime () {
            return bookedtime;
        }

        public void setBookedtime ( double bookedtime ) {
            this.bookedtime = bookedtime;
        }

        public double getAvailabletime () {
            return availabletime;
        }

        public void setAvailabletime ( double availabletime ) {
            this.availabletime = availabletime;
        }
            
            
    
    
}
