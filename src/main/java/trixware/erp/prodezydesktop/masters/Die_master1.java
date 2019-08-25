/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import trixware.erp.prodezydesktop.Model.DieExelData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.DieDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.components.ButtonForm;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.components.TextInputForm;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.helpers.ZXingHelper;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author dell
 */
public class Die_master1 extends javax.swing.JPanel {

    ArrayList<MachineDR> machines = null;
    ArrayList<DieDR> die = null;
    ArrayList<DieExelData> dieExport = null;

    datechooser.beans.DateChooserCombo datechoosercombo1, datechoosercombo2, datechoosercombo3;
    int selectedMC_ID;
    JTable jt = new JTable();
    JTabbedPane jtp;

    ButtonForm btnreset, btnedit, btnclose, btnadd, btnimport, btnQRcode, btnDieExport, btnprint;
    TextInputForm die_no1, die_name1, total_strokes1, main_stroke1, rem_count1, cost_purchase1, shut_height1, tonnage1, dimensions1, cost_maintainance1, owned_by1;
    String main_date = null;
    String rem_date = null;
    String purchase_date = null;
    Date date, date1, date2 = null;

    //import from excel variable
    ArrayList<String[]> uploadData = new ArrayList<String[]>();
    int uploadedCount = 0;

    /**
     * Creates new form Die_master
     */
    public Die_master1() {
        initComponents();

        loadcontent();

        JPanel jpmain = new JPanel();

        jtp = new JTabbedPane();
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();

        jp1.setLayout(null);

        jtp.add(new JScrollPane(jp1), "Create new Die / Tool");
        jtp.add(new JScrollPane(jp2), "Data");
        jtp.setBounds(0, 0, 1250, 550);

        setPreferredSize(new Dimension(1250, 550));
        add(jtp);

        die_no1 = new TextInputForm("Die(Tool) No", true, "Enter Die ID");
        //die_no.setBounds(60,100,260 , 70);
        die_no1.setBounds(30, 30, 260, 70);
        jp1.add(die_no1);

        die_name1 = new TextInputForm("Die(Tool) Name", true, "Enter Die name");
        //die_name.setBounds(60,170,260 , 70);
        die_name1.setBounds(330, 30, 260, 70);
        jp1.add(die_name1);

        total_strokes1 = new TextInputForm("Total Strokes(Tool Life)", true, "total stroke");
        //total_strokes.setBounds(60, 240, 260, 70);
        total_strokes1.setBounds(630, 30, 260, 70);
        jp1.add(total_strokes1);

        main_stroke1 = new TextInputForm("Maintenance Frequency (Strokes)", true, "Maintenance stroke");
        //main_stroke.setBounds(60, 310, 260, 70);
        main_stroke1.setBounds(30, 100, 260, 70);
        jp1.add(main_stroke1);

        rem_count1 = new TextInputForm("Reminder Count", true, "Reminder count");
        rem_count1.setBounds(330, 100, 260, 70);
        jp1.add(rem_count1);

        JLabel lbl1 = new JLabel("Date Wise Maintenance");
        lbl1.setFont(new java.awt.Font("Leelawadee UI", 0, 12));
        datechoosercombo1 = new datechooser.beans.DateChooserCombo();
        lbl1.setBounds(640, 90, 220, 35);
        datechoosercombo1.setBounds(660, 120, 220, 35);
        datechoosercombo1.setBackground(Color.WHITE);
        jp1.add(lbl1);
        jp1.add(datechoosercombo1);

        //TextInputForm date_wise_main1=new TextInputForm("Date Wise Maintainance", true, "Date wise maintainance");
        //date_wise_main.setBounds(370, 100, 260, 70);
        //date_wise_main1.setBounds(630, 100, 260, 70);
        //jp1.add(date_wise_main1);
        JLabel lbl2 = new JLabel("Preventive Maintenance Reminder Date");
        lbl2.setFont(new java.awt.Font("Leelawadee UI", 0, 12));
        datechoosercombo2 = new datechooser.beans.DateChooserCombo();
        lbl2.setBounds(40, 160, 220, 35);
        datechoosercombo2.setBounds(50, 190, 220, 35);
        jp1.add(lbl2);
        jp1.add(datechoosercombo2);

//        TextInputForm rem_date1=new TextInputForm("Preventive Maintainance Reminder Date", false, "Reminder date");
//        //rem_date.setBounds(370, 170, 260, 70);
//        rem_date1.setBounds(30, 170, 260, 70);
//        jp1.add(rem_date1);
        JLabel lbl3 = new JLabel("Date of Purchase");
        lbl3.setFont(new java.awt.Font("Leelawadee UI", 0, 12));
        datechoosercombo3 = new datechooser.beans.DateChooserCombo();
        lbl3.setBounds(340, 160, 220, 35);
        datechoosercombo3.setBounds(350, 190, 220, 35);
        jp1.add(lbl3);
        jp1.add(datechoosercombo3);

        //TextInputForm date_purchase1=new TextInputForm("Date of Purchase", false, "");
        //date_purchase.setBounds(370, 240, 260, 70);
        //date_purchase1.setBounds(330, 170, 260, 70);
        //jp1.add(date_purchase1);
        cost_purchase1 = new TextInputForm("Cost of Purchase", true, "Cost Of purchase");
        //cost_purchase.setBounds(370, 310, 260, 70);
        cost_purchase1.setBounds(630, 170, 260, 70);
        jp1.add(cost_purchase1);

        shut_height1 = new TextInputForm("Shut Height (mm)", false, "Shut Height");
        //shut_height.setBounds(370, 380, 260, 70);
        shut_height1.setBounds(30, 240, 260, 70);
        jp1.add(shut_height1);

        tonnage1 = new TextInputForm("Tonnage", false, "Tonnage");
        //tonnage.setBounds(700, 100, 260, 70);
        tonnage1.setBounds(330, 240, 260, 70);
        jp1.add(tonnage1);

        dimensions1 = new TextInputForm("Dimensions", false, "Dimensions");
        //dimensions.setBounds(700, 170, 260, 70);
        dimensions1.setBounds(630, 240, 260, 70);
        jp1.add(dimensions1);

        cost_maintainance1 = new TextInputForm("Maintenance Cost", false, "Cost of Maintenance");
        //cost_maintainance.setBounds(700, 240, 260, 70);
        //cost_maintainance.setBounds(30, 310, 260, 70);
        cost_maintainance1.setText("0");
        cost_maintainance1.setBounds(960, 100, 260, 70);
        jp1.add(cost_maintainance1);

        owned_by1 = new TextInputForm("Owned BY", true, "Enter Owner name");
        //cost_maintainance.setBounds(700, 240, 260, 70);
        //cost_maintainance.setBounds(30, 310, 260, 70);
        owned_by1.setBounds(960, 170, 260, 70);
        jp1.add(owned_by1);

        //SpinnerForm machine1=new SpinnerForm("Machine", true, "Select machine",machines);
        //TextAreaInputForm machine1=new TextAreaInputForm("Machine", false, "");
        //machine.setBounds(700, 310, 260, 150);
        //machine.setBounds(30, 380, 260, 150);
        //machine1.setBounds(960, 170, 260, 150);
        //jp1.add(machine1);
        btnadd = new ButtonForm("Save", null);
        btnadd.setBounds(170, 370, 300, 50);
        //btnadd.addActionListener(save);
        jp1.add(btnadd);

        btnreset = new ButtonForm("Reset", null);
        btnreset.setBounds(470, 370, 300, 50);
        jp1.add(btnreset);

        btnprint = new ButtonForm("Export History for Selected Die", null);
        btnprint.setBounds(770, 370, 300, 50);
        jp1.add(btnprint);

        btnedit = new ButtonForm("Edit", null);
        btnedit.setBounds(170, 420, 300, 50);
        jp1.add(btnedit);

        ButtonForm btnexport = new ButtonForm("Export Maintenance History - All", null);
        btnexport.setBounds(200, 420, 300, 50);
        jp2.add(btnexport);

        btnclose = new ButtonForm("Close", null);
        btnclose.setBounds(770, 420, 300, 50);
        jp1.add(btnclose);

        btnimport = new ButtonForm("Import from Excel", null);
        btnimport.setBounds(170, 470, 300, 500);
        jp1.add(btnimport);

        btnQRcode = new ButtonForm("Create QR Code", null);
        btnQRcode.setBounds(470, 420, 300, 50);

        jp1.add(btnQRcode);

        btnDieExport = new ButtonForm("Export Die Master - All", null);
        btnDieExport.setBounds(500, 420, 300, 50);

        jp2.add(btnDieExport);

        //JLabel print=new JLabel("Print/Export Die");
        //print.setBounds(60, 470, 120, 50);
        //*******************Role wise priority set edited by mayur****************************************       
//        int role;
//        role=Integer.parseInt(Proman_User.getRole());
//        
//        switch (role) {
//            case 1: 
//                
        btnexport.setEnabled(true);
        btnprint.setEnabled(false);
        btnedit.setEnabled(false);
        btnQRcode.setEnabled(false);
        btnDieExport.setEnabled(true);
//            break;
//
//            case 2:
//                btnexport.setEnabled(true);
//                btnprint.setEnabled(false);
//                btnedit.setEnabled(false);  
//                btnQRcode.setEnabled(false);
//                btnDieExport.setEnabled(true);
//            break;
//
//            case 3:   
//                btnexport.setEnabled(false);
//                btnprint.setEnabled(false);
//                btnedit.setEnabled(false);
//                btnQRcode.setEnabled(false);
//                btnDieExport.setEnabled(false);
//            break;
//
//            default:
//        }

        //*******************end Role wise priority set edited by mayur****************************************  
        JLabel tab = new JLabel("Table Value");
        //tab.setBounds(60, 520, 120, 50);

        ButtonForm btntab = new ButtonForm("Print Table", null);
        //btntab.setBounds(370, 520, 300, 50);

        jp2.setLayout(null);
        //String data[][]={ {"","",""},    
        /// {"","",""},    
        // {"","",""}};    
        //String column[]={"Die ID","Die NAME","Total Stroke"};     

        // jt.setModel(   new DefaultTableModel( data, column )   );
        //jt.setBounds(100, 100, 600, 300);
        JScrollPane sp = new JScrollPane(jt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //sp.setViewportView(jt ) ;
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        sp.setBounds(100, 100, 800, 300);
        jp2.add(sp);

        //cost_maintainance1.setEnabled(false);
        cost_maintainance1.setEditable(false);

        jt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel) jt.getModel();

                // get the selected row index
                int selectedRowIndex = jt.getSelectedRow();
                //jTextField6.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );
                jtp.setSelectedIndex(0);
                //selectedRowIndex += 1;
                //selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );
                //System.out.println ( ""+selectedRecordId );

                selecteddieRecordId = Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString());

