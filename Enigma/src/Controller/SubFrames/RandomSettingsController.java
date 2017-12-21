package Controller.SubFrames;

import Controller.BasicGUIController;
import View.BasicGUI.SubFrames.RandomSettings;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

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
        String name = ((JButton) e.getSource()).getName();

        switch (name) {
            case "Ok":
                String[] settings = gui.getSettings();
                gui.closePanel();
                
                parent.updateRandomizationSettings(settings);
                
                break;
            default:
                break;
        }
    }
}
