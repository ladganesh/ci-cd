/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class RMProcessPanel extends javax.swing.JPanel {

    String resource = "", resourceType = "";

    String RM_ID,RM_TYPE,RM_NAME,RM_CTG,RM_RATE,RM_CLASS,RMM_UOM_ID,length,width,thickness,density,RM_CODE,in_diameter,wall,out_diameter,diameter,shape;
    
    String transport, taxes, other, inspection, rateFlu, scrap, ICC ; 
    
    ArrayList<String[]> rmData ;
    DecimalFormat df = new DecimalFormat("#.####");
    double taxAmt;
    double iccAmt;
    double rateFluctAmt;

    /**
     * Creates new form Raw_Material
     */
    public RMProcessPanel () {
        initComponents ();
       
        resourceType = "Masters";
        resource = "Raw_Material";

        jTextField1.addKeyListener ( k );
        jTextField9.addKeyListener ( k );
        jTextField10.addKeyListener ( k );
        jTextField11.addKeyListener ( k );
        jTextField12.addKeyListener ( k );
        jTextField12.addKeyListener ( keyList );
        jTextField13.addKeyListener ( k );
        jTextField14.addKeyListener ( k );
        jTextField1.addKeyListener ( k );
        
        jTextField15.addKeyListener ( k );
        jTextField16.addKeyListener ( k );
      //  equivalentGrade.addKeyListener ( k );
        jTextField18.addKeyListener ( k );
        jTextField19.addKeyListener ( k );
        jTextField20.addKeyListener ( k );
        jTextField17.addKeyListener(k);
        jTextField21.addKeyListener ( k );
        jTextField22.addKeyListener ( k );
        jTextField23.addKeyListener ( k );
       
        jTextField1.addFocusListener ( f2 );
        jTextField9.addFocusListener ( f2 );
        jTextField10.addFocusListener ( f2 );
        jTextField11.addFocusListener ( f2 );
        jTextField12.addFocusListener ( f2 );
        jTextField20.addKeyListener(keyListner);
        jTextField17.addKeyListener(keyListner);
        jTextField5.addKeyListener(keyListner);
        jTextField6.addKeyListener(keyListner); 
        jTextField8.addKeyListener(keyListner);
        jTextField7.addKeyListener(keyListner); 
        jTextField9.addKeyListener(keyListner);
        jTextField14.addKeyListener(keyListner);
        jTextField1.addKeyListener ( keyListner );

        jTextField24.addKeyListener(keyListner);
        jTextField15.addKeyListener(keyListner);
        jTextField11.addKeyListener(keyListner);
        jTextField10.addKeyListener(keyListner);
        jTextField16.addKeyListener(keyListner);
        
        jTextField5.addFocusListener ( f2 );

        jTextField13.addKeyListener(keyListner);
        jTextField18.addKeyListener(keyListner);
        jTextField21.addKeyListener(keyListner);
        jTextField19.addKeyListener(keyListner);
        jTextField23.addKeyListener(keyListner);
        jTextField22.addKeyListener(keyListner);
        jScrollPane1.setVisible ( false);
        jLabel19.setVisible(false );
        componentVisibility();
        setData();
        loadShapeParameter();
        loadCheackBox();
        rawMaterialCalc();  
        rawMatAdditionalTx();
    }
    
    public RMProcessPanel(ArrayList<String[]> rmData)
    {
        initComponents ();

        resourceType = "Masters";
        resource = "Raw_Material";

        jTextField1.addKeyListener ( k );
        jTextField9.addKeyListener ( k );
        jTextField10.addKeyListener ( k );
        jTextField11.addKeyListener ( k );
        jTextField12.addKeyListener ( k );
        jTextField12.addKeyListener ( keyList );
        jTextField13.addKeyListener ( k );
        jTextField14.addKeyListener ( k );
        jTextField1.addKeyListener ( k );

        jTextField15.addKeyListener ( k );
        jTextField16.addKeyListener ( k );
      //  equivalentGrade.addKeyListener ( k );
        jTextField18.addKeyListener ( k );
        jTextField19.addKeyListener ( k );
        jTextField20.addKeyListener ( k );
        jTextField17.addKeyListener(k);
        jTextField21.addKeyListener ( k );
        jTextField22.addKeyListener ( k );
        jTextField23.addKeyListener ( k );
       
        jTextField1.addFocusListener ( f2 );
        jTextField9.addFocusListener ( f2 );
        jTextField10.addFocusListener ( f2 );
        jTextField11.addFocusListener ( f2 );
        jTextField12.addFocusListener ( f2 );
        
        jTextField20.addKeyListener(keyListner);
        jTextField17.addKeyListener(keyListner);
        jTextField13.addKeyListener(keyListner);
        jTextField18.addKeyListener(keyListner);
        jTextField21.addKeyListener(keyListner);
        jTextField19.addKeyListener(keyListner);
        jTextField23.addKeyListener(keyListner);
        jTextField22.addKeyListener(keyListner);
        jTextField5.addKeyListener(keyListner);
        jTextField6.addKeyListener(keyListner); 
        jTextField8.addKeyListener(keyListner);
        jTextField7.addKeyListener(keyListner); 
        jTextField9.addKeyListener(keyListner);
        jTextField14.addKeyListener(keyListner);
        jTextField1.addKeyListener ( keyListner );

        jTextField24.addKeyListener(keyListner);
        jTextField15.addKeyListener(keyListner);
        jTextField11.addKeyListener(keyListner);
        jTextField10.addKeyListener(keyListner);
        jTextField16.addKeyListener(keyListner);

        jTextField5.addFocusListener ( f2 );

        jScrollPane1.setVisible ( false);
        jLabel19.setVisible(false );
        componentVisibility();
        setData();
        loadShapeParameter();
        loadCheackBox();         
        rawMaterialCalc();  
        rawMatAdditionalTx();
    }
    public String getFCWeight()
    {
        return jTextField5.getText();
    }
    public void setFCWeight(String fcweight)
    {
        jTextField5.setText(fcweight);
    }
    
    public void setIccMonth(String month)
    {
        jComboBox2.setSelectedItem(month);
    }
    public String getIccMonth()
    {
        return jComboBox2.getSelectedItem().toString();
    }
    public double getTaxAmt()
    {
        return taxAmt;
    }
    public double getFluctAmt()
    {
        return rateFluctAmt;
    }
    public double getIccAmt()
    {
        return iccAmt;
    }    
    
    public String getUOM(String uid)
    {
        String uom="";
        String addEmpAPICall1 = "unitmeasedit?UOM_ID=" + uid;
        String result3 = WebAPITester.prepareWebCall(addEmpAPICall1, StaticValues.getHeader(), "");
        if (!result3.contains("not found")) {
            if (result3 != null) {
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                JSONObject jObject1 = new JSONObject(result3);
                Iterator<?> keys1 = jObject1.keys();

                while (keys1.hasNext()) {
                    String key1 = (String) keys1.next();
                    Object value1 = jObject1.get(key1);
                    map1.put(key1, value1);
                }

                JSONArray records1 = (JSONArray) map1.get("data");

                JSONObject um = null;//(JSONObject) map1.get("data");//st1.getJSONObject("CUSTOMER_NAME");
                um = records1.getJSONObject(0);
                uom = um.get("UOM").toString();
            }
        }
        return uom;
    }
    
    public RMProcessPanel ( ArrayList<String[]> rawMaterial,   double qty ,ArrayList<String[]> rawMaterialDetails ) {
        initComponents ();
       
        for ( int i = 0 ; i < rawMaterial.size(); i ++ ) {
            jComboBox1.addItem ( rawMaterial.get(i)[1]);
        }
        
        if(rawMaterialDetails!=null)
        {
            for(String[] s:rawMaterialDetails)
            {
                setRM_ID(s[0]);
                setRM_NAME(s[1]);
                setRM_RATE(s[2]);
                setRmUnit(s[3]);
//                setRM_CODE(s[4]);
                setRMType(s[5]);
                setRM_CTG(s[6]);
                setShape(s[7]);
                setLength(s[8]);
                setThickness(s[9]);
                setDiameter(s[10]);
//                setWall(s[11]);
                setDensity(s[12]);
                setWidth1(s[13]);
                setIn_diameter(s[14]);
                setOut_diameter(s[15]);
                setVolume(s[16]);
                setWeight(s[17]);
                setTransport(s[18]);
                setInspection(s[19]);
                setRateFlu(s[20]);
                setScrap(s[21]);
                setICC(s[22]);
                setOther(s[23]);
                setTaxes(s[24]);
                setTotal(s[25]);
                setRMTotal(s[26]);
                setFCWeight(s[27]);
                setSCStatus(s[28]);
                setSCRecovery(s[29]);
                setSCRecoveryPerce(s[30]);
                setScWeight(s[31]);
                setRMPackaging(s[33]);
                setIccMonth(s[34]);
                //    setRMTotal(s[26]);
            }
        }
        jComboBox1.addActionListener(  selectRM  ) ;
        jComboBox3.addActionListener(actionListner);
        jComboBox2.addActionListener(actionListner1);
        rmData = rawMaterial ;
        jTextField5.addKeyListener(keyListner);
        jTextField5.addFocusListener ( f2 );
        
        jTextField1.addKeyListener ( k );
        jTextField9.addKeyListener ( k );
        jTextField10.addKeyListener ( k );
        jTextField11.addKeyListener ( k );
        jTextField12.addKeyListener ( k );
        jTextField12.addKeyListener ( keyList );
        jTextField13.addKeyListener ( k );
        jTextField14.addKeyListener ( k );
        jTextField1.addKeyListener ( k );

        jTextField15.addKeyListener ( k );
        jTextField16.addKeyListener ( k );
        //equivalentGrade.addKeyListener ( k );
        jTextField18.addKeyListener ( k );
        jTextField19.addKeyListener ( k );
        jTextField20.addKeyListener ( k );
        jTextField17.addKeyListener(k);
        jTextField21.addKeyListener ( k );
        jTextField22.addKeyListener ( k );
        jTextField23.addKeyListener ( k );
       
        jTextField1.addFocusListener ( f2 );
        jTextField9.addFocusListener ( f2 );
        jTextField10.addFocusListener ( f2 );
        jTextField11.addFocusListener ( f2 );
        jTextField12.addFocusListener ( f2 );

        jTextField20.addKeyListener(keyListner);
        jTextField17.addKeyListener(keyListner);
        jTextField13.addKeyListener(keyListner);
        jTextField18.addKeyListener(keyListner);
        jTextField21.addKeyListener(keyListner);
        jTextField19.addKeyListener(keyListner);
        jTextField23.addKeyListener(keyListner);
        jTextField22.addKeyListener(keyListner);

        jTextField6.addKeyListener(keyListner); 
        jTextField8.addKeyListener(keyListner);
        jTextField7.addKeyListener(keyListner); 
        jTextField9.addKeyListener(keyListner);
        jTextField14.addKeyListener(keyListner);
        jTextField1.addKeyListener ( keyListner );

        jTextField24.addKeyListener(keyListner);
        jTextField15.addKeyListener(keyListner);
        jTextField11.addKeyListener(keyListner);
        jTextField10.addKeyListener(keyListner);
        jTextField16.addKeyListener(keyListner);

        jScrollPane1.setVisible ( false);
        jLabel19.setVisible(false );
        int selectedIndext = jComboBox1.getSelectedIndex();
        String[] data = rmData.get(selectedIndext);
        setRMM_UOM_ID(data[14]);
        System.out.println(rmData.size());
        componentVisibility();
//        setData();
        loadShapeParameter();
        loadCheackBox();  
        rawMaterialCalc();  
        rawMatAdditionalTx();
    }
    
    public void loadShapeParameter()
    {
        String shape1=jComboBox3.getSelectedItem().toString();
        jLabel26.setText("Hight");
        jLabel27.setText("Radius");
        
        switch(shape1)
        {
            case "Round":
                //Thickness
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");
                //width
                jLabel16.setVisible(false);
                jTextField10.setVisible(false);
                jTextField10.setText("0.0");

                //Inner Diameter
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);
                jTextField15.setText("0.0");

                //Outer Diameter
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);
                jTextField16.setText("0.0");

                //Wall
                jLabel25.setVisible(false);
                equivalentGrade.setVisible(true);      

                //height
                jTextField24.setVisible(false);
                jLabel26.setVisible(false);
                jTextField24.setText("0.0");

                //radius
                jTextField25.setVisible(false);
                jLabel27.setVisible(false);
                jTextField25.setText("0.0");
                
                //Length

                jTextField9.setVisible(true);
                jLabel9.setVisible(true);
                //Diameter
                jLabel22.setVisible(true);
                jTextField14.setVisible(true);
                
//                jLabel36.setVisible(true);
//                jLabel37.setVisible(false);
//                jLabel39.setVisible(false);
//                jLabel41.setVisible(true);

                break;
            case "Square":
                //Thickness
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");

                //width
                jLabel16.setVisible(true);
                jTextField10.setVisible(true);

                //Inner Diameter
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);
                jTextField15.setText("0.0");

                //Outer Diameter
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);
                jTextField16.setText("0.0");

                //Wall
                jLabel25.setVisible(false);
                equivalentGrade.setVisible(true);      
                
                //Length
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);
                jTextField14.setText("0.0");

                //height
                jTextField24.setVisible(false);
                jLabel26.setVisible(false);
                jTextField24.setText("0.0");

                //radius
                jTextField25.setVisible(false);
                jLabel27.setVisible(false);
                jTextField25.setText("0.0");

            break;
            case "Rectangle":
                //Thickness
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");

                //width
                jLabel16.setVisible(true);
                jTextField10.setVisible(true);

                //Inner Diameter
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);
                jTextField15.setText("0.0");

                //Outer Diameter
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);
                jTextField16.setText("0.0");

                //height
                jLabel26.setText("Height");
                jTextField24.setVisible(true);
                jLabel26.setVisible(true);

                //radius
                jTextField25.setVisible(false);
                jLabel27.setVisible(false);
                jTextField25.setText("0.0");

