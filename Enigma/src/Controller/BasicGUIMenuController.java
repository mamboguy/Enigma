/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Date Created Nov 26, 2017
 *
 * @author Michael C
 */
public class BasicGUIMenuController
        implements ActionListener {

    public BasicGUIMenuController() {
        //Empty constructor
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
            default:
                break;
        }
    }

}
