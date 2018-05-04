package View.BasicGUI;

//<editor-fold defaultstate="collapsed" desc="Imports">

import Controller.BasicGUIController;
import Controller.Filters.PlugboardDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
//</editor-fold>

/**
 * Date Created Nov 19, 2017
 *
 * @author Michael C
 */
public class BasicInputScreen
        extends JFrame {

    //<editor-fold desc="Constants">
    private static final Dimension STANDARD_SPACER_SIZE = new Dimension(5, 5);
    private static final int DEFAULT_ROTORS = 3;
    private static final String[] DEFAULT_SETTINGS = {"2", "1", "0", "A", "A", "A", "A", "A", "A", "1", ""};
    //</editor-fold>

    //<editor-fold desc="Private Variables">
    private JButton reset;
    private JButton translate;
    private JButton saveSettings;
    private JButton reloadSavedSettings;
    private JButton randomizeKeySettings;

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

        //<editor-fold desc="HistoricalRotor Component Creation">
        //Create a rotor combo box, label and key field for each default rotor
        for (int i = 0; i < DEFAULT_ROTORS; i++) {
            String formattedNumber = getFormattedNumber(i + 1);
            rotorCombos.add(basicJComboBox("rotor" + (i + 1), "Select rotor to use in " + formattedNumber + " slot", rotorFieldHeight, rotorFieldWidth));
            labelFields.add(basicJTextField("labelRotor" + i, "Label setting for rotor in " + formattedNumber + " slot", rotorFieldHeight, rotorFieldWidth));
            keyFields.add(basicJTextField("keyRotor" + i, "Key setting for rotor in " + formattedNumber + " slot", rotorFieldHeight, rotorFieldWidth));
        }
        //</editor-fold>

        //<editor-fold desc="Reflector Panel Creation">      
        //Create the reflector panel and layout vertically
        JPanel reflectorPanel = new JPanel();
        reflectorPanel.setLayout(new BoxLayout(reflectorPanel, BoxLayout.Y_AXIS));

        //Initialize the combo box for the reflector
        reflector = basicJComboBox("reflector", "Selector reflector to use", rotorFieldHeight, rotorFieldWidth);

        //Setup the layout of the reflector panel
        reflectorPanel.add(centeredLabel("Reflector"));
        reflectorPanel.add(standardSpacer());
        reflectorPanel.add(reflector);
        //</editor-fold>

        //<editor-fold desc="HistoricalRotor Panel Creation">
        //Layout the rotor panel from left to right
        JPanel rotorPanels = new JPanel();
        rotorPanels.setLayout(new FlowLayout(FlowLayout.CENTER));

        //Add the reflectors to the rotor panel before the rest of the rotors
        rotorPanels.add(reflectorPanel);
        rotorPanels.add(standardSpacer());

        //Add each rotor's panel and a spacer between them
        for (int i = rotorCombos.size() - 1; i >= 0; i--) {
            rotorPanels.add(standardRotorPanel(i));
            rotorPanels.add(standardSpacer());
        }
        //</editor-fold>

        //<editor-fold desc="Plugboard GUI Creation">
        JPanel plugboardPanel = new JPanel();
        JPanel plugboardTopRow = new JPanel();
        JPanel plugboardBotRow = new JPanel();

        //Layout the overall panel vertically, but the top and bottom rows horizontally
        plugboardPanel.setLayout(new BoxLayout(plugboardPanel, BoxLayout.Y_AXIS));
        plugboardTopRow.setLayout(new BoxLayout(plugboardTopRow, BoxLayout.X_AXIS));
        plugboardBotRow.setLayout(new BoxLayout(plugboardBotRow, BoxLayout.X_AXIS));

        //Create a plugboard field for each letter
        for (int i = 0; i < BasicGUIController.VALID_CHARS; i++) {
            String name = (char) (i + 65) + "Field";
            String toolTipText = "Type in letter that will swap with " + (char) (i + 65);
            plugboardFields.add(basicJTextField(name, toolTipText, 30, 30));
        }

        //For half of the plugboard, add them to tho top row panel
        for (int i = 0; i < BasicGUIController.VALID_CHARS / 2; i++) {
            JPanel temp = new JPanel();
            temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
            temp.add(plugboardMiniPanel(i));
            temp.add(plugboardFields.get(i));
            plugboardFields.get(i).setHorizontalAlignment(JTextField.CENTER);
            plugboardTopRow.add(temp);
        }

        //For the other half of the plugboard, add them to the bottom row panel
        for (int i = BasicGUIController.VALID_CHARS / 2; i < BasicGUIController.VALID_CHARS; i++) {
            JPanel temp = new JPanel();
            temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
            temp.add(plugboardMiniPanel(i));
            temp.add(plugboardFields.get(i));
            plugboardFields.get(i).setHorizontalAlignment(JTextField.CENTER);
            plugboardBotRow.add(temp);
        }

        //Set the final layout of the plugboard
        plugboardPanel.add(centeredLabel("Plugboard Settings"));
        plugboardPanel.add(standardSpacer());
        plugboardPanel.add(plugboardTopRow);
        plugboardPanel.add(standardSpacer());
        plugboardPanel.add(plugboardBotRow);
        //</editor-fold>

        //<editor-fold desc="Text Area Panel Creation">
        JPanel textPanel = new JPanel();
        JPanel plainPanel = new JPanel();
        JPanel cipherPanel = new JPanel();

        //Create text areas
        plaintext = createTextArea("Enter plaintext to encode here");
        ciphertext = createTextArea("Encoded plaintext appears here");

        //Create plaintext area panel
        plainPanel.setLayout(new BoxLayout(plainPanel, BoxLayout.Y_AXIS));
        plainPanel.add(centeredLabel("Input"));
        plainPanel.add(plaintext);

        //Create ciphertext area panel
        cipherPanel.setLayout(new BoxLayout(cipherPanel, BoxLayout.Y_AXIS));
        cipherPanel.add(centeredLabel("Output"));
        cipherPanel.add(ciphertext);

        //Layout the text panels horizontally
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));

        //Set the final layout for the text panel
        textPanel.add(plainPanel);
        textPanel.add(standardSpacer());
        textPanel.add(cipherPanel);
        //</editor-fold>

        //<editor-fold desc="Button Panel Creation">
        JPanel buttonPanel = new JPanel();

        //Create buttons
        reset = initializeJButton("resetButton", "Reset", "Resets the plaintext and ciphertext fields");
        translate = initializeJButton("encodeButton", "Encode", "Takes the plaintext and translates into ciphertext");
        saveSettings = initializeJButton("saveButton", "Save Key", "Saves the current key settings, allowing reload later");
        reloadSavedSettings = initializeJButton("reloadButton", "Reload Key", "Reload the last saved key");
        randomizeKeySettings = initializeJButton("randomizeButton", "Randomize", "Randomizes all enigma key settings");

        //Set the final layout for the button panel
        buttonPanel.add(translate);
        buttonPanel.add(standardSpacer());
        buttonPanel.add(reset);
        buttonPanel.add(standardSpacer());
        buttonPanel.add(randomizeKeySettings);
        buttonPanel.add(standardSpacer());
        buttonPanel.add(saveSettings);
        buttonPanel.add(standardSpacer());
        buttonPanel.add(reloadSavedSettings);
        //</editor-fold>

        //<editor-fold desc="Text and Button Panel Joining">      
        JPanel textbuttons = new JPanel();

        //Layout the panel vertically
        textbuttons.setLayout(new BoxLayout(textbuttons, BoxLayout.Y_AXIS));

        //Add the text panels and the buttons to the final panel layout
        textbuttons.add(textPanel);
        textbuttons.add(buttonPanel);
        //</editor-fold>

        //<editor-fold desc="HistoricalRotor and Plugboard Panel Joining">
        JPanel settingsPanel = new JPanel();

        //Layout the settings panel vertically
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        //Set up the settings panel final layout
        settingsPanel.add(rotorPanels);
        settingsPanel.add(standardSpacer());
        settingsPanel.add(plugboardPanel);
        //</editor-fold>

        //<editor-fold desc="Menu Bar and Menu Creation">       
        JMenuBar menuBar = new JMenuBar();

        //Create both tabs
        JMenu tab1 = new JMenu("File");
        tab1.setMnemonic(KeyEvent.VK_F);

        JMenu tab2 = new JMenu("Settings");
        tab2.setMnemonic(KeyEvent.VK_S);

        JMenu tab3 = new JMenu("About");
        tab3.setMnemonic(KeyEvent.VK_U);
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

        JMenuItem tab2RandomSettings = new JMenuItem("Randomize Settings");
        tab2RandomSettings.setName("menu_RandomSettings");
        tab2RandomSettings.setMnemonic(KeyEvent.VK_R);
        tab2RandomSettings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));

        JMenuItem tab2EnforceHistorical = new JCheckBoxMenuItem("Enforce Historical Accuracy");
        tab2EnforceHistorical.setName("menu_Historical");
        tab2EnforceHistorical.setMnemonic(KeyEvent.VK_H);
        tab2EnforceHistorical.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
        //</editor-fold>

        //<editor-fold desc="Menu Joining">
        tab1.add(tab1Open);
        tab1.add(tab1Save);
        tab1.add(tab1Exit);

        tab2.add(tab2EnforceHistorical);
        tab2.add(tab2RandomSettings);        

        menuItems.add(tab1Open);
        menuItems.add(tab1Exit);
        menuItems.add(tab1Save);
        menuItems.add(tab2RandomSettings);
        menuItems.add(tab2EnforceHistorical);

        menuBar.add(tab1);
        menuBar.add(tab2);
        menuBar.add(tab3);
        //</editor-fold>

        //<editor-fold desc="Master Panel Final Joining">        
        JPanel masterPanel = new JPanel();

        //Add panels to master
        masterPanel.setLayout(new BorderLayout(5, 5));
        masterPanel.add(menuBar, BorderLayout.NORTH);
        masterPanel.add(settingsPanel, BorderLayout.CENTER);
        masterPanel.add(textbuttons, BorderLayout.SOUTH);
        //</editor-fold>

        //Set each of the default rotors to being used and set their selection history
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

    //<editor-fold desc="Initializing JComponents">
    /**
     * Creates standardized JButtons using passed inputs
     *
     * @param buttonName - Name of the button
     * @param buttonText - The text to display on the button
     * @param toolTip    - Button's tooltip
     *
     * @return - Initialized button of a standardized size with above attributes
     */
    private JButton initializeJButton(String buttonName, String buttonText, String toolTip) {
        JButton temp = new JButton(buttonText);

        temp.setToolTipText(toolTip);
        temp.setName(buttonName);
        temp.setPreferredSize(new Dimension(120, 50));

        return temp;
    }

    /**
     * Creates standardized JTextAreas using passed inputs
     *
     * @param toolTipText - TextArea's tooltip
     *
     * @return - Initialized JTextArea
     */
    private JTextArea createTextArea(String toolTipText) {
        JTextArea temp = new JTextArea(5, 20);
        temp.setLineWrap(true);
        temp.setMinimumSize(new Dimension(120, 60));
        temp.setToolTipText(toolTipText);

        return temp;
    }

    /**
     * Creates standardized JComboBoxes using passed inputs
     *
     * @param name        - Name of the comboBox
     * @param toolTipText - JComboBox's tooltip
     * @param height      - Height of the box
     * @param width       - Width of the box
     *
     * @return - Initialized JComboBox
     */
    private JComboBox basicJComboBox(String name, String toolTipText, int height, int width) {
        JComboBox temp = new JComboBox();

        temp.setName(name);
        temp.setPreferredSize(new Dimension(width, height));
        temp.setMinimumSize(new Dimension(width, height));
        temp.setToolTipText(toolTipText);

        return temp;
    }

    /**
     * Creates standardized JTextFields using passed inputs
     *
     * @param name        - Name of the textfield
     * @param toolTipText - TextField's tooltip
     * @param height      - Height of the textfield
     * @param width       - Width of the textfield
     *
     * @return - Initialized JTextField
     */
    private JTextField basicJTextField(String name, String toolTipText, int height, int width) {
        JTextField temp = new JTextField();

        temp.setName(name);
        temp.setPreferredSize(new Dimension(width, height));
        temp.setMinimumSize(new Dimension(width, height));
        temp.setToolTipText(toolTipText);

        return temp;
    }

    /**
     * Creates standardized JLabels with a centered alignment preference
     *
     * @param name - Label's display title
     *
     * @return - Initialized JLabel
     */
    public static JLabel centeredLabel(String name) {
        JLabel temp = new JLabel(name);
        temp.setAlignmentX(Component.CENTER_ALIGNMENT);

        return temp;
    }

    /**
     * Creates a standardized spacer for GUI separation
     *
     * @return - Rigid area of size designated by STANDARD_SPACER_SIZE
     */
    private Component standardSpacer() {
        return Box.createRigidArea(STANDARD_SPACER_SIZE);
    }

    /**
     * Creates the top part of an individual plugboard key
     *
     * @param value - the integer value of the character it is to represent
     *
     * @return - The combined panel of a label and spacer
     */
    public JPanel plugboardMiniPanel(int value) {
        JPanel temp = new JPanel();

        //Layout the panel vertically
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        //Create the centered label and spacer
        temp.add(centeredLabel("" + ((char) (value + 65))));
        temp.add(standardSpacer());

        return temp;
    }

    /**
     * Returns the formatted number for allowing proper grammar in dynamically
     * generated tooltips
     *
     * @param i - the number to format
     *
     * @return - formatted with its two characters e.g. "1st", "2nd", "3rd",
     *         "4th", etc
     */
    private String getFormattedNumber(int i) {
        String end = "th";

        //TODO - check for > 10 properly
        //TODO - check for 11-13
        if (i >= 11 && i <= 13) {
            //Leave as default
        } else {
            switch (i % 10) {
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
        }

        return (i + end);
    }

    /**
     * Creates a standardized rotor panel. Allows for dynamic number of
     * rotors
     *
     * @param i - The number of the rotor panel being added
     *
     * @return - The full rotor panel
     */
    //TODO - move method
    private JPanel standardRotorPanel(int i) {
        JPanel temp = new JPanel();

        //Layout the panel vertically
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        //Create a label and add each field in kind
        temp.add(centeredLabel("HistoricalRotor " + (i + 1)));
        temp.add(standardSpacer());
        temp.add(rotorCombos.get(i));
        temp.add(standardSpacer());
        temp.add(labelFields.get(i));
        temp.add(standardSpacer());
        temp.add(keyFields.get(i));

        return temp;
    }
    //</editor-fold>

    //<editor-fold desc="Listener registration">    
    /**
     * Hook to register the rotor's comboBoxes, textfields and the frame's
     * buttons with the controller
     *
     * @param al - ActionListener controller
     * @param kl - KeyListener controller
     */
    public void registerListeners(ActionListener al, KeyListener kl) {
        //Todo - split into different controllers (button and combo controller)

        //Add buttons actionlisteners
        saveSettings.addActionListener(al);
        reloadSavedSettings.addActionListener(al);
        reset.addActionListener(al);
        translate.addActionListener(al);
        randomizeKeySettings.addActionListener(al);

        //Initialize the plugboard filter (forces caps, doesn't accept non A-Z letters
        PlugboardDocumentFilter myFilter = new PlugboardDocumentFilter();

        //Set the document filter to the plugboard and register listeners
        for (int i = 0; i < plugboardFields.size(); i++) {
            AbstractDocument d = (AbstractDocument) plugboardFields.get(i).getDocument();
            d.setDocumentFilter(myFilter);
            plugboardFields.get(i).addKeyListener(kl);
        }

        //Set the document filter to the labels and key fields and register listeners
        for (int i = 0; i < labelFields.size(); i++) {
            AbstractDocument d = (AbstractDocument) labelFields.get(i).getDocument();
            d.setDocumentFilter(myFilter);
            labelFields.get(i).addKeyListener(kl);

            d = (AbstractDocument) keyFields.get(i).getDocument();
            d.setDocumentFilter(myFilter);
            keyFields.get(i).addKeyListener(kl);
        }
    }

    public void registerComboListeners(ActionListener al) {
        //Registers all of the rotor combos to the actionListener
        for (int i = 0; i < rotorCombos.size(); i++) {
            rotorCombos.get(i).addActionListener(al);
        }

    }

    /**
     * Hook for controller to register the menu bar listeners
     *
     * @param al - ActionListener controller
     */
    public void registerMenuListeners(ActionListener al) {
        for (int i = 0; i < menuItems.size(); i++) {
            menuItems.get(i).addActionListener(al);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Update combo box lists">    
    /**
     * Removes and adds all reflectors available for selection in the reflector
     * comboBox
     *
     * @param reflectorsAvailable - List of available reflectors to use
     */
    public void updateReflectorComboList(String[] reflectorsAvailable) {
        //Clear the combo box
        reflector.removeAllItems();

        //Add a new item for each passed reflector
        for (int i = 0; i < reflectorsAvailable.length; i++) {
            reflector.addItem(reflectorsAvailable[i]);
        }
    }

    /**
     * Removes and adds all rotors available for selection in the rotor
     * comboBoxes
     *
     * @param rotorsAvailable - List of available rotors to use
     */
    public void updateRotorComboList(String[] rotorsAvailable) {

        //Clears all rotor combo boxes
        for (int i = 0; i < rotorCombos.size(); i++) {
            rotorCombos.get(i).removeAllItems();
        }

        //For each rotor passed
        for (int i = 0; i < rotorsAvailable.length; i++) {

            //Add it to each rotor selector combo
            for (int j = 0; j < rotorCombos.size(); j++) {
                rotorCombos.get(j).addItem(rotorsAvailable[i]);
            }
        }
    }
    //</editor-fold>  

    //<editor-fold desc="Encode button method getters/pushers">   
    /**
     * Sets the text in the ciphertext area to the passed value
     *
     * @param ciphertext - The string value to set the ciphertext TextArea to
     */
    public void setCiphertext(String ciphertext) {
        this.ciphertext.setText(ciphertext);
    }

    /**
     * Updates the keyFields with the current rotor key setting after encoding a
     * message
     *
     * @param keys - the string of current key settings
     */
    public void setCurrentKeyPosition(String[] keys) {
        int j = 0;

        //Start from leftmost rotor in chain (largest)
        for (int i = keys.length - 1; i >= 0; i--) {

            //Read the keyFields backwards, but the key field forwards
            keyFields.get(i).setText(keys[j]);
            j++;
        }
    }
    //</editor-fold>

    //<editor-fold desc="HistoricalRotor Combo Selection Adjustment">
    /**
     * Checks to see if a rotor name is in use and if so, returns which rotor is
     * using it
     *
     * @param selectionIndex - the index to see if multiple rotors have selected
     *
     * @return - The location of the rotor using that name - Returns 0 if not in
     *         use
     */
    public int isRotorAlreadySelected(int selectionIndex) {
        int i = 0;

        //For each entry in the selection history
        for (int j = 0; j < rotorSelectionHistory.size(); j++) {

            //Checks the rotor's history to see if it was last at the index and if the rotor is in use
            if (rotorSelectionHistory.get(j) == selectionIndex && rotorsInUse.get(j)) {
                i = j + 1;
            }
        }

        return i;
    }

    /**
     * Swaps the selected indexs of two rotors
     *
     * @param rotorSelectedByUser - the rotor that was just changed by the user
     * @param duplicateRotor      - the duplicate rotor flagged that needs to
     *                            change
     *                            to user selected rotor's last value
     */
    public void swapRotorCombos(int rotorSelectedByUser, int duplicateRotor) {
        //Decrement both to get their arraylist location
        rotorSelectedByUser--;
        duplicateRotor--;

        //Change the rotor selected by the user to be the duplicate rotor's value
        rotorCombos.get(rotorSelectedByUser).setSelectedIndex(rotorCombos.get(duplicateRotor).getSelectedIndex());

        //Set the duplicate rotor's value to the last value of the rotor the user just changed
        rotorCombos.get(duplicateRotor).setSelectedIndex(rotorSelectionHistory.get(rotorSelectedByUser));

//        //TODO - delete if no function change
//        int swappedSelection = rotorCombos.get(duplicateRotor).getSelectedIndex();
//
//        rotorCombos.get(duplicateRotor).setSelectedIndex(rotorSelectionHistory.get(rotorSelectedByUser));
//        rotorCombos.get(rotorSelectedByUser).setSelectedIndex(swappedSelection);
    }

    /**
     * Updates the selection history with each rotor's currently selected index
     */
    public void updateSelectionHistory() {
        for (int i = 0; i < rotorSelectionHistory.size(); i++) {
            rotorSelectionHistory.set(i, rotorCombos.get(i).getSelectedIndex());
        }
    }
    //</editor-fold>

    //<editor-fold desc="Plugboard Textfield Manipulation">    
    /**
     * Pairs the second letter in the plugboard with the first inputted by the
     * user
     *
     * @param location   - the int value of the letter to be paired
     * @param inputField - the character to pair it with
     */
    public void pairLetters(int location, char inputField) {
        plugboardFields.get(location).setText("" + inputField);
    }

    /**
     * Erases the plugboard setting at the designated location
     *
     * @param location - the int value of the letter to be erased
     */
    public void eraseLetter(int location) {
        plugboardFields.get(location).setText("");
    }

    /**
     * Deletes the pairing at of the designated letter at the passed location
     *
     * @param originalLocation - the field name of the letter to be deleted
     */
    public void deletePairing(String originalLocation) {
        //Parse the letter to delete from the fieldname
        originalLocation = originalLocation.replaceAll("Field", "");

        //Iterate through all plugboardFields to find the one whose text matches the letter to delete and delete it
        for (int i = 0; i < plugboardFields.size(); i++) {
            if (plugboardFields.get(i).getText().equalsIgnoreCase(originalLocation)) {
                eraseLetter(i);
            }
        }
    }

    /**
     * Erase the last letter in a textfield
     *
     * @param fieldtype - fieldtype to erase
     * @param location  - int value of the location that needs to be deleted
     */
    public void eraseLastLetter(String fieldtype, int location) {
        String temp = "";

        switch (fieldtype) {

            //Each case will get the substring of only the first character of the field
            //It will then set that field to that substring
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

    /**
     * Checks the plugboard for any fields that do not have an associated pair
     * (lone stecker)
     *
     * @param ignoreLocation  - the first location to ignore (the place the user
     *                        just inputted a value)
     * @param ignoreLocation2 - the second location to ignore (the pairing
     *                        location with the user inputted value)
     */
    public void checkForLoners(int ignoreLocation, int ignoreLocation2) {

        //For each of the valid chars
        for (int i = 0; i < BasicGUIController.VALID_CHARS; i++) {

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

                //If the pairedLocaiton is valid
                if (pairedLocation < 26 && pairedLocation >= 0) {

                    //If the pairedLocation's text is not reciprocated in its pair
                    if (!plugboardFields.get(pairedLocation).getText().equalsIgnoreCase("" + ((char) (i + 65)))) {

                        //Delete the checked original pair that is now childless
                        plugboardFields.get(i).setText("");
                    }
                }
            }
        }
    }
    //</editor-fold>

    /**
     * Checks to see if the plugboard location has more than one character. The
     * reason for 1 being "multiple chars" is that the keylistener acts before
     * the text is changed on the field. Thus, if the field is already populated
     * when this check occurs, it was previously filled and adding a new
     * character will create a multiple character situation
     *
     * @param location - the location to check
     *
     * @return - true if more than 0 chars, false if not
     */
    public boolean locationHasMultipleChars(int location) {
        return (plugboardFields.get(location).getText().length() > 0);
    }

    //<editor-fold desc="Getters">    
    /**
     * Returns the text in the plaintext area
     *
     * @return - plaintext area string
     */
    public String getPlaintext() {
        return plaintext.getText();
    }

    //TODO - space out plaintext
    //TODO - add setting for plaintext spacing
    /**
     * Reads the key settings for passing to the model
     *
     * @return - The key settings with the rotor's names
     */
    public String[] getCurrentKeySettings() {

        //Get the standard key settings array
        String[] settings = readGUISettings();

        //Converts the rotor indexes in the array to the rotor's names
        for (int i = 0; i < rotorCombos.size(); i++) {
            settings[i] = (String) rotorCombos.get(i).getSelectedItem();
        }

        return settings;
    }

    /**
     * Reads all of the GUI settings into a standardized array. Array is of size
     * (rotors * 3) + 2. This accounts for each rotor's name/index, label and
     * key settings plus the reflector and plugboard settings. The order of
     * storage in the array is: rotor names/indexes, labels, keys, reflector
     * name/index and plugboard settings
     *
     * @return - standardized array of settings
     */
    private String[] readGUISettings() {
        String[] settings = new String[rotorCombos.size() * 3 + 2];

        int j = 0;

        for (int i = 0; i < rotorCombos.size(); i++) {
            settings[j] = "" + rotorCombos.get(i).getSelectedItem();
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

        settings[j] = "" + reflector.getSelectedItem();
        j++;

        settings[j] = getPlugboardString();

        return settings;
    }

    /**
     * Builds the plugboard setting string based on the current plugboard
     * values. This is a hook for the controller to get the plugboard settings
     *
     * @return - the steckering pattern string of all plugboard pairs
     */
    private String getPlugboardString() {

        String steckerBoardPattern = "";

        //Create a temporary boolean array that tracks whether a position has been
        //added to the plugboard string or not.  Each starts off as valid (true)
        boolean[] temp = new boolean[plugboardFields.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = true;
        }

        //Iterates through each field
        for (int i = 0; i < plugboardFields.size(); i++) {

            //If the position hasn't been used as is not empty
            if (temp[i] && !plugboardFields.get(i).getText().isEmpty()) {

                //Then add the position's char value plus the char in its text field to the steckering pattern
                steckerBoardPattern += "" + ((char) (i + 65)) + plugboardFields.get(i).getText() + " ";

                //Set the current position and its pair to false
                temp[i] = false;
                temp[plugboardFields.get(i).getText().charAt(0) - 65] = false;
            }
        }

        return steckerBoardPattern.trim();
    }
    //</editor-fold>

    /**
     * Saves the currently configured key settings for recall
     */
    //TODO - break off into own class?
    public void saveCurrentKeySettings() {
        savedKeySettings = readGUISettings();
    }

    /**
     * Reloads the saved key settings
     */
    public void useSavedKeySettings() {
        loadKeySetting(savedKeySettings);
    }

    /**
     * Loads the passed key settings into the current key settings
     *
     * @param settings - the settings to configure
     */
    //TODO - move method
    private void loadKeySetting(String[] settings) {

        //If the settings are valid
        if (settings != null) {

            //Set the combo, label, key and reflector fields
            int j = 0;

            //Set the rotor combo selections to the settings passed
            for (int i = 0; i < rotorCombos.size(); i++) {
                rotorCombos.get(i).setSelectedIndex(Integer.parseInt(settings[j]));
                j++;
            }

            //Set the labels to the passed settings
            for (int i = 0; i < labelFields.size(); i++) {
                labelFields.get(i).setText(settings[j]);
                j++;
            }

            //Set the key to the passed settings
            for (int i = 0; i < keyFields.size(); i++) {
                keyFields.get(i).setText(settings[j]);
                j++;
            }

            //Set the reflector
            reflector.setSelectedIndex(Integer.parseInt(settings[j]));

            //Pair the keys on the plugboard
            pairKeysOnPlugboard(settings[settings.length - 1]);
        }
    }

    /**
     * Loads the default keys defined in this class
     * Also clears the two text areas
     */
    public void resetToDefault() {
        loadKeySetting(DEFAULT_SETTINGS);

        ciphertext.setText("");
        plaintext.setText("");
    }

    public void randomizeGUI() {
        
        //TODO - add support for random settings

        loadKeySetting(DEFAULT_SETTINGS);

        //Randomize all of the rotors, ensuring that none are repeated
        do {
            for (int i = 0; i < rotorCombos.size(); i++) {
                rotorCombos.get(i).setSelectedIndex((int) (Math.random() * rotorCombos.get(0).getItemCount()));
            }
        } while (guiHasRotorDuplicates());

        for (int i = 0; i < labelFields.size(); i++) {
            labelFields.get(i).setText("" + (char) (((int) (Math.random() * BasicGUIController.VALID_CHARS)) + 65));
        }

        for (int i = 0; i < keyFields.size(); i++) {
            keyFields.get(i).setText("" + (char) (((int) (Math.random() * BasicGUIController.VALID_CHARS)) + 65));
        }

        reflector.setSelectedIndex((int) (Math.random() * reflector.getItemCount()));

        //TODO - Add in user preference for number of pairs
        //Select a random number of plugs to stecker
        int plugs = (int) (Math.random() * (BasicGUIController.VALID_CHARS / 2));

        //Initialize a temporary arraylist that will hold all available chars
        ArrayList<Integer> temp = new ArrayList<Integer>();

        //Fill arraylist with chars (represented as ints)
        for (int j = 0; j < BasicGUIController.VALID_CHARS; j++) {
            temp.add(j + 65);
        }

        String pattern = "";

        for (int i = 0; i < plugs; i++) {
            int index = (int) (temp.size() * Math.random());
            int start = temp.get(index);
            temp.remove(index);

            index = (int) (temp.size() * Math.random());
            int end = temp.get(index);
            temp.remove(index);

            pattern += "" + (char) start + (char) end + " ";
        }

        pairKeysOnPlugboard(pattern.trim());
    }

    /**
     * Keys the GUI to the passed string
     *
     * @param key - the passed settings key
     */
    //TODO - combine with other methods to simplify code
    //TODO - break out into class that deals with settings
    public void keyGUI(String[] key) {

        //Get rotor count
        int count = (key.length - 2) / 3;
        int k = 0;
        String[] temp = new String[count];

        //For each rotor, check to see what index is associated with the passed rotor name
        for (int i = count - 1; i >= 0; i--) {
            for (int j = 0; j < rotorCombos.get(0).getItemCount(); j++) {
                if (key[i].equalsIgnoreCase("" + rotorCombos.get(0).getItemAt(j))) {
                    temp[k] = "" + j;
                    k++;
                }
            }
        }

        //For the reflector, check to see what index is associated with the passed reflector name
        for (int i = 0; i < reflector.getItemCount(); i++) {
            if (key[key.length - 2].equalsIgnoreCase("" + reflector.getItemAt(i))) {
                key[key.length - 2] = i + "";
            }
        }

        //Store the temp values to the key
        for (int i = 0; i < count; i++) {
            key[i] = temp[i];
        }

        //Load the passed settings
        loadKeySetting(key);
    }

    private boolean guiHasRotorDuplicates() {

        boolean duplicates = false;

        for (int i = 0; i < rotorCombos.size(); i++) {
            for (int j = 0; j < rotorCombos.size(); j++) {
                if (i == j) {
                    //Ignore when checking against self
                } else {
                    if (rotorCombos.get(i).getSelectedIndex() == rotorCombos.get(j).getSelectedIndex()) {
                        duplicates = true;
                    }
                }
            }
        }

        return duplicates;
    }

    private void pairKeysOnPlugboard(String plugboardSetting) {
        //Split the plugboard settings into pairs
        String[] stringArray = plugboardSetting.split(" ");

        //If the plugboard string wasn't empty
        if (!plugboardSetting.equalsIgnoreCase("")) {

            //For each pair
            for (int i = 0; i < stringArray.length; i++) {

                //Get the locations of each char's field
                int start = stringArray[i].charAt(0) - 65;
                int end = stringArray[i].charAt(1) - 65;

                //Set each field to be paired with the other
                plugboardFields.get(start).setText("" + stringArray[i].charAt(1));
                plugboardFields.get(end).setText("" + stringArray[i].charAt(0));
            }
        } else {
            //Blank out the plugboard
            for (int i = 0; i < plugboardFields.size(); i++) {
                plugboardFields.get(i).setText("");
            }
        }
    }
}
