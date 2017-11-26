/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
public class BasicGUIMenuController
        implements ActionListener {
    
    BasicGUIController parent;

    public BasicGUIMenuController(BasicGUIController parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JMenuItem) e.getSource()).getName();

        switch (name) {
            case "menu_Exit":

                String[] options = new String[]{"Exit", "Cancel"};

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

                JFileChooser fc = new JFileChooser();

                FileNameExtensionFilter myFilter = new FileNameExtensionFilter("Enigma Key Files", "ekf");
                fc.setFileFilter(myFilter);

                if (fc.showSaveDialog(fc) == JFileChooser.APPROVE_OPTION) {
                    if (!fc.getSelectedFile().toString().contains(".ekf")){
                        fc.getSelectedFile();
                    }
                    parent.saveKeyFile(fc.getSelectedFile());
                }

                break;
            default:
                break;
        }
    }

}
