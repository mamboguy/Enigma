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
import javax.swing.JButton;

/**
 *
 * @author Mamboguy
 */
public class BasicGUIController implements ActionListener {

    BasicInputScreen gui;
    Enigma model;

    public BasicGUIController() {
        model = new Enigma();

        gui = new BasicInputScreen();
        gui.registerListeners(this);
        gui.updateRotorCombos(model.getRotorsAvailable());
        gui.updateReflectorCombos(model.getReflectorsAvailable());
        gui.resetToDefault();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton temp = (JButton) e.getSource();
        String buttonName = temp.getName();
        String holder;

        switch (buttonName) {

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

}
