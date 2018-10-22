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

public class HistoricalEnigma {

    //<editor-fold desc="Constants">
    private static final int MESSAGE_SPACING = 4;
    //</editor-fold>

    //<editor-fold desc="Private Variables">
    //TODO - 04 Oct 2018 - Combine into one storage cache?
    private RotorAssembly rotorAssembly;
    private IPlugboard plugboard;
    private Message message;
    //</editor-fold>

    public HistoricalEnigma() {
        //TODO - Fix path
        rotorAssembly = new RotorAssembly(3);

        // TODO: 9/30/2018 FIX ME
        plugboard = new HistoricalPlugboard();
    }

    /**
     * Encodes messages into ciphertext
     *
     * @param message - plaintext message to encode
     *
     * @return - ciphertext of plaintext message
     */
    public Message processMessage(String message) {

        String cipher = "";

        for (int i = 0; i < message.length(); i++) {
            cipher += processCharacter(message.charAt(i));
        }

        printToConsole();

        //Return the ciphertext
        return new Message(message, cipher.trim());
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
}