//
                //Wall
                jLabel25.setVisible(false);
                equivalentGrade.setVisible(true);      
                
                //Length
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);
                jTextField14.setText("0.0");

            break;
            case "Hexagonal":
                //Thickness
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");

                //width
                jLabel16.setVisible(false);
                jTextField10.setVisible(false);
                jTextField10.setText("0.0");

                //Inner Diameter
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);
                jTextField15.setText("0.0");

                //Outer Diameter
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);
                jTextField16.setText("0.0");

                //Wall
                jLabel25.setVisible(false);
                equivalentGrade.setVisible(true);      
                //height
                jTextField24.setVisible(false);
                jTextField24.setText("0.0");
                jLabel26.setVisible(false);
                //radius
                jTextField25.setVisible(false);
                jLabel27.setVisible(false);
                jTextField25.setText("0.0");
                
                //Length
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);
                //Diameter
                jLabel22.setVisible(true);
                jTextField14.setVisible(true);
                
            break;
            case "Sheet":
                //Thickness
                jLabel17.setVisible(true);
                jTextField11.setVisible(true);

                //width
                jLabel16.setVisible(true);
                jTextField10.setVisible(true);

                //Inner Diameter
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);
                jTextField15.setText("0.0");

                //Outer Diameter
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);
                jTextField16.setText("0.0");

                //Wall
                jLabel25.setVisible(false);
                equivalentGrade.setVisible(true);      

                //height
                jTextField24.setVisible(false);
                jLabel26.setVisible(false);
                jTextField24.setText("0.0");
                
                //radius
                jTextField25.setVisible(false);
                jLabel27.setVisible(false);
                jTextField25.setText("0.0");
                
                //Length
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);
 
                //Diameter
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);
                jTextField14.setText("0.0");

            break;
            case "Plate":
                //Thickness
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");

                //width
                jLabel16.setVisible(true);
                jTextField10.setVisible(true);
