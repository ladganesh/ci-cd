/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import javax.swing.JFrame;


public class PieChartDemo extends JFrame{
//	public static void main(String[] args) {
//		//TODO: Add code to generate PDFs with charts
//                                writeChartToPDF(generateBarChart(), 500, 400, "E://barchart.pdf");
//		writeChartToPDF(generatePieChart(), 500, 400, "E://piechart.pdf");
//	}

                public PieChartDemo(){
  /*                  
                    //ChartPanel cp = new ChartPanel(  generateBarChart ()  );
                    ChartPanel cp = new ChartPanel( generatePieChart () );
                    cp.setPreferredSize ( new java.awt.Dimension(500,300));
                    setContentPane ( cp);
               */     
                }
    
    /*
	public static JFreeChart generatePieChart() {
		
                                DefaultPieDataset dataSet = new DefaultPieDataset();
		
                                dataSet.setValue("China", 19.64);
		dataSet.setValue("India", 17.3);
		dataSet.setValue("United States", 4.54);
		dataSet.setValue("Indonesia", 3.4);
		dataSet.setValue("Brazil", 2.83);
		dataSet.setValue("Pakistan", 2.48);
		dataSet.setValue("Bangladesh", 2.38);

		JFreeChart chart = ChartFactory.createPieChart(
				"World Population by countries", dataSet, true, true, false);
                 
		return chart;
	}

	public static JFreeChart generateBarChart() {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.setValue(791, "Population", "1750 AD");
		dataSet.setValue(978, "Population", "1800 AD");
		dataSet.setValue(1262, "Population", "1850 AD");
		dataSet.setValue(1650, "Population", "1900 AD");
		dataSet.setValue(2519, "Population", "1950 AD");
		dataSet.setValue(6070, "Population", "2000 AD");

		JFreeChart chart = ChartFactory.createBarChart(
				"World Population growth", "Year", "Population in millions",
				dataSet, PlotOrientation.VERTICAL, false, true, false);

		return chart;
	}
        
        public static void writeChartToPDF(JFreeChart chart, int width, int height, String fileName) {
		PdfWriter writer = null;

		Document document = new Document();

		try {
                                                   File f = new File(fileName);
                                                if(!f.exists ()){
                                                    f.createNewFile ();
                                                }
                    
			writer = PdfWriter.getInstance(document, new FileOutputStream(
					fileName));
                        
                        
			document.open();
			PdfContentByte contentByte = writer.getDirectContent();
			PdfTemplate template = contentByte.createTemplate(width, height);
			Graphics2D graphics2d = template.createGraphics(width, height,
					new DefaultFontMapper());
			Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
					height);

			chart.draw(graphics2d, rectangle2d);
			
			graphics2d.dispose();
			contentByte.addTemplate(template, 0, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		document.close();
	}*/
}