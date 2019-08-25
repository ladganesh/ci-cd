/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.assets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Rajesh
 */
public class BGJPanelTop  extends JPanel {

    private InnerPanel panel;

    public BGJPanelTop() {
        setLayout(new BorderLayout ());
        panel = new InnerPanel();
        add(panel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent ( g );
        

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
        
        int h = getHeight();
        
            Color color1 = new Color(118,197,240);
        Color color2 = new Color(88,147,180);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);

        gb.setPaint(gp);
        gb.fillRect(0, 0, width, 630);
        gb.dispose();

        // Set a rounded clipping region:
        RoundRectangle2D r = new RoundRectangle2D.Float(0, 0, width, h, 0, 0);
        g.setClip(r);

        // Draw the rectangle (and see whether it has round corners)
        g.drawImage(bi, 0, 0, null);
    

    }
}
    

