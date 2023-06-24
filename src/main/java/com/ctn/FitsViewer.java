package com.ctn;

import nom.tam.fits.*;
import nom.tam.util.Cursor;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

public class FitsViewer {

    public static void main(String[] args) {
        try {
            Fits fits = new Fits("C:/Users/nadee/Downloads/RemoteAstrophotography-com-NGC2070-narrowband/Tarantula Nebula-oiii.fit");
            BasicHDU imageHDU = fits.getHDU(0);
            Object data = imageHDU.getKernel();
            // Access and process FITS image data
            //int[][] counts = (int[][])  fits.getHDU(0).getKernel();

            System.out.println(fits.getNumberOfHDUs());
            // Perform operations on the image data

            // Read the first HDU

            // Get the image data from the HDU
            Cursor<String, HeaderCard> header = imageHDU.getHeader().iterator();
            
            while(header.hasNext()){
                System.out.println(header.next().toString().trim());
            }
            

            // Check if the data is a 2D array (image)
            if (data instanceof float[][]) {
                
                float[][] imageData = (float[][]) data;
                
                // Normalize the image data to 0-255 range
                float min = Float.POSITIVE_INFINITY;
                float max = Float.NEGATIVE_INFINITY;
                int count = 0;
                for (float[] row : imageData) {
                    for (float pixelValue : row) {
                        min = Math.min(min, pixelValue);
                        max = Math.max(max, pixelValue);
                        if (pixelValue > 255) count++;
                    }
                }
                float range = max - min;
                int height = imageData.length;
                int width = imageData[0].length;
                
                System.out.println("min: " + min + ", max: " + max);
                System.out.println("count: " + count + "/" + width*height);

                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
                int[][] intData = new int[height][width];

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        float pixelValue = imageData[y][x];
                        int normalizedPixel = Math.min((int) pixelValue, 255); //((pixelValue - min) / range * 255);
                        int pixel = (normalizedPixel << 16) | (normalizedPixel << 8) | normalizedPixel;
                        image.setRGB(x, y, (int) pixel);
                        intData[y][x] = pixel;
                    }
                }
            
                 
                // Create a JFrame to hold the image
                JFrame frame = new JFrame("Image Display");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Create a JLabel to hold the image
                JLabel imageLabel = new JLabel();
                
                // Load the image from file 
                ImageIcon originalIcon = new ImageIcon(image);
                Image scaledIcon = originalIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
               
                
                // Set the image on the JLabel
                imageLabel.setIcon(new ImageIcon(scaledIcon));
                
                // Add the JLabel to the JFrame
                frame.getContentPane().add(imageLabel);
                
                // Adjust the frame size to fit the image
                frame.pack();
                
                // Make the frame visible
                frame.setVisible(true);
            }



            /*
            // Check if the data is a 2D array (image)
            if (data instanceof float[][]) {
                float[][] imageData = (float[][]) data;
                
                // Process the image data here
                System.out.println("(rows, cols): (" + imageData[0].length + ", " + imageData.length + ")");

                for (int i = 0; i<100; i++){
                    for (int j = 0; j<100; j++){
                        System.out.print(imageData[i][j]+ ", ");
                    }

                    System.out.println();
                }
            }
            */

            fits.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
