package View.Basic;

import Controller.Filters.PlugboardDocumentFilter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
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
    private static final int DEFAULT_ROTORS = 3;
    private static final int VALID_CHARS = 26;
    public static final String[] DEFAULT_SETTINGS = {"2", "1", "0", "G", "H", "C", "L", "E", "A", "1", ""};
    //</editor-fold>

    //<editor-fold desc="Private Variables">
    private JButton reset;
    private JButton translate;
    private JButton saveSettings;
    private JButton reloadSavedSettings;

    private JTextArea plaintext;
    private JTextArea ciphertext;

    private JComboBox reflector;

    private String[] savedKeySettings;

    private ArrayList<JTextField> keyFields = new ArrayList<JTextField>();
    private ArrayList<JTextField> labelFields = new ArrayList<JTextField>();
    private ArrayList<JComboBox> rotorCombos = new ArrayList<JComboBox>();
    private ArrayList<Integer> rotorSelectionHistory = new ArrayList<Integer>();
    private ArrayList<Boolean> rotorsInUse = new ArrayList<Boolean>();
    private ArrayList<JTextField> plugboardFields = new ArrayList<JTextField>();
    private ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();
    //</editor-fold>

    public BasicInputScreen() {
        //Standard height and width for fields involved with rotors
        int rotorFieldHeight = 30;
        int rotorFieldWidth = 130;

        //<editor-fold desc="Rotor Component Creation">
        for (int i = 0; i < DEFAULT_ROTORS; i++) {
            String formattedNumber = getFormattedNumber(i);
            rotorCombos.add(basicJComboBox("rotor" + (i + 1), "Select rotor to use in " + formattedNumber + " slot", rotorFieldHeight, rotorFieldWidth));
            labelFields.add(basicJTextField("labelRotor" + i, "Label setting for rotor in " + formattedNumber + " slot", rotorFieldHeight, rotorFieldWidth));
            keyFields.add(basicJTextField("keyRotor" + i, "Key setting for rotor in " + formattedNumber + " slot", rotorFieldHeight, rotorFieldWidth));
        }
        //</editor-fold>

        //<editor-fold desc="Reflector Panel Creation">       
        JPanel reflectorPanel = new JPanel();
        reflectorPanel.setLayout(new BoxLayout(reflectorPanel, BoxLayout.Y_AXIS));

        reflector = basicJComboBox("reflector", "Selector reflector to use", rotorFieldHeight, rotorFieldWidth);

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

        //<editor-fold desc="Text and Button Panel Joining">      
        JPanel textbuttons = new JPanel();
        textbuttons.setLayout(new BoxLayout(textbuttons, BoxLayout.Y_AXIS));
        //textbuttons.setAlignmentY(CENTER_ALIGNMENT);
        textbuttons.add(textPanel);
        textbuttons.add(buttonPanel);
        //</editor-fold>

        //<editor-fold desc="Rotor and Plugboard Panel Joining">        
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        settingsPanel.add(rotorPanels);
        settingsPanel.add(standardSpacer());
        settingsPanel.add(plugboardPanel);
        //</editor-fold>

        //<editor-fold desc="Menu Bar and Menu Creation">       
        JMenuBar menuBar = new JMenuBar();
        JMenu tab1 = new JMenu("File");
        tab1.setMnemonic(KeyEvent.VK_F);

        JMenu tab2 = new JMenu("About");
        tab2.setMnemonic(KeyEvent.VK_U);
        //</editor-fold>

        //<editor-fold desc="Menu Item Creation">
        JMenuItem tab1Exit = new JMenuItem("Exit");
        tab1Exit.setName("menu_Exit");
        tab1Exit.setMnemonic(KeyEvent.VK_X);
        tab1Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));

        JMenuItem tab1Save = new JMenuItem("Save Enigma Keying");
        tab1Save.setName("menu_SaveKey");
        tab1Save.setMnemonic(KeyEvent.VK_S);
        tab1Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));

        JMenuItem tab1Open = new JMenuItem("Open Enigma Keying");
        tab1Open.setName("menu_OpenKey");
        tab1Open.setMnemonic(KeyEvent.VK_O);
        tab1Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        //</editor-fold>

        //<editor-fold desc="Menu Joining">
        tab1.add(tab1Open);
        tab1.add(tab1Save);
        tab1.add(tab1Exit);

        menuItems.add(tab1Open);
        menuItems.add(tab1Exit);
        menuItems.add(tab1Save);

        menuBar.add(tab1);
        menuBar.add(tab2);
        //</editor-fold>

        //<editor-fold desc="Master Panel Final Joining">        
        JPanel masterPanel = new JPanel();
        //Add panels to master
        masterPanel.setLayout(new BorderLayout(5, 5));
        masterPanel.add(menuBar, BorderLayout.NORTH);
        masterPanel.add(settingsPanel, BorderLayout.CENTER);
        masterPanel.add(textbuttons, BorderLayout.SOUTH);
        //</editor-fold>

        for (int i = 0; i < DEFAULT_ROTORS; i++) {
            rotorSelectionHistory.add(Integer.parseInt(DEFAULT_SETTINGS[i]));
            rotorsInUse.add(true);
        }

        this.add(masterPanel);
        this.setTitle("Enigma v0.1");
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

        for (int i = 0; i < rotorCombos.size(); i++) {
            AbstractDocument d = (AbstractDocument) labelFields.get(i).getDocument();
            d.setDocumentFilter(myFilter);
            labelFields.get(i).addKeyListener(kl);

            d = (AbstractDocument) keyFields.get(i).getDocument();
            d.setDocumentFilter(myFilter);
            keyFields.get(i).addKeyListener(kl);
        }
    }

    public void registerMenuListeners(ActionListener al) {
        for (int i = 0; i < menuItems.size(); i++) {
            menuItems.get(i).addActionListener(al);
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

        for (int i = 0; i < rotorCombos.size(); i++) {
            rotorCombos.get(i).setSelectedIndex(Integer.parseInt(settings[j]));
            j++;
        }

        for (int i = 0; i < labelFields.size(); i++) {
            labelFields.get(i).setText(settings[j]);
            j++;
        }

        for (int i = 0; i < keyFields.size(); i++) {
            keyFields.get(i).setText(settings[j]);
            j++;
        }

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
            if (plugboardFields.get(i).getText().equalsIgnoreCase(originalLocation)) {
                eraseLetter(i);
            }
        }
    }

    public boolean locationHasMultipleChars(int location) {
        return (plugboardFields.get(location).getText().length() > 0);
    }

    public void eraseLastLetter(String fieldtype, int location) {
        String temp = "";

        switch (fieldtype) {

            case "plugboard":
                temp = plugboardFields.get(location).getText();
                temp = temp.substring(0, 0);
                plugboardFields.get(location).setText(temp);
                break;
            case "label":
                temp = labelFields.get(location).getText();
                temp = temp.substring(0, 0);
                labelFields.get(location).setText(temp);
                break;
            case "key":
                temp = keyFields.get(location).getText();
                temp = temp.substring(0, 0);
                keyFields.get(location).setText(temp);
                break;
        }
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
        temp.add(centeredLabel("Rotor " + (i + 1)));
        temp.add(standardSpacer());
        temp.add(rotorCombos.get(i));
        temp.add(standardSpacer());
        temp.add(labelFields.get(i));
        temp.add(standardSpacer());
        temp.add(keyFields.get(i));

        return temp;
    }

    public void saveCurrentKeySettings() {
        savedKeySettings = new String[rotorCombos.size() * 3 + 2];

        int j = 0;

        for (int i = 0; i < rotorCombos.size(); i++) {
            savedKeySettings[j] = "" + rotorCombos.get(i).getSelectedIndex();
            j++;
        }

        for (int i = 0; i < labelFields.size(); i++) {
            savedKeySettings[j] = labelFields.get(i).getText();
            j++;
        }

        for (int i = 0; i < keyFields.size(); i++) {
            savedKeySettings[j] = keyFields.get(i).getText();
            j++;
        }

        savedKeySettings[j] = "" + reflector.getSelectedIndex();
        j++;

        savedKeySettings[j] = getPlugboardString();
    }

    public void useSavedKeySettings() {
        loadKeySetting(savedKeySettings);
    }

    private void loadKeySetting(String[] settings) {
        if (settings != null) {

            resetToDefault(settings);
            String plugboardSetting = settings[settings.length - 1];

            String[] temp = plugboardSetting.split(" ");

            if (!plugboardSetting.equalsIgnoreCase("")) {

                for (int i = 0; i < temp.length; i++) {
                    int start = temp[i].charAt(0) - 65;
                    int end = temp[i].charAt(1) - 65;

                    plugboardFields.get(start).setText("" + temp[i].charAt(1));
                    plugboardFields.get(end).setText("" + temp[i].charAt(0));
                }
            }
        }
    }

    public void keyGUI(String[] key) {
        int count = (key.length - 2) / 3;
        int k = 0;
        String[] temp = new String[count];

        for (int i = count - 1; i >= 0; i--) {
            for (int j = 0; j < rotorCombos.get(0).getItemCount(); j++) {
                if (key[i].equalsIgnoreCase("" + rotorCombos.get(0).getItemAt(j))) {
                    temp[k] = "" + j;
                    k++;
                }
            }
        }

        for (int i = 0; i < reflector.getItemCount(); i++) {
            if (key[key.length - 2].equalsIgnoreCase("" + reflector.getItemAt(i))) {
                key[key.length - 2] = i + "";
            }
        }

        for (int i = 0; i < count; i++) {
            key[i] = temp[i];
        }

        loadKeySetting(key);
    }
}
