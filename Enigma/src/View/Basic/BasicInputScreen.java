/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Basic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Date Created Nov 19, 2017
 *
 * @author Michael C
 */
public class BasicInputScreen
        extends JFrame {

    //<editor-fold desc="Constants">
    private static final Component STANDARD_X_SPACER = Box.createRigidArea(new Dimension(5, 0));
    private static final Component STANDARD_Y_SPACER = Box.createRigidArea(new Dimension(0, 5));
//</editor-fold>

    //<editor-fold desc="Private Variables">
    private JPanel masterPanel;
    private JPanel rotor1Panel;
    private JPanel rotor2Panel;
    private JPanel rotor3Panel;
    private JPanel rotor4Panel;
    private JPanel reflectorPanel;
    private JPanel textPanel;
    private JPanel buttonPanel;

    private JButton reset;
    private JButton translate;

    private JTextArea plaintext;
    private JTextArea ciphertext;

    private JComboBox rotor1;
    private JComboBox rotor2;
    private JComboBox rotor3;
    private JComboBox rotor4;
    private JComboBox reflector;
    private JComboBox labelRotor4;
    private JComboBox labelRotor3;
    private JComboBox labelRotor2;
    private JComboBox labelRotor1;
    private JComboBox keyRotor4;
    private JComboBox keyRotor3;
    private JComboBox keyRotor2;
    private JComboBox keyRotor1;
    //</editor-fold>

    public BasicInputScreen() {
        masterPanel = new JPanel();
        masterPanel.setLayout(new BorderLayout(5,5));

        rotor1Panel = new JPanel();
        rotor2Panel = new JPanel();
        rotor3Panel = new JPanel();
        rotor4Panel = new JPanel();

        rotor1Panel.setLayout(new BoxLayout(rotor1Panel, BoxLayout.Y_AXIS));
        rotor2Panel.setLayout(new BoxLayout(rotor2Panel, BoxLayout.Y_AXIS));
        rotor3Panel.setLayout(new BoxLayout(rotor3Panel, BoxLayout.Y_AXIS));
        rotor4Panel.setLayout(new BoxLayout(rotor4Panel, BoxLayout.Y_AXIS));

        reflectorPanel = new JPanel();

        int rotorHeights = 30;
        int rotorWidths = 130;
        rotor4 = basicJComboBox("rotor4", "Select rotor to use in 4th slot", rotorHeights, rotorWidths);
        rotor3 = basicJComboBox("rotor3", "Select rotor to use in 3rd slot", rotorHeights, rotorWidths);
        rotor2 = basicJComboBox("rotor2", "Select rotor to use in 2nd slot", rotorHeights, rotorWidths);
        rotor1 = basicJComboBox("rotor1", "Select rotor to use in 1st slot", rotorHeights, rotorWidths);

        labelRotor4 = basicJComboBox("labelRotor4", "Ringstellung setting for rotor in 4th slot", rotorHeights, rotorWidths);
        labelRotor3 = basicJComboBox("labelRotor3", "Ringstellung setting for rotor in 3rd slot", rotorHeights, rotorWidths);
        labelRotor2 = basicJComboBox("labelRotor2", "Ringstellung setting for rotor in 2nd slot", rotorHeights, rotorWidths);
        labelRotor1 = basicJComboBox("labelRotor1", "Ringstellung setting for rotor in 1st slot", rotorHeights, rotorWidths);

        keyRotor4 = basicJComboBox("keyRotor4", "Grundstellung setting for rotor in 4th slot", rotorHeights, rotorWidths);
        keyRotor3 = basicJComboBox("keyRotor4", "Grundstellung setting for rotor in 3rd slot", rotorHeights, rotorWidths);
        keyRotor2 = basicJComboBox("keyRotor4", "Grundstellung setting for rotor in 2nd slot", rotorHeights, rotorWidths);
        keyRotor1 = basicJComboBox("keyRotor4", "Grundstellung setting for rotor in 1st slot", rotorHeights, rotorWidths);

        //Adjust text areas
        plaintext = createTextArea("Enter plaintext to encode here");
        ciphertext = createTextArea("Encoded plaintext appears here");

        reflector = basicJComboBox("reflector", "Selector reflector to use", rotorHeights, rotorWidths);

        reset = initializeJButton("resetButton", "Reset", "Resets the plaintext and ciphertext fields");
        translate = initializeJButton("translateButton", "Encode", "Takes the plaintext and translates into ciphertext");

        rotor1Panel.add(rotor1);
        rotor1Panel.add(STANDARD_Y_SPACER);
        rotor1Panel.add(labelRotor1);
        rotor1Panel.add(STANDARD_Y_SPACER);
        rotor1Panel.add(keyRotor1);

        rotor2Panel.add(rotor2);
        rotor2Panel.add(STANDARD_Y_SPACER);
        rotor2Panel.add(labelRotor2);
        rotor2Panel.add(STANDARD_Y_SPACER);
        rotor2Panel.add(keyRotor2);

        rotor3Panel.add(rotor3);
        rotor3Panel.add(STANDARD_Y_SPACER);
        rotor3Panel.add(labelRotor3);
        rotor3Panel.add(STANDARD_Y_SPACER);
        rotor3Panel.add(keyRotor3);

        rotor4Panel.add(rotor4);
        rotor4Panel.add(STANDARD_Y_SPACER);
        rotor4Panel.add(labelRotor4);
        rotor4Panel.add(STANDARD_Y_SPACER);
        rotor4Panel.add(keyRotor4);

        reflectorPanel.add(reflector);

        JPanel rotorPanels = new JPanel();
        rotorPanels.setLayout(new FlowLayout(FlowLayout.CENTER));
        rotorPanels.add(reflector);
        rotorPanels.add(STANDARD_X_SPACER);
        rotorPanels.add(rotor3);
        rotorPanels.add(STANDARD_X_SPACER);
        rotorPanels.add(rotor2);
        rotorPanels.add(STANDARD_X_SPACER);
        rotorPanels.add(rotor1);

        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        textPanel.add(plaintext);
        textPanel.add(STANDARD_X_SPACER);
        textPanel.add(ciphertext);

        buttonPanel = new JPanel();
        buttonPanel.add(translate);
        rotor1Panel.add(STANDARD_X_SPACER);
        buttonPanel.add(reset);

        JPanel textbuttons = new JPanel();
        textbuttons.setLayout(new BoxLayout(textbuttons, BoxLayout.Y_AXIS));
        textbuttons.setAlignmentY(CENTER_ALIGNMENT);
        textbuttons.setAlignmentX(LEFT_ALIGNMENT);
        textbuttons.add(textPanel);
        textbuttons.add(Box.createRigidArea(new Dimension(0, 5)));
        textbuttons.add(buttonPanel);

        //Add panels to master
        masterPanel.add(rotorPanels, BorderLayout.CENTER);
        masterPanel.add(textbuttons, BorderLayout.SOUTH);

        this.add(masterPanel);
        this.setTitle("Enigma v0.01");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
//        this.setResizable(false);
    }

    private JButton initializeJButton(String buttonName, String buttonText, String toolTip) {
        JButton temp = new JButton(buttonText);

        temp.setToolTipText(toolTip);
        temp.setName(buttonName);
        temp.setPreferredSize(new Dimension(120, 50));

        return temp;
    }

    private JTextArea createTextArea(String toolTipText) {
        JTextArea temp = new JTextArea(5, 20);
        temp.setLineWrap(true);
        temp.setMinimumSize(new Dimension(120, 60));
        temp.setToolTipText(toolTipText);

        return temp;
    }

    private JComboBox basicJComboBox(String name, String toolTipText, int height, int width) {
        JComboBox temp = new JComboBox();

        temp.setName(name);
        temp.setPreferredSize(new Dimension(width, height));
        temp.setMinimumSize(new Dimension(width, height));
        temp.setToolTipText(toolTipText);

        return temp;
    }
}