                //******Created by Harshali QR code generation
                selecteddieNo = model.getValueAt(selectedRowIndex, 1).toString();
                selecteddieName = model.getValueAt(selectedRowIndex, 2).toString();
                total_Strokes = Integer.parseInt(model.getValueAt(selectedRowIndex, 3).toString());
                maintain = Integer.parseInt(model.getValueAt(selectedRowIndex, 4).toString());
                owner_id = model.getValueAt(selectedRowIndex, 8).toString();

                //*********************
                die_no1.setText(model.getValueAt(selectedRowIndex, 1).toString());
                die_name1.setText(model.getValueAt(selectedRowIndex, 2).toString());
                total_strokes1.setText(model.getValueAt(selectedRowIndex, 3).toString());
                main_stroke1.setText(model.getValueAt(selectedRowIndex, 4).toString());
                cost_maintainance1.setText(model.getValueAt(selectedRowIndex, 5).toString());
                rem_count1.setText(model.getValueAt(selectedRowIndex, 6).toString());
                cost_purchase1.setText(model.getValueAt(selectedRowIndex, 7).toString());
                owned_by1.setText(model.getValueAt(selectedRowIndex, 8).toString());
                shut_height1.setText(model.getValueAt(selectedRowIndex, 9).toString());
                tonnage1.setText(model.getValueAt(selectedRowIndex, 10).toString());
                dimensions1.setText(model.getValueAt(selectedRowIndex, 11).toString());

//                switch (role) {
//            case 1: 
//                
//                btnexport.setEnabled(true);
//                btnprint.setEnabled(true);
//                btnedit.setEnabled(true);
//                btnQRcode.setEnabled(true);
//                btnDieExport.setEnabled(true);
//            break;
//
//            case 2:
//                btnexport.setEnabled(true);
//               btnprint.setEnabled(true);
//                btnedit.setEnabled(true);
//                btnQRcode.setEnabled(true);
//                btnDieExport.setEnabled(true);
//            break;
//
//            case 3:   
//                btnexport.setEnabled(false);
//                btnprint.setEnabled(false);
//                btnedit.setEnabled(false);
//                btnQRcode.setEnabled(false);
//                btnDieExport.setEnabled(false);
//            break;
//
//            default:
//        }
//                
                btnQRcode.setEnabled(true);
                btnedit.setEnabled(true);
                btnprint.setEnabled(true);

            }
        });

        btnadd.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                String die_tool_no = null, die_name = null, total_strokes = null, cost_maintenance = null, maintenance_strokes = null, reminder_count = null, date_maintenance = null, owner_id = null, reminder_date = null, date_purchase = null, cost_purchase = null, shut_height = null, tonnage = null, dimension = null, machine_id = null;

                callingdatefun();
                selectedMC_ID = (machines.get(jComboBox1.getSelectedIndex()).MC_ID);
                die_tool_no = die_no1.getText();
                die_name = die_name1.getText();
                total_strokes = total_strokes1.getText();
                cost_maintenance = cost_maintainance1.getText();
                maintenance_strokes = main_stroke1.getText();
                reminder_count = rem_count1.getText();
                date_maintenance = main_date;
                owner_id = owned_by1.getText();
                reminder_date = rem_date;
                date_purchase = purchase_date;
                cost_purchase = cost_purchase1.getText();
                //machine_id= selectedMC_ID;
                shut_height = shut_height1.getText();
                tonnage = tonnage1.getText();
                dimension = dimensions1.getText();

                if (!die_tool_no.isEmpty() && !die_name.isEmpty() && !total_strokes.isEmpty() && !maintenance_strokes.isEmpty() && !reminder_count.isEmpty() && !cost_purchase.isEmpty() && !owner_id.isEmpty()) {
                    String addEmpAPICall = null;
                    try {
                        addEmpAPICall = "die_tool_add?die_tool_no=" + URLEncoder.encode(die_tool_no, "UTF-8") + "&die_name=" + URLEncoder.encode(die_name, "UTF-8") + "&total_strokes=" + URLEncoder.encode(total_strokes, "UTF-8") + "&cost_maintenance=" + URLEncoder.encode(cost_maintenance, "UTF-8") + "&maintenance_strokes=" + URLEncoder.encode(maintenance_strokes, "UTF-8") + "&reminder_count=" + URLEncoder.encode(reminder_count, "UTF-8") + "&date_maintenance=" + URLEncoder.encode(date_maintenance, "UTF-8") + "&owner_id=" + URLEncoder.encode(owner_id, "UTF-8") + "&reminder_date=" + URLEncoder.encode(reminder_date, "UTF-8") + "&date_purchase=" + URLEncoder.encode(date_purchase, "UTF-8") + "&cost_purchase=" + URLEncoder.encode(cost_purchase, "UTF-8") + "&machine_id=" + URLEncoder.encode(selectedMC_ID + "", "UTF-8") + "&shut_height=" + URLEncoder.encode(shut_height, "UTF-8") + "&tonnage=" + URLEncoder.encode(tonnage, "UTF-8") + "&dimension=" + URLEncoder.encode(dimension, "UTF-8");
                    } catch (Exception ef) {
                        JOptionPane.showMessageDialog(null, "added exception");
                    }
                    String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                    JOptionPane.showMessageDialog(null, "Successfully Added...!");

                    loadcontent();
                    resetall();
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill all fields marked with * ");

                }

//                }catch(Exception e1){
//                    e1.getMessage();
//                    JOptionPane.showMessageDialog( null, "please fill all the fields" );
//                }
//            }catch(Exception e2)
//            {
//                 e2.getMessage();
//                 JOptionPane.showMessageDialog( null, "Error in variables " );
//            }  
            }
        });

        btnreset.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

//                die_no1.setText("");
//                die_name1.setText("");
//                total_strokes1.setText("");
//                main_stroke1.setText("");
//                rem_count1.setText("");
//                cost_maintainance1.setText("");
//                cost_purchase1.setText("");
//                owned_by1.setText("");
//                shut_height1.setText("");
//                tonnage1.setText("");
//                dimensions1.setText("");
                resetall();
            }
        });

        //*****************Created by harshali
        btnQRcode.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selecteddieRecordId != 0) {
                    try {
                        if (!selecteddieNo.contains("null") && !selecteddieName.contains("null")) {
                            JSONObject object = new JSONObject();
                            object.accumulate("die_id", selecteddieRecordId);
                            object.accumulate("Die_name", selecteddieName);
                            object.accumulate("Die_no", selecteddieNo);
                            object.accumulate("tot_strokes", total_Strokes);
                            object.accumulate("Maintainance", maintain);
                            object.accumulate("owner_id", owner_id);
                            generate_qr(selecteddieName + "_" + selecteddieRecordId, object.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "Please select another record ");
                        }
                    } catch (Exception e1) {
                        e1.getMessage();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a record first");
                }

            }
        });
        //******************************
        btnedit.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //try{
                String die_tool_no = null, die_name = null, total_strokes = null, cost_maintenance = null, maintenance_strokes = null, reminder_count = null, date_maintenance = null, owner_id = null, reminder_date = null, date_purchase = null, cost_purchase = null, shut_height = null, tonnage = null, dimension = null, machine_id = null;
                Date date, date1, date2 = null;

                callingdatefun();
                die_tool_no = die_no1.getText();
                die_name = die_name1.getText();
                total_strokes = total_strokes1.getText();
                cost_maintenance = cost_maintainance1.getText();
                maintenance_strokes = main_stroke1.getText();
                reminder_count = rem_count1.getText();
                date_maintenance = main_date;
                owner_id = owned_by1.getText();
                reminder_date = rem_date;
                date_purchase = purchase_date;
                cost_purchase = cost_purchase1.getText();
                //machine_id="5555";
                shut_height = shut_height1.getText();
                tonnage = tonnage1.getText();
                dimension = dimensions1.getText();

                selectedMC_ID = (machines.get(jComboBox1.getSelectedIndex()).MC_ID);
                if (!die_tool_no.isEmpty() && !die_name.isEmpty() && !total_strokes.isEmpty() && !maintenance_strokes.isEmpty() && !reminder_count.isEmpty() && !cost_purchase.isEmpty() && !owner_id.isEmpty()) {
                    String updateEmpAPICall = null;
                    try {
                        updateEmpAPICall = "die_tool_update?DT_ID=" + URLEncoder.encode(selecteddieRecordId + "", "UTF-8") + "&die_tool_no=" + URLEncoder.encode(die_tool_no, "UTF-8") + "&die_name=" + URLEncoder.encode(die_name, "UTF-8") + "&total_strokes=" + URLEncoder.encode(total_strokes, "UTF-8") + "&cost_maintenance=" + URLEncoder.encode(cost_maintenance, "UTF-8") + "&maintenance_strokes=" + URLEncoder.encode(maintenance_strokes, "UTF-8") + "&reminder_count=" + URLEncoder.encode(reminder_count, "UTF-8") + "&date_maintenance=" + URLEncoder.encode(date_maintenance, "UTF-8") + "&owner_id=" + URLEncoder.encode(owner_id, "UTF-8") + "&reminder_date=" + URLEncoder.encode(reminder_date, "UTF-8") + "&date_purchase=" + URLEncoder.encode(date_purchase, "UTF-8") + "&cost_purchase=" + URLEncoder.encode(cost_purchase, "UTF-8") + "&machine_id=" + URLEncoder.encode(selectedMC_ID + "", "UTF-8") + "&shut_height=" + URLEncoder.encode(shut_height, "UTF-8") + "&tonnage=" + URLEncoder.encode(tonnage, "UTF-8") + "&dimension=" + URLEncoder.encode(dimension, "UTF-8");
                    } catch (Exception ed) {
                        JOptionPane.showMessageDialog(null, "Die update error");
                    }
                    String result2 = WebAPITester.prepareWebCall(updateEmpAPICall, StaticValues.getHeader(), "");
                    //JOptionPane.showMessageDialog ( null , result2);
                    JOptionPane.showMessageDialog(null, "Record updated Successfully..!");

                    loadcontent();
                    resetall();
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill all fields marked with * ");
                }
