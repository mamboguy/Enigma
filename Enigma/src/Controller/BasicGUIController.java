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

    private BasicInputScreen gui;
    private Enigma model;

    //Sub-controllers
    private BasicGUIMenuController menuController;

    public BasicGUIController() {
        model = new Enigma();
        gui = new BasicInputScreen();
        menuController = new BasicGUIMenuController(this);

        gui.updateRotorCombos(model.getRotorsAvailable());
        gui.updateReflectorCombos(model.getReflectorsAvailable());

        //Set the gui to its default state
        gui.resetToDefault(BasicInputScreen.DEFAULT_SETTINGS);

        //Register all listeners to proper classes
        gui.registerMenuListeners(menuController);
        gui.registerListeners(this, this);
    }
    
    public BasicGUIController(boolean a){
        
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
                JComboBox rotor = (JComboBox) e.getSource();
                String rotorName = (String) rotor.getName();
                int rotorDuplicateLocation = gui.isRotorAlreadySelected(rotor.getSelectedIndex());
                String rotorComboBoxSelectedName = "rotor" + rotorDuplicateLocation;
                int rotorCurrentlySelected = Integer.parseInt(rotorName.replaceAll("rotor", ""));

                if (rotorDuplicateLocation != 0 && !rotorComboBoxSelectedName.equalsIgnoreCase(sourceName)) {
                    if (rotor.hasFocus()) {
                        gui.swapRotorCombos(rotorCurrentlySelected, rotorDuplicateLocation);
                    }
                }

                gui.updateSelectionHistory();

                break;
            case "encodeButton":
                model.setSettings(gui.getCurrentKeySettings());
                gui.setCiphertext(model.inputMessage(gui.getPlaintext()));
                gui.setCurrentKeyPosition(model.getCurrentKeyPositions());

                break;

            case "resetButton":

                gui.resetToDefault(BasicInputScreen.DEFAULT_SETTINGS);
                model.setSettings(gui.getCurrentKeySettings());

                break;
            case "saveButton":
                gui.saveCurrentKeySettings();
                break;
            case "reloadButton":
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

            char name = temp.getName().replaceAll("Field", "").charAt(0);

            //Only continue if alphabetic character
            if (Character.isAlphabetic(e.getKeyChar())) {

                char inputField = Character.toUpperCase(e.getKeyChar());
                int location = name - 65;

                //If the typed character is different from the field
                if (inputField != name) {

                    if (gui.locationHasMultipleChars(location)) {
                        //Erase the last letter pressed to prevent multiple characters in the space
                        gui.eraseLastLetter("plugboard", location);
                        gui.deletePairing(temp.getName());
                    }
                    //If it passes the test, set the other field in the pair to reflect the pairing
                    gui.pairLetters(inputField - 65, name);
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

            if (Character.isAlphabetic(e.getKeyChar())) {
                System.out.println("Key/Label field");
                String location = temp.getName();
                location = location.replaceAll("labelRotor", "");
                location = location.replaceAll("keyRotor", "");

                int field = Integer.parseInt(location);

                location = temp.getName();
                location = location.replaceAll("[0-9]", "");
                location = location.replaceAll("Rotor", "");

                gui.eraseLastLetter(location, field);
            } else if (e.getKeyChar() == 127 || e.getKeyChar() == 8) {
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
        EnigmaFileManipulation.saveKeyFile(selectedFile, gui.getCurrentKeySettings());
    }

    void openKeyFile(File selectedFile) {
        String[] key = EnigmaFileManipulation.openKeyFile(selectedFile);
        
        for (int i = 0; i < key.length; i++) {
            System.out.println("Key[" + i + "] = " + key[i]);
        }
        
        gui.keyGUI(key);
    }
}
