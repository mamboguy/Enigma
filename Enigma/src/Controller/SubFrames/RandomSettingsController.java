package Controller.SubFrames;

import Controller.BasicGUIController;
import View.BasicGUI.SubFrames.RandomSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Date Created Dec 20, 2017
 *
 * @author Michael C
 */
public class RandomSettingsController
        implements ActionListener {

    private RandomSettings gui;
    private BasicGUIController parent;

    public RandomSettingsController(BasicGUIController parent) {
        this.parent = parent;
        this.gui = new RandomSettings();
        gui.registerListener(this);
    }

    public void openSettingsPanel() {
        gui.openPanel(parent.requestRotorNames());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String name;

        if (e.getSource().getClass() == JButton.class) {
            name = ((JButton) e.getSource()).getName();
        } else {
            name = ((JRadioButton) e.getSource()).getName();
        }

        switch (name) {
            case "Ok":
                String[] settings = gui.getSettings();
                gui.closePanel();
                
                for (int i = 0; i < settings.length; i++) {
                    System.out.println("Settings[" + i + "]= " + settings[i]);
                }

                parent.updateRandomizationSettings(settings);

                break;

            case "equalTo":
                gui.hideOrEqualTo();
                break;

            case "lessThan":
                gui.showOrEqualTo();
                break;

            case "greaterThan":
                gui.showOrEqualTo();
                break;

            default:
                break;
        }
    }
}
