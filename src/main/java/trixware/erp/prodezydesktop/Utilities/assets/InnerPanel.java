/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.assets;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Rajesh
 */
public class InnerPanel extends JPanel {

    public InnerPanel() {
        setOpaque ( false);
        // To prove the point
        setBackground ( Color.red );
        JLabel label = new JLabel("");
        add(label);
    }
}