//                jTextField10.setText("0.0");

                //Inner Diameter
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);
                jTextField15.setText("0.0");

                //Outer Diameter
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);
                jTextField16.setText("0.0");

                //height
                jTextField24.setVisible(true);
                jLabel26.setVisible(true);
//                jTextField24.setText("0.0");

                //radius
                jTextField25.setVisible(false);
                jLabel27.setVisible(false);
                jTextField25.setText("0.0");
                
                //Wall
                jLabel25.setVisible(false);
                equivalentGrade.setVisible(true);      
                
                //Length
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);
//                jTextField9.setText("0.0");

                //Diameter
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);
                jTextField14.setText("0.0");

                break;
            case "Tubular":
                //Thickness
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");

                //width
                jLabel16.setVisible(false);
                jTextField10.setVisible(false);
                jTextField10.setText("0.0");

                //Inner Diameter
                jLabel23.setVisible(true);
                jTextField15.setVisible(true);

                //Outer Diameter
                jLabel24.setVisible(true);
                jTextField16.setVisible(true);

                //height
                jTextField24.setVisible(false);
                jLabel26.setVisible(false);
                jTextField24.setText("0.0");

                //radius
                jTextField25.setVisible(false);
                jLabel27.setVisible(false);
                jTextField25.setText("0.0");
                
                //Wall
                jLabel25.setVisible(false);
                equivalentGrade.setVisible(true);      
                
                //Length
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);
                //Diameter
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);
                jTextField14.setText("0.0");
            break;

            case "Ring":
                //Thickness
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");

                //width
                jLabel16.setVisible(false);
                jTextField10.setVisible(false);
                jTextField10.setText("0.0");

                //Inner Diameter
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);
                jTextField15.setText("0.0");

                //Outer Diameter
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);
                jTextField16.setText("0.0");

                //Wall
                jLabel25.setVisible(false);
                equivalentGrade.setVisible(true);      
                
                //Length
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);
//                jTextField19.setText("0.0");

                //Diameter
                jLabel22.setVisible(true);
                jTextField14.setVisible(true);
//                jTextField14.setText("0.0");

                //height
                jTextField24.setVisible(false);
                jLabel26.setVisible(false);
                jTextField24.setText("0.0");

                //radius
                jTextField25.setVisible(false);
                jLabel27.setVisible(false);
                jTextField25.setText("0.0");

            break;
            case "Pipe":
                //Thickness
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");

                //width
                jLabel16.setVisible(false);
                jTextField10.setVisible(false);
                jTextField10.setText("0.0");

                //Inner Diameter
                jLabel23.setVisible(true);
                jTextField15.setVisible(true);

                //Outer Diameter
                jLabel24.setVisible(true);
                jTextField16.setVisible(true);
//
                //Wall
                jLabel25.setVisible(false);
                equivalentGrade.setVisible(true);      
