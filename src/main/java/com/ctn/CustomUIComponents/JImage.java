package com.ctn.CustomUIComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


public class JImage extends JPanel{
    private BufferedImage image;
    private int width, height;


    //Methods for getting and setting the image
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }


    //Methods to change image dimensions
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDimensions(int width, int height) {
        setWidth(width);
        setHeight(height);
    }


    //Method to display image
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, width, height, null);
        }
    }
}
