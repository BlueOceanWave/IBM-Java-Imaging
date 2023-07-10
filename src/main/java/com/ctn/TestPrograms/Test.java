package com.ctn.TestPrograms;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Resize Components in TabbedPane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        final JPanel tab1 = new JPanel();
        tab1.setLayout(new BorderLayout());
        tab1.add(createResizableComponent("Component 1"), BorderLayout.CENTER);

        final JPanel tab2 = new JPanel();
        tab2.setLayout(new BorderLayout());
        tab2.add(createResizableComponent("Component 2"), BorderLayout.CENTER);

        tabbedPane.addTab("Tab 1", tab1);
        tabbedPane.addTab("Tab 2", tab2);

        // Add a property change listener to the frame
        frame.addPropertyChangeListener("bounds", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // Resize the components within the tabbed pane
                tab1.revalidate();
                tab2.revalidate();
            }
        });

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private static Component createResizableComponent(String text) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(200, 200));
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }
}