//                }catch(UnsupportedEncodingException e23){
//                    e23.getMessage();
//                }

//             }
//             else
//             {
//                 JOptionPane.showMessageDialog(null, "Please fill all fields.. ");
//             }
//            }catch(Exception e45)
//            {e45.getMessage();
//             JOptionPane.showMessageDialog(null, "Die master edit error ");}
            }
        });

        //import from excel code 
        btnimport.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                showDataImportDialog();
            }
        });

        btnclose.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                HomeScreen.home.removeForms();
            }
        });

        //die master export functionality done by mayur all data is store in exel
        btnDieExport.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                showDialogDie();
            }
        });

        btnexport.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                showDialog();
                storeDataExel();

            }
        });

        btnprint.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                showDialog1();
            }
        });

        jtp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                int xc = jtp.getSelectedIndex();
                if (xc == 0) {
                    jLabel1.setVisible(true);
                    jComboBox1.setVisible(true);
                } else {
                    jLabel1.setVisible(false);
                    jComboBox1.setVisible(false);

                }
            }
        });

        //setBackground(new java.awt.Color(255, 255, 255)); 
        repaint();
        revalidate();
        //setSize(1400,700);
        jp1.setBackground(Color.WHITE);
        jp2.setBackground(Color.WHITE);
        setBackground(Color.WHITE);
        //setLayout(null);
        setVisible(true);
    }

    public static void generate_qr(String image_name, String qrCodeData) {
        try {

            byte[] QRresult = ZXingHelper.getQRCodeImage(qrCodeData, 300, 350);
            final ImageIcon icon = new ImageIcon(QRresult);
            Image image2 = icon.getImage().getScaledInstance(400, 400, 0);

            //JOptionPane.showMessageDialog(null,"QR Code Location : "+filename,"QR Code of "+image_name,JOptionPane.PLAIN_MESSAGE, new ImageIcon(image2));
            int selection = JOptionPane.showConfirmDialog(null, null, "Click Yes To Save on Desk", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon(image2));
            if (selection == JOptionPane.YES_OPTION) {
                final JFileChooser chooser = new JFileChooser() {
                    public void approveSelection() {
                        if (getSelectedFile().isFile()) {
                            return;
                        } else {
                            super.approveSelection();
                        }
                    }
                };

                //chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Select Folder");
                chooser.setApproveButtonText("Save");
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.showOpenDialog(null);
                
                String filePath = "" + chooser.getSelectedFile().getAbsolutePath();
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile().getAbsolutePath());
                String filename = filePath + "\\" + image_name + ".jpg";
               
                File outputfile = new File(filename);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 400, 400);
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", outputfile);
            JOptionPane.showConfirmDialog(null, "QR Code Location : "+filename, "QR Code of "+image_name, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);

                
                
       /*         String charset = "UTF-8"; // or "ISO-8859-1"
                Map< EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, 400, 400, (Hashtable) hintMap);
                MatrixToImageWriter.writeToFile(matrix, filename.substring(filename.lastIndexOf('.') + 1), new File(filename));
                //JOptionPane.showConfirmDialog(null, "QR Code Location : "+filename  , "QR Code of "+image_name , JOptionPane.OK_OPTION);
                JOptionPane.showConfirmDialog(null, "QR Code Location : " + filename, "QR Code of " + image_name, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                */
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    ProgressDialog progress = new ProgressDialog(null);
    Thread getTableAndFilesContent = null;
    Thread getDataFromExcel = null;

    public boolean showDialog() {
        getTableAndFilesContent = new Thread() {
            public void run() {
                //storeDataExel ();
                dieAllDateExport();
            }
        };
        getTableAndFilesContent.start();
        progress.openProgressWindow();
        return true;
    }

    public boolean showDataImportDialog() {
        progress.updateTitle("Importing Records from Excel");
        progress.updateMessage("Fetching Records from Excel");
        getDataFromExcel = new Thread() {
            public void run() {
                uploadDataFromExcel();
            }
        };
        getDataFromExcel.start();
        progress.openProgressWindow();
        return true;
    }

    public boolean hideDialog() {
        progress.closeProgressWindow();
        return true;
    }

    public void uploadDataFromExcel() {

        uploadData = new ArrayList<String[]>();

        File selectedFile = null;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new MyCustomFilter("excel 97-2003"));
        fileChooser.setCurrentDirectory(new File(System.getProperty(
                "user.home")));
        int result = fileChooser.showOpenDialog(HomeScreen.home);
        if (result == JFileChooser.APPROVE_OPTION) {

            selectedFile = fileChooser.getSelectedFile();
        

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        File dir = new File("dataupload\\");
        dir.mkdirs();

        try {
            inputChannel = new FileInputStream(selectedFile).getChannel();
            outputChannel = new FileOutputStream(new File(dir, selectedFile.getName())).getChannel();

            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

            inputChannel.close();
            outputChannel.close();

        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
        }

        try {

            FileInputStream fis;
            //String query = "INSERT INTO xl_import_test ( ID, name, address, phone, email) VALUES ( ?,?,?,?,?)";
            String query = "INSERT INTO finished_goods ( FG_PART_UNIQUE_ID,  FG_PART_NO, PART_NAME ) VALUES ( ?,?,?  )";

            // pst = con.prepareStatement ( query );
            fis = new FileInputStream(new File("dataupload\\" + selectedFile.getName()));

            HSSFWorkbook my_xls_workbook = new HSSFWorkbook(fis);
            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);

            Iterator<Row> rowIterator = my_worksheet.iterator();

            progress.updateMessage("Fetching Records from Excel");

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String[] dataRow = new String[16];
                try {
                    dataRow[0] = row.getCell(0).getStringCellValue();
                } catch (Exception e2) {
                    try {
                        dataRow[0] = row.getCell(0).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }
                try {
                    dataRow[1] = row.getCell(1).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[1] = row.getCell(1).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }
                try {
                    dataRow[2] = row.getCell(2).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[2] = row.getCell(2).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }
                try {
                    dataRow[3] = row.getCell(3).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[3] = row.getCell(3).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }
                try {
                    dataRow[4] = row.getCell(4).getStringCellValue();
                } catch (Exception e2) {
                    try {
                        dataRow[4] = row.getCell(4).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }
                try {
                    dataRow[5] = row.getCell(5).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[5] = row.getCell(5).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                try {
                    dataRow[6] = row.getCell(6).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[6] = row.getCell(6).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                try {
                    dataRow[7] = row.getCell(7).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[7] = row.getCell(7).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                try {
                    dataRow[8] = row.getCell(8).getStringCellValue();
                } catch (Exception e2) {
                    try {
                        dataRow[8] = row.getCell(8).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }
                try {
                    dataRow[9] = row.getCell(9).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[9] = row.getCell(9).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }
                try {
                    dataRow[10] = row.getCell(10).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[10] = row.getCell(10).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }
                try {
                    dataRow[11] = row.getCell(11).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[11] = row.getCell(11).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }
                try {
                    dataRow[12] = row.getCell(12).getStringCellValue();
                } catch (Exception e2) {
                    try {
                        dataRow[12] = row.getCell(12).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }
                try {
                    dataRow[13] = row.getCell(13).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[13] = row.getCell(13).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                try {
                    dataRow[14] = row.getCell(14).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[14] = row.getCell(14).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                try {
                    dataRow[15] = row.getCell(15).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        dataRow[15] = row.getCell(15).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                uploadedCount = 0;
                uploadData.add(dataRow);

            }

            for (int i = 0; i < uploadData.size(); i++) {

                try {

                    String addEmpAPICall = "die_tool_add?die_tool_no=" + URLEncoder.encode(uploadData.get(i)[1], "UTF-8")
                            + "&die_name=" + URLEncoder.encode(uploadData.get(i)[0], "UTF-8")
                            + "&total_strokes=" + URLEncoder.encode(uploadData.get(i)[2], "UTF-8")
                            + "&cost_maintenance=" + URLEncoder.encode(uploadData.get(i)[3], "UTF-8")
                            + "&maintenance_strokes=" + URLEncoder.encode(uploadData.get(i)[4], "UTF-8")
                            + "&reminder_count=" + URLEncoder.encode(uploadData.get(i)[5], "UTF-8")
                            // + "&date_maintenance=" + URLEncoder.encode(dataRow[6], "UTF-8")
                            + "&date_maintenance=2019-05-15+00:00:00"
                            + "&owner_id=" + URLEncoder.encode(uploadData.get(i)[7], "UTF-8")
                            + "&date_purchase=2019-05-15+00:00:00"
                            + "&cost_purchase=" + URLEncoder.encode(uploadData.get(i)[9], "UTF-8")
                            + "&shut_height=" + URLEncoder.encode(uploadData.get(i)[10], "UTF-8")
                            + "&tonnage=" + URLEncoder.encode(uploadData.get(i)[11], "UTF-8")
                            + "&dimension=" + URLEncoder.encode(uploadData.get(i)[12], "UTF-8")
                            + "&machine_id=" + URLEncoder.encode(uploadData.get(i)[13], "UTF-8")
                            + "&strokes_till_date=" + URLEncoder.encode(uploadData.get(i)[14], "UTF-8")
                            + "&reminder_date=2019-05-15+00:00:00";

                    String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                    uploadedCount++;

                    progress.updateMessage("Uploaded " + i + " Records of " + uploadData.size());

                } catch (UnsupportedEncodingException e34) {
                    hideDialog();

                } catch (NullPointerException e34) {
                    hideDialog();
                    System.out.println(" " + e34.getMessage());
                }

            }

            hideDialog();

            JOptionPane.showMessageDialog(null, uploadedCount + " records uploaded in Finished Good Master");

            String addEmpAPICall = "die_tool";
            String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

            if (!result2.contains("not found")) {

                if (result2 != null) {
                    jt.setModel(buildTableModelfromJSON(result2));
                    jt.getColumnModel().getColumn(0).setMinWidth(0);
                    jt.getColumnModel().getColumn(0).setMaxWidth(0);
                }
            }

            fis.close();

        } catch (IOException ex) {
            System.out.println("IO  " + ex.getMessage());
            hideDialog();
        }
    }  
        else{
        hideDialog();
        JOptionPane.showMessageDialog(null, "Data is not imported..Please check your file");
                }
    }
    
//     public static void generate_qr(String image_name,String qrCodeData) 
//     {
//        try 
//        {
//            
//          
//             byte [] QRresult =ZXingHelper.getQRCodeImage(qrCodeData, 300, 350);
//                      final ImageIcon icon = new ImageIcon(QRresult);
//                     Image image2 = icon.getImage().getScaledInstance(400,450,0);
//                     
//          //JOptionPane.showMessageDialog(null,"QR Code Location : "+filename,"QR Code of "+image_name,JOptionPane.PLAIN_MESSAGE, new ImageIcon(image2));
//         int selection= JOptionPane.showConfirmDialog(null, null,"Click Yes To Save on Desk", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon(image2));
//            if(selection== JOptionPane.YES_OPTION)
//            {
//             final JFileChooser chooser = new JFileChooser() {
//              public void approveSelection() 
//              {
//                if (getSelectedFile().isFile()) 
//                {
//                    return;
//                } else
//                    super.approveSelection();
//              }
//             };

    public void storeDataExel() {
        String[] columns = {"Die/Tool code", "Die Purchase Cost", "Maintenance Date", "Maintenance By(Employee Name)", "Strokes at the time of Maintenance", "Activity", "Activity Cost", "Total_cost"};
        //blank workbook
        Workbook workbook = new HSSFWorkbook();

        //Create a blank sheet
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("die master data");

        // Create a Row
        Row headerRow = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.AUTOMATIC.getIndex());
        style.setFont(font);

        // Create a Font for styling header cells
        //Font headerFont = workbook.createFont();
        //headerFont.setFontHeightInPoints((short) 14);
        //headerFont.setColor(IndexedColors.RED.getIndex());
        // Create a CellStyle with the font
        //CellStyle headerCellStyle = workbook.createCellStyle();
        //headerCellStyle.setFont(headerFont);
        // Create cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            //cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for (DieExelData exelData : dieExport) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(exelData.getDie_code());

            row.createCell(1)
                    .setCellValue(exelData.getDie_purchase_cost());

            row.createCell(2)
                    .setCellValue(exelData.getMaint_dt());

            row.createCell(3)
                    .setCellValue(exelData.getEmp_nm());
            row.createCell(4)
                    .setCellValue(exelData.getStroke_mt());
            row.createCell(5)
                    .setCellValue(exelData.getActivity());
            row.createCell(6)
                    .setCellValue(exelData.getAct_cost());
            row.createCell(7)
                    .setCellValue(exelData.getTotal_cost());

        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

//                //Iterate over data and write to sheet
//                Set<String> keyset = data.keySet();
//                int rownum = 0;
//                for (String key : keyset) {
//                    Row row = sheet.createRow(rownum++);
//                    Object[] objArr = data.get(key);
//                    int cellnum = 0;
//                    for (Object obj : objArr) {
//                        Cell cell = row.createCell(cellnum++);
//                        if (obj instanceof String) {
//                            cell.setCellValue((String) obj);
//                        } else if (obj instanceof Integer) {
//                            cell.setCellValue((Integer) obj);
//                        }
//                    }
//                }
        File file = null;
        try {

            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
            String strDate2 = sdf2.format(c2.getTime());

            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Choose a directory to save your file: ");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (jfc.getSelectedFile().isDirectory()) {
                    System.out.println("You selected the directory: " + jfc.getSelectedFile());

                    //Write the workbook in file system
                    file = new File(jfc.getSelectedFile() + "\\" + "Die Tool History Data  " + strDate2 + ".xls");
                    FileOutputStream fileop = new FileOutputStream(file);
                    workbook.write(fileop);
                    workbook.close();
                    fileop.close();
                    JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
                }

            }

            //System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } catch (IOException Ie) {
            Ie.printStackTrace();
        }

    }

    public void loadcontent() {

        String addEmpAPICall, result2;
        if (jComboBox1.getItemCount() == 0) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "machines";
            result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

            if (!result2.contains("not found")) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject jObject = new JSONObject(result2);
                Iterator<?> keys = jObject.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Object value = jObject.get(key);
                    map.put(key, value);
                }

                JSONObject st = (JSONObject) map.get("data");
                JSONArray records = st.getJSONArray("records");

                JSONObject emp = null;
                machines = new ArrayList<MachineDR>();
                for (int i = 0; i < records.length(); i++) {

                    emp = records.getJSONObject(i);
                    jComboBox1.addItem(emp.get("MACHINE_NO").toString());
                    machines.add(new MachineDR(Integer.parseInt(emp.get("MCH_ID").toString()), emp.get("MACHINE_NO").toString()));
                }
            }
        }

        String EmpAPICall = "die_tool";
        String result3 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

        if (result3 != null && !result3.contains("not found")) {
            jt.setModel(buildTableModelfromJSON(result3));
            jt.getColumnModel().getColumn(0).setMinWidth(0);
            jt.getColumnModel().getColumn(0).setMaxWidth(0);
        }
    }

    public static DefaultTableModel buildTableModelfromJSON(String dieJSONList) {

        Vector<String> columnNames = new Vector<String>();
        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();

        JSONArray jARR = null;
        JSONObject jOB = null;

        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject jObject = new JSONObject(dieJSONList);
        Iterator<?> keys = jObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = jObject.get(key);
            map.put(key, value);
        }

        JSONObject st = (JSONObject) map.get("data");
        JSONArray records = st.getJSONArray("records");

        JSONObject die = null;

        columnNames = new Vector<String>();
        columnNames.add("DT_ID");
        columnNames.add("die_tool_no");
        columnNames.add("die_name");
        columnNames.add("total_strokes");
        columnNames.add("maintenance_strokes");
        columnNames.add("cost_maintenance");
        columnNames.add("reminder_count");
        columnNames.add("cost_purchase");
        columnNames.add("owner_id");
        columnNames.add("shut_height");
        columnNames.add("tonnage");
        columnNames.add("dimension");

        // data of the table
        for (int i = 0; i < records.length(); i++) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 0; columnIndex < columnNames.size(); columnIndex++) {
                die = records.getJSONObject(i);
                vector.add(die.get(columnNames.get(columnIndex)));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    public void resetall() {
        die_no1.setText("");
        die_name1.setText("");
        total_strokes1.setText("");
        main_stroke1.setText("");
        rem_count1.setText("");
        cost_purchase1.setText("");
        shut_height1.setText("");
        tonnage1.setText("");
        dimensions1.setText("");
        cost_maintainance1.setText("0");
        owned_by1.setText("");

        btnprint.setEnabled(false);
        btnedit.setEnabled(false);
        btnQRcode.setEnabled(false);
    }

    public void dieAllDateExport() {
        progress.updateTitle("All Tool history data is fetching please wait...");
        progress.updateMessage("Getting all Tool data");
        String die_tool = "", purchase_cost = "", date1 = "", employe = "", emp_name = "", activity = "", strokes_at = "", cost = "", total_cost = "", activity_id = "", temp = "";

        int die_id;
        int l = 2;

        //Map<String, Object[]> data;
        //data = new TreeMap<String, Object[]>();
        //String[] columns = {"Die/Tool code","Die Purchase Cost", "Maintenance Date", "Maintenance By(Employee Name)", "Strokes at the time of Maintenance", "Activity", "Activity Cost", "Total_cost"};
        //data.put("1", new Object[]{"Die/Tool code", "Maintenance Date", "Maintenance By(Employee Name)", "Strokes at the time of Maintenance", "Activity", "Activity Cost", "Total_cost"});
        dieExport = new ArrayList<>();
        if (dieExport.size() > 0) {
            dieExport.clear();
        }

        String result2, addEmpAPICall;

        addEmpAPICall = "die_tool";

        result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (!result2.contains("not found")) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject(result2);
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = jObject.get(key);
                map.put(key, value);
            }

            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = st.getJSONArray("records");

            JSONObject emp = null;
            die = new ArrayList<DieDR>();
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                //jComboBox1.addItem ( emp.get ( "die_tool_no" ).toString () );
                die.add(new DieDR(Integer.parseInt(emp.get("DT_ID").toString()), emp.get("die_tool_no").toString(), Float.parseFloat(emp.get("cost_maintenance").toString())));
                die_id = Integer.parseInt(emp.get("DT_ID").toString());
                die_tool = emp.get("die_tool_no").toString();
                total_cost = emp.get("cost_maintenance").toString();
                purchase_cost = emp.get("cost_purchase").toString();

                //data.put("" + l, new Object[]{die_tool, temp, temp, temp, temp, temp, temp});
                dieExport.add(new DieExelData(die_tool, purchase_cost, temp, temp, temp, temp, temp, temp));
                l = l + 1;

                //all maintenance history logs by die id
                String EmpAPICall = "maintenance_history?DT_ID=" + die_id;
                String tab = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                if (!tab.contains("not found")) {
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    JSONObject jObject1 = new JSONObject(tab);
                    Iterator<?> keys1 = jObject1.keys();

                    while (keys1.hasNext()) {
                        String key1 = (String) keys1.next();
                        Object value1 = jObject1.get(key1);
                        map1.put(key1, value1);
                    }

                    JSONObject st1 = (JSONObject) map1.get("data");
                    JSONArray records1 = st1.getJSONArray("records");

                    JSONObject emp1 = null;

                    for (int j = 0; j < records1.length(); j++) {

                        emp1 = records1.getJSONObject(j);

//                                die_tool=emp.get("die_tool_no").toString();
//                                total_cost=emp.get("cost_maintenance").toString();
//                                emp1.get("MH_REF_UID").toString();
                        String mh_id = emp1.get("MH_ID").toString();
                        date1 = emp1.get("date_time").toString();
                        employe = emp1.get("emp_id").toString();
                        strokes_at = emp1.get("stroke_mt").toString();

                        String load_emp, result_for_emp;
                        load_emp = "employeeedit?EmployeePK=" + employe;
                        result_for_emp = WebAPITester.prepareWebCall(load_emp, StaticValues.getHeader(), "");

                        if (!result_for_emp.contains("not found")) {

                            JSONObject jsobje = new JSONObject(result_for_emp);
                            String page11 = jsobje.getJSONArray("data").toString();
                            JSONArray machinejsarray = new JSONArray(page11);

                            for (int p = 0; p < machinejsarray.length(); p++) {

                                JSONObject jsonobject = machinejsarray.getJSONObject(p);
                                emp_name = jsonobject.optString("EMP_NAME");
                            }
                        }
                        Date dt = new Date();
                        String maintdate = "";
                        SimpleDateFormat FormatYearTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat FormatYr = new SimpleDateFormat("MMM dd, yyyy");
                        try {
                            dt = FormatYearTime.parse(date1);
                            maintdate = FormatYr.format(dt);
                        } catch (ParseException fg) {
                            fg.getMessage();
                        }

                        //data.put("" + l, new Object[]{temp, maintdate, emp_name, strokes_at, temp, temp, temp});
                        dieExport.add(new DieExelData(temp, temp, maintdate, emp_name, strokes_at, temp, temp, temp));
                        l = l + 1;

                        String mhAPICall = "maint_history_logs?MH_REF_UID=" + mh_id;
                        String tab1 = WebAPITester.prepareWebCall(mhAPICall, StaticValues.getHeader(), "");

                        if (!tab1.contains("not found")) {
                            HashMap<String, Object> map2 = new HashMap<String, Object>();
                            JSONObject jObject2 = new JSONObject(tab1);
                            Iterator<?> keys2 = jObject2.keys();

                            while (keys2.hasNext()) {
                                String key2 = (String) keys2.next();
                                Object value2 = jObject2.get(key2);
                                map2.put(key2, value2);
                            }

                            JSONObject st2 = (JSONObject) map2.get("data");
                            JSONArray records2 = st2.getJSONArray("records");

                            JSONObject emp2 = null;

                            for (int k = 0; k < records2.length(); k++) {

                                emp2 = records2.getJSONObject(k);

                                //emp1.get("MH_REF_UID").toString();
                                activity_id = emp2.get("MH_ACT_ID").toString();
                                activity = emp2.get("MH_ACT_COMM").toString();
                                cost = emp2.get("MH_ACT_CST").toString();

                                //data.put("" + l, new Object[]{temp, temp, temp, temp, activity, cost, temp});
                                dieExport.add(new DieExelData(temp, temp, temp, temp, temp, activity, cost, temp));
                                l = l + 1;
                            }
                        }

                    }
                }
                //data.put("" + l, new Object[]{temp, temp, temp, temp, temp, temp, total_cost});
                dieExport.add(new DieExelData(temp, temp, temp, temp, temp, temp, temp, total_cost));
                l = l + 1;
            }
        }
        hideDialog();

    }

    ProgressDialog progressExcelData = new ProgressDialog(null);
    Thread getTableAndFilesContent1 = null;

    public boolean showDialog1() {
        getTableAndFilesContent1 = new Thread() {
            public void run() {

                selectedDieHistoryData();
            }
        };
        getTableAndFilesContent1.start();
        progressExcelData.openProgressWindow();
        return true;
    }

    public boolean hideDialog1() {
        progressExcelData.closeProgressWindow();
        return true;
    }

    public void selectedDieHistoryData() {
        progressExcelData.updateTitle("All Selected Tool history data is fetching please wait...");
        progressExcelData.updateMessage("Getting all Die data");
        String die_tool = "", purchase_cost = "", date1 = "", employe = "", emp_name = "", activity = "", strokes_at = "", cost = "", total_cost = "", activity_id = "", temp = "";

        int die_id;
        int l = 2;

        //Map<String, Object[]> data;
        //data = new TreeMap<String, Object[]>();
        //String[] columns = {"Die/Tool code","Die Purchase Cost", "Maintenance Date", "Maintenance By(Employee Name)", "Strokes at the time of Maintenance", "Activity", "Activity Cost", "Total_cost"};
        //data.put("1", new Object[]{"Die/Tool code", "Maintenance Date", "Maintenance By(Employee Name)", "Strokes at the time of Maintenance", "Activity", "Activity Cost", "Total_cost"});
        dieExport = new ArrayList<>();

        if (dieExport.size() > 0) {
            dieExport.clear();
        }
        String result2, addEmpAPICall;

        addEmpAPICall = "die_tool_edit?DT_ID=" + selecteddieRecordId;

        result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (!result2.contains("not found")) {

            JSONObject jsobje0 = new JSONObject(result2);
            String page = jsobje0.getJSONArray("data").toString();
            JSONArray machinejsarray0 = new JSONArray(page);

            for (int i = 0; i < machinejsarray0.length(); i++) {

                JSONObject jsonobject0 = machinejsarray0.getJSONObject(i);
                //emp_name = jsonobject.optString("EMP_NAME");

                //jComboBox1.addItem ( emp.get ( "die_tool_no" ).toString () );
                //die.add(new DieDR(Integer.parseInt(emp.get("DT_ID").toString()), emp.get("die_tool_no").toString(), Float.parseFloat(emp.get("cost_maintenance").toString())));
                die_id = (jsonobject0.optInt("DT_ID"));
                die_tool = jsonobject0.optString("die_tool_no");
                total_cost = jsonobject0.optString("cost_maintenance");
                purchase_cost = jsonobject0.optString("cost_purchase");

                //data.put("" + l, new Object[]{die_tool, temp, temp, temp, temp, temp, temp});
                dieExport.add(new DieExelData(die_tool, purchase_cost, temp, temp, temp, temp, temp, temp));
                l = l + 1;

                //all maintenance history logs by die id
                String EmpAPICall = "maintenance_history?DT_ID=" + die_id;
                String tab = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                if (!tab.contains("not found")) {
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    JSONObject jObject1 = new JSONObject(tab);
                    Iterator<?> keys1 = jObject1.keys();

                    while (keys1.hasNext()) {
                        String key1 = (String) keys1.next();
                        Object value1 = jObject1.get(key1);
                        map1.put(key1, value1);
                    }

                    JSONObject st1 = (JSONObject) map1.get("data");
                    JSONArray records1 = st1.getJSONArray("records");

                    JSONObject emp1 = null;

                    for (int j = 0; j < records1.length(); j++) {

                        emp1 = records1.getJSONObject(j);

//                                die_tool=emp.get("die_tool_no").toString();
//                                total_cost=emp.get("cost_maintenance").toString();
//                                emp1.get("MH_REF_UID").toString();
                        String mh_id = emp1.get("MH_ID").toString();
                        date1 = emp1.get("date_time").toString();
                        employe = emp1.get("emp_id").toString();
                        strokes_at = emp1.get("stroke_mt").toString();

                        String load_emp, result_for_emp;
                        load_emp = "employeeedit?EmployeePK=" + employe;
                        result_for_emp = WebAPITester.prepareWebCall(load_emp, StaticValues.getHeader(), "");

                        if (!result_for_emp.contains("not found")) {

                            JSONObject jsobje = new JSONObject(result_for_emp);
                            String page11 = jsobje.getJSONArray("data").toString();
                            JSONArray machinejsarray = new JSONArray(page11);

                            for (int p = 0; p < machinejsarray.length(); p++) {

                                JSONObject jsonobject = machinejsarray.getJSONObject(p);
                                emp_name = jsonobject.optString("EMP_NAME");
                            }
                        }
                        Date dt = new Date();
                        String maintdate = "";
                        SimpleDateFormat FormatYearTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat FormatYr = new SimpleDateFormat("MMM dd, yyyy");
                        try {
                            dt = FormatYearTime.parse(date1);
                            maintdate = FormatYr.format(dt);
                        } catch (ParseException fg) {
                            fg.getMessage();
                        }

                        //data.put("" + l, new Object[]{temp, maintdate, emp_name, strokes_at, temp, temp, temp});
                        dieExport.add(new DieExelData(temp, temp, maintdate, emp_name, strokes_at, temp, temp, temp));
                        l = l + 1;

                        String mhAPICall = "maint_history_logs?MH_REF_UID=" + mh_id;
                        String tab1 = WebAPITester.prepareWebCall(mhAPICall, StaticValues.getHeader(), "");

                        if (!tab1.contains("not found")) {
                            HashMap<String, Object> map2 = new HashMap<String, Object>();
                            JSONObject jObject2 = new JSONObject(tab1);
                            Iterator<?> keys2 = jObject2.keys();

                            while (keys2.hasNext()) {
                                String key2 = (String) keys2.next();
                                Object value2 = jObject2.get(key2);
                                map2.put(key2, value2);
                            }

                            JSONObject st2 = (JSONObject) map2.get("data");
                            JSONArray records2 = st2.getJSONArray("records");

                            JSONObject emp2 = null;

                            for (int k = 0; k < records2.length(); k++) {

                                emp2 = records2.getJSONObject(k);

                                //emp1.get("MH_REF_UID").toString();
                                activity_id = emp2.get("MH_ACT_ID").toString();
                                activity = emp2.get("MH_ACT_COMM").toString();
                                cost = emp2.get("MH_ACT_CST").toString();

                                //data.put("" + l, new Object[]{temp, temp, temp, temp, activity, cost, temp});
                                dieExport.add(new DieExelData(temp, temp, temp, temp, temp, activity, cost, temp));
                                l = l + 1;
                            }
                        }

                    }
                }
                //data.put("" + l, new Object[]{temp, temp, temp, temp, temp, temp, total_cost});
                dieExport.add(new DieExelData(temp, temp, temp, temp, temp, temp, temp, total_cost));
                l = l + 1;
            }
        }

        String[] columns = {"Die/Tool code", "Die Purchase Cost", "Maintenance Date", "Maintenance By(Employee Name)", "Strokes at the time of Maintenance", "Activity", "Activity Cost", "Total_cost"};
        //blank workbook
        Workbook workbook = new HSSFWorkbook();

        //Create a blank sheet
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("die master data");

        // Create a Row
        Row headerRow = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.AUTOMATIC.getIndex());
        style.setFont(font);

        // Create cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            //cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for (DieExelData exelData : dieExport) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(exelData.getDie_code());

            row.createCell(1)
                    .setCellValue(exelData.getDie_purchase_cost());

            row.createCell(2)
                    .setCellValue(exelData.getMaint_dt());

            row.createCell(3)
                    .setCellValue(exelData.getEmp_nm());
            row.createCell(4)
                    .setCellValue(exelData.getStroke_mt());
            row.createCell(5)
                    .setCellValue(exelData.getActivity());
            row.createCell(6)
                    .setCellValue(exelData.getAct_cost());
            row.createCell(7)
                    .setCellValue(exelData.getTotal_cost());

        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        hideDialog1();

        File file = null;
        try {

            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
            String strDate2 = sdf2.format(c2.getTime());

            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Choose a directory to save your file: ");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (jfc.getSelectedFile().isDirectory()) {
                    System.out.println("You selected the directory: " + jfc.getSelectedFile());
                }

                //Write the workbook in file system
                file = new File(jfc.getSelectedFile() + "\\" + "Die Tool Selected History Data  " + strDate2 + ".xls");
                FileOutputStream fileop = new FileOutputStream(file);
                workbook.write(fileop);
                workbook.close();
                fileop.close();

                JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
            }
            //System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } catch (IOException Ie) {
            Ie.printStackTrace();
        }

    }
    
    
     ProgressDialog progressDieExcelData = new ProgressDialog(null);
    Thread getTableAndFilesContent2 = null;

    public boolean showDialogDie() {
        getTableAndFilesContent2 = new Thread() {
            public void run() {

                allDieToolExportData();
            }
        };
        getTableAndFilesContent2.start();
        progressDieExcelData.openProgressWindow();
        return true;
    }

    public boolean hideDialogDie() {
        progressDieExcelData.closeProgressWindow();
        return true;
    }
    
    public void allDieToolExportData()
    {
        progressDieExcelData.updateTitle("All Die/Tool data is fetching please wait...");
        progressDieExcelData.updateMessage("Getting all Die data");    
        
                        //String die_id="",die_tool = "",die_name="", date1 = "", employe = "", emp_name = "", activity = "", strokes_at = "", cost = "", total_cost = "", activity_id = "",temp="";
                String stroke_till_date = "", die_id = "", die_tool_no = "", die_name = "", total_strokes = "", cost_maintenance = "", maintenance_strokes = "", reminder_count = "", date_maintenance = "", owner_id = "", reminder_date = "", date_purchase = "", cost_purchase = "", shut_height = "", tonnage = "", dimension = "", machine_id = "", machine_name = "";

                int h = 2;
                String[] columns = {"Die ID", "Die Tool No", "Die name", "Total Strokes", "Maintenance Cost", "Maintenance Frequency (Strokes)", "Reminder Count", "Date of Maintenance", "Owner Name", "Reminder Date", "Date of Purchase", "Cost of Purchase", "Shut Height", "Tonnage", "Dimension", "Machine Name", "Stroke Till Date"};
                Map<String, Object[]> data;
                data = new TreeMap<String, Object[]>();
                data.put("1", new Object[]{"Die ID", "Die Tool No", "Die name", "Total Strokes", "Maintenance Cost", "Maintenance Frequency (Strokes)", "Reminder Count", "Date of Maintenance", "Owner Name", "Reminder Date", "Date of Purchase", "Cost of Purchase", "Shut Height", "Tonnage", "Dimension", "Machine Name", "Stroke Till Date"});

                String result2, addEmpAPICall;

                addEmpAPICall = "die_tool";
                result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

                if (!result2.contains("not found")) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jObject = new JSONObject(result2);
                    Iterator<?> keys = jObject.keys();

                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Object value = jObject.get(key);
                        map.put(key, value);
                    }

                    JSONObject st = (JSONObject) map.get("data");
                    JSONArray records = st.getJSONArray("records");

                    JSONObject emp = null;

                    for (int i = 0; i < records.length(); i++) {

                        emp = records.getJSONObject(i);
                        //jComboBox1.addItem ( emp.get ( "die_tool_no" ).toString () );
                        die_id = emp.get("DT_ID").toString();
                        die_name = emp.get("die_name").toString();
                        total_strokes = emp.get("total_strokes").toString();
                        cost_maintenance = emp.get("cost_maintenance").toString();
                        maintenance_strokes = emp.get("maintenance_strokes").toString();
                        reminder_count = emp.get("reminder_count").toString();
                        owner_id = emp.get("owner_id").toString();
                        reminder_date = emp.get("reminder_date").toString();
                        date_purchase = emp.get("date_purchase").toString();
                        cost_purchase = emp.get("cost_purchase").toString();
                        shut_height = emp.get("shut_height").toString();
                        tonnage = emp.get("tonnage").toString();
                        dimension = emp.get("dimension").toString();
                        machine_id = emp.get("machine_id").toString();
                        die_tool_no = emp.get("die_tool_no").toString();
                        stroke_till_date = emp.get("strokes_till_date").toString();
                        date_maintenance = emp.get("date_maintenance").toString();

                        Date dt = new Date();
                        SimpleDateFormat FormatYearTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat FormatYr = new SimpleDateFormat("MMM dd, yyyy");
                        try {
                            dt = FormatYearTime.parse(reminder_date);
                            reminder_date = FormatYr.format(dt);

                            dt = FormatYearTime.parse(date_purchase);
                            date_purchase = FormatYr.format(dt);

                            dt = FormatYearTime.parse(date_maintenance);
                            date_maintenance = FormatYr.format(dt);

                        } catch (ParseException fg) {
                            fg.getMessage();
                        }

                        //machineedit?MCH_ID=2
                        String load_machine, result_for_machine;
                        if (!machine_id.contains("0")) {
                            load_machine = "machineedit?MCH_ID=" + machine_id;
                            result_for_machine = WebAPITester.prepareWebCall(load_machine, StaticValues.getHeader(), "");

                            if (!result_for_machine.contains("not found")) {

                                JSONObject jsobje = new JSONObject(result_for_machine);
                                String page11 = jsobje.getJSONArray("data").toString();
                                JSONArray machinejsarray = new JSONArray(page11);

                                for (int p = 0; p < machinejsarray.length(); p++) {

                                    JSONObject jsonobject = machinejsarray.getJSONObject(p);
                                    machine_name = jsonobject.optString("MACHINE_NO");

                                    data.put("" + h, new Object[]{die_id, die_tool_no, die_name, total_strokes, cost_maintenance, maintenance_strokes, reminder_count, date_maintenance, owner_id, reminder_date, date_purchase, cost_purchase, shut_height, tonnage, dimension, machine_name, stroke_till_date});
                                    h += 1;
                                }
                            }
                        }
                        if (machine_id.contains("0")) {
                            data.put("" + h, new Object[]{die_id, die_tool_no, die_name, total_strokes, cost_maintenance, maintenance_strokes, reminder_count, date_maintenance, owner_id, reminder_date, date_purchase, cost_purchase, shut_height, tonnage, dimension, machine_name, stroke_till_date});
                            h += 1;
                        }
                    }
                }

                //Blank workbook
                Workbook workbook = new HSSFWorkbook();

                //Create a blank sheet
                HSSFSheet sheet = (HSSFSheet) workbook.createSheet("die master");

                CellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
                style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                Font font = workbook.createFont();
                font.setColor(IndexedColors.AUTOMATIC.getIndex());
                style.setFont(font);

                //Iterate over data and write to sheet
                Set<String> keyset = data.keySet();
                int rownum = 0;
                for (String key : keyset) {
                    Row row = sheet.createRow(rownum++);
                    Object[] objArr = data.get(key);
                    int cellnum = 0;
                    for (Object obj : objArr) {
                        Cell cell = row.createCell(cellnum++);
                        if (obj instanceof String) {
                            cell.setCellValue((String) obj);
                        } else if (obj instanceof Integer) {
                            cell.setCellValue((Integer) obj);
                        }
                    }
                }
                // Resize all columns to fit the content size
                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }
                
                hideDialogDie();

                File file = null;
                try {

                    Calendar c2 = Calendar.getInstance();
                    SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
                    String strDate2 = sdf2.format(c2.getTime());

                    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    jfc.setDialogTitle("Choose a directory to save your file: ");
                    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int returnValue = jfc.showSaveDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        if (jfc.getSelectedFile().isDirectory()) {
                            System.out.println("You selected the directory: " + jfc.getSelectedFile());
                        }

                        //Write the workbook in file system
                        file = new File(jfc.getSelectedFile() + "\\" + "Die Data " + strDate2 + ".xls");
                        FileOutputStream fileop = new FileOutputStream(file);
                        workbook.write(fileop);
                        workbook.close();
                        fileop.close();
                        JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
                    }
                    //System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
                } catch (IOException Ie) {
                    Ie.printStackTrace();
                }
    }

    public void callingdatefun() {

        //date wise maintenance
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        main_date = sdf.format(datechoosercombo1.getSelectedDate().getTime());
        System.out.println(main_date);

        //reminder date
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        rem_date = sdf1.format(datechoosercombo2.getSelectedDate().getTime());
        System.out.println(rem_date);

        //date of purchase
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        purchase_date = sdf2.format(datechoosercombo3.getSelectedDate().getTime());
        System.out.println(purchase_date);

        //                SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
//                    s=datechoosercombo1.getText().toString();
//                    try{
//                    date=formatter1.parse(s);
//                    }catch(ParseException ex)
//                    {   ex.getMessage();
//                        JOptionPane.showMessageDialog(null, "Date error variable 1");}
//                    //s=datechoosercombo1.getSelectedDate().toString();
//                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            
//                    s2=formatter.format(date);
//                    //date=datechoosercombo1.getDateFormat();
//                    //date=formatter1.parse(s); 
//                    System.out.println(s2);  
//                    x=datechoosercombo2.getText().toString();
//                    SimpleDateFormat sdf211 = new SimpleDateFormat ( "dd/MM/yy" );
//                    try{
//                    date1=sdf211.parse(x);
//                    }catch(ParseException ex){JOptionPane.showMessageDialog(null, "Date error variable 2");}
//                    SimpleDateFormat sdf21 = new SimpleDateFormat("yyyy-MM-dd");
//                    x1=sdf21.format(date1);
//                    System.out.println(x1);
//                    y=datechoosercombo3.getText().toString();
//                    SimpleDateFormat sdf31 = new SimpleDateFormat ( "dd/MM/yy" );
//                    try{
//                    date2=sdf31.parse(y);
//                    }catch(ParseException ex){JOptionPane.showMessageDialog(null, "Date error variable 3");}
//                    SimpleDateFormat sdf3 = new SimpleDateFormat ( "yyyy-MM-dd" );
//                     y1=sdf3.format (date2  );
//                    System.out.println(y1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1200, 600));

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Machine   ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                .addGap(83, 83, 83))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(199, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
 int selecteddieRecordId = 0, total_Strokes, maintain;
    String selecteddieNo, selecteddieName, owner_id;
}
//        btnadd.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
//                
//            //Calendar c2 = Calendar.getInstance();
//            //SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss" );
//            //String strDate2 = sdf2.format(c2.getTime());   
//            String s,s1;
//            s=datechoosercombo1.getSelectedDate().getTime().toString();
//            SimpleDateFormat sdf1 = new SimpleDateFormat ( "yyyy-MM-dd" );
//            s1=sdf1.format (s  + " 00:00:00");
//            System.out.println(s1);
//            
//            String x,x1;
//            x=datechoosercombo2.getSelectedDate().getTime().toString();
//            SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );
//            x1=sdf2.format (x  + " 00:00:00");
//            System.out.println(x1);
//            
//            String y,y1;
//            y=datechoosercombo2.getSelectedDate().getTime().toString();
//            SimpleDateFormat sdf3 = new SimpleDateFormat ( "yyyy-MM-dd" );
//            y1=sdf3.format (y  + " 00:00:00");
//            System.out.println(y1);
//            
//            //sdf2.format(datechoosercombo2.getSelectedDate().getTime())+" 00:00:00";
//            selectedMC_ID  = (machines.get ( jComboBox1.getSelectedIndex () ).MC_ID);
//            String die_tool_no, die_name,total_strokes,cost_maintenance,maintenance_strokes,reminder_count,date_maintenance,owner_id,reminder_date,date_purchase,cost_purchase,shut_height,tonnage,dimension,machine_id;
//            
//            
//            die_tool_no=die_no1.getText();
//            die_name=die_name1.getText();
//            total_strokes=total_strokes1.getText();
//            cost_maintenance=cost_maintainance1.getText();
//            maintenance_strokes=main_stroke1.getText();
//            reminder_count=rem_count1.getText();
//            date_maintenance=s1;
//            //date_maintenance="2022-04-01";
//            owner_id=owned_by1.getText();
//            reminder_date=x1;
//            //reminder_date="2022-04-01";
//            date_purchase=y1;
//            //date_purchase="2022-04-01";
//            cost_purchase=cost_purchase1.getText();
//            //machine_id= Integer.parseInt(selectedMC_ID).toString();
//            machine_id="5555";
//            shut_height=shut_height1.getText();
//            tonnage=tonnage1.getText();
//            dimension=dimensions1.getText();
//            
//            try{
//                    
//                    String addEmpAPICall = "die_tool_add?die_tool_no="+URLEncoder.encode(die_tool_no, "UTF-8")+"&die_name="+URLEncoder.encode(die_name, "UTF-8")+"&total_strokes="+URLEncoder.encode(total_strokes , "UTF-8")+"&cost_maintenance="+URLEncoder.encode(cost_maintenance, "UTF-8")+"&maintenance_strokes="+URLEncoder.encode( maintenance_strokes, "UTF-8")+"&reminder_count="+URLEncoder.encode( reminder_count, "UTF-8")+"&date_maintenance="+URLEncoder.encode( date_maintenance, "UTF-8")+"&owner_id="+URLEncoder.encode( owner_id, "UTF-8")+"&reminder_date="+URLEncoder.encode( reminder_date, "UTF-8")+"&date_purchase="+URLEncoder.encode( date_purchase, "UTF-8")+"&cost_purchase="+URLEncoder.encode( cost_purchase, "UTF-8")+"&machine_id="+URLEncoder.encode( machine_id, "UTF-8")+"&shut_height="+URLEncoder.encode( shut_height, "UTF-8")+"&tonnage="+URLEncoder.encode( tonnage, "UTF-8")+"&dimension="+URLEncoder.encode(dimension, "UTF-8");
//                    String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//                    JOptionPane.showMessageDialog ( null , result2);
//                
//                }catch(UnsupportedEncodingException e1){
//                    e1.getMessage();
//                }
//            }
//        });
//

