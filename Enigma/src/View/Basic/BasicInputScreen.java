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
import javax.swing.JLabel;
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
    private static final Dimension SPACER = new Dimension(5, 5);
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
        rotor1Panel = new JPanel();
        rotor2Panel = new JPanel();
        rotor3Panel = new JPanel();
        rotor4Panel = new JPanel();
        reflectorPanel = new JPanel();
        textPanel = new JPanel();
        buttonPanel = new JPanel();

        rotor1Panel.setLayout(new BoxLayout(rotor1Panel, BoxLayout.Y_AXIS));
        rotor2Panel.setLayout(new BoxLayout(rotor2Panel, BoxLayout.Y_AXIS));
        rotor3Panel.setLayout(new BoxLayout(rotor3Panel, BoxLayout.Y_AXIS));
        rotor4Panel.setLayout(new BoxLayout(rotor4Panel, BoxLayout.Y_AXIS));
        reflectorPanel.setLayout(new BoxLayout(reflectorPanel, BoxLayout.Y_AXIS));

        //Create combo boxes for settings
        int comboHeights = 30;
        int comboWidths = 130;
        rotor4 = basicJComboBox("rotor4", "Select rotor to use in 4th slot", comboHeights, comboWidths);
        rotor3 = basicJComboBox("rotor3", "Select rotor to use in 3rd slot", comboHeights, comboWidths);
        rotor2 = basicJComboBox("rotor2", "Select rotor to use in 2nd slot", comboHeights, comboWidths);
        rotor1 = basicJComboBox("rotor1", "Select rotor to use in 1st slot", comboHeights, comboWidths);

        labelRotor4 = basicJComboBox("labelRotor4", "Ringstellung setting for rotor in 4th slot", comboHeights, comboWidths);
        labelRotor3 = basicJComboBox("labelRotor3", "Ringstellung setting for rotor in 3rd slot", comboHeights, comboWidths);
        labelRotor2 = basicJComboBox("labelRotor2", "Ringstellung setting for rotor in 2nd slot", comboHeights, comboWidths);
        labelRotor1 = basicJComboBox("labelRotor1", "Ringstellung setting for rotor in 1st slot", comboHeights, comboWidths);

        keyRotor4 = basicJComboBox("keyRotor4", "Grundstellung setting for rotor in 4th slot", comboHeights, comboWidths);
        keyRotor3 = basicJComboBox("keyRotor4", "Grundstellung setting for rotor in 3rd slot", comboHeights, comboWidths);
        keyRotor2 = basicJComboBox("keyRotor4", "Grundstellung setting for rotor in 2nd slot", comboHeights, comboWidths);
        keyRotor1 = basicJComboBox("keyRotor4", "Grundstellung setting for rotor in 1st slot", comboHeights, comboWidths);

        reflector = basicJComboBox("reflector", "Selector reflector to use", comboHeights, comboWidths);

        //Create text areas
        plaintext = createTextArea("Enter plaintext to encode here");
        ciphertext = createTextArea("Encoded plaintext appears here");

        //Create buttons
        reset = initializeJButton("resetButton", "Reset", "Resets the plaintext and ciphertext fields");
        translate = initializeJButton("translateButton", "Encode", "Takes the plaintext and translates into ciphertext");

        rotor1Panel.add(centeredLabel("Rotor 1"));
        rotor1Panel.add(standardSpacer());
        rotor1Panel.add(rotor1);
        rotor1Panel.add(standardSpacer());
        rotor1Panel.add(labelRotor1);
        rotor1Panel.add(standardSpacer());
        rotor1Panel.add(keyRotor1);

        rotor2Panel.add(centeredLabel("Rotor 2"));
        rotor2Panel.add(standardSpacer());
        rotor2Panel.add(rotor2);
        rotor2Panel.add(standardSpacer());
        rotor2Panel.add(labelRotor2);
        rotor2Panel.add(standardSpacer());
        rotor2Panel.add(keyRotor2);

        rotor3Panel.add(centeredLabel("Rotor 3"));
        rotor3Panel.add(standardSpacer());
        rotor3Panel.add(rotor3);
        rotor3Panel.add(standardSpacer());
        rotor3Panel.add(labelRotor3);
        rotor3Panel.add(standardSpacer());
        rotor3Panel.add(keyRotor3);

        rotor4Panel.add(centeredLabel("Rotor 4"));
        rotor4Panel.add(standardSpacer());
        rotor4Panel.add(rotor4);
        rotor4Panel.add(standardSpacer());
        rotor4Panel.add(labelRotor4);
        rotor4Panel.add(standardSpacer());
        rotor4Panel.add(keyRotor4);

        reflectorPanel.add(centeredLabel("Reflector"));
        reflectorPanel.add(standardSpacer());
        reflectorPanel.add(reflector);

        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        textPanel.add(plaintext);
        textPanel.add(standardSpacer());
        textPanel.add(ciphertext);

        buttonPanel.add(translate);
        buttonPanel.add(standardSpacer());
        buttonPanel.add(reset);

        JPanel textbuttons = new JPanel();
        textbuttons.setLayout(new BoxLayout(textbuttons, BoxLayout.Y_AXIS));
        textbuttons.setAlignmentY(CENTER_ALIGNMENT);
//        textbuttons.setAlignmentX(LEFT_ALIGNMENT);
        textbuttons.add(textPanel);
        textbuttons.add(buttonPanel);

        JPanel rotorPanels = new JPanel();
        rotorPanels.setLayout(new FlowLayout(FlowLayout.CENTER));
        rotorPanels.add(reflectorPanel);
        rotorPanels.add(standardSpacer());
        rotorPanels.add(rotor4Panel);
        rotorPanels.add(standardSpacer());
        rotorPanels.add(rotor3Panel);
        rotorPanels.add(standardSpacer());
        rotorPanels.add(rotor2Panel);
        rotorPanels.add(standardSpacer());
        rotorPanels.add(rotor1Panel);

        //Add panels to master
        masterPanel.setLayout(new BorderLayout(5, 5));
        masterPanel.add(rotorPanels, BorderLayout.CENTER);
        masterPanel.add(textbuttons, BorderLayout.SOUTH);

        this.add(masterPanel);
        this.setTitle("Enigma v0.01");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
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

    private JLabel centeredLabel(String name) {
        JLabel temp = new JLabel(name);
        temp.setAlignmentX(Component.CENTER_ALIGNMENT);

        return temp;
    }

    private Component standardSpacer() {
        return Box.createRigidArea(SPACER);
    }
}