package View.SubFrames;

import Controller.Filters.PlugboardDocumentFilter;
import Model.Rotors.IRotor;
import Model.Storages.ComponentFactory;
import View.Observer.IObservable;
import View.Observer.IObserver;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class IRotorGUI extends JPanel implements IObserver {

    private JTextField label;
    private JTextField key;
    private JComboBox<String> rotorNames;

    //Standard height and width for fields involved with rotors
    private static final Dimension STANDARD_SPACER_SIZE = new Dimension(5, 5);
    private static final int ROTOR_FIELD_HEIGHT = 30;
    private static final int ROTOR_FIELD_WIDTH = 130;

    public IRotorGUI(String name) {
        super();

        this.label = new JTextField();
        this.key = new JTextField();
        this.rotorNames = new JComboBox();

        insertRotorNames();

        standardizeSettings(label, "Label", "Set Rotor Label Here");
        standardizeSettings(key, "Key", "Set Rotor Key Here");
        standardizeSettings(rotorNames, "Available Rotors", "Set Rotor To Use Here");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel(name));
        this.add(standardSpacer());
        this.add(rotorNames);
        this.add(standardSpacer());
        this.add(label);
        this.add(standardSpacer());
        this.add(key);
    }

    private void insertRotorNames() {
        String[] names = ComponentFactory.getInstance().getAllRotorNames();

        for (int i = 0; i < names.length; i++) {
            rotorNames.addItem(names[i]);
        }
    }

    private Component standardSpacer() {
        return Box.createRigidArea(STANDARD_SPACER_SIZE);
    }

    public void registerListeners(ActionListener al, KeyListener kl) {
        label.addKeyListener(kl);
        key.addKeyListener(kl);
        rotorNames.addActionListener(al);

        AbstractDocument labelDocument = (AbstractDocument) label.getDocument();
        labelDocument.setDocumentFilter(new PlugboardDocumentFilter());

        AbstractDocument keyDocument = (AbstractDocument) key.getDocument();
        keyDocument.setDocumentFilter(new PlugboardDocumentFilter());
    }

    private void standardizeSettings(JComponent component, String name, String toolTipText) {
        component.setName(name);
        component.setPreferredSize(new Dimension(ROTOR_FIELD_WIDTH, ROTOR_FIELD_HEIGHT));
        component.setMinimumSize(new Dimension(ROTOR_FIELD_WIDTH, ROTOR_FIELD_HEIGHT));
        component.setToolTipText(toolTipText);
    }

    @Override
    public void update(IObservable observable) {
        if (observable instanceof IRotor) {
            IRotor temp = (IRotor) observable;

            if (hasName(temp.getName())) {
                rotorNames.setSelectedIndex(getIndex(temp.getName()));
            }
            label.setText(temp.getLabelPosition() + "");
            key.setText(temp.getKeyPosition() + "");
        }
    }

    private boolean hasName(String name) {
        for (int i = 0; i < rotorNames.getItemCount(); i++) {
            if (rotorNames.getItemAt(i).equals(name)) {
                return true;
            }
        }

        return false;
    }

    private int getIndex(String name) {
        for (int i = 0; i < rotorNames.getItemCount(); i++) {
            if (rotorNames.getItemAt(i).equals(name)) {
                return i;
            }
        }

        return -1;
    }
}
