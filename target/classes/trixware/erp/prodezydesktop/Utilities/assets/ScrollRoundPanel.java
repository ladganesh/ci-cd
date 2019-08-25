/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities.assets;

import trixware.erp.prodezydesktop.Utilities.assets.*;
import static trixware.erp.prodezydesktop.examples.HomeScreen.height;
import static trixware.erp.prodezydesktop.examples.HomeScreen.width;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Rajesh
 */
 public class ScrollRoundPanel extends JScrollPane {
    @Override
    protected void paintComponent(Graphics g) {
        
        int width = 0, height = 0;
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
                GraphicsDevice[] gs = ge.getScreenDevices ();
                for ( int i = 0 ; i < gs.length ; i ++ ) {
                    DisplayMode dm = gs[ i ].getDisplayMode ();
                    if ( width > dm.getWidth () || width == 0 ) {
                        width = dm.getWidth ();
                        height = dm.getHeight ();
                    }
                }
        
        // Prepare a red rectangle
        BufferedImage bi = new BufferedImage(width, 667, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gb = bi.createGraphics();
        gb.setPaint(Color.WHITE);
        gb.fillRect(0, 0, width-20, 630);
        gb.dispose();

        // Set a rounded clipping region:
        RoundRectangle2D r = new RoundRectangle2D.Float(0, 0, width-20, 615, 20, 20);
        g.setClip(r);

        // Draw the rectangle (and see whether it has round corners)
        g.drawImage(bi, 0, 0, null);
    }
}

