package com.ctn.TestPrograms;

import javax.swing.*;

import com.ctn.FitsDocument;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Test2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        final JFrame frame = new JFrame("Image Rescaling Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        final JPanel imagePanel = new ImagePanel();
        frame.add(imagePanel, BorderLayout.CENTER);

        JButton button = new JButton("Load Image");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Load the image and set it in the panel
                BufferedImage image = loadImage();
                ((ImagePanel) imagePanel).setImage(image);
                frame.repaint();
            }
        });
        frame.add(button, BorderLayout.SOUTH);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // Rescale the image based on the new frame size and repaint the panel
                BufferedImage image = ((ImagePanel) imagePanel).getImage();
                if (image != null) {
                    BufferedImage scaledImage = scaleImage(image, imagePanel.getHeight(), imagePanel.getHeight());
                    ((ImagePanel) imagePanel).setImage(scaledImage);
                    frame.repaint();
                }
            }
        });

        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private static BufferedImage loadImage() {
        // Load and return the image here
        // Example:
        // BufferedImage image = ImageIO.read(new File("path/to/image.png"));
        // return image;
        return FitsDocument.getImagePNG("C:/Users/nadee/Downloads/RemoteAstrophotography-com-NGC2070-narrowband/Tarantula Nebula-oiii.fit");
    }

    private static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //g2d.setTransform(new AffineTransform());
        g2d.drawImage(image, 0, 0, height, height, null);
        g2d.dispose();
        return scaledImage;
    }
}

class ImagePanel extends JPanel {
    private BufferedImage image;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getHeight(), getHeight(), null);
        }
    }
}
