/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Rajesh
 */
public class UID_Machine {

    String secureKey;
    String ipStr, macId, licenseeName, installationDate, subscriptionType, applicationType ; 
    
    
    public String getInstallationDate () {

        return installationDate;
    }
    
    public void setInstallationDate( String _installationDate){
        
        this.installationDate = _installationDate ;
    }

    public String getMachineIP () {

        InetAddress ip = null;
        try {

            ip = InetAddress.getLocalHost ();

        } catch ( UnknownHostException e ) {

            e.printStackTrace ();

        }

        ipStr = ip.getHostAddress ();
        return ipStr ;
    }

    public String getMACID () {

        InetAddress ip;
        StringBuilder sb = null ;
        try {

            ip = InetAddress.getLocalHost ();
            
            NetworkInterface network = NetworkInterface.getByInetAddress ( ip );

            byte[] mac = network.getHardwareAddress ();

            System.out.print ( "Current MAC address : " );

            sb = new StringBuilder ();
            for ( int i = 0 ; i < mac.length ; i ++ ) {
                sb.append ( String.format ( "%02X%s" , mac[ i ] , ( i < mac.length - 1 ) ? "-" : "" ) );
            }
            System.out.println ( sb.toString () );

        }  catch ( SocketException e ) {

            e.printStackTrace ();

        } catch ( UnknownHostException e ) {

            e.printStackTrace ();

        }

        macId = sb.toString() ;
        
        return macId ;
    }

    public String getLicenseeName () {

        return this.licenseeName ;
    }
    
    public void setLicenseeName( String _licenseeName ){
        this.licenseeName =  _licenseeName ;       
    }

    public String getTypeOfSubscription () {
        // Yearly
        // Half_yearly
        // Quarterly 
        // Monthly
        subscriptionType = "Monthly";
        
        return subscriptionType;
    }

    public String getTypeOfApplication () {
        // Enterprize
        // Advance
        // Basic
        applicationType = "Enterprize" ;
        
        return applicationType;
    }
    
    
    
    public String createUniqeKey(){
        
        getMachineIP ();
        getMACID ();
        getLicenseeName();
        getTypeOfSubscription ();
        getTypeOfApplication ();
        
        return ipStr +"_"+ macId+"_"+licenseeName+"_"+ installationDate+"_"+ subscriptionType+"_"+ applicationType ;
    }
    
}
