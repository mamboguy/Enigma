package View.BasicGUI;

import javax.swing.*;

public class MainGUIFrame extends JFrame {

    public MainGUIFrame() {

        this.setTitle("Enigma v0.1");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setResizable(true);
    }


    @Override
    public void repaint() {
        this.pack();

        super.repaint();
    }
}
