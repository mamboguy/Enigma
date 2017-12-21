/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.BasicGUI.SubFrames;

import Controller.SubFrames.RandomSettingsController;
import View.BasicGUI.BasicInputScreen;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

/**
 * Date Created Dec 20, 2017
 *
 * @author Michael C
 */
public class RandomSettings
        extends JFrame {

    private JButton approve;

    public RandomSettings() {
        approve = new JButton("Ok");
    }

    public void openPanel(String[] rotorNames) {
        JList rotorSelection = new JList(rotorNames);
        rotorSelection.setToolTipText("Select all rotors that are available to be randomed");
        rotorSelection.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        rotorSelection.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        rotorSelection.setVisibleRowCount(4);
        rotorSelection.setFixedCellWidth(50);

        JPanel rotorSelectorPanel = new JPanel();
        rotorSelectorPanel.setLayout(new BoxLayout(rotorSelectorPanel, BoxLayout.Y_AXIS));

        rotorSelectorPanel.add(BasicInputScreen.centeredLabel("Rotors Available"));
        rotorSelectorPanel.add(rotorSelection);

        JPanel masterPanel = new JPanel();
        masterPanel.setLayout(new BorderLayout(5, 5));

        masterPanel.add(rotorSelectorPanel, BorderLayout.CENTER);
        masterPanel.add(approve, BorderLayout.SOUTH);

        this.add(masterPanel);
        this.setTitle("Random Settings v0.01");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
    }

    public void registerListener(ActionListener al) {
        approve.addActionListener(al);
    }
    
    public String[] getSettings(){
        //TODO - Implement settings pull
        
        return null;
    }

    public void closePanel() {        
        this.dispose();
    }

}
