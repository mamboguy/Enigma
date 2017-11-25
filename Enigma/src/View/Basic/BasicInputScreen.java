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
import java.awt.event.KeyListener;
import java.util.ArrayList;
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
    private static final int MAX_ROTORS = 4;
    private static final ArrayList<Integer> ROTOR_DEFAULTS = new ArrayList<Integer>() {
        {
            add(2);
            add(1);
            add(0);
            add(0);
        }
    };
    private static final int VALID_CHARS = 26;
//</editor-fold>

    //<editor-fold desc="Private Variables">
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

    private ArrayList<JComboBox> rotorCombos = new ArrayList<JComboBox>();
    private ArrayList<Integer> rotorSelectionHistory = new ArrayList<Integer>();
    private ArrayList<Boolean> rotorsInUse = new ArrayList<Boolean>();

    private ArrayList<JTextField> plugboardFields = new ArrayList<JTextField>();
    //</editor-fold>

    public BasicInputScreen() {
        JPanel masterPanel = new JPanel();
        JPanel rotor1Panel = new JPanel();
        JPanel rotor2Panel = new JPanel();
        JPanel rotor3Panel = new JPanel();
        JPanel rotor4Panel = new JPanel();
        JPanel reflectorPanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel plugboardPanel = new JPanel();
        JPanel plugboardTopRow = new JPanel();
        JPanel plugboardBotRow = new JPanel();

        rotor1Panel.setLayout(new BoxLayout(rotor1Panel, BoxLayout.Y_AXIS));
        rotor2Panel.setLayout(new BoxLayout(rotor2Panel, BoxLayout.Y_AXIS));
        rotor3Panel.setLayout(new BoxLayout(rotor3Panel, BoxLayout.Y_AXIS));
        rotor4Panel.setLayout(new BoxLayout(rotor4Panel, BoxLayout.Y_AXIS));
        reflectorPanel.setLayout(new BoxLayout(reflectorPanel, BoxLayout.Y_AXIS));
        plugboardPanel.setLayout(new BoxLayout(plugboardPanel, BoxLayout.Y_AXIS));
        plugboardTopRow.setLayout(new BoxLayout(plugboardTopRow, BoxLayout.X_AXIS));
        plugboardBotRow.setLayout(new BoxLayout(plugboardBotRow, BoxLayout.X_AXIS));

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

        //todo - Add to for loop using ArrayLists for scalability
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

        for (int i = 0; i < VALID_CHARS; i++) {
            String name = (char) (i + 65) + "Field";
            String toolTipText = "Type in letter that will swap with " + (char) (i + 65);
            plugboardFields.add(basicJTextField(name, toolTipText, 30, 30));
        }

        for (int i = 0; i < VALID_CHARS / 2; i++) {
            JPanel temp = new JPanel();
            temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
            temp.add(plugboardMiniPanel(i));
            temp.add(plugboardFields.get(i));
            plugboardFields.get(i).setHorizontalAlignment(JTextField.CENTER);
            plugboardTopRow.add(temp);
        }

        for (int i = VALID_CHARS / 2; i < VALID_CHARS; i++) {
            JPanel temp = new JPanel();
            temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
            temp.add(plugboardMiniPanel(i));
            temp.add(plugboardFields.get(i));
            plugboardFields.get(i).setHorizontalAlignment(JTextField.CENTER);
            plugboardBotRow.add(temp);
        }

        plugboardPanel.add(centeredLabel("Plugboard Settings"));
        plugboardPanel.add(standardSpacer());
        plugboardPanel.add(plugboardTopRow);
        plugboardPanel.add(standardSpacer());
        plugboardPanel.add(plugboardBotRow);

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

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        settingsPanel.add(rotorPanels);
        settingsPanel.add(standardSpacer());
        settingsPanel.add(plugboardPanel);

        //Add panels to master
        masterPanel.setLayout(new BorderLayout(5, 5));
        masterPanel.add(settingsPanel, BorderLayout.CENTER);
        masterPanel.add(textbuttons, BorderLayout.SOUTH);

        rotorCombos.add(rotor1);
        rotorCombos.add(rotor2);
        rotorCombos.add(rotor3);
        rotorCombos.add(rotor4);

        for (int i = 0; i < ROTOR_DEFAULTS.size(); i++) {
            rotorSelectionHistory.add(ROTOR_DEFAULTS.get(i));
            rotorsInUse.add(true);
        }

        rotorsInUse.set(3, false);

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

    public void registerListeners(ActionListener al, KeyListener kl) {
        exit.addActionListener(al);
        reset.addActionListener(al);
        translate.addActionListener(al);

        for (int i = 0; i < rotorCombos.size(); i++) {
            rotorCombos.get(i).addActionListener(al);
        }

        for (int i = 0; i < plugboardFields.size(); i++) {
            plugboardFields.get(i).addKeyListener(kl);
        }
    }

    public void updateReflectorCombos(String[] reflectorsAvailable) {
        reflector.removeAllItems();

        for (int i = 0; i < reflectorsAvailable.length; i++) {
            reflector.addItem(reflectorsAvailable[i]);
        }
    }

    public void updateRotorCombos(String[] rotorsAvailable) {

        for (int i = 0; i < rotorCombos.size(); i++) {
            rotorCombos.get(i).removeAllItems();
        }

        for (int i = 0; i < rotorsAvailable.length; i++) {

            for (int j = 0; j < rotorCombos.size(); j++) {
                rotorCombos.get(j).addItem(rotorsAvailable[i]);
            }
        }
    }

    public void resetToDefault() {
        for (int i = 0; i < rotorCombos.size(); i++) {
            rotorCombos.get(i).setSelectedIndex(ROTOR_DEFAULTS.get(i));
        }
        reflector.setSelectedIndex(1);

        keyRotor1.setText("L");
        keyRotor2.setText("E");
        keyRotor3.setText("A");
        keyRotor4.setText("A");

        labelRotor1.setText("C");
        labelRotor2.setText("N");
        labelRotor3.setText("C");
        labelRotor4.setText("A");

        ciphertext.setText("");
        plaintext.setText("");

        for (int i = 0; i < rotorCombos.size(); i++) {
            rotorSelectionHistory.set(i, ROTOR_DEFAULTS.get(i));
        }
        
        for (int i = 0; i < plugboardFields.size(); i++) {
            plugboardFields.get(i).setText("");
        }
    }

    //TODO - limit labels and keys to 1 char
    //TODO - remove rotor from other comboboxes upon selection
    //TODO - space out plaintext
    //TODO - add setting for plaintext spacing
    //TODO - add plugboard steckering
    public String[] getCurrentKeySettings() {

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

    public String getPlaintext() {
        return plaintext.getText();
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext.setText(ciphertext);
    }

    public void setCurrentKeyPosition(boolean fourthRotor, String[] keys) {

        int i = 2;

        if (fourthRotor) {
            i = 3;
            this.keyRotor4.setText(keys[0]);
        }

        this.keyRotor1.setText(keys[i]);
        i--;
        this.keyRotor2.setText(keys[i]);
        i--;
        this.keyRotor3.setText(keys[i]);
    }

    /**
     * Checks to see if a rotor name is in use and if so, returns which rotor is
     * using it
     *
     * @param name - The name of the rotor that needs to be checked if in use
     * @return - The location of the rotor using that name - Returns 0 if not in
     * use
     */
    public int isRotorAlreadySelected(int selectionIndex) {
        int i = 0;

        for (int j = 0; j < rotorSelectionHistory.size(); j++) {
            if (rotorSelectionHistory.get(j) == selectionIndex && rotorsInUse.get(j)) {
                i = j + 1;
            }
        }

        return i;
    }

    public void swapRotorCombos(int rotorSelectedByUser, int duplicateRotor) {
        rotorSelectedByUser--;
        duplicateRotor--;

        int swappedSelection = rotorCombos.get(duplicateRotor).getSelectedIndex();

        rotorCombos.get(duplicateRotor).setSelectedIndex(rotorSelectionHistory.get(rotorSelectedByUser));
        rotorCombos.get(rotorSelectedByUser).setSelectedIndex(swappedSelection);
    }

    public JPanel plugboardMiniPanel(int value) {
        JPanel temp = new JPanel();

        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        String label = "" + (char) (value + 65);

        temp.add(centeredLabel(label));
        temp.add(standardSpacer());

        return temp;
    }

    public void updateSelectionHistory() {
        for (int i = 0; i < rotorSelectionHistory.size(); i++) {
            rotorSelectionHistory.set(i, rotorCombos.get(i).getSelectedIndex());
        }
    }

    public void pairLetters(int location, char inputField) {
        plugboardFields.get(location).setText("" + inputField);
    }

    public void eraseLetter(int location) {
        plugboardFields.get(location).setText("");
    }

    public void deletePairing(String originalLocation) {
        originalLocation = originalLocation.replaceAll("Field", "");
        
        for (int i = 0; i < plugboardFields.size(); i++) {
            if (plugboardFields.get(i).getText().equalsIgnoreCase(originalLocation)){
                eraseLetter(i);
            }
        }
    }

    public boolean locationHasMultipleChars(int location) {
        return (plugboardFields.get(location).getText().length() > 0);
    }

    public void eraseLastLetter(int location) {
        String temp = plugboardFields.get(location).getText();
        temp = temp.substring(0, 0);
        plugboardFields.get(location).setText(temp);
    }
}
