/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation.partmaster;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Akshay
 */
public class TableModiFier {
    public static void setTableSize(JTable jTable1,int size,int width)
    {
        JTableHeader header = jTable1.getTableHeader();
        header.setPreferredSize(new Dimension(100, size+5));//                 jTable1.set
        header.setFont(new Font("Calibri", 1, 13));
        jTable1.setRowHeight(size);
    }
}
