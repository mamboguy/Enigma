package Controller.SubFrames;

import Model.Storages.ComponentFactory;
import Model.Rotors.IRotor;
import Model.Storages.IEnigmaComponent;
import View.SubFrames.IRotorGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class IRotorController implements ActionListener, KeyListener, IController {

    private IRotor model;
    private IRotorGUI view;

    public IRotorController(int rotorNumber){
        model = ComponentFactory.getInstance().getRandomRotor(this);
        view = new IRotorGUI("Rotor " + rotorNumber);

        view.registerListeners(this, this);
        model.addObserver(view);

        view.update(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox combo = (JComboBox) e.getSource();

        model = ComponentFactory.getInstance().getRotor((String) combo.getSelectedItem(), this);

        view.update(model);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        JTextField temp = (JTextField) e.getComponent();
        String typed = e.getKeyChar() + "";

        System.out.println(typed);

        temp.setText(typed);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public IEnigmaComponent getModel() {
        return model;
    }

    @Override
    public JPanel getView() {
        return view;
    }
}
