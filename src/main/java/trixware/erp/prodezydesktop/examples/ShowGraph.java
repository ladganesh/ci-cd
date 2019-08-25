/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import javax.swing.JFrame;

/**
 *
 * @author Rajesh
 */
public class ShowGraph {
    
    public static void main(String[] args) {
		//TODO: Add code to generate PDFs with charts
                                
                PieChartDemo PCD = new PieChartDemo ();
                PCD.pack ();
                PCD.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE);
                PCD.setVisible ( true );
	}
    
}
