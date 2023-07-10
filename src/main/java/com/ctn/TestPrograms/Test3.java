package com.ctn.TestPrograms;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Test3 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Resize Components in TabbedPane Example");
        JLabel a = new JLabel("one");
        JLabel b = new JLabel("two");

        a.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(a, BorderLayout.SOUTH);
        frame.add(b, BorderLayout.SOUTH);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

}
