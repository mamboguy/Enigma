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
        
        rotor1.printWiring();

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

        rotor1.printWiring();
        
        return cipher.trim();
    }

    private char inputCharacter(char charInput) {
        int input = charInput - 65;

        stepMachine();
        
        rotor1.printWiring();
        
        System.out.println("charInput = " + charInput);
        System.out.println("Input = " + input);

        input = rotor1.getLeftOutput(input);
        System.out.println("Left exit of rotor 1 = " + ((char)(input+65)));
        
        input = rotor2.getLeftOutput(input);
        System.out.println("Left exit of rotor 2 = " + ((char)(input+65)));
        
        input = rotor3.getLeftOutput(input);
        System.out.println("Left exit of rotor 3 = " + ((char)(input+65)));

        if (fourthRotorUsed) {
            input = rotor4.getLeftOutput(input);
        }

        input = reflector.getReflection(input);
        System.out.println("Reflection = " + ((char)(input+65)));

        if (fourthRotorUsed) {
            input = rotor4.getRightOutput(input);
        }

        input = rotor3.getRightOutput(input);
        System.out.println("Right exit of rotor 3 = " + ((char)(input+65)));
        
        input = rotor2.getRightOutput(input);
        System.out.println("Right exit of rotor 2 = " + ((char)(input+65)));
        System.out.println("Right exit of rotor 2 pin = " + input);
        
        input = rotor1.getRightCharOutput(input);
        System.out.println("Right exit of rotor 1 = " + (char) input);

        return ((char) input);
    }

    //<editor-fold desc="Adjust settings methods">
    public void changeLabels(String temp) {

        temp = sanitizeInput(temp);

        if (fourthRotorUsed && temp.length() != 4) {
            //TODO - IMPLEMENT ERROR?
        } else if (!fourthRotorUsed && temp.length() != 3) {
            //TODO - IMPLEMENT ERROR?
        } else {

            if (fourthRotorUsed) {
                rotor4.setLabelPosition(temp.charAt(0));
                rotor3.setLabelPosition(temp.charAt(1));
                rotor2.setLabelPosition(temp.charAt(2));
                rotor1.setLabelPosition(temp.charAt(3));
            } else {
                rotor3.setLabelPosition(temp.charAt(0));
                rotor2.setLabelPosition(temp.charAt(1));
                rotor1.setLabelPosition(temp.charAt(2));
            }
        }
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
