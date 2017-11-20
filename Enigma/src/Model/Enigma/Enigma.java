package Model.Enigma;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michael C
 */
import Model.Plugboard.Plugboard;
import Model.Reflectors.Reflector;
import Model.Reflectors.ReflectorFileReader;
import Model.Rotors.Rotor;
import Model.Rotors.RotorFileReader;
import java.util.ArrayList;

public class Enigma {

    //<editor-fold desc="Constants">
    private static final int MESSAGE_SPACING = 5;
    //</editor-fold>

    //<editor-fold desc="Private Variables">
    private ArrayList<Reflector> reflectorsAvailable;
    private ArrayList<Rotor> rotorsAvailable;
    private Plugboard plugboard;

    //Rotor order from left to right is: 4, 3, 2, 1
    private Rotor rotor4;
    private Rotor rotor3;
    private Rotor rotor2;
    private Rotor rotor1;
    private Reflector reflector;

    //Tracks whether fourth rotor is used
    private boolean fourthRotorUsed;
    //</editor-fold>

    public Enigma() {
        //TODO - Fix path
        rotorsAvailable = RotorFileReader.readRotorFile("TODO - fix path");
        reflectorsAvailable = ReflectorFileReader.readReflectorFile("TODO - fix path");
        plugboard = new Plugboard();

        rotor3 = rotorsAvailable.get(0);
        rotor2 = rotorsAvailable.get(1);
        rotor1 = rotorsAvailable.get(2);
        reflector = reflectorsAvailable.get(1);
    }

    public void stepMachine() {

        if (rotor3.willStepNextUse()) {
            if (fourthRotorUsed) {
                rotor4.stepRotor();
            }
        }

        if (rotor2.willStepNextUse()) {
            rotor3.stepRotor();
        }

        if (rotor1.willStepNextUse()) {
            rotor2.stepRotor();
        }

        rotor1.stepRotor();
    }

    public String inputMessage(String message) {

        message = sanitizeInput(message);

        String plain = "";
        String cipher = "";

        printKeySettings();

        for (int i = 0; i < message.length(); i++) {
            if (i % MESSAGE_SPACING == 0) {
                plain += " ";
                cipher += " ";
            }
            plain += message.charAt(i);
            cipher += inputCharacter(message.charAt(i));
        }

        System.out.println("_____________________________________________");
        System.out.println("Input text:  " + plain.trim());
        System.out.println("Output text: " + cipher.trim());
        System.out.println("_____________________________________________");
        System.out.println("");

        return cipher.trim();
    }

    private char inputCharacter(char charInput) {
        
        charInput = plugboard.getPairedLetter(charInput);
        int input = charInput - 65;

        stepMachine();

        input = rotor1.getLeftOutput(input);
        input = rotor2.getLeftOutput(input);
        input = rotor3.getLeftOutput(input);

        if (fourthRotorUsed) {
            input = rotor4.getLeftOutput(input);
        }

        input = reflector.getReflection(input);

        if (fourthRotorUsed) {
            input = rotor4.getRightOutput(input);
        }

        input = rotor3.getRightOutput(input);
        input = rotor2.getRightOutput(input);
        input = rotor1.getRightCharOutput(input);

        return ((char) input);
    }

    //<editor-fold desc="Adjust settings methods">
    public void changeLabels(String labelPositions) {

        labelPositions = sanitizeInput(labelPositions);

        if (fourthRotorUsed && labelPositions.length() != 4) {
            //TODO - IMPLEMENT ERROR?
            throw new UnsupportedOperationException();
        } else if (!fourthRotorUsed && labelPositions.length() != 3) {
            //TODO - IMPLEMENT ERROR?
            throw new UnsupportedOperationException();
        } else {
            if (fourthRotorUsed) {
                rotor4.setLabelPosition(labelPositions.charAt(0));
                rotor3.setLabelPosition(labelPositions.charAt(1));
                rotor2.setLabelPosition(labelPositions.charAt(2));
                rotor1.setLabelPosition(labelPositions.charAt(3));
            } else {
                rotor3.setLabelPosition(labelPositions.charAt(0));
                rotor2.setLabelPosition(labelPositions.charAt(1));
                rotor1.setLabelPosition(labelPositions.charAt(2));
            }
        }
    }

