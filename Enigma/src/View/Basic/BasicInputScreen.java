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
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
    private JButton exit;

    private JTextArea plaintext;
    private JTextArea ciphertext;

    private JComboBox rotor1;
    private JComboBox rotor2;
    private JComboBox rotor3;
    private JComboBox rotor4;
    private JComboBox reflector;
    private JTextField labelRotor4;
    private JTextField labelRotor3;
    private JTextField labelRotor2;
    private JTextField labelRotor1;
    private JTextField keyRotor4;
    private JTextField keyRotor3;
    private JTextField keyRotor2;
    private JTextField keyRotor1;
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

        labelRotor4 = basicJTextField("labelRotor4", "Ringstellung setting for rotor in 4th slot", comboHeights, comboWidths);
        labelRotor3 = basicJTextField("labelRotor3", "Ringstellung setting for rotor in 3rd slot", comboHeights, comboWidths);
        labelRotor2 = basicJTextField("labelRotor2", "Ringstellung setting for rotor in 2nd slot", comboHeights, comboWidths);
        labelRotor1 = basicJTextField("labelRotor1", "Ringstellung setting for rotor in 1st slot", comboHeights, comboWidths);

        keyRotor4 = basicJTextField("keyRotor4", "Grundstellung setting for rotor in 4th slot", comboHeights, comboWidths);
        keyRotor3 = basicJTextField("keyRotor4", "Grundstellung setting for rotor in 3rd slot", comboHeights, comboWidths);
        keyRotor2 = basicJTextField("keyRotor4", "Grundstellung setting for rotor in 2nd slot", comboHeights, comboWidths);
        keyRotor1 = basicJTextField("keyRotor4", "Grundstellung setting for rotor in 1st slot", comboHeights, comboWidths);

        reflector = basicJComboBox("reflector", "Selector reflector to use", comboHeights, comboWidths);

        //Create text areas
        plaintext = createTextArea("Enter plaintext to encode here");
        ciphertext = createTextArea("Encoded plaintext appears here");

        //Create buttons
        reset = initializeJButton("resetButton", "Reset", "Resets the plaintext and ciphertext fields");
        translate = initializeJButton("encodeButton", "Encode", "Takes the plaintext and translates into ciphertext");
        exit = initializeJButton("exitButton", "Exit", "Exits the application");

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

//        rotor4Panel.add(centeredLabel("Rotor 4"));
//        rotor4Panel.add(standardSpacer());
//        rotor4Panel.add(rotor4);
//        rotor4Panel.add(standardSpacer());
//        rotor4Panel.add(labelRotor4);
//        rotor4Panel.add(standardSpacer());
//        rotor4Panel.add(keyRotor4);

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
        buttonPanel.add(standardSpacer());
        buttonPanel.add(exit);

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

    private JTextField basicJTextField(String name, String toolTipText, int height, int width) {
        JTextField temp = new JTextField();

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

    public void registerListeners(ActionListener al) {
        exit.addActionListener(al);
        reset.addActionListener(al);
        translate.addActionListener(al);
    }
    
    public void updateReflectorCombos(String[] reflectorsAvailable){
        reflector.removeAllItems();
        
        for (int i = 0; i < reflectorsAvailable.length; i++) {
            reflector.addItem(reflectorsAvailable[i]);
        }
    }

    public void updateRotorCombos(String[] rotorsAvailable) {

        rotor1.removeAllItems();

        for (int i = 0; i < rotorsAvailable.length; i++) {
            rotor1.addItem(rotorsAvailable[i]);
            rotor2.addItem(rotorsAvailable[i]);
            rotor3.addItem(rotorsAvailable[i]);
            rotor4.addItem(rotorsAvailable[i]);
        }
    }

    public void resetToDefault() {
        rotor1.setSelectedIndex(2);
        rotor2.setSelectedIndex(1);
        rotor3.setSelectedIndex(0);
        rotor4.setSelectedIndex(0);
        reflector.setSelectedIndex(1);
        
        keyRotor1.setText("A");
        keyRotor2.setText("A");
        keyRotor3.setText("A");
        keyRotor4.setText("A");
        
        labelRotor1.setText("A");
        labelRotor2.setText("A");
        labelRotor3.setText("A");
        labelRotor4.setText("A");
        
        ciphertext.setText("");
        plaintext.setText("");
    }
    
    //TODO - limit labels and keys to 1 char
    //TODO - remove rotor from other comboboxes upon selection
    //TODO - space out plaintext
    //TODO - add setting for plaintext spacing
    //TODO - add plugboard steckering
    
    public String[] getCurrentKeySettings(){
        
        String[] settings = new String[13];
        
        settings[0] = (String) rotor4.getSelectedItem();
        settings[1] = (String) rotor3.getSelectedItem();
        settings[2] = (String) rotor2.getSelectedItem();
        settings[3] = (String) rotor1.getSelectedItem();
        
        settings[4] = labelRotor4.getText();
        settings[5] = labelRotor3.getText();
        settings[6] = labelRotor2.getText();
        settings[7] = labelRotor1.getText();
        
        settings[8] = keyRotor4.getText();
        settings[9] = keyRotor3.getText();
        settings[10] = keyRotor2.getText();
        settings[11] = keyRotor1.getText();
        
        settings[12] = (String) reflector.getSelectedItem();
        
        return settings;
    }
    
    public String getPlaintext(){
        return plaintext.getText();
    }
    
    public void setCiphertext(String ciphertext){
        this.ciphertext.setText(ciphertext);
    }
}
