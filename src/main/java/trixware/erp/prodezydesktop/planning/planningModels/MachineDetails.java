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
public class MachineDetails {
      
        private int machineid;
        private String machinename ;

        public int getMachineid () {
            return machineid;
        }

        public void setMachineid ( int machineid ) {
            this.machineid = machineid;
        }

        public String getMachinename () {
            return machinename;
        }

        public void setMachinename ( String machinename ) {
            this.machinename = machinename;
        }

}
