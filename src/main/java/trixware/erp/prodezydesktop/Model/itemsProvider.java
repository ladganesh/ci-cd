/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Rajesh
 */
public class itemsProvider extends DefaultComboBoxModel<String> {

    ResultSet rs ;
    
    public itemsProvider(String[] items) {
        super(items);
    }

    @Override
    public String getSelectedItem() {
        // Job selectedJob = (Job) super.getSelectedItem();

        // do something with this job before returning...
        try {

            String value = (String)super.getSelectedItem();
           
            return value;
        } catch (Exception e) {

        }
    
    return null;
    }
}
