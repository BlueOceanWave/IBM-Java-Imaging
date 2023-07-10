package com.ctn.TestPrograms;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

public class HistogramFrameDemo extends JFrame {

    public HistogramFrameDemo() {
        setTitle("Histogram");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prepare data
        HistogramDataset dataset = new HistogramDataset();
        double[] values = { 1.2, 2.5, 1.7, 3.1, 2.8, 2.0};
        dataset.addSeries("Data", values, 3); // The third argument specifies the number of bins

        // Create the chart
        JFreeChart chart = ChartFactory.createHistogram("Histogram", "X-axis", "Frequency", dataset);

        // Create a ChartPanel and add it to the frame
        ChartPanel chartPanel = new ChartPanel(chart);
        
        add(chartPanel);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        HistogramFrameDemo frame = new HistogramFrameDemo();
    }
}
