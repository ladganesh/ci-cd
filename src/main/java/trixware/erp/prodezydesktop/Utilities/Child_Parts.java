/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

/**
 *
 * @author 
 * 
 * created by HArshali for update BOM of Assembled Parts 27-Jun-2019
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trixware.erp.prodezydesktop.masters.AssembledParts;
import trixware.erp.prodezydesktop.masters.Child_PartUI;
 
public class Child_Parts extends JDialog {
 
      Child_PartUI childParts ;
      AssembledParts assembled;
      JButton save_btn, cancle_btn;
    public Child_Parts()
    {
        
    }
       public Child_Parts(Frame parent, int FG_ID) 
       {
        super(parent, "Child Part and Bought out RM List", true);
        
        
        childParts = new Child_PartUI(FG_ID );
        childParts.setBounds(0, 0, 940,500);
 
        getContentPane().add( childParts  , BorderLayout.CENTER);
        
        pack();
        setResizable(true);
        setBounds(0, 0, 940 ,550);
        
        
        setLocationRelativeTo(parent);

        save_btn = new JButton();
        save_btn.setOpaque(false);
        save_btn.setBounds(300, 450, 150, 36);
        save_btn.setText("Save & Close");
        save_btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                childParts.onsaveButton();
                dispose();
            }
        });
        getContentPane().add(save_btn);
        
        cancle_btn = new JButton();
        cancle_btn.setOpaque(false);
        cancle_btn.setBounds(480, 450, 150, 36);
        cancle_btn.setText("Discard & Close");
        cancle_btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        getContentPane().add(cancle_btn);
        setLayout(null);
        setDefaultCloseOperation ( JDialog.DISPOSE_ON_CLOSE);
        setBackground( Color.WHITE);
      //  this.getRootPane().setDefaultButton(btnLogin);
    }
      public Child_Parts(Frame parent) 
      {
        super(parent, "Assembled Parts List", true);
        
       
        assembled = new AssembledParts();
        assembled.setBounds(0, 0, 940,500);
 
        getContentPane().add( assembled  , BorderLayout.CENTER);
        
        pack();
        setResizable(true);
        setBounds(0, 0, 940 , 500);
        
        setLocationRelativeTo(parent);
        
        
        
      //  this.getRootPane().setDefaultButton(btnLogin);
    }
    public void closeBOMWindow()
    {
         dispose();
    }
}