    public void changeRotors(String rotorNames) {
        rotorNames = rotorNames.toUpperCase();

        String rotors[] = rotorNames.split(" ");

        //TODO - Implement checking of type/usage
        switch (rotors.length) {
            case 4:
                fourthRotorUsed = true;

                for (int i = 0; i < rotorsAvailable.size(); i++) {
                    if (rotors[0].equalsIgnoreCase(rotorsAvailable.get(i).getRotorName())) {
                        rotor4 = rotorsAvailable.get(i);
                    }
                }
            case 3:
                for (int i = 0; i < rotors.length; i++) {

                    for (int j = 0; j < rotorsAvailable.size(); j++) {

                        if (rotors[i].equalsIgnoreCase(rotorsAvailable.get(j).getRotorName())) {

                            switch (i) {
                                case 0:
                                    rotor3 = rotorsAvailable.get(j);
                                    break;
                                case 1:
                                    rotor2 = rotorsAvailable.get(j);
                                    break;
                                case 2:
                                    rotor1 = rotorsAvailable.get(j);
                                    break;
                            }

                        }
                    }
                }

                break;
            default:
                throw new UnsupportedOperationException("Too many/few rotors");
        }
    }

    public void changeRotorStart(String rotorKeyPositions) {

        rotorKeyPositions = sanitizeInput(rotorKeyPositions);

        if (fourthRotorUsed && rotorKeyPositions.length() != 4) {
            //TODO - IMPLEMENT ERROR?
            throw new UnsupportedOperationException();
        } else if (!fourthRotorUsed && rotorKeyPositions.length() != 3) {
            //TODO - IMPLEMENT ERROR?
            throw new UnsupportedOperationException();
        } else {
            if (fourthRotorUsed) {
                rotor4.setKeyPosition(rotorKeyPositions.charAt(0));
                rotor3.setKeyPosition(rotorKeyPositions.charAt(1));
                rotor2.setKeyPosition(rotorKeyPositions.charAt(2));
                rotor1.setKeyPosition(rotorKeyPositions.charAt(3));
            } else {
                rotor3.setKeyPosition(rotorKeyPositions.charAt(0));
                rotor2.setKeyPosition(rotorKeyPositions.charAt(1));
                rotor1.setKeyPosition(rotorKeyPositions.charAt(2));
            }
        }
    }

    public void changeReflector(String reflectorName) {
        reflectorName = sanitizeInput(reflectorName);

        for (int i = 0; i < reflectorsAvailable.size(); i++) {
            if (reflectorName.equalsIgnoreCase(reflectorsAvailable.get(i).getName())) {
                reflector = reflectorsAvailable.get(i);
            }
        }
    }

    public void steckerPairs(String steckeredPairs) {
        steckeredPairs = sanitizeInput(steckeredPairs);
        
        String temp = "";
        
        for (int i = 0; i < steckeredPairs.length(); i++) {
            if (i % 2 == 0){
                temp += " ";
            }
            temp += steckeredPairs.charAt(i);
        }
        
        plugboard.steckerPattern(temp);
    }
    //</editor-fold>

    //<editor-fold desc="Print methods">
    private void printKeySettings() {
        String rotorString = rotor3.getRotorName() + " " + rotor2.getRotorName() + " " + rotor1.getRotorName();
        String rotorLabels = "" + rotor3.getLabelPosition() + rotor2.getLabelPosition() + rotor1.getLabelPosition();
        String rotorKeyPositions = "" + rotor3.getKeyPosition() + rotor2.getKeyPosition() + rotor1.getKeyPosition();

        if (fourthRotorUsed) {
            rotorString = rotor4.getRotorName() + " " + rotorString;
            rotorLabels = rotor4.getLabelPosition() + rotorLabels;
            rotorKeyPositions = rotor4.getKeyPosition() + rotorKeyPositions;
        }

        System.out.println("_____________________________________________");
        System.out.println("_____________________________________________");
        System.out.println("KEY SETTINGS: ");
        System.out.println("Reflector: " + reflector.getName());
        System.out.println("Rotors: " + rotorString);
        System.out.println("Label Positions: " + rotorLabels);
        //TODO - Implement plugboard
        System.out.println("Plugboard Settings: ");
        System.out.println("Key Positions: " + rotorKeyPositions);
    }

    public void printAvailableRotorStats() {
        for (int i = 0; i < rotorsAvailable.size(); i++) {
            System.out.println("Rotor " + i + " = " + rotorsAvailable.get(i));
        }
    }

    public void printAvailableReflectorStats() {
        for (int i = 0; i < reflectorsAvailable.size(); i++) {
            System.out.println("Reflector " + i + " = " + reflectorsAvailable.get(i));
        }
    }
    //</editor-fold>

    private String sanitizeInput(String temp) {
        temp = temp.toUpperCase();
        temp = temp.replaceAll(" ", "");

        return temp;
    }
}
