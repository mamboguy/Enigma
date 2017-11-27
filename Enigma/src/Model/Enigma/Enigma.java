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
    private ArrayList<Rotor> rotorsUsed;
    private Plugboard plugboard;

    //Rotor order from left to right is: 4, 3, 2, 1
    //TODO - Convert to arraylist
    private Rotor rotor4;
    private Rotor rotor3;
    private Rotor rotor2;
    private Rotor rotor1;

    private Reflector reflector;

    //todo - remove upon conversion to arraylist
    //Tracks whether fourth rotor is used
    private boolean fourthRotorUsed;
    //</editor-fold>

    public Enigma() {
        //TODO - Fix path
        rotorsAvailable = RotorFileReader.readRotorFile("TODO - fix path");
        reflectorsAvailable = ReflectorFileReader.readReflectorFile("TODO - fix path");
        plugboard = new Plugboard();

        //Initialize rotors with first three historical rotors
        rotor3 = rotorsAvailable.get(0);
        rotor2 = rotorsAvailable.get(1);
        rotor1 = rotorsAvailable.get(2);

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

        //Todo - rework for conversion to arraylist
        if (rotor3.willStepNextUse()) {
            if (fourthRotorUsed) {
                rotor4.stepRotor();
                rotor3.stepRotor();
            }
        } else if (rotor2.willStepNextUse()) {
            rotor3.stepRotor();
            rotor2.stepRotor();
        } else if (rotor1.willStepNextUse()) {
            rotor2.stepRotor();
        }

        rotor1.stepRotor();
    }

    /**
     * Encodes messages into ciphertext
     *
     * @param message - plaintext message to encode
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
//        System.out.println("_____________________________________________");
//        System.out.println("Input text:  " + plain.trim());
//        System.out.println("Output text: " + cipher.trim());
//        System.out.println("_____________________________________________");
//        System.out.println("");
        //Return the ciphertext
        return cipher.trim();
    }

    /**
     * Takes a plaintext char and encodes it
     *
     * @param charInput - plaintext char
     * @return - ciphered char
     */
    private char inputCharacter(char charInput) {

        //Run the char through the plugboard first
        charInput = plugboard.getPairedLetter(charInput);

        //Convert the plugboard output into a pin input
        int input = charInput - 65;

        //Step the machine
        stepMachine();

        //todo - rework for conversion to arraylist
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

        //Run the final rotor output through the plugboard
        charInput = plugboard.getPairedLetter((char) input);

        return (charInput);
    }

    //<editor-fold desc="Adjust settings methods">
    public void changeLabels(String labelPositions) {

        //Sanitize the label input
        labelPositions = sanitizeInput(labelPositions);

        //Todo - rework for conversion to arraylist
        if (fourthRotorUsed && labelPositions.length() != 4) {
            //TODO - IMPLEMENT ERROR?
            throw new UnsupportedOperationException();
        } else if (!fourthRotorUsed && labelPositions.length() != 3) {
            //TODO - IMPLEMENT ERROR?
            throw new UnsupportedOperationException();
        } else {

            int i = 0;

            if (fourthRotorUsed) {

                if (rotor4.getLabelPosition() == labelPositions.charAt(0)) {
                    rotor4.setLabelPosition(labelPositions.charAt(0));
                }

                i = 1;
            }

            if (rotor3.getLabelPosition() != labelPositions.charAt(i)) {
                rotor3.setLabelPosition(labelPositions.charAt(i));
            }
            i++;

            if (rotor2.getLabelPosition() != labelPositions.charAt(i)) {
                rotor2.setLabelPosition(labelPositions.charAt(i));
            }
            i++;

            if (rotor1.getLabelPosition() != labelPositions.charAt(i)) {
                rotor1.setLabelPosition(labelPositions.charAt(i));
            }
        }
    }

    public void changeRotors(String rotorNames) {
        rotorNames = rotorNames.toUpperCase();

        String rotors[] = rotorNames.split(" ");

        //TODO - Implement checking of type/usage
        //Todo - rework for conversion to arraylist
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

        //Sanitize key input
        rotorKeyPositions = sanitizeInput(rotorKeyPositions);

        //todo - rework for conversion to arraylist
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

        //TODO - Fix for if GUI adds rotor then enigma adds rotor
        this.changeRotors(rotors);
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
        //todo - rework for conversion to arraylist
        String rotorKeyPositions = rotor3.getKeyPosition() + " " + rotor2.getKeyPosition() + " " + rotor1.getKeyPosition();

        if (fourthRotorUsed) {
            rotorKeyPositions = rotor4.getKeyPosition() + " " + rotorKeyPositions;
        }

        String[] temp = rotorKeyPositions.split(" ");

        return temp;
    }

    public static boolean[] getUsageStats(int usage) {
       
        boolean[] temp = new boolean[3];
       
        //Initialize usage to all false
        for (int i = 0; i < temp.length; i++) {
            temp[i] = false;
        }

        //Set usage flags
        if (usage >= 100) {
            temp[Rotor.KRIEGSMARINE] = true;
            usage -= 100;
        }

        if (usage >= 10) {
            temp[Rotor.LUFTWAFFE] = true;
            usage -= 10;
        }

        if (usage == 1) {
            temp[Rotor.WEHRMACHT] = true;
        }
        
        return temp;
    }
}
