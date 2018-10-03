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

import Model.Enigma.Storages.ReflectorStorage;
import Model.Enigma.Storages.RotorStorage;
import Model.Plugboard.HistoricalPlugboard;
import Model.Plugboard.IPlugboard;
import Model.Reflectors.IReflector;
import Model.Reflectors.ReflectorFileReader;
import Model.Rotors.Name;
import Model.Rotors.RotorFileReader;

import java.util.ArrayList;
import java.util.Map;

public class HistoricalEnigma {

    //<editor-fold desc="Constants">
    private static final int MESSAGE_SPACING = 4;
    //</editor-fold>

    //<editor-fold desc="Private Variables">
    private ReflectorStorage reflectorsAvailable;
    private RotorStorage rotorsAvailable;
    private RotorAssembly rotorAssembly;
    private IPlugboard plugboard;
    private IReflector reflector;
    private Message message;
    //</editor-fold>

    public HistoricalEnigma() {
        //TODO - Fix path
        rotorsAvailable = RotorFileReader.readRotorFile("TODO - fix path");
        reflectorsAvailable = ReflectorFileReader.readReflectorFile("TODO - fix path");
        rotorAssembly = new RotorAssembly(3);

        // TODO: 9/30/2018 FIX ME
        plugboard = new HistoricalPlugboard();

        //Initialize rotors with first three historical rotors (in left->right order: I II III)
        rotorAssembly.changeRotor(rotorsAvailable.getRotor(new Name("I")), 2);
        rotorAssembly.changeRotor(rotorsAvailable.getRotor(new Name("II")), 1);
        rotorAssembly.changeRotor(rotorsAvailable.getRotor(new Name("III")), 0);

        //Initialize to UKW-B
        rotorAssembly.changeReflector(reflectorsAvailable.getReflector((new Name("UKW-B"))));
    }

    /**
     * Used to populate the GUI's combo boxes with all available options for
     * rotors
     *
     * @return - The list of rotor names
     */
    public String[] getRotorsAvailable() {
        //Holder for the rotor names
        String[] temp = new String[rotorsAvailable.size()];

        //Get all the available rotor names
        for (int i = 0; i < rotorsAvailable.size(); i++) {
            temp[i] = rotorsAvailable.get(i).getRotorName();
        }

        return temp;
    }

    /**
     * Used to populate the GUI's combo box with all available options for
     * reflectors
     *
     * @return - The list of reflector names
     */
    public String[] getReflectorsAvailable() {
        //Holder for the reflector names
        String[] temp = new String[reflectorsAvailable.size()];

        //Get all the available reflector names
        for (int i = 0; i < reflectorsAvailable.size(); i++) {
            temp[i] = reflectorsAvailable.get(i).getReflectorName();
        }

        return temp;
    }

    public void stepMachine() {
        rotorAssembly.stepAssembly();
    }

    /**
     * Encodes messages into ciphertext
     *
     * @param message - plaintext message to encode
     *
     * @return - ciphertext of plaintext message
     */
    public String processMessage(String message) {

        String cipher = "";

        for (int i = 0; i < message.length(); i++) {
            cipher += processCharacter(message.charAt(i));
        }

        printToConsole();

        //Return the ciphertext
        return cipher.trim();
    }

    private void printToConsole() {
        //Print out plaintext and ciphertext to the console
        System.out.println("_____________________________________________");
        System.out.println("Input text:  " + message.getSpacedPlainText(4));
        System.out.println("Output text: " + message.getSpacedCipherText(4));
        System.out.println("_____________________________________________");
        System.out.println("");
    }

    /**
     * Takes a plaintext char and encodes it
     * @param charInput - plaintext char
     * @return - ciphered char
     */
    private char processCharacter(char charInput) {

        //Get the pin output from the plugboard
        int input = plugboard.convertInput(charInput);

        //Step the machine
        stepMachine();

        //Process the character through the machine
        input = rotorAssembly.processCharacter(input);

        //Run the final rotor output through the plugboard
        charInput = plugboard.convertOutput(input);

        return (charInput);
    }

    //<editor-fold desc="Adjust settings methods">
    public void changeLabels(String labelPositions) {

        //Sanitize the label input
        labelPositions = sanitizeInput(labelPositions);

        //If the label string is not equal in length to the number of rotors used
        if (labelPositions.length() != rotorsUsed.size()) {

            //Throw exception
            throw new UnsupportedOperationException("Label length does not equal number of rotors used");
        } else {

            //Otherwise, start counting at the last character in the string
            int j = labelPositions.length() - 1;

            //For each rotor, set its label based on the string
            for (int i = 0; i < rotorsUsed.size(); i++) {
//                System.out.println("rotorsUsed.get(" + i + ").getLabelPosition = " + rotorsUsed.get(i).getLabelPosition());
//                System.out.println("labelpositions.charAt(" + j + ") = " + labelPositions.charAt(j));
//                System.out.println("rotorsUsed == labelPosition???: " + (rotorsUsed.get(i).getLabelPosition() != labelPositions.charAt(j)));
                if (rotorsUsed.get(i).getLabelPosition() != labelPositions.charAt(j)) {
                    rotorsUsed.get(i).setLabelPosition(labelPositions.charAt(j));
                }

                j--;
            }
        }
    }

    private void changeLabel(int location, String label) {
        //Sanitize the label input
        label = sanitizeInput(label);

        if (rotorsUsed.get(location).getLabelPosition() != label.charAt(0)) {
            rotorsUsed.get(location).setLabelPosition(label.charAt(0));
        }
    }

