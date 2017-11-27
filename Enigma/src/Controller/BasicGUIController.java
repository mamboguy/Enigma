/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.File.EnigmaFileManipulation;
import Model.Enigma.Enigma;
import View.Basic.BasicInputScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Mamboguy
 */
public class BasicGUIController
        implements ActionListener,
        KeyListener {

    public static final int VALID_CHARS = 26;

    //The model and view
    private BasicInputScreen gui;
    private Enigma model;

    //Sub-controllers
    private BasicGUIMenuController menuController;

    public BasicGUIController() {
        //Initialize the model, view and any sub-controllers
        model = new Enigma();
        gui = new BasicInputScreen();
        menuController = new BasicGUIMenuController(this);

        //Populate the combo boxes in the gui with the model's available rotors
        gui.updateRotorCombos(model.getRotorsAvailable());
        gui.updateReflectorCombos(model.getReflectorsAvailable());

        //Set the gui to its default state
        gui.resetToDefault(BasicInputScreen.DEFAULT_SETTINGS);

        //Register all listeners to proper classes
        gui.registerMenuListeners(menuController);
        gui.registerListeners(this, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent temp = (JComponent) e.getSource();
        String sourceName = temp.getName();

        switch (sourceName) {

            case "rotor1":
            case "rotor2":
            case "rotor3":
            case "rotor4":
                //todo - Split off into combo box controller
                JComboBox rotor = (JComboBox) e.getSource();
                String rotorName = (String) rotor.getName();

                //Get the index of the rotor's duplicate location
                int rotorDuplicateLocation = gui.isRotorAlreadySelected(rotor.getSelectedIndex());

                //Get the name of the rotor at the duplicate location
                String rotorComboBoxSelectedName = "rotor" + rotorDuplicateLocation;

                //Parse the current rotor's location
                int rotorCurrentlySelected = Integer.parseInt(rotorName.replaceAll("rotor", ""));

                //If the rotor has a duplicate and it is not a duplicate with itself
                if (rotorDuplicateLocation != 0 && !rotorComboBoxSelectedName.equalsIgnoreCase(sourceName)) {

                    //If the rotor has focus (to prevent the duplicated rotor from hanging the program)
                    if (rotor.hasFocus()) {

                        //Swap the current rotor with its duplicate
                        gui.swapRotorCombos(rotorCurrentlySelected, rotorDuplicateLocation);
                    }
                }

                //Update all rotors selection history
                gui.updateSelectionHistory();

                break;

            case "encodeButton":
                //Set the enigma model to mirror the current GUI configuration
                model.setSettings(gui.getCurrentKeySettings());

                //Send the entered plaintext to the model for encoding, then display on the GUI
                gui.setCiphertext(model.inputMessage(gui.getPlaintext()));

                //Set the key positions to their current position after the message finishes encoding
                gui.setCurrentKeyPosition(model.getCurrentKeyPositions());

                break;

            case "resetButton":

                //Reset the gui to the default settings
                gui.resetToDefault(BasicInputScreen.DEFAULT_SETTINGS);

                //todo - remove if no effect
                //model.setSettings(gui.getCurrentKeySettings());
                break;
            case "saveButton":

                //Tell the gui to temporarily store the current key settings
                gui.saveCurrentKeySettings();
                break;
            case "reloadButton":

                //Restore the temporarily stored key
                gui.useSavedKeySettings();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        JTextField temp = (JTextField) e.getSource();

        //If the field is in the plugboard
        if (temp.getName().contains("Field")) {

            //Get the plugboard field's name
            char name = temp.getName().replaceAll("Field", "").charAt(0);

            //Only continue if alphabetic character
            if (Character.isAlphabetic(e.getKeyChar())) {

                char inputField = Character.toUpperCase(e.getKeyChar());
                int location = name - 65;

                //If the typed character is different from the field
                if (inputField != name) {

                    //If this is a multi-type in the same field
                    if (gui.locationHasMultipleChars(location)) {

                        //Erase the last letter pressed to prevent multiple characters in the space
                        gui.eraseLastLetter("plugboard", location);

                    }

                    //Set the other field in the pair to reflect the pairing
                    gui.pairLetters(inputField - 65, name);

                    //Remove any loners, excluding the current pair
                    gui.checkForLoners(location, inputField - 65);

                } else {
                    //Consume the event since the same key as the field was pressed
                    e.consume();
                }

                //If delete or backspace key is used
            } else if (e.getKeyChar() == 127 || e.getKeyChar() == 8) {
                //Delete the key and its pairing
                gui.deletePairing(temp.getName());

                //If the typed location is the location's second letter
            }

            //Otherwise, it is a key/label field, just need to erase the last typed letter
        } else {

            //As long as the character typed is alphabetical
            if (Character.isAlphabetic(e.getKeyChar())) {

                //Get the associated location's rotor
                String location = temp.getName();
                location = location.replaceAll("labelRotor", "");
                location = location.replaceAll("keyRotor", "");
                int field = Integer.parseInt(location);

                //Get the field name associated with the typed char
                location = temp.getName();
                location = location.replaceAll("[0-9]", "");
                location = location.replaceAll("Rotor", "");

                //Erase the last letter
                gui.eraseLastLetter(location, field);

            } else if (e.getKeyChar() == 127 || e.getKeyChar() == 8) {
                //If the letter is being deleted, replace it with "A" instead
                temp.setText("A");
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Ignore
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Ignore
    }

    public void saveKeyFile(File selectedFile) {
        //Save the file using the current GUI settings
        EnigmaFileManipulation.saveKeyFile(selectedFile, gui.getCurrentKeySettings());
    }

    void openKeyFile(File selectedFile) {
        //Extract the key settings from the selected file
        String[] key = EnigmaFileManipulation.openKeyFile(selectedFile);

        //Key the GUI to the extracted key
        gui.keyGUI(key);
    }
}
