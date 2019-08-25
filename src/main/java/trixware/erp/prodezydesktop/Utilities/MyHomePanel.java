/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import java.awt.DisplayMode;
import static trixware.erp.prodezydesktop.examples.HomeScreen.width ;
import static trixware.erp.prodezydesktop.examples.HomeScreen.height        ;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;

/**
 *
 * @author Rajesh
 */
public class MyHomePanel extends javax.swing.JPanel {

    /**
     * Creates new form MyHomePanel
     */
    public MyHomePanel ()  {
        initComponents ();
        
        try{
            File f = new File( getClass().getResource("bg.png").toURI () );
        // File f = new File("bg.png");
            backgroundImage = ImageIO.read(  f );
        //               backgroundImage =   new ImageIcon("bg.png").getImage() ;
        }catch(NullPointerException e){
                        System.out.println ( ""+e.getMessage () );
        }   
        catch(IOException e){
                    System.out.println ( ""+e.getMessage () );
        }catch(URISyntaxException e){
                        System.out.println ( ""+e.getMessage () );
        } 
    }

    @Override
    protected void paintComponent ( Graphics g ) {

        super.paintComponent ( g );
        
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
                GraphicsDevice[] gs = ge.getScreenDevices ();
                for ( int i = 0 ; i < gs.length ; i ++ ) {
                    DisplayMode dm = gs[ i ].getDisplayMode ();
                    if ( width > dm.getWidth () || width == 0 ) {
                        width = dm.getWidth ();
                        height = dm.getHeight ();
                    }
                }

            g.drawImage (backgroundImage, 0, 0, this);
          
    }
    
     @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private Image backgroundImage  = null ;

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
