/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import eu.hansolo.steelseries.gauges.Radial;
import eu.hansolo.steelseries.tools.LedColor;

public class TestGauge {
    private static void createAndShowUI() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);

        JPanel panel = new JPanel() {
            @Override 
            public Dimension getPreferredSize() {
                return new Dimension(250, 250);
            }
        };

        final Radial gauge = new Radial();
        gauge.setMaxValue ( 150 );
        gauge.setTitle("OEE");
        gauge.setThreshold ( 75);
        gauge.setLedColor ( LedColor.GREEN_LED );
        //gauge.setUnitString("Some units");

        panel.setLayout(new BorderLayout());
        panel.add(gauge, BorderLayout.CENTER);
        frame.add(panel);

        JPanel buttonsPanel = new JPanel();

        frame.add(buttonsPanel, BorderLayout.NORTH);

        Thread t = new Thread(){
           
            public void run(){
                while(true){
                    for ( int i = 1 ; i <= 100 ; i ++ ) {
                        if( i <  100){
                            try{   
                            sleep( 500 ) ;
                            double value = Double.valueOf( i );
                                    gauge.setValueAnimated(value);
                            }catch(   InterruptedException e   ){

                        }
                        }else{
                            i=0 ;
                        }
                }                
            }
            }
        };
        
        t.start() ;
        
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
                //RoundGauge rg = new RoundGauge() ;
                
            }
        });
    }
}
