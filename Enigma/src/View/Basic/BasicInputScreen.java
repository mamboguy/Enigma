package View.Basic;

import Controller.Filters.PlugboardDocumentFilter;
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
import javax.swing.text.AbstractDocument;

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
    private static final int DEFAULT_ROTORS = 3;
//    private static final ArrayList<Integer> ROTOR_DEFAULTS = new ArrayList<Integer>() {
//        {
//            add(2);
//            add(1);
//            add(0);
//            add(0);
//        }
//    };
    private static final int VALID_CHARS = 26;
    public static String[] DEFAULT_SETTINGS = {"2", "1", "0", "L", "E", "A", "G", "H", "C", "1", ""};
    //</editor-fold>

    //<editor-fold desc="Private Variables">
    private JButton reset;
    private JButton translate;
    private JButton saveSettings;
    private JButton reloadSavedSettings;

    private JTextArea plaintext;
    private JTextArea ciphertext;

    private JComboBox reflector;

//    //TODO - Lump into ArrayLists
//    private JComboBox rotor1;
//    private JComboBox rotor2;
//    private JComboBox rotor3;
//    private JComboBox rotor4;
//    private JTextField labelRotor4;
//    private JTextField labelRotor3;
//    private JTextField labelRotor2;
//    private JTextField labelRotor1;
//    private JTextField keyRotor4;
//    private JTextField keyRotor3;
//    private JTextField keyRotor2;
//    private JTextField keyRotor1;
    private ArrayList<JTextField> keyFields = new ArrayList<JTextField>();
    private ArrayList<JTextField> labelFields = new ArrayList<JTextField>();
    private ArrayList<JComboBox> rotorCombos = new ArrayList<JComboBox>();
    private ArrayList<Integer> rotorSelectionHistory = new ArrayList<Integer>();
    private ArrayList<Boolean> rotorsInUse = new ArrayList<Boolean>();

    private ArrayList<JTextField> plugboardFields = new ArrayList<JTextField>();
    //</editor-fold>

    public BasicInputScreen() {
        //Create combo boxes for settings
        int comboHeights = 30;
        int comboWidths = 130;

        for (int i = 0; i < DEFAULT_ROTORS; i++) {
            String formattedNumber = getFormattedNumber(i);
            rotorCombos.add(basicJComboBox("rotor" + i, "Select rotor to use in " + formattedNumber + " slot", comboHeights, comboWidths));
            labelFields.add(basicJTextField("labelRotor" + i, "Label setting for rotor in " + formattedNumber + " slot", comboHeights, comboWidths));
            keyFields.add(basicJTextField("keyRotor" + i, "Key setting for rotor in " + formattedNumber + " slot", comboHeights, comboWidths));
        }

        //<editor-fold desc="Reflector Panel Creation">       
        JPanel reflectorPanel = new JPanel();
        reflectorPanel.setLayout(new BoxLayout(reflectorPanel, BoxLayout.Y_AXIS));

        reflector = basicJComboBox("reflector", "Selector reflector to use", comboHeights, comboWidths);

        reflectorPanel.add(centeredLabel("Reflector"));
        reflectorPanel.add(standardSpacer());
        reflectorPanel.add(reflector);
        //</editor-fold>

        //<editor-fold desc="Rotor Panel Creation">
        JPanel rotorPanels = new JPanel();
        rotorPanels.setLayout(new FlowLayout(FlowLayout.CENTER));
        rotorPanels.add(reflectorPanel);
        rotorPanels.add(standardSpacer());

        for (int i = rotorCombos.size() - 1; i >= 0; i--) {
            //TODO - Add Document Listener to force uppercase
            rotorPanels.add(standardRotorPanel(i));
            rotorPanels.add(standardSpacer());
        }
        //</editor-fold>

        //<editor-fold desc="Plugboard GUI Creation">
        JPanel plugboardPanel = new JPanel();
        JPanel plugboardTopRow = new JPanel();
        JPanel plugboardBotRow = new JPanel();
        plugboardPanel.setLayout(new BoxLayout(plugboardPanel, BoxLayout.Y_AXIS));
        plugboardTopRow.setLayout(new BoxLayout(plugboardTopRow, BoxLayout.X_AXIS));
        plugboardBotRow.setLayout(new BoxLayout(plugboardBotRow, BoxLayout.X_AXIS));

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
        //</editor-fold>

        //<editor-fold desc="Text Area Panel Creation">
        JPanel textPanel = new JPanel();

        //Create text areas
        plaintext = createTextArea("Enter plaintext to encode here");
        ciphertext = createTextArea("Encoded plaintext appears here");

        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        textPanel.add(plaintext);
        textPanel.add(standardSpacer());
        textPanel.add(ciphertext);
        //</editor-fold>

        //<editor-fold desc="Button Panel Creation">
        JPanel buttonPanel = new JPanel();

        //Create buttons
        reset = initializeJButton("resetButton", "Reset", "Resets the plaintext and ciphertext fields");
        translate = initializeJButton("encodeButton", "Encode", "Takes the plaintext and translates into ciphertext");
        saveSettings = initializeJButton("saveButton", "Save Key", "Saves the current key settings, allowing reload later");
        reloadSavedSettings = initializeJButton("reloadButton", "Reload Key", "Reload the last saved key");

        buttonPanel.add(translate);
        buttonPanel.add(standardSpacer());
        buttonPanel.add(reset);
        buttonPanel.add(standardSpacer());
        buttonPanel.add(saveSettings);
        buttonPanel.add(standardSpacer());
        buttonPanel.add(reloadSavedSettings);
        //</editor-fold>

        JPanel textbuttons = new JPanel();
        textbuttons.setLayout(new BoxLayout(textbuttons, BoxLayout.Y_AXIS));
        //textbuttons.setAlignmentY(CENTER_ALIGNMENT);
        textbuttons.add(textPanel);
        textbuttons.add(buttonPanel);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        settingsPanel.add(rotorPanels);
        settingsPanel.add(standardSpacer());
        settingsPanel.add(plugboardPanel);

        JPanel masterPanel = new JPanel();
        //Add panels to master
        masterPanel.setLayout(new BorderLayout(5, 5));
        masterPanel.add(settingsPanel, BorderLayout.CENTER);
        masterPanel.add(textbuttons, BorderLayout.SOUTH);

        for (int i = 0; i < DEFAULT_ROTORS; i++) {
            rotorSelectionHistory.add(Integer.parseInt(DEFAULT_SETTINGS[i]));
            rotorsInUse.add(true);
        }

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
        saveSettings.addActionListener(al);
        reloadSavedSettings.addActionListener(al);
        reset.addActionListener(al);
        translate.addActionListener(al);

        for (int i = 0; i < rotorCombos.size(); i++) {
            rotorCombos.get(i).addActionListener(al);
        }

        PlugboardDocumentFilter myFilter = new PlugboardDocumentFilter();

        for (int i = 0; i < plugboardFields.size(); i++) {
            AbstractDocument d = (AbstractDocument) plugboardFields.get(i).getDocument();
            d.setDocumentFilter(myFilter);
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

    public void resetToDefault(String[] settings) {

        int j = 0;

        System.out.println("rotorCombos.size = " + rotorCombos.size());
        System.out.println("keyFields.size = " + keyFields.size());
        System.out.println("labelFields.size = " + labelFields.size());

        for (int i = 0; i < rotorCombos.size(); i++) {
            rotorCombos.get(i).setSelectedIndex(Integer.parseInt(settings[j]));
            j++;
        }
        System.out.println("j = " + j);

        for (int i = 0; i < keyFields.size(); i++) {
            keyFields.get(i).setText(settings[j]);
            j++;
        }
        System.out.println("j = " + j);

        for (int i = 0; i < labelFields.size(); i++) {
            labelFields.get(i).setText(settings[j]);
            j++;
        }
        System.out.println("j = " + j);
        System.out.println("settings.length = " + settings.length);

        reflector.setSelectedIndex(Integer.parseInt(settings[j]));

        ciphertext.setText("");
        plaintext.setText("");

        for (int i = 0; i < rotorCombos.size(); i++) {
            rotorSelectionHistory.set(i, Integer.parseInt(DEFAULT_SETTINGS[i]));
        }

        for (int i = 0; i < plugboardFields.size(); i++) {
            plugboardFields.get(i).setText("");
        }
    }

    //TODO - space out plaintext
    //TODO - add setting for plaintext spacing
    public String[] getCurrentKeySettings() {

        //TODO - Implement Arraylist
        String[] settings = new String[rotorCombos.size() * 3 + 2];

        int j = 0;

        for (int i = 0; i < rotorCombos.size(); i++) {
            settings[j] = (String) rotorCombos.get(i).getSelectedItem();
            j++;
        }

        for (int i = 0; i < labelFields.size(); i++) {
            settings[j] = labelFields.get(i).getText();
            j++;
        }

        for (int i = 0; i < keyFields.size(); i++) {
            settings[j] = keyFields.get(i).getText();
            j++;
        }

        settings[j] = (String) reflector.getSelectedItem();
        j++;

        settings[j] = getPlugboardString();

        return settings;
    }

    public String getPlaintext() {
        return plaintext.getText();
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext.setText(ciphertext);
    }

    public void setCurrentKeyPosition(String[] keys) {
        int j = 0;

        for (int i = keys.length - 1; i >= 0; i--) {
            keyFields.get(i).setText(keys[j]);
            j++;
        }
    }

    /**
     * Checks to see if a rotor name is in use and if so, returns which rotor is
     * using it
     *
     * @param name - The name of the rotor that needs to be checked if in use
     *
     * @return - The location of the rotor using that name - Returns 0 if not in
     *         use
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
            if (plugboardFields.get(i).getText().equalsIgnoreCase(originalLocation)) {
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

    public void checkForLoners(int ignoreLocation, int ignoreLocation2) {
        for (int i = 0; i < VALID_CHARS; i++) {

            //If the letter was just typed and is to be ignored, then skip
            if (i != ignoreLocation && i != ignoreLocation2) {

                //Temporarily store the plugboards text
                String temp = plugboardFields.get(i).getText();
                int pairedLocation = -1;

                //If the location has valid text
                if (temp.length() > 0) {

                    //Get the character at that location
                    pairedLocation = (temp.charAt(0)) - 65;
                }

                //If the a;
                if (pairedLocation < 26 && pairedLocation >= 0) {
                    if (!plugboardFields.get(pairedLocation).getText().equalsIgnoreCase("" + ((char) (i + 65)))) {
                        plugboardFields.get(i).setText("");
                    }
                }
            }
        }
    }

    private String getPlugboardString() {

        String steckerBoardPattern = "";

        boolean[] temp = new boolean[plugboardFields.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = true;
        }

        for (int i = 0; i < plugboardFields.size(); i++) {
            if (temp[i] && !plugboardFields.get(i).getText().isEmpty()) {
                steckerBoardPattern += "" + ((char) (i + 65)) + plugboardFields.get(i).getText() + " ";
                temp[i] = false;
                temp[plugboardFields.get(i).getText().charAt(0) - 65] = false;
            }
        }

        return steckerBoardPattern.trim();
    }

    private String getFormattedNumber(int i) {
        String end = "th";

        switch (i) {
            case 1:
                end = "st";
                break;
            case 2:
                end = "nd";
                break;
            case 3:
                end = "rd";
                break;
            default:
        }

        return (i + end);
    }

    private JPanel standardRotorPanel(int i) {
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
        temp.add(centeredLabel("Rotor " + i));
        temp.add(standardSpacer());
        temp.add(rotorCombos.get(i));
        temp.add(standardSpacer());
        temp.add(labelFields.get(i));
        temp.add(standardSpacer());
        temp.add(keyFields.get(i));

        return temp;
    }

    public void setKeySettings(String[] savedKey) {
        //TODO - Implement method setKeySettings()
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
