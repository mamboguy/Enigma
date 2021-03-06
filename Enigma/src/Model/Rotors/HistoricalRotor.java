package Model.Rotors;

import Model.Rotors.RotorSubAssemblies.CircularLinkedList;
import Model.Rotors.RotorSubAssemblies.LabelSlot;
import Model.Rotors.RotorSubAssemblies.Node;
import Model.Rotors.RotorSubAssemblies.RotorSlot;
import View.Observer.IObserver;

import java.util.ArrayList;

public class HistoricalRotor implements IRotor {

    private CircularLinkedList<RotorSlot> rotor;
    private CircularLinkedList<LabelSlot> label;
    private boolean stepNextUse;
    private String name;
    private ArrayList<IObserver> observers;

    public static final String BASE_SEQUENCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public HistoricalRotor(String name, String wiringSequence, String notchLocations) {
        rotor = new CircularLinkedList<>();
        label = new CircularLinkedList<>();

        observers = new ArrayList<IObserver>();

        this.name = name;

        createRotor(wiringSequence, notchLocations);
    }

    void createRotor(String wiringSequence, String notchLocations) {

        //Create right rotor from base input wiring sequence
        createRightRotorHalf(wiringSequence, notchLocations);

        //Now set the left offset based on right offset
        createLeftRotorHalf();

        //Create label wheel
        createLabelWheel();

        //Set rotor to Label = "A"
        setDefaults();
    }

    void createLabelWheel() {
        for (int i = 0; i < BASE_SEQUENCE.length(); i++) {
            label.insert(new LabelSlot(BASE_SEQUENCE.charAt(i)));
        }
    }

    public int getSize() {
        return rotor.getSize();
    }

    void createLeftRotorHalf() {
        for (int i = 0; i < rotor.getSize(); i++) {

            //Go to the next position to assign
            while (rotor.getData().getDefaultLabel() != (char) ('A' + i)) {
                rotor.stepNext();
            }

            //Get the offset at that position
            int offset = rotor.getData().getRightToLeftOffset();

            //Step forward or backward to that offset's end point
            for (int j = 0; j < Math.abs(offset); j++) {
                if (offset < 0) {
                    rotor.stepBack();
                } else {
                    rotor.stepNext();
                }
            }

            rotor.getData().setLeftToRightOffset(offset * -1);
        }
    }

    void createRightRotorHalf(String wiringSequence, String notchLocations) {
        for (int i = 0; i < wiringSequence.length(); i++) {
            rotor.insert(new RotorSlot(BASE_SEQUENCE.charAt(i), wiringSequence.charAt(i), notchLocations.contains(BASE_SEQUENCE.charAt(i) + "")));
        }
    }

    void setDefaults() {
        resetRotor();

        resetLabel();
    }

    void resetRotor() {
        while (rotor.getData().getDefaultLabel() != 'A') {
            rotor.stepNext();
        }
    }

    void resetLabel() {

        Node<RotorSlot> temp = rotor.getNodeAtPosition(0);

        while (temp.getData().getDefaultLabel() != 'A') {
            temp = temp.getNext();
        }

        while (label.getData().getLabel() != temp.getData().getDefaultLabel()) {
            label.stepNext();
        }
    }

    public void printRotor() {

        System.out.println("Right Input");

        for (int i = 0; i < rotor.getSize(); i++) {
            printOutAligned(rotor.getData().getRightToLeftOffset());
            rotor.stepNext();
        }

        System.out.println();
        System.out.println("Left Input");

        for (int i = 0; i < rotor.getSize(); i++) {
            printOutAligned(rotor.getData().getLeftToRightOffset());
            rotor.stepNext();
        }

    }

    void printOutAligned(int leftToRightOffset) {

        String temp = " <-> ";

        if (leftToRightOffset > 0) {
            temp = " " + temp;
        }

        if (leftToRightOffset < 10) {
            temp = " " + temp;
        }

        System.out.print(leftToRightOffset + temp);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLeftOutput(int rightPinInput) {
        return (rotor.getNodeAtPosition(rightPinInput).getData().getRightToLeftOffset() + rightPinInput) % 26;
    }

    @Override
    public int getRightOutput(int leftPinInput) {
        return (rotor.getNodeAtPosition(leftPinInput).getData().getLeftToRightOffset() + leftPinInput) % 26;
    }

    @Override
    public char getKeyPosition() {
        return label.getData().getLabel();
    }

    @Override
    public char getLabelPosition() {
        return label.getData().getLabel();
    }

    @Override
    public boolean willStepNextUse() {

        if (stepNextUse) {
            stepNextUse = false;

            return true;
        }

        return stepNextUse;
    }

    void setKeyPosition(char position) {
        while (label.getData().getLabel() != position) {
            rotor.stepNext();
            label.stepNext();
        }

        stepNextUse = rotor.getData().isNotch();

        notifyObservers();
    }

    void setLabelPosition(char position) {

        resetLabel();

        for (int i = 'A'; i < position; i++) {
            label.stepNext();
        }

        notifyObservers();
    }

    @Override
    public void stepRotor() {
        stepRotorForward();
    }

    @Override
    public void stepRotorForward() {
        rotor.stepNext();
        label.stepNext();

        stepNextUse = rotor.getData().isNotch();

        notifyObservers();
    }

    @Override
    public void stepRotorBackward() {
        rotor.stepBack();
        label.stepBack();

        stepNextUse = rotor.getData().isNotch();

        notifyObservers();
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(this);
        }
    }
}
