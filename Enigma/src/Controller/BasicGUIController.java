/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Enigma.Enigma;
import View.Basic.BasicInputScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Mamboguy
 */
public class BasicGUIController implements ActionListener, KeyListener {

    BasicInputScreen gui;
    Enigma model;
    private boolean secondCall = false;

    public BasicGUIController() {
        model = new Enigma();

        gui = new BasicInputScreen();
        gui.updateRotorCombos(model.getRotorsAvailable());
        gui.updateReflectorCombos(model.getReflectorsAvailable());
        gui.resetToDefault();
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
                gui.setCurrentKeyPosition(model.usingFourthRotor(), model.getCurrentKeyPositions());

                break;

            case "resetButton":

                gui.resetToDefault();
                model.setSettings(gui.getCurrentKeySettings());

                break;
            case "exitButton":

                String[] options = new String[]{"Exit", "Cancel"};

//                if (JOptionPane.showOptionDialog(null,
//                        "Are you sure you want to exit?",
//                        "Exit confirmation",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.QUESTION_MESSAGE,
//                        null,
//                        options,
//                        options[1]) == JOptionPane.YES_OPTION) {
                System.exit(0);
//                }

                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

        JTextField temp = (JTextField) e.getSource();
        char name = temp.getName().replaceAll("Field", "").charAt(0);

        char inputField = Character.toUpperCase(e.getKeyChar());
        int location = name - 65;

        //If the typed character is different from the field
        if (inputField != name) {

            //If delete or backspace key is used
            if (e.getKeyChar() == 127 || e.getKeyChar() == 8) {

                //Delete the key and its pairing
                gui.deletePairing(temp.getName());

                //If the typed location is the location's second letter
            } else if (gui.locationHasMultipleChars(location)) {
                //Erase the last letter pressed to prevent multiple characters in the space
                gui.eraseLastLetter(location);
                gui.deletePairing(temp.getName());
            }
            //If it passes the test, set the other field in the pair to reflect the pairing
            gui.pairLetters(inputField - 65, name);

        } else {
            //Consume the event since the same key as the field was pressed
            e.consume();
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
}
