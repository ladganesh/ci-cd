/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.components;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Rajesh
 */
public class ColumnRenderer{
        
        public  DefaultTableCellRenderer getColumnRenderer() {
      
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            //table.setDefaultRenderer(String.class, centerRenderer);
            
            return centerRenderer ;
        }
    }