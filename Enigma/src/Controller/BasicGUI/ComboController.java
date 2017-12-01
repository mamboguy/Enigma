/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.BasicGUI;

import Controller.BasicGUIController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JComponent;

/**
 * Date Created Nov 29, 2017
 *
 * @author Michael C
 */
public class ComboController
        implements ActionListener {

    private BasicGUIController parent;

    public ComboController(BasicGUIController parent) {
        this.parent = parent;
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
                //todo - work into guiHasRotorDuplicates???
                //todo - Split off into combo box controller
                JComboBox rotor = (JComboBox) e.getSource();
                String rotorName = (String) rotor.getName();

                //Get the index of the rotor's duplicate location
                int rotorDuplicateLocation = parent.isRotorAlreadySelected(rotor.getSelectedIndex());

                //Get the name of the rotor at the duplicate location
                String rotorComboBoxSelectedName = "rotor" + rotorDuplicateLocation;

                //Parse the current rotor's location
                int rotorCurrentlySelected = Integer.parseInt(rotorName.replaceAll("rotor", ""));

                //If the rotor has a duplicate and it is not a duplicate with itself
                if (rotorDuplicateLocation != 0 && !rotorComboBoxSelectedName.equalsIgnoreCase(sourceName)) {

                    //If the rotor has focus (to prevent the duplicated rotor from hanging the program)
                    if (rotor.hasFocus()) {

                        //Swap the current rotor with its duplicate
                        parent.swapRotorCombos(rotorCurrentlySelected, rotorDuplicateLocation);
                    }
                }

                //Update all rotors selection history
                parent.updateSelectionHistory();

                break;

            default:
                break;
        }
    }
}