/////****************Make directory
//            File theDir = new File(filePath);
//            if (!theDir.exists()) {
//            System.out.println("creating directory: " + theDir.getName());
//            boolean result = false;
//            try 
//            {
//                theDir.mkdirs();
//                result = true;
//            } catch (SecurityException se) 
//                { System.out.println(se.getMessage());  }
//             if (result)
//             {
//                System.out.println("Folder created");
//             }
//             } else if (theDir.exists()) {
//
//                 System.out.println("Folder exist");
//            }
////****************die all data and store in specific folder*********************
//                String die_tool = "", date1 = "", employe = "", emp_name = "", activity = "", strokes_at = "", cost = "", total_cost = "", activity_id = "", temp = "";
//
//                int die_id;
//                int l = 2;
//
//                Map<String, Object[]> data;
//                data = new TreeMap<String, Object[]>();
//
//                String[] columns = {"Die/Tool code", "Maintenance Date", "Maintenance By(Employee Name)", "Strokes at the time of Maintenance", "Activity", "Activity Cost", "Total_cost"};
//                data.put("1", new Object[]{"Die/Tool code", "Maintenance Date", "Maintenance By(Employee Name)", "Strokes at the time of Maintenance", "Activity", "Activity Cost", "Total_cost"});
//                dieExport = new ArrayList<>();
//
//                //Blank workbook
//                //Workbook workbook = new HSSFWorkbook();
//
//                //Create a blank sheet
//                //HSSFSheet sheet = (HSSFSheet) workbook.createSheet("die master data");
//
//                String result2, addEmpAPICall;
//
//                addEmpAPICall = "die_tool";
//                result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
//
//                if (!result2.contains("not found")) {
//                    HashMap<String, Object> map = new HashMap<String, Object>();
//                    JSONObject jObject = new JSONObject(result2);
//                    Iterator<?> keys = jObject.keys();
//
//                    while (keys.hasNext()) {
//                        String key = (String) keys.next();
//                        Object value = jObject.get(key);
//                        map.put(key, value);
//                    }
//
//                    JSONObject st = (JSONObject) map.get("data");
//                    JSONArray records = st.getJSONArray("records");
//
//                    JSONObject emp = null;
//                    die = new ArrayList<DieDR>();
//                    for (int i = 0; i < records.length(); i++) {
//
//                        emp = records.getJSONObject(i);
//                        //jComboBox1.addItem ( emp.get ( "die_tool_no" ).toString () );
//                        die.add(new DieDR(Integer.parseInt(emp.get("DT_ID").toString()), emp.get("die_tool_no").toString(), Float.parseFloat(emp.get("cost_maintenance").toString())));
//                        die_id = Integer.parseInt(emp.get("DT_ID").toString());
//                        die_tool = emp.get("die_tool_no").toString();
//                        total_cost = emp.get("cost_maintenance").toString();
//
//                        data.put("" + l, new Object[]{die_tool, temp, temp, temp, temp, temp, temp});
//                        dieExport.add(new DieExelData(die_tool, temp, temp, temp, temp, temp, temp));
//                        l = l + 1;
//
//                        //all maintenance history logs by die id
//                        String EmpAPICall = "maintenance_history?DT_ID=" + die_id;
//                        String tab = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");
//
//                        if (!tab.contains("not found")) {
//                            HashMap<String, Object> map1 = new HashMap<String, Object>();
//                            JSONObject jObject1 = new JSONObject(tab);
//                            Iterator<?> keys1 = jObject1.keys();
//
//                            while (keys1.hasNext()) {
//                                String key1 = (String) keys1.next();
//                                Object value1 = jObject1.get(key1);
//                                map1.put(key1, value1);
//                            }
//
//                            JSONObject st1 = (JSONObject) map1.get("data");
//                            JSONArray records1 = st1.getJSONArray("records");
//
//                            JSONObject emp1 = null;
//
//                            for (int j = 0; j < records1.length(); j++) {
//
//                                emp1 = records1.getJSONObject(j);
//
////                                die_tool=emp.get("die_tool_no").toString();
////                                total_cost=emp.get("cost_maintenance").toString();
////                                emp1.get("MH_REF_UID").toString();
//                                String mh_id = emp1.get("MH_ID").toString();
//                                date1 = emp1.get("date_time").toString();
//                                employe = emp1.get("emp_id").toString();
//                                strokes_at = emp1.get("stroke_mt").toString();
//
//                                String load_emp, result_for_emp;
//                                load_emp = "employeeedit?EmployeePK=" + employe;
//                                result_for_emp = WebAPITester.prepareWebCall(load_emp, StaticValues.getHeader(), "");
//
//                                if (!result_for_emp.contains("not found")) {
//
//                                    JSONObject jsobje = new JSONObject(result_for_emp);
//                                    String page11 = jsobje.getJSONArray("data").toString();
//                                    JSONArray machinejsarray = new JSONArray(page11);
//
//                                    for (int p = 0; p < machinejsarray.length(); p++) {
//
//                                        JSONObject jsonobject = machinejsarray.getJSONObject(p);
//                                        emp_name = jsonobject.optString("EMP_NAME");
//                                    }
//                                }
//                                Date dt = new Date();
//                                String maintdate = "";
//                                SimpleDateFormat FormatYearTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                SimpleDateFormat FormatYr = new SimpleDateFormat("MMM dd, yyyy");
//                                try {
//                                    dt = FormatYearTime.parse(date1);
//                                    maintdate = FormatYr.format(dt);
//                                } catch (ParseException fg) {
//                                    fg.getMessage();
//                                }
//
//                                data.put("" + l, new Object[]{temp, maintdate, emp_name, strokes_at, temp, temp, temp});
//                                dieExport.add(new DieExelData(temp, maintdate, emp_name, strokes_at, temp, temp, temp));
//                                l = l + 1;
//
//                                String mhAPICall = "maint_history_logs?MH_REF_UID=" + mh_id;
//                                String tab1 = WebAPITester.prepareWebCall(mhAPICall, StaticValues.getHeader(), "");
//
//                                if (!tab1.contains("not found")) {
//                                    HashMap<String, Object> map2 = new HashMap<String, Object>();
//                                    JSONObject jObject2 = new JSONObject(tab1);
//                                    Iterator<?> keys2 = jObject2.keys();
//
//                                    while (keys2.hasNext()) {
//                                        String key2 = (String) keys2.next();
//                                        Object value2 = jObject2.get(key2);
//                                        map2.put(key2, value2);
//                                    }
//
//                                    JSONObject st2 = (JSONObject) map2.get("data");
//                                    JSONArray records2 = st2.getJSONArray("records");
//
//                                    JSONObject emp2 = null;
//
//                                    for (int k = 0; k < records2.length(); k++) {
//
//                                        emp2 = records2.getJSONObject(k);
//
//                                        //emp1.get("MH_REF_UID").toString();
//                                        activity_id = emp2.get("MH_ACT_ID").toString();
//                                        activity = emp2.get("MH_ACT_COMM").toString();
//                                        cost = emp2.get("MH_ACT_CST").toString();
//
//                                        data.put("" + l, new Object[]{temp, temp, temp, temp, activity, cost, temp});
//                                        dieExport.add(new DieExelData(temp, temp, temp, temp, activity, cost, temp));
//                                        l = l + 1;
//                                    }
//                                }
//                                //System.out.println(die_id);
//                                //System.out.println(die_tool);
//                                //System.out.println(date1);
//                                //System.out.println(employe);
//                                //System.out.println(activity_id);
//                                //System.out.println(activity);
//                                //System.out.println(strokes_at);
//                                //System.out.println(cost);
//                                //System.out.println(total_cost);
//                            }
//                        }
//                        data.put("" + l, new Object[]{temp, temp, temp, temp, temp, temp, total_cost});
//                        dieExport.add(new DieExelData(temp, temp, temp, temp, temp, temp, total_cost));
//                        l = l + 1;
//                    }
//                }
//                // Create a Font for styling header cells
//                Font headerFont = workbook.createFont();
//                headerFont.setFontHeightInPoints((short) 14);
//                headerFont.setColor(IndexedColors.RED.getIndex());
//
//                // Create a CellStyle with the font
//                CellStyle headerCellStyle = workbook.createCellStyle();
//                headerCellStyle.setFont(headerFont);
//
//                // Create a Row
//                Row headerRow = sheet.createRow(0);
//
//                // Create cells
//                for (int i = 0; i < columns.length; i++) {
//                    Cell cell = headerRow.createCell(i);
//                    cell.setCellValue(columns[i]);
//                    //cell.setCellStyle(headerCellStyle);
//                }
//
//                // Create Other rows and cells with employees data
//                int rowNum = 1;
//                for (DieExelData exelData : dieExport) {
//                    Row row = sheet.createRow(rowNum++);
//
//                    row.createCell(0)
//                            .setCellValue(exelData.getDie_code());
//
//                    row.createCell(1)
//                            .setCellValue(exelData.getMaint_dt());
//
//                    row.createCell(2)
//                            .setCellValue(exelData.getEmp_nm());
//                    row.createCell(3)
//                            .setCellValue(exelData.getStroke_mt());
//                    row.createCell(4)
//                            .setCellValue(exelData.getActivity());
//                    row.createCell(5)
//                            .setCellValue(exelData.getAct_cost());
//                    row.createCell(6)
//                            .setCellValue(exelData.getTotal_cost());
//                }
//
//                // Resize all columns to fit the content size
//                for (int i = 0; i < columns.length; i++) {
//                    sheet.autoSizeColumn(i);
//                }
//
////                //Iterate over data and write to sheet
////                Set<String> keyset = data.keySet();
////                int rownum = 0;
////                for (String key : keyset) {
////                    Row row = sheet.createRow(rownum++);
////                    Object[] objArr = data.get(key);
////                    int cellnum = 0;
////                    for (Object obj : objArr) {
////                        Cell cell = row.createCell(cellnum++);
////                        if (obj instanceof String) {
////                            cell.setCellValue((String) obj);
////                        } else if (obj instanceof Integer) {
////                            cell.setCellValue((Integer) obj);
////                        }
////                    }
////                }
//                File file = null;
//                try {
//
//                    Calendar c2 = Calendar.getInstance();
//                    SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
//                    String strDate2 = sdf2.format(c2.getTime());
//
//                    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//                    jfc.setDialogTitle("Choose a directory to save your file: ");
//                    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//
//                    int returnValue = jfc.showSaveDialog(null);
//                    if (returnValue == JFileChooser.APPROVE_OPTION) {
//                        if (jfc.getSelectedFile().isDirectory()) {
//                            System.out.println("You selected the directory: " + jfc.getSelectedFile());
//                        }
//                    }
//
//                    //Write the workbook in file system
//                    file = new File(jfc.getSelectedFile() + "\\" + "Die Tool History Data  " + strDate2 + ".xls");
//                    FileOutputStream fileop = new FileOutputStream(file);
//                    workbook.write(fileop);
//                    workbook.close();
//                    fileop.close();
//                    //System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
//                } catch (IOException Ie) {
//                    Ie.printStackTrace();
//                }
//
//                JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
//end all data into exel file with class
