/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities.assets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Rajesh
 */
 public class RoundPanel2 extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        // Prepare a red rectangle
        BufferedImage bi = new BufferedImage(500, 60, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gb = bi.createGraphics();
        gb.setPaint(Color.WHITE);
        gb.fillRect(0, 0, 500, 60);
        gb.dispose();

        // Set a rounded clipping region:
        RoundRectangle2D r = new RoundRectangle2D.Float(0, 0, 500, 60, 10, 10);
        g.setClip(r);

        // Draw the rectangle (and see whether it has round corners)
        g.drawImage(bi, 0, 0, null);
    }
}