//                
                //Length
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);
                jTextField14.setText("0.0");

                //height
                jTextField24.setVisible(false);
                jLabel26.setVisible(false);
                jTextField24.setText("0.0");

                //radius
                jTextField25.setVisible(false);
                jLabel27.setVisible(false);
                jTextField25.setText("0.0");
            break;
            default:
            break;
        }
    }
    private void rawMatAdditionalTx(){

        double total=Double.parseDouble(jLabel46.getText());
        if(total!=0)
        {
            double transportCost=Double.parseDouble(jTextField13.getText());
            double taxes1=Double.parseDouble(jTextField18.getText());
            double othExpence=Double.parseDouble(jTextField21.getText());
            double inspCost=Double.parseDouble(jTextField19.getText());
            double rateFluct=Double.parseDouble(jTextField23.getText());
            double scrap1=Double.parseDouble(jTextField22.getText());
            double icc= Double.parseDouble(jTextField20.getText());         
            int iccmonths=Integer.parseInt(jComboBox2.getSelectedItem().toString());
            double packaginForwording=Double.parseDouble(jTextField17.getText()); 
        
            double ActualRM=total;

            double tax=(ActualRM*taxes1)/100;
    //        double iccMo=icc/12;
            icc=(icc/12)*iccmonths;
            double icc1=(ActualRM*icc)/100;
            double gTotal=0.0;

            if(rateFluct!=0.0&&rateFluct!=0);
            {
                gTotal=(ActualRM*rateFluct)/100;
            }    

            double Total=ActualRM+transportCost+tax+othExpence+inspCost+icc1+packaginForwording+gTotal;     

            iccAmt=icc1;
            taxAmt=tax;
            rateFluctAmt=gTotal;

            //        double fTotal=Double.parseDouble(df.format((Total)));

            if (jCheckBox1.isSelected()) {
                double fcweight = Double.parseDouble(jTextField5.getText());
                if (fcweight != 0 && fcweight != 0.0) {
                double rmweight = Double.parseDouble(jLabel42.getText());
//                double rmWeight = rmweight / 1000;
                  double scrapWeight = rmweight - fcweight;

                double scRecovery = Double.parseDouble(jTextField6.getText());

                double scraPercentage = (scrapWeight * scRecovery) / 100;

                double scrapTotal = scraPercentage * Double.parseDouble(jTextField22.getText());

                scrapRate1 = df.format(scrapTotal);

                Total = Total - scrapTotal;
                jTextField7.setText(df.format(scrapWeight));
                jTextField8.setText(df.format(scrapTotal));

            } else {
                jTextField7.setText(df.format(0.0));
                jTextField8.setText(df.format(0.0));
            }
        }
        jLabel48.setText(df.format(Total));                

        }
    }
    private void rawMaterialCalc(){
        
        Double materialDensity=Double.parseDouble(jTextField12.getText());
        if(materialDensity!=0.0){

//        double S=Double.parseDouble(jTextField12.getText());            
        String shapes=jComboBox3.getSelectedItem().toString();
       
        double volume=0.0;
        double total=0.0;
        double Weight = 0.0;
        double length1=0.0;
        double width=0.0;
        double csArea=0.0;

        switch(shapes)
            {
                case "Round":

                    length1=Double.parseDouble(jTextField9.getText());
                    double diameter1=Double.parseDouble(jTextField14.getText());                    
                    double lengt=(length1*100)/1000;
                    double diam=(diameter1*100)/1000;
                    csArea=3.14159*((diam/2)*(diam/2));
                    volume=csArea*lengt;     

                    Weight=(materialDensity)*(volume); 
                    
                    break;
                case "Square":

                    length1=Double.parseDouble(jTextField9.getText());
                    length1=(length1*100)/1000;
                    double width1=Double.parseDouble(jTextField10.getText());
                    double width2=(width1*100)/1000;
                    csArea=width2*width2;
                    volume=csArea*length1;
                    Weight=(materialDensity)*(volume);

                    break;
                case "Rectangle":
                    width=Double.parseDouble(jTextField10.getText());
                    length1=Double.parseDouble(jTextField9.getText());
                    double height=Double.parseDouble(jTextField24.getText());
                    csArea=getInMM(width)*getInMM(height);
                    volume=csArea*getInMM(length1);
                    Weight=(materialDensity)*(volume);
                    
                break;
                case "Hexagonal":
                    length1=Double.parseDouble(jTextField9.getText());
                    double diameter2=Double.parseDouble(jTextField14.getText());                    
                    double lengt1=(length1*100)/1000;
                    double diam1=(diameter2*100)/1000;
                    
                    double rootThree=1.7320508075689;
                    csArea=1.5*rootThree*((diam1/2)*(diam1/2));
                    volume=csArea*lengt1;     
                    Weight=(materialDensity)*(volume);
                
                break;
                case "Sheet":
                    length1=Double.parseDouble(jTextField9.getText());
                    width=Double.parseDouble(jTextField10.getText());
                    double thickness1=Double.parseDouble(jTextField11.getText());                    
                    csArea=getInMM(thickness1)*getInMM(width);
                    volume=csArea*getInMM(length1);
                    Weight=(materialDensity)*(volume);                 
                break;
                case "Plate":
                    width=Double.parseDouble(jTextField10.getText());
                    length1=Double.parseDouble(jTextField9.getText());
                    double height1=Double.parseDouble(jTextField24.getText());
                    csArea=getInMM(width)*getInMM(height1);
                    volume=csArea*getInMM(length1);
                    Weight=(materialDensity)*(volume);
    
                break;
                case "Tubular":
                    length1=Double.parseDouble(jTextField9.getText());
                    double indiam1=Double.parseDouble(jTextField15.getText());
                    double outdiam1=Double.parseDouble(jTextField16.getText());
                    double temp3=(3.14159*((getInMM(outdiam1)/2)*(getInMM(outdiam1)/2)));
                    double temp4=(3.14159*((getInMM(indiam1)/2)*(getInMM(indiam1)/2)));
                    csArea=(temp3-temp4);
                    volume=csArea*getInMM(length1);                    
                    Weight=(materialDensity)*(volume); 
    
                break;
                case "Ring":
                    length1=Double.parseDouble(jTextField9.getText());
                    double diami=Double.parseDouble(jTextField14.getText());                    
                    double lengt2=(length1*100)/1000;
                    double diam12=(diami*100)/1000;
                    csArea=3.14159*((diam12/2)*(diam12/2));
                    volume=csArea*lengt2;     

                    Weight=(materialDensity)*(volume); 
    
                break;
                case "Pipe":
                    length1=Double.parseDouble(jTextField9.getText());
                    double indiam=Double.parseDouble(jTextField15.getText());
                    double outdiam=Double.parseDouble(jTextField16.getText());
                    double temp1=(3.14159*((getInMM(outdiam)/2)*(getInMM(outdiam)/2)));
                    double temp2=(3.14159*((getInMM(indiam)/2)*(getInMM(indiam)/2)));
                    csArea=(temp1-temp2);
                    volume=csArea*getInMM(length1);
                    
                    Weight=(materialDensity)*(volume); 
                    break;
                default:
                break;
            }

        double basicRmCost=Double.parseDouble(jTextField1.getText());        

        double calc=Weight/1000;
        double totalLandedCost=basicRmCost*calc;
           
        setCsArea(csArea+"");
        jLabel43.setText(df.format(volume));
        jLabel42.setText(df.format(calc));
        jLabel46.setText(df.format(totalLandedCost));
            rawMatAdditionalTx();
        }
    }
    public double getInMM(double value)
    {
        double cal=(value*100)/1000;
        return cal;
    }
    ActionListener actionListner=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadShapeParameter();
            rawMaterialCalc();            
        }
    };
    ActionListener actionListner1=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            rawMaterialCalc();  
            rawMatAdditionalTx();
        }
    };    
    ActionListener selectRM = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
          setData();
        }
    };
    public void setData()
    {
        int selectedIndext = jComboBox1.getSelectedIndex();
        String[] data = rmData.get(selectedIndext);

        setRM_ID(data[0]);
        setRM_NAME(data[1]);
        setRM_RATE(data[2]);
//            setRM_CODE ( data[3]);
        setRM_TYPE(data[4]);
        setRM_CTG(data[5]);
        setLength(data[6]);
        setThickness(data[7]);
        setDiameter(data[8]);
//        setWall(data[9]);
        setDensity(data[10]);
        setRMWidth(data[11]);
        setIn_diameter(data[12]);
        setOut_diameter(data[13]);
        setRMM_UOM_ID(data[14]);
        setHight(data[15]);
        setRadius(data[16]);        
        setICC(data[17]);
        setScrapRate(data[18]);
        setScrap(data[18]);
        setTaxes(data[19]);
        setShape(data[20]);
        
    }
    public String scrapRate="0.0";
    public String scrapRate1="";
    public void setScrapRate(String scrapRate)
    {
        this.scrapRate=scrapRate;
    }
    public String getScrapRate()
    {
        return scrapRate;
    }
    public void setScrapRate1(String scrapRate)
    {
        this.scrapRate1=scrapRate;
    }
    public String getScrapRate1()
    {
        return scrapRate1;
    }
    public void setRMPackaging(String packaging)
    {
        jTextField17.setText(packaging);
    }
    public String getRMPackaging()
    {
        return jTextField17.getText();
    }
    public String getRM_ID () {
        return RM_ID;
    }

    public void setRM_ID ( String RM_ID ) {
        this.RM_ID = RM_ID;
    }

    public String getRM_TYPE () {
        return equivalentGrade.getText();
    }

    public void setRM_TYPE ( String RM_TYPE ) {
        jLabel11.setText( RM_TYPE );
        this.RM_TYPE = RM_TYPE;
    }

    public String getRM_NAME () {
        return RM_NAME;
    }

    public void setRM_NAME ( String RM_NAME ) {
        this.RM_NAME = RM_NAME;
        jComboBox1.setSelectedItem(RM_NAME);
    }

    public String getRM_CTG () {
        return RM_CTG;
    }

    public void setRM_CTG ( String RM_CTG ) {
        jLabel12.setText ( RM_CTG );
        this.RM_CTG = RM_CTG;
    }

    public String getRM_RATE () {
        return jTextField1.getText();
    }

    public void setRM_RATE ( String RM_RATE ) {
        this.RM_RATE = RM_RATE;
        jTextField1.setText( RM_RATE ) ;
    }

    public String getRM_CLASS () {
        return RM_CLASS;
    }

    public void setRM_CLASS ( String RM_CLASS ) {
        this.RM_CLASS = RM_CLASS;
    }

    public String getRMM_UOM_ID () {
        return RMM_UOM_ID;
    }

    public void setRMM_UOM_ID ( String RMM_UOM_ID ) {
     
        String umm=getUOM(RMM_UOM_ID);
        jLabel14.setText(umm);
//        jLabel21.setText(umm);
        this.RMM_UOM_ID = RMM_UOM_ID;
    }

    public String getLength () {
        return jTextField9.getText () ;
    }

    public void setLength ( String length ) {
        jTextField9.setText( length) ;
        this.length = length;
    }

    public String getRMWidth () {
        return jTextField10.getText();
    }

    public void setRMWidth ( String width ) {
        jTextField10.setText( width);
        this.width = width;
    }

    public String getThickness () {
        return jTextField11.getText() ;
    }

    public void setThickness ( String thickness ) {
        jTextField11.setText( thickness ) ;
        this.thickness = thickness;
    }

    public String getDensity () {
        return jTextField12.getText();
    }

    public void setDensity ( String density ) {
        jTextField12.setText(  density );
        this.density = density;
    }

