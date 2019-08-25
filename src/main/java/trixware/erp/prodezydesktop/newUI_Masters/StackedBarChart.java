/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.newUI_Masters;

/**
 *
 * @author WIN7
 */
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class StackedBarChart extends ApplicationFrame {

  public StackedBarChart(String titel) {
  super(titel);
      DatasetGroup d=new DatasetGroup();
     
  final CategoryDataset dataset = createDataset1();
  final CategoryDataset dataset1 = createDataset();
  final JFreeChart chart = createChart(dataset);
  final JFreeChart chart1 = createChart(dataset1);
  final ChartPanel chartPanel = new ChartPanel(chart);
  chartPanel.setPreferredSize(new java.awt.Dimension(500, 350));
  chartPanel.setChart(chart1);
  setContentPane(chartPanel);
  }

  private CategoryDataset createDataset1() {
  double[][] data = new double[][]{
  {210, 300, 320, 265, 299, 200},
  {200, 304, 201, 201, 340, 300},
  };
  
  
    Comparable[] c = new Comparable[2];
    for(int i=0;i<=1;i++)
    {
        c[i]="a :"+i;
    }
    Comparable[] b = new Comparable[6];
    for(int i=0;i<=5;i++)
    {
        b[i]="b :"+i;
    }
    CategoryDataset d=null;
    try{
        d=DatasetUtilities.createCategoryDataset( c,b, data);
       
    }catch(Exception ex){System.err.println(ex);}
          return d;
  }
 private CategoryDataset createDataset() {
  double[][] data = new double[][]{
  {100, 1010, 100, 01, 100, 100},
  {200, 304, 201, 201, 340, 300},
  };
  
  
    Comparable[] c = new Comparable[2];
    for(int i=0;i<=1;i++)
    {
        c[i]="a :"+i;
    }
    Comparable[] b = new Comparable[6];
    for(int i=0;i<=5;i++)
    {
        b[i]="b :"+i;
    }
    CategoryDataset d=null;
    try{
        d=DatasetUtilities.createCategoryDataset( c,b, data);
       
    }catch(Exception ex){System.err.println(ex);}
          return d;
  }
  private JFreeChart createChart(final CategoryDataset dataset) {

  final JFreeChart chart = ChartFactory.createStackedBarChart(
  "Stacked Bar Chart ", "", "Score",
  dataset, PlotOrientation.VERTICAL, true, true, false);

  chart.setBackgroundPaint(new Color(249, 231, 236));

  CategoryPlot plot = chart.getCategoryPlot();
  plot.getRenderer().setSeriesPaint(0, new Color(128, 0, 0));
  plot.getRenderer().setSeriesPaint(1, new Color(0, 0, 255));

  return chart;
  }

  public static void main(final String[] args) {

  final StackedBarChart demo = 
  new StackedBarChart("Stacked Bar Chart");
  demo.pack();
  RefineryUtilities.centerFrameOnScreen(demo);
  demo.setVisible(true);
  }
}