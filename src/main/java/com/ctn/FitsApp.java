package com.ctn;

import nom.tam.fits.*;
import nom.tam.util.*;

import com.ctn.CustomUIComponents.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.jfree.chart.*;
import org.jfree.data.statistics.*;



public class FitsApp implements ActionListener{
    final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    //Global variables
    String imageFilePath = "ExampleFitsFiles\\Tarantula Nebula-halpha.fit";

    //Main frame
    JFrame f;    
    JMenuBar mb;    
    JMenu file,edit,help;    
    JMenuItem cut,copy,paste,selectAll,open,save;    
    JPanel tab1, tab2, tab3;
    JTabbedPane tp;

    //Tab 1
    JPanel imagePanel;
    JLabel imageLabel = null, fitsImageName;
    JImage fitsImage = null;
    ChartPanel histogramImage = null;
    JButton reloadImage, loadHistogram;
    


    public static void main(String[] args) { 
        //Set the look of the GUI to the OS default
        // try {
        //    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // } catch (Exception e) {e.printStackTrace();}


        new FitsApp();    
    }   
    
    
    FitsApp(){    
        f = new JFrame();  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize((int)(0.5*SCREEN_WIDTH), (int)(0.8*SCREEN_HEIGHT));    
        

        open = new JMenuItem("Open");
        save = new JMenuItem("Save");

        cut=new JMenuItem("cut");    
        copy=new JMenuItem("copy");    
        paste=new JMenuItem("paste");    
        selectAll=new JMenuItem("selectAll");    
        
        mb=new JMenuBar();    
        file=new JMenu("File");    
        edit=new JMenu("Edit");    
        help=new JMenu("Help");     
        
        file.add(open);file.add(save);

        edit.add(cut);edit.add(copy);edit.add(paste);edit.add(selectAll);    
        mb.add(file);mb.add(edit);mb.add(help);
        //mb.add((new JComboBox(new String[] { "Opt-1", "Opt-2", "Opt-3", "Opt-4" })));    
          
        tp = new JTabbedPane(JTabbedPane.TOP);
        
        createTab1();


        tab2 = new JPanel();
        tab3 = new JPanel();
        tp.add("Tab 1", tab1);
        tp.add("Tab 2", tab2);
        tp.add("Tab 3", tab3);
        tp.setPreferredSize(f.getSize()); 
        

        f.add(tp);
        f.setJMenuBar(mb);  
        f.setVisible(true);  
        
        open.addActionListener(this);
        save.addActionListener(this);
        reloadImage.addActionListener(this);

        System.out.println("dims: " + imageLabel.getWidth() + ", " + imageLabel.getHeight());
    } 

    public void createTab1()
    {
        tab1 = new JPanel();
        tab1.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.weightx=0.5; gbc.weighty=0;

            //Image Name Label
        fitsImageName = new JLabel("Image Name", SwingConstants.CENTER);
        
        //Draw a border around the components
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2); 
        fitsImageName.setBorder(border);
        
        gbc.fill=GridBagConstraints.BOTH;
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.insets = new Insets(0,100,0,0);

        tab1.add(fitsImageName, gbc);

            //Fits Image
        //Load and scale image
        imageLabel = new JLabel("Image");
        BufferedImage image = FitsDocument.getImagePNG(imageFilePath);
        ImageIcon originalIcon = new ImageIcon(image);
        Image scaledIcon = originalIcon.getImage().getScaledInstance((int)(f.getHeight()*0.5), (int)(f.getHeight()*0.5), Image.SCALE_SMOOTH);

        // Set the image on the JLabel
        if (imageLabel != null) tab1.remove(imageLabel); 
        imageLabel = new JLabel();   
        imageLabel.setIcon(new ImageIcon(scaledIcon));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        imageLabel.setBorder(border);

        gbc.fill=GridBagConstraints.BOTH;
        //gbc.weighty=0.5;
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        tab1.add(imageLabel, gbc);
        
            //Reload Button
        reloadImage = new JButton("Reload Image");
        reloadImage.setBorder(border);
        gbc.fill=GridBagConstraints.NONE;
        gbc.weighty=0.5;
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.insets = new Insets(0,0,0,0);
        tab1.add(reloadImage, gbc);

            //Histogram Button
        loadHistogram = new JButton("Load Histogram");
        loadHistogram.setBorder(border);
        gbc.fill=GridBagConstraints.NONE;
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.insets = new Insets(0,50,0,0);
        tab1.add(loadHistogram, gbc);

            //Histogram Chart
        JFreeChart chart = ChartFactory.createHistogram("", "X-axis", "Frequency", new HistogramDataset());
        histogramImage = new ChartPanel(chart);
        //histogramImage.setPreferredSize(new Dimension(400, 300));
        histogramImage.setBorder(border);
        gbc.fill=GridBagConstraints.BOTH;
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.insets = new Insets(20,50,20,-50);
        tab1.add(histogramImage, gbc);


    }

    public void actionPerformed(ActionEvent e) 
    {  
        Object action = e.getSource();
        

        if(action==open)
            openFile();
        if(action==save)
            System.out.println("Saving file");
        if(action==reloadImage)
            loadImage(imageFilePath);
    }     

    public void openFile() 
    { 
        //Open File Explorer
        JSystemFileChooser fc=new JSystemFileChooser();  
        fc.setMultiSelectionEnabled(true);  
        int i=fc.showOpenDialog(f);
        
        //Proceed with the selected option
        if(i==JFileChooser.APPROVE_OPTION)
        {    
            //Get file
            File file = fc.getSelectedFile();    
            imageFilePath = file.getPath();   
            System.out.println("Path: " + imageFilePath);

            //Display file
            loadImage(imageFilePath);
        }
    }

    public void loadImage(String filepath)
    {
        // Load the image from file 
        BufferedImage image = FitsDocument.getImagePNG(filepath);
        ImageIcon originalIcon = new ImageIcon(image);
        int dim = Math.min(imageLabel.getHeight(), imageLabel.getWidth());
        dim = (int)(f.getHeight()*0.5);
        Image scaledIcon = originalIcon.getImage().getScaledInstance(dim, dim, Image.SCALE_SMOOTH);
        
        
        // Set the image on the JLabel  
        imageLabel.setIcon(new ImageIcon(scaledIcon));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        f.revalidate();
    
    }

}