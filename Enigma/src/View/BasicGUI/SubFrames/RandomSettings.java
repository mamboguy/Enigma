/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.BasicGUI.SubFrames;

import Controller.BasicGUIController;
import View.BasicGUI.BasicInputScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Date Created Dec 20, 2017
 *
 * @author Michael C
 */
public class RandomSettings
        extends JFrame {

    private JButton approve;

    private JRadioButton lessThan;
    private JRadioButton equalTo;
    private JRadioButton greaterThan;

    private JCheckBox orEqualTo;

    JComboBox numOfSteckers;

    private JList rotorSelection;

    private boolean isBuilt = false;

    public RandomSettings() {
        approve = new JButton("Ok");
        approve.setName("Ok");

        lessThan = new JRadioButton("Less than");
        greaterThan = new JRadioButton("Greater than");
        equalTo = new JRadioButton("Equal to");
        equalTo.setSelected(true);

        orEqualTo = new JCheckBox("or equal to?");
    }

    public void openPanel(String[] rotorNames) {

        if (!isBuilt) {
            createPanel(rotorNames);
            isBuilt = true;
        } else {
            this.setVisible(true);
        }

        hideOrEqualTo();
    }

    public void registerListener(ActionListener al) {
        approve.addActionListener(al);

        equalTo.addActionListener(al);
        greaterThan.addActionListener(al);
        lessThan.addActionListener(al);
    }

    public String[] getSettings() {
        ArrayList<String> settings = new ArrayList<String>();

        int[] temp = rotorSelection.getSelectedIndices();

        for (int i = 0; i < temp.length; i++) {
            System.out.println("i = " + temp[i]);

            settings.add(temp[i] + "");
        }

        settings.add(numOfSteckers.getSelectedItem().toString());

        if (equalTo.isSelected()) {
            settings.add("1");
        } else if (lessThan.isSelected()) {
            if (orEqualTo.isSelected()) {
                settings.add("3");
            } else {
                settings.add("2");
            }
        } else {
            if (orEqualTo.isSelected()) {
                settings.add("5");
            } else {
                settings.add("4");
            }
        }

        String[] settingsString = new String[settings.size()];

        for (int i = 0; i < settings.size(); i++) {
            settingsString[i] = settings.get(i).toString();
        }

        return settingsString;
        //TODO - Implement settings pull
    }

    public void closePanel() {
        this.dispose();
    }

    public void hideOrEqualTo() {
        orEqualTo.setVisible(false);
        orEqualTo.setSelected(false);
    }

    public void showOrEqualTo() {
        orEqualTo.setVisible(true);
    }

    private void createPanel(String[] rotorNames) {

        //<editor-fold defaultstate="collapsed" desc="JList of HistoricalRotorDeprecated Names">
        rotorSelection = new JList(rotorNames);
        rotorSelection.setToolTipText("Select all rotors that are available to be randomed");
        rotorSelection.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        rotorSelection.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        rotorSelection.setVisibleRowCount(4);
        rotorSelection.setFixedCellWidth(50);

        rotorSelection.setSelectionInterval(0, rotorNames.length - 1);
        //</editor-fold>        

        JPanel rotorSelectorPanel = new JPanel();
        rotorSelectorPanel.setLayout(new BoxLayout(rotorSelectorPanel, BoxLayout.Y_AXIS));

        rotorSelectorPanel.add(BasicInputScreen.centeredLabel("Rotors Available"));
        rotorSelectorPanel.add(rotorSelection);

        numOfSteckers = new JComboBox();
        for (int i = 0; i < BasicGUIController.VALID_CHARS / 2; i++) {
            numOfSteckers.addItem(i + 1);
        }

        lessThan.setName("lessThan");
        greaterThan.setName("greaterThan");
        equalTo.setName("equalTo");

        ButtonGroup options = new ButtonGroup();
        options.add(lessThan);
        options.add(equalTo);
        options.add(greaterThan);

        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        optionPanel.add(lessThan);
        optionPanel.add(equalTo);
        optionPanel.add(greaterThan);

        JPanel radioPanel = new JPanel();
        radioPanel.add(orEqualTo);

        JPanel optionButtonsPanel = new JPanel();
        optionButtonsPanel.add(optionPanel);
        optionButtonsPanel.add(radioPanel);

        JPanel steckerPanel = new JPanel();
        steckerPanel.setLayout(new BoxLayout(steckerPanel, BoxLayout.Y_AXIS));
        steckerPanel.add(BasicInputScreen.centeredLabel("Number of pairs to stecker"));
        steckerPanel.add(numOfSteckers);

        JPanel steckerOptions = new JPanel();
        steckerOptions.setLayout(new BoxLayout(steckerOptions, BoxLayout.Y_AXIS));
        steckerOptions.add(steckerPanel);
        steckerOptions.add(optionButtonsPanel);

        JPanel settingsPanel = new JPanel();
        settingsPanel.add(rotorSelectorPanel);
        settingsPanel.add(steckerOptions);

        JPanel masterPanel = new JPanel();
        masterPanel.setLayout(new BorderLayout(5, 5));

        masterPanel.add(settingsPanel, BorderLayout.CENTER);
        masterPanel.add(approve, BorderLayout.SOUTH);

        this.add(masterPanel);
        this.setTitle("Random Settings v0.01");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
    }

}
