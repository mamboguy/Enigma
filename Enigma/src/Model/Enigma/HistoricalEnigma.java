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

import Model.Enigma.Storages.ComponentStorage;
import Model.Plugboard.HistoricalPlugboard;
import Model.Plugboard.IPlugboard;
import Model.Reflectors.ReflectorFileReader;
import Model.Rotors.RotorFileReader;
import Model.Setting.EnigmaSetting;
import Model.Setting.RotorAssemblySetting;

public class HistoricalEnigma {

    //<editor-fold desc="Constants">
    private static final int MESSAGE_SPACING = 4;
    //</editor-fold>

    //<editor-fold desc="Private Variables">
    //TODO - 04 Oct 2018 - Combine into one storage cache?
    private ComponentStorage reflectorsAvailable;
    private ComponentStorage rotorsAvailable;
    private RotorAssembly rotorAssembly;
    private IPlugboard plugboard;
    private Message message;
    private EnigmaSetting setting;
    //</editor-fold>

    public HistoricalEnigma() {
        //TODO - Fix path
        rotorAssembly = new RotorAssembly(3);

        // TODO: 9/30/2018 FIX ME
        plugboard = new HistoricalPlugboard();
    }

    /**
     * Used to populate the GUI's combo boxes with all available options for
     * rotors
     *
     * @return - The list of rotor names
     */
    public String[] getRotorNames() {
        return rotorsAvailable.getAllNames();
    }

    /**
     * Used to populate the GUI's combo box with all available options for
     * reflectors
     *
     * @return - The list of reflector names
     */
    public String[] getReflectorsAvailable() {
        return reflectorsAvailable.getAllNames();
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
        rotorAssembly.stepAssembly();

        //Process the character through the machine
        input = rotorAssembly.processCharacter(input);

        //Run the final rotor output through the plugboard
        charInput = plugboard.convertOutput(input);

        return (charInput);
    }

    //<editor-fold desc="Adjust settings methods">
    public void changeSettings(EnigmaSetting newSetting) {
        setting = newSetting;

        rotorAssembly.keyComponent(newSetting);
        plugboard.keyComponent(newSetting.getPlugboardSetting());
    }

    public String[] getSettings() {
        //TODO - Implement method getSettings
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //</editor-fold>
}
