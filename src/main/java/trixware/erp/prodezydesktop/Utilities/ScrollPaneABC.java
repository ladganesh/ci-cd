/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Rajesh
 */
public class ScrollPaneABC extends  JScrollPane{
    
    public static JPanel abc = null;
     int width = 0, height = 0;
    @Override
    protected void paintComponent(Graphics g) {
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
        GraphicsDevice[] gs = ge.getScreenDevices ();
        for ( int i = 0 ; i < gs.length ; i ++ ) {
            DisplayMode dm = gs[ i ].getDisplayMode ();
            if ( width > dm.getWidth () || width == 0 ) {
                width = dm.getWidth ();
                height = dm.getHeight ();
            }
        }
        
        setBounds(10 , 75 , (width-20) , 615 );
        
        if(abc!=null){
             abc.setBounds ( 5, 5, width-30 , 605);
        }
    }

  
    
    
    
    public ScrollPaneABC () {
       
        abc = new JPanel();
        abc.setBounds ( 5, 5, width-30 , 605);
        abc.revalidate ();
        abc.repaint ();
        add(abc) ;
       // setViewportView ( abc);
      
        setVerticalScrollBarPolicy ( VERTICAL_SCROLLBAR_AS_NEEDED );
        setHorizontalScrollBarPolicy ( HORIZONTAL_SCROLLBAR_AS_NEEDED );
    }
    
}
