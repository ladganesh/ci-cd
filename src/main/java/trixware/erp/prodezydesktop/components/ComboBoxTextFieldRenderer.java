/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Rajesh
 */
public class ComboBoxTextFieldRenderer extends JPanel implements ListCellRenderer{

    public ComboBoxTextFieldRenderer () {
    
          
    
    }

    
    
    @Override
    public Component getListCellRendererComponent ( JList list , Object value , int index , boolean isSelected , boolean cellHasFocus ) {
        
        return this ;
    }
    
}
