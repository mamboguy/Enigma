package Controller.BasicGUI;

import Controller.BasicGUIController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Date Created Nov 26, 2017
 *
 * @author Michael C
 */
public class MenuController
        implements ActionListener {

    BasicGUIController parent;

    public MenuController(BasicGUIController parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JMenuItem) e.getSource()).getName();

        //Create a new file chooser
        JFileChooser fc = new JFileChooser();

        //Create a file filter for enigma key files (.ekf)
        //Currently, file is encoded/decoded as simple txt file
        FileNameExtensionFilter myFilter = new FileNameExtensionFilter("Enigma Key Files", "ekf");

        //Apply the filter
        fc.setFileFilter(myFilter);

        switch (name) {
            case "menu_Exit":

                String[] options = new String[]{"Exit", "Cancel"};

                //Confirm if the user would like to exit
                if (JOptionPane.showOptionDialog(null,
                                                 "Are you sure you want to exit?",
                                                 "Exit confirmation",
                                                 JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.QUESTION_MESSAGE,
                                                 null,
                                                 options,
                                                 options[1]) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;
            case "menu_SaveKey":

                //Open the file chooser dialog option
                if (fc.showSaveDialog(fc) == JFileChooser.APPROVE_OPTION) {
                    //If a valid file is chosen, tell the master controller to save the file
                    parent.saveKeyFile(fc.getSelectedFile());
                }

                break;
            case "menu_OpenKey":

                //Open the file chooser dialog option
                if (fc.showOpenDialog(fc) == JFileChooser.APPROVE_OPTION) {
                    //If a valid file is chosen, tell the master controller to open the file
                    parent.openKeyFile(fc.getSelectedFile());
                }

                break;

            case "menu_RandomSettings":
                parent.requestRandomKeySettingsPanel();
                
                break;
            default:
                break;
        }
    }

}