    public void changeRotors(String rotorNames) {
        //Can't sanitize due to similar names of historical rotors
        rotorNames = rotorNames.toUpperCase();

        //Split along spacing
        String rotors[] = rotorNames.split(" ");

        //TODO - Implement checking of type/usage
        int k = rotors.length;

        for (int i = 0; i < rotors.length; i++) {

            //Untested, but should add a new rotor to set if one is not available
            if (i >= rotorsUsed.size()) {
                rotorsUsed.add(null);
            }

            //Decrement to travel from right to left on array
            k--;

//            System.out.println("----------------------------------------");
//            System.out.println("----------------------------------------");
//            System.out.println("k = " + k);
//            System.out.println("i = " + i);

            for (int j = 0; j < rotorsAvailable.size(); j++) {
//                System.out.println("j = " + j);
                if (rotorsAvailable.get(j).getRotorName().equalsIgnoreCase(rotors[k])) {
                    rotorsUsed.set(i, rotorsAvailable.get(j));
                    j = rotorsAvailable.size();
                }
            }
        }
    }

    public void changeRotor(int location, Name rotorName) {
        rotorAssembly.changeRotor(rotorsAvailable.getRotor(rotorName), location);
    }

    public void changeRotorStart(String rotorKeyPositions) {

        //Sanitize key input
        rotorKeyPositions = sanitizeInput(rotorKeyPositions);

        int j = rotorKeyPositions.length() - 1;

        if (rotorsUsed.size() != rotorKeyPositions.length()) {
            throw new UnsupportedOperationException("HistoricalRotor key length does not match rotors used");
        } else {
            for (int i = 0; i < rotorKeyPositions.length(); i++) {
                rotorsUsed.get(i).setKeyPosition(rotorKeyPositions.charAt(j));
                j--;
            }
        }
    }

    /**
     * Changes the reflector used by the enigma based on the passed input
     *
     * @param reflectorName - Name of the new reflector to use
     */
    public void changeReflector(String reflectorName) {
        //Sanitize reflector input
        reflectorName = sanitizeInput(reflectorName);

        //for all available reflectors
        for (int i = 0; i < reflectorsAvailable.size(); i++) {

            //if the passed reflector name equals the currently iterated reflector
            if (reflectorName.equalsIgnoreCase(reflectorsAvailable.get(i).getReflectorName())) {

                //Set the used reflector to the new reflector
                reflector = reflectorsAvailable.get(i);
            }
        }
    }

    /**
     * Steckers the plugboard based off the passed input
     *
     * @param steckeredPairs - String of letter pairs. Formatted "AB CD EF" etc
     */
    public void steckerPattern(String steckeredPairs) {
        //Sanitize input for pattern
        steckeredPairs = sanitizeInput(steckeredPairs);

        String temp = "";

        //For the entire pair, re-add the space sanitized earlier
        for (int i = 0; i < steckeredPairs.length(); i++) {
            if (i % 2 == 0) {
                temp += " ";
            }
            temp += steckeredPairs.charAt(i);
        }

        //Tell the plugboard to stecker the entire string
        plugboard.steckerPattern(temp);
    }
    //</editor-fold>

    /**
     * Sanitizes the inputs for each of the enigma inputs
     *
     * @param temp - the string to sanitize
     *
     * @return
     */
    private String sanitizeInput(String temp) {
        //Force uppercase and remove all spaces
        temp = temp.toUpperCase();
        temp = temp.replaceAll(" ", "");

        return temp;
    }

    //todo - put settings reading/writing into own class
    public void setSettings(String[] settings) {

        //todo - put settings reading/writing into own class
        String rotors = "";
        String labels = "";
        String keys = "";

        for (int i = 0; i < settings.length; i++) {
//            System.out.println("Settings[" + i + "] = " + settings[i]);
        }

        int rotorCount = (settings.length - 2) / 3;

        for (int i = 0; i < rotorCount; i++) {
            rotors = settings[i] + " " + rotors;
        }

        rotors = rotors.trim();

        for (int i = rotorCount; i < rotorCount * 2; i++) {
            labels = settings[i] + labels;
        }

        for (int i = rotorCount * 2; i < rotorCount * 3; i++) {
            keys = settings[i] + keys;
        }

//        System.out.println("rotors = " + rotors);
//        System.out.println("reflector = " + settings[rotorCount * 3]);
//        System.out.println("stecker pattern = " + settings[rotorCount * 3 + 1]);
//        System.out.println("labels = " + labels);
//        System.out.println("keys = " + keys);

        for (int i = 0; i < rotorCount; i++) {
            if (this.changeRotor(i, settings[i])) {
                this.changeLabel(i, "" + labels.charAt(labels.length() - i - 1));
            }
        }

        //Change out rotors
        this.changeRotors(rotors);
        
        //Resets the rotors to defaults
        this.changeLabels("AAA");
        this.changeRotorStart("AAA");
        
        //Set rotor settings, reflector and plugboard
        this.changeReflector(settings[rotorCount * 3]);
        this.plugboard.steckerPattern(settings[rotorCount * 3 + 1]);
        this.changeLabels(labels);
        this.changeRotorStart(keys);
    }

    /**
     * Get all the key positions for the current selection of rotors on the
     * enigma
     *
     * @return - the string array of all the rotor's key positions
     */
    public String[] getCurrentKeyPositions() {

        String rotorKeyPositions = "";

        for (int i = 0; i < rotorsUsed.size(); i++) {
            rotorKeyPositions = rotorsUsed.get(i).getKeyPosition() + " " + rotorKeyPositions;
        }

        return rotorKeyPositions.split(" ");
    }

    public static boolean[] getUsageStats(int usage) {

        boolean[] temp = new boolean[3];

        //Initialize usage to all false
        for (int i = 0; i < temp.length; i++) {
            temp[i] = false;
        }

        return temp;
    }
}