//    public String getRM_CODE () {
//        return RM_CODE;
//    }

//    public void setRM_CODE ( String RM_CODE ) {
//        jLabel27.setText( RM_CODE );
//        this.RM_CODE = RM_CODE;
//    }

    public String getIn_diameter () {
        return jTextField15.getText();
    }

    public void setIn_diameter ( String in_diameter ) {
        jTextField15.setText( in_diameter );
        this.in_diameter = in_diameter;
    }

    public String getWall () {
        return  equivalentGrade.getText();
    }

    public void setWall ( String wall ) {
         
        equivalentGrade.setText( wall ) ;
        this.wall = wall;
    }
    public String getScRecovery () {
        return  jTextField8.getText();
    }
    public void setSCRecovery ( String sc ) {    
        jTextField8.setText( sc ) ;
    }
    public String getScWeight () {
        return  jTextField7.getText();
    }
    public void setScWeight ( String sc ) {    
        jTextField7.setText( sc ) ;
    }
    public String getScRecoveryPerc () {
        return  jTextField6.getText();
    }
    public void setSCRecoveryPerce ( String sc ) {    
        jTextField6.setText( sc ) ;
    }
    
    public String getScStatus () {
        if(jCheckBox1.isSelected())
        {
            return  "y";    
        }else{
            return  "n";    
        }
        
    }
    public void setSCStatus ( String sc ) {    
        if(sc.equals("y"))
        {
            jCheckBox1.setSelected(true);
        }else{
            jCheckBox1.setSelected(false);            
        }
        loadCheackBox();
    }
    
    public String getOut_diameter () {
        return jTextField16.getText();
    }

    public void setOut_diameter ( String out_diameter ) {
        jTextField16.setText( out_diameter );
        this.out_diameter = out_diameter;
    }

    public String getDiameter () {
        return jTextField14.getText() ;
    }

    public void setDiameter ( String diameter ) {
                jTextField14.setText( diameter ) ;
        this.diameter = diameter;
    }

    public String getShape () {
        return jComboBox3.getSelectedItem ().toString ();
    }

    public void setShape ( String shape ) {
        this.shape = shape;
        jComboBox3.setSelectedItem(shape);
    }
    public void setHight(String hight)
    {
        jTextField24.setText(hight);       
    }
    public void setRadius(String radius)
    {
        jTextField25.setText(radius);
    }
    public String getRadius()
    {
        return jTextField25.getText();
    }
    public String getHight()
    {
        return jTextField24.getText();
    }
    public ArrayList<String[]> getUOM () {
        return UOM;
    }

    public void setUOM ( ArrayList<String[]> UOM ) {
        this.UOM = UOM;
    }

    public String getRMType () {
        
        return equivalentGrade.getText();
    }

    public void setRMType ( String RMType ) {
        equivalentGrade.setText(RMType);
//        this.RMType = RMType;
    }

    public ArrayList<String[]> getCategory () {
        return category;
    }

    public void setCategory ( ArrayList<String[]> category ) {
        this.category = category;
    }

    public String getTransport () {
        return jTextField13.getText();
    }

    public void setTransport ( String transport ) {
        jTextField13.setText( transport );
        this.transport = transport;
    }

    public String getTaxes () {
        return jTextField18.getText();
    }

    public void setTaxes ( String taxes ) {
        jTextField18.setText( taxes );
        this.taxes = taxes;
    }

    public String getOther () {
        return jTextField21.getText();
    }

    public void setOther ( String other ) {
        jTextField21.setText( other );
        this.other = other;
    }

    public String getInspection () {
        return jTextField19.getText();
    }

    public void setInspection ( String inspection ) {
        jTextField19.setText( inspection );
        this.inspection = inspection;
    }

    public String getRateFlu () {
        return jTextField23.getText();
    }

    public void setRateFlu ( String rateFlu ) {
        jTextField23.setText( rateFlu );
        this.rateFlu = rateFlu;
    }

    public String getScrap () {
        return jTextField22.getText();
    }

    public void setScrap ( String scrap ) {
        jTextField22.setText( scrap );
        this.scrap = scrap;
    }

    public String getICC () {
        return jTextField20.getText();
    }

    public void setICC ( String ICC ) {
        jTextField20.setText( ICC );
        this.ICC = ICC;
    }
    public String getWeight()
    {
        return jLabel42.getText();        
    }
    public void setWeight(String weight)
    {
        jLabel42.setText(weight);
    }
    public String getTotal(){        
        return jLabel48.getText();
    }
    public void setTotal(String Total)
    {
        jLabel48.setText(Total);
    }
    public String getVolume()
    {
        return jLabel43.getText();
    }
    public void setVolume(String volume)
    {
        jLabel43.setText(volume);
    }
    public String getRmUnit()
    {
        return jLabel14.getText();
    }
    public void setRmUnit(String rmunit)
    {
        jLabel14.setText(rmunit);
    }
    public String getWidth1() {
        return jTextField10.getText();
    }
    public void setWidth1(String width)
    {
        jTextField10.setText(width);
    }    
    public String getRMTotal()
    {
        return jLabel46.getText();
    }        
    public void setRMTotal(String total)
    {
        jLabel46.setText(total);
    }    
    ArrayList<String[]> UOM = null;
    ArrayList<String[]> RMType = null;
    ArrayList<String[]> category = null;
    ArrayList<String[]> rmcode=null;
    ArrayList<String[]> docList = null ;
    
    KeyListener keyList=new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
         //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
      //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
           rawMaterialCalc();
        }
    };
    KeyListener k = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            char enter = e.getKeyChar ();

            String dot = Character.toString ( enter );

            if (  ! ( Character.isDigit ( enter ) ) &&  ! dot.equals ( "." ) ) {
                e.consume ();
            }

        }

        @Override
        public void keyPressed ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
            rawMatAdditionalTx();
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    };

    KeyListener keyListner=new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {
               
        }

        @Override
        public void keyReleased(KeyEvent e) {
            rawMaterialCalc();  
            rawMatAdditionalTx();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
       
    
 
    FocusListener f2 = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = (JTextField)  e.getSource() ;
            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {

            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 || num == 0 ) {
                    jcb.setText ( "0.0" );

                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
            }
        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel35 = new javax.swing.JLabel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        equivalentGrade = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel53 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jTextField17 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();

        jLabel35.setText("mm");

        jInternalFrame1.setVisible(true);

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(650, 250));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Type :");
        add(jLabel1);
        jLabel1.setBounds(10, 120, 70, 30);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Category RM\\Part :");
        add(jLabel2);
        jLabel2.setBounds(10, 120, 70, 30);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("<html> Rate / Unit </html>");
        add(jLabel4);
        jLabel4.setBounds(10, 50, 60, 30);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("0.0");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(90, 50, 100, 30);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Acceptance Criteria :");
        jLabel5.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel5.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jLabel5);
        jLabel5.setBounds(750, 130, 0, 0);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextArea1.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextArea1.setNextFocusableComponent(jTextField2);
        jScrollPane2.setViewportView(jTextArea1);

        add(jScrollPane2);
        jScrollPane2.setBounds(880, 130, 0, 0);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("EC No :");
        jLabel6.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel6.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jLabel6);
        jLabel6.setBounds(750, 10, 0, 0);

        jTextField2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField2.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextField2.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextField2.setNextFocusableComponent(jTextField3);
        add(jTextField2);
        jTextField2.setBounds(880, 10, 0, 0);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("CAS No :");
        jLabel7.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel7.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jLabel7);
        jLabel7.setBounds(750, 50, 0, 0);

        jTextField3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField3.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextField3.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextField3.setNextFocusableComponent(jTextField4);
        add(jTextField3);
        jTextField3.setBounds(880, 50, 0, 0);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("GST No :");
        jLabel8.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel8.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jLabel8);
        jLabel8.setBounds(750, 90, 0, 0);

        jTextField4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField4.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextField4.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jTextField4);
        jTextField4.setBounds(880, 90, 0, 0);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Select RM :");
        add(jLabel3);
        jLabel3.setBounds(10, 10, 80, 30);
        add(jLabel13);
        jLabel13.setBounds(190, 50, 50, 30);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("<html>Length<br>in mm</html>");
        add(jLabel9);
        jLabel9.setBounds(500, 50, 50, 30);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("<html>Width<br>in mm</html>");
        add(jLabel16);
        jLabel16.setBounds(500, 90, 50, 30);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("<html>Thickness<br>in mm</html>");
        add(jLabel17);
        jLabel17.setBounds(280, 90, 70, 30);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("<html>Material<br>Density</html>");
        add(jLabel18);
        jLabel18.setBounds(500, 10, 70, 30);

        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField9.setText("0.0");
        add(jTextField9);
        jTextField9.setBounds(570, 50, 90, 30);

        jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField10.setText("0.0");
        add(jTextField10);
        jTextField10.setBounds(570, 90, 90, 30);

        jTextField11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField11.setText("0.0");
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });
        add(jTextField11);
        jTextField11.setBounds(360, 90, 100, 30);

        jTextField12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField12.setText("0.0");
        add(jTextField12);
        jTextField12.setBounds(570, 10, 90, 30);

        jList1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1);
        jScrollPane1.setBounds(170, 210, 0, 0);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("<html>Reference<br>Documents</html>");
        jLabel19.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel19.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jLabel19);
        jLabel19.setBounds(30, 210, 0, 0);

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel20.setText("<html>Material<br>Shape</html>");
        add(jLabel20);
        jLabel20.setBounds(280, 10, 60, 30);

        jLabel22.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel22.setText("<html>Diameter<br>in mm</html>");
        add(jLabel22);
        jLabel22.setBounds(280, 50, 70, 30);

        jTextField14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField14.setText("0.0");
        add(jTextField14);
        jTextField14.setBounds(360, 50, 100, 30);

        jLabel23.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel23.setText("<html>Inner<br>Diameter in mm</html>");
        add(jLabel23);
        jLabel23.setBounds(280, 90, 70, 40);

        jTextField15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField15.setText("0.0");
        add(jTextField15);
        jTextField15.setBounds(360, 90, 100, 30);

        jLabel24.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel24.setText("<html>Outer<br>Diameter <br>in mm</html>");
        add(jLabel24);
        jLabel24.setBounds(500, 90, 90, 40);

        jTextField16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField16.setText("0.0");
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        add(jTextField16);
        jTextField16.setBounds(570, 90, 90, 30);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Wall");
        add(jLabel25);
        jLabel25.setBounds(20, 120, 50, 30);

        equivalentGrade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        equivalentGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equivalentGradeActionPerformed(evt);
            }
        });
        add(equivalentGrade);
        equivalentGrade.setBounds(90, 90, 100, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Round", "Square", "Rectangle", "Hexagonal", "Sheet", "Plate", "Tubular", "Ring", "Pipe" }));
        add(jComboBox3);
        jComboBox3.setBounds(360, 10, 100, 35);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Type :");
        add(jLabel11);
        jLabel11.setBounds(10, 90, 130, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Category RM\\Part :");
        add(jLabel12);
        jLabel12.setBounds(40, 70, 140, 30);

        add(jComboBox1);
        jComboBox1.setBounds(90, 10, 170, 35);

        jSeparator1.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));
        add(jSeparator1);
        jSeparator1.setBounds(0, 192, 710, 0);

        jLabel28.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel28.setText("<html>Transport<br>Cost in Rs</html>");
        add(jLabel28);
        jLabel28.setBounds(20, 220, 70, 30);

        jLabel29.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel29.setText("Taxes%");
        add(jLabel29);
        jLabel29.setBounds(490, 180, 50, 30);

        jLabel30.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel30.setText("<html>Inspection<br>Cost in Rs</html>");
        add(jLabel30);
        jLabel30.setBounds(250, 220, 60, 30);

        jTextField13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField13.setText("0.0");
        add(jTextField13);
        jTextField13.setBounds(100, 220, 100, 30);

        jTextField18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField18.setText("0.0");
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });
        add(jTextField18);
        jTextField18.setBounds(580, 180, 100, 30);

        jTextField19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField19.setText("0.0");
        add(jTextField19);
        jTextField19.setBounds(330, 220, 100, 30);

        jLabel31.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel31.setText("<html>Packaging<br>Forwarding</html>");
        add(jLabel31);
        jLabel31.setBounds(20, 300, 80, 30);

        jTextField20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField20.setText("0.0");
        add(jTextField20);
        jTextField20.setBounds(100, 260, 100, 30);

        jLabel32.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel32.setText("<html>Other<br>Expenses in Rs</html>");
        add(jLabel32);
        jLabel32.setBounds(490, 260, 90, 30);

        jTextField21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField21.setText("0.0");
        jTextField21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField21ActionPerformed(evt);
            }
        });
        add(jTextField21);
        jTextField21.setBounds(580, 260, 100, 30);

        jLabel33.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel33.setText("<html>Scrap<br>Rate  Rs/ Kg </html>");
        add(jLabel33);
        jLabel33.setBounds(250, 180, 80, 30);

        jTextField22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField22.setText("0.0");
        add(jTextField22);
        jTextField22.setBounds(330, 180, 100, 30);

        jLabel34.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel34.setText("<html>Rate<br>Fluctuation %</html>");
        add(jLabel34);
        jLabel34.setBounds(490, 220, 90, 30);

        jTextField23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField23.setText("0.0");
        jTextField23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField23ActionPerformed(evt);
            }
        });
        add(jTextField23);
        jTextField23.setBounds(580, 220, 100, 30);

        jLabel14.setText(" ");
        add(jLabel14);
        jLabel14.setBounds(220, 60, 30, 14);

        jLabel44.setText("Volume");
        add(jLabel44);
        jLabel44.setBounds(100, 130, 60, 30);

        jLabel45.setText("Weight in Kg");
        add(jLabel45);
        jLabel45.setBounds(260, 130, 80, 30);

        jLabel47.setText("Total in Rs");
        add(jLabel47);
        jLabel47.setBounds(440, 130, 70, 30);

        jLabel49.setText("Total in Rs");
        add(jLabel49);
        jLabel49.setBounds(480, 370, 70, 30);

        jTextField24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField24.setText("0.0");
        jTextField24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField24ActionPerformed(evt);
            }
        });
        add(jTextField24);
        jTextField24.setBounds(360, 90, 100, 30);

        jLabel26.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel26.setText("Height in mm");
        add(jLabel26);
        jLabel26.setBounds(280, 90, 80, 30);

        jLabel27.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel27.setText("<html>Outer<br>Diameter</html>");
        add(jLabel27);
        jLabel27.setBounds(10, 120, 60, 30);

        jTextField25.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField25.setText("0.0");
        add(jTextField25);
        jTextField25.setBounds(80, 90, 90, 30);

        jLabel15.setText("<html>FC Weight<br>in Kg</html>");
        add(jLabel15);
        jLabel15.setBounds(20, 180, 80, 30);

        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("0.0");
        add(jTextField5);
        jTextField5.setBounds(100, 180, 100, 30);
        add(jLabel21);
        jLabel21.setBounds(200, 180, 50, 30);
        add(jSeparator2);
        jSeparator2.setBounds(0, 162, 790, 0);

        jLabel50.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel50.setText("<html>ICC Month</html>");
        add(jLabel50);
        jLabel50.setBounds(250, 260, 60, 30);

        jLabel51.setText("<html>Scrap <br>Recovery</html>");
        add(jLabel51);
        jLabel51.setBounds(240, 370, 60, 30);
        add(jLabel52);
        jLabel52.setBounds(320, 310, 100, 30);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4","5","6","7", "8", "9", "10","11","12" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(330, 260, 100, 30);

        jLabel53.setText("Rs");
        add(jLabel53);
        jLabel53.setBounds(450, 370, 20, 30);

        jLabel55.setText("%");
        add(jLabel55);
        jLabel55.setBounds(330, 370, 20, 30);

        jLabel64.setText("<html>Scrap Weight<br>in Kg</html>");
        add(jLabel64);
        jLabel64.setBounds(20, 370, 110, 30);

        jTextField6.setText("80");
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        add(jTextField6);
        jTextField6.setBounds(300, 370, 30, 30);

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Scrap Recovery");
        jCheckBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox1MouseClicked(evt);
            }
        });
        add(jCheckBox1);
        jCheckBox1.setBounds(0, 340, 140, 23);

        jTextField7.setText(" ");
        add(jTextField7);
        jTextField7.setBounds(130, 370, 100, 30);
        add(jTextField8);
        jTextField8.setBounds(350, 370, 90, 30);
        add(jLabel63);
        jLabel63.setBounds(470, 190, 50, 30);

        jLabel48.setText("0");
        jLabel48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLabel48ActionPerformed(evt);
            }
        });
        add(jLabel48);
        jLabel48.setBounds(560, 370, 100, 30);

        jLabel43.setText("0");
        add(jLabel43);
        jLabel43.setBounds(160, 130, 90, 30);

        jLabel42.setText("0");
        add(jLabel42);
        jLabel42.setBounds(340, 130, 90, 30);

        jLabel46.setText("0");
        add(jLabel46);
        jLabel46.setBounds(510, 130, 100, 30);
        add(jSeparator3);
        jSeparator3.setBounds(0, 336, 770, 2);

        jLabel10.setText("<html>Equivalent<br> Grade</html>");
        add(jLabel10);
        jLabel10.setBounds(10, 90, 70, 30);
        add(jSeparator4);
        jSeparator4.setBounds(0, 160, 0, 20);
        add(jSeparator5);
        jSeparator5.setBounds(0, 170, 770, 0);
        add(jSeparator6);
        jSeparator6.setBounds(280, 172, 90, 0);

        jTextField17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField17.setText("0.0");
        jTextField17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });
        add(jTextField17);
        jTextField17.setBounds(100, 300, 100, 30);

        jLabel36.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel36.setText("<html>ICC<br>Annum%</html>");
        add(jLabel36);
        jLabel36.setBounds(20, 260, 60, 30);
    }// </editor-fold>//GEN-END:initComponents

    
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
  
    }//GEN-LAST:event_jList1MouseClicked

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18ActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jTextField24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField24ActionPerformed

    private void jTextField21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField21ActionPerformed

    private void jTextField23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField23ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jCheckBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox1MouseClicked
        loadCheackBox();
        rawMaterialCalc();  
        rawMatAdditionalTx();
    }//GEN-LAST:event_jCheckBox1MouseClicked

    private void jLabel48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLabel48ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel48ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        rawMatAdditionalTx();
        rawMaterialCalc();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void equivalentGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equivalentGradeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_equivalentGradeActionPerformed

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17ActionPerformed

    public void loadCheackBox()
    {
        if (jCheckBox1.isSelected()) {
            jLabel64.setVisible(true);
            jTextField7.setVisible(true);
            jLabel51.setVisible(true);
            jTextField6.setVisible(true);
            jLabel55.setVisible(true);
            jTextField8.setVisible(true);
            jLabel53.setVisible(true);
//            jLabel56.setVisible(true);
        } else {
            jLabel64.setVisible(false);
            jTextField7.setVisible(false);
            jLabel51.setVisible(false);
            jTextField6.setVisible(false);
            jLabel55.setVisible(false);
            jTextField8.setVisible(false);
            jLabel53.setVisible(false);
     //       jLabel56.setVisible(true);
        }

    }
     public static String retreiveSuccessMessage ( String json ) {
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString ( "118" );
        return id;
    }
        
    public  void deleteMaster( String apiName  ){
        String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
        JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
        JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
    }
        
        
    ActionListener selectUnitAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {

        }
    };


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField equivalentGrade;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jLabel42;
    private javax.swing.JTextField jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JTextField jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JTextField jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    int selectedEmpRecordId = 0;

    public void componentVisibility()
    {
        jLabel11.setVisible(false);
        jLabel12.setVisible(false);
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
    }
    String csArea="";
    public void setCsArea(String csarea)
    {
        this.csArea=csarea;
    }
    public String getCsArea() {
        return csArea;
    }
}
