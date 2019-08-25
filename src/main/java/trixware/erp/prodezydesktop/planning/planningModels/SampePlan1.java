/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planning.planningModels;

import java.util.Date;

/*
 partid     partname    reqqty(unit)      prodqty(unit)
              processid    processname   cycletime(min)   maxtime(min)       bookedtime(min)      availabletime(min) = (maxtime-bookedtime)       
                       machineid   machinename     
                                   assigned_qty(unit)     bookedtime(min) = cycletime*assignedqty         fromdatetime        todateime       shiftid     
                       
 */


/**
 *
 * @author Rajesh
 */
public class SampePlan1 {
            
    private int partid ;
    private String partname;
    private double reqqty = 0.0   ,  prodqty = 0.0 ;
    private ProcDetails process ;
    private MachineDetails machine ;
    private AllocationDetails allocation ;
    

    public int getPartid () {
        return partid;
    }

    public void setPartid ( int partid ) {
        this.partid = partid;
    }

    public String getPartname () {
        return partname;
    }

    public void setPartname ( String partname ) {
        this.partname = partname;
    }

    public double getReqqty () {
        return reqqty;
    }

    public void setReqqty ( double reqqty ) {
        this.reqqty = reqqty;
    }

    public double getProdqty () {
        return prodqty;
    }

    public void setProdqty ( double prodqty ) {
        this.prodqty = prodqty;
    }

    public ProcDetails getProcess () {
                
        return process;
    }

    public void setProcess ( ProcDetails _process ) {
        //this.process = _process;
        process = new ProcDetails ();
        
        this.process.setProcessid (  _process.getProcessid () );
        this.process.setProcessname ( _process.getProcessname () );
        this.process.setCycletime ( _process.getCycletime () );
        this.process.setMaxtime (  _process.getMaxtime () );
        this.process.setBookedtime ( _process.getBookedtime () ) ;
        this.process.setAvailabletime (_process.getAvailabletime () ) ;
    }

    public MachineDetails getMachine () {
        return machine;
    }

    public void setMachine ( MachineDetails _machine ) {
        //this.machine = machine;
        machine = new MachineDetails ();
        this.machine.setMachineid ( _machine.getMachineid () ) ;
        this.machine.setMachinename ( _machine.getMachinename ()  ) ;
    }

    public AllocationDetails getAllocation () {
        return allocation;
    }

    public void setAllocation ( AllocationDetails _allocation ) {
        //this.allocation = allocation;
        allocation = new AllocationDetails ();
        this.allocation.setAssigned_qty ( _allocation.getAssigned_qty()   );
        this.allocation.setBookedtime ( _allocation.getBookedtime () );
        this.allocation.setFromdatetime ( _allocation.getFromdatetime () );
        this.allocation.setTodateime ( _allocation.getTodateime () );
        this.allocation.setShiftid ( _allocation.getShiftid () );
        
    }
}
    

    

            
    



