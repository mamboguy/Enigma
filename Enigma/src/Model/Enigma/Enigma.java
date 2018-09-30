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

import Model.Plugboard.HistoricalPlugboard;
import Model.Plugboard.IPlugboard;
import Model.Reflectors.HistoricalReflector;
import Model.Reflectors.IReflector;
import Model.Reflectors.ReflectorFileReader;
import Model.Rotors.IRotor;
import Model.Rotors.RotorFileReader;

import java.util.ArrayList;

public class Enigma {

    //<editor-fold desc="Constants">
    private static final int MESSAGE_SPACING = 4;
    //</editor-fold>

    //<editor-fold desc="Private Variables">
    private ArrayList<IReflector> reflectorsAvailable;
    private ArrayList<IRotor> rotorsAvailable;
    private ArrayList<IRotor> rotorsUsed;
    private IPlugboard plugboard;

    private IReflector reflector;

    //</editor-fold>
    public Enigma() {
        //TODO - Fix path
        rotorsAvailable = RotorFileReader.readRotorFile("TODO - fix path");
        reflectorsAvailable = ReflectorFileReader.readReflectorFile("TODO - fix path");
        rotorsUsed = new ArrayList<>();

        // TODO: 9/30/2018 FIX ME
        plugboard = new HistoricalPlugboard();

        //Initialize rotors with first three historical rotors (in left->right order: I II III)
        rotorsUsed.add(rotorsAvailable.get(2));
        rotorsUsed.add(rotorsAvailable.get(1));
        rotorsUsed.add(rotorsAvailable.get(0));

        //Initialize to UKW-B
        reflector = reflectorsAvailable.get(1);
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
        //Always step the right-most rotor
        rotorsUsed.get(0).stepRotor();

        boolean stepNext = rotorsUsed.get(0).willStepNextUse();

        //Starting at the 2nd from the right rotor, move to each rotor from right to left
        for (int i = 1; i < rotorsUsed.size(); i++) {

            //If the current rotor will step on the next use (this use) or if the last rotor stepped
            if (stepNext) {

                //Check to see if the next rotor should step based off this rotor config
                stepNext = rotorsUsed.get(i).willStepNextUse();

                //Step the current rotor
                rotorsUsed.get(i).stepRotor();
            }
        }
    }

    /**
     * Encodes messages into ciphertext
     *
     * @param message - plaintext message to encode
     *
     * @return - ciphertext of plaintext message
     */
    public String inputMessage(String message) {

        //Sanitize the message input
        message = sanitizeInput(message);

        String plain = "";
        String cipher = "";

        //Print out the key settings onto the console
        //printKeySettings();
        //Run through the entire message, one char at a time
        for (int i = 0; i < message.length(); i++) {
            //If the message is at its spacing requirement, add in a space
            if (i % MESSAGE_SPACING == 0) {
                plain += " ";
                cipher += " ";
            }
            //Add plaintext char to the plaintext string
            plain += message.charAt(i);

            //Add encoded char to the ciphertext string
            cipher += inputCharacter(message.charAt(i));
        }

        //Print out plaintext and ciphertext to the console
        System.out.println("_____________________________________________");
        System.out.println("Input text:  " + plain.trim());
        System.out.println("Output text: " + cipher.trim());
        System.out.println("_____________________________________________");
        System.out.println("");

        //Return the ciphertext
        return cipher.trim();
    }

    /**
     * Takes a plaintext char and encodes it
     *
     * @param charInput - plaintext char
     *
     * @return - ciphered char
     */
    private char inputCharacter(char charInput) {

        //Get the pin output from the plugboard
        int input = plugboard.convertInput(charInput);

        //Step the machine
        stepMachine();

        //Step through the rotors from right to left presenting the last rotors
        //output as the next rotor's input
        for (int i = 0; i < rotorsUsed.size(); i++) {
            input = rotorsUsed.get(i).getLeftOutput(input);
        }

        //Reflect the last rotors output
        input = reflector.getReflection(input);

        //Step through the rotors from right to left presenting the last rotor's
        //output as the next rotor's input
        for (int i = rotorsUsed.size() - 1; i >= 0; i--) {
            input = rotorsUsed.get(i).getRightOutput(input);
        }

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
                System.out.println("rotorsUsed.get(" + i + ").getLabelPosition = " + rotorsUsed.get(i).getLabelPosition());
                System.out.println("labelpositions.charAt(" + j + ") = " + labelPositions.charAt(j));
                System.out.println("rotorsUsed == labelPosition???: " + (rotorsUsed.get(i).getLabelPosition() != labelPositions.charAt(j)));
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

            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("k = " + k);
            System.out.println("i = " + i);

            for (int j = 0; j < rotorsAvailable.size(); j++) {
                System.out.println("j = " + j);
                if (rotorsAvailable.get(j).getRotorName().equalsIgnoreCase(rotors[k])) {
                    rotorsUsed.set(i, rotorsAvailable.get(j));
                    j = rotorsAvailable.size();
                }
            }
        }
    }

    public boolean changeRotor(int location, String rotorName) {

        boolean changedRotor = false;

        //Can't sanitize due to similar names of historical rotors
        rotorName = rotorName.toUpperCase();

        //Untested, but should add a new rotor to set if one is not available        
        if (location >= rotorsUsed.size()) {
            rotorsUsed.add(null);
        } else {
            if (!rotorsUsed.get(location).getRotorName().equals(rotorName)) {
                for (int i = 0; i < rotorsAvailable.size(); i++) {
                    if (rotorsAvailable.get(i).getRotorName().equalsIgnoreCase(rotorName)) {
                        rotorsUsed.set(location, rotorsAvailable.get(i));

                        changedRotor = true;
                    }
                }
            }
        }

        return changedRotor;
    }

    public void changeRotorStart(String rotorKeyPositions) {

        //Sanitize key input
        rotorKeyPositions = sanitizeInput(rotorKeyPositions);

        int j = rotorKeyPositions.length() - 1;

        if (rotorsUsed.size() != rotorKeyPositions.length()) {
            throw new UnsupportedOperationException("HistoricalRotorDeprecated key length does not match rotors used");
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
            System.out.println("Settings[" + i + "] = " + settings[i]);
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

        System.out.println("rotors = " + rotors);
        System.out.println("reflector = " + settings[rotorCount * 3]);
        System.out.println("stecker pattern = " + settings[rotorCount * 3 + 1]);
        System.out.println("labels = " + labels);
        System.out.println("keys = " + keys);

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

//        //Set usage flags
//        if (usage >= 100) {
//            temp[HistoricalRotorDeprecated.KRIEGSMARINE] = true;
//            usage -= 100;
//        }
//
//        if (usage >= 10) {
//            temp[HistoricalRotorDeprecated.LUFTWAFFE] = true;
//            usage -= 10;
//        }
//
//        if (usage == 1) {
//            temp[HistoricalRotorDeprecated.WEHRMACHT] = true;
//        }

        return temp;
    }
}
