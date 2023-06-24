package com.ctn;

import nom.tam.fits.*;
import nom.tam.util.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

import com.ctn.FitsDocument;

public class FitsApp implements ActionListener{
    JFrame f;    
    JMenuBar mb;    
    JMenu file,edit,help;    
    JMenuItem cut,copy,paste,selectAll,open, save;    
    JTextArea ta;    


    public static void main(String[] args) {    
            new FitsApp();    
    }   
    
    
    FitsApp(){    
        f = new JFrame();  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        open = new JMenuItem("Open");
        save = new JMenuItem("Save");

        cut=new JMenuItem("cut");    
        copy=new JMenuItem("copy");    
        paste=new JMenuItem("paste");    
        selectAll=new JMenuItem("selectAll");    
        
        open.addActionListener(this);
        save.addActionListener(this);
        
        mb=new JMenuBar();    
        file=new JMenu("File");    
        edit=new JMenu("Edit");    
        help=new JMenu("Help");     
        
        file.add(open);file.add(save);

        edit.add(cut);edit.add(copy);edit.add(paste);edit.add(selectAll);    
        mb.add(file);mb.add(edit);mb.add(help);    
          
        //f.add(mb);
        f.setJMenuBar(mb);  
        f.setLayout(new BorderLayout());    
        f.setSize(400,400);    
        f.setVisible(true);    
    } 

    public void actionPerformed(ActionEvent e) 
    { 
        
        Object action = e.getSource();
    

        if(action==open)
            openFile();
        if(action==save)
            System.out.println("Saving file");
    }     

    public void openFile() 
    {
        
        //Open File Explorer
        JFileChooser fc=new JFileChooser();    
        int i=fc.showOpenDialog(f);
        
        //Proceed with the selected option
        if(i==JFileChooser.APPROVE_OPTION)
        {    
            File file = fc.getSelectedFile();    
            String filepath = file.getPath();   
            
 

            // Load the image from file 
            BufferedImage image = FitsDocument.getImagePNG(filepath);
            ImageIcon originalIcon = new ImageIcon(image);
            Image scaledIcon = originalIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            
            
            // Set the image on the JLabel
            JLabel imageLabel = new JLabel();   
            imageLabel.setIcon(new ImageIcon(scaledIcon));
    
            
            // Add the JLabel to the JFrame
            f.add(imageLabel, BorderLayout.CENTER);
            f.revalidate();
            f.pack();
            f.repaint();

            

            // Adjust the frame size to fit the image
            //f.pack();
            
            // Make the frame visible
            //f.setVisible(true);
        }
    }

     

}