/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date Created Nov 12, 2017
 *
 * @author Michael C
 */
public class Rotor {

    //Logger creation
    private static final Logger LOGGER = Logger.getLogger(Rotor.class.getName());

    private final String rotorName;
    private final String leftWiringSequence;
    private final char notchLocation;
    private char currentKeyPosition;
    private final String rightWiringSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private boolean stepNext = false;

//<editor-fold desc="Constructors">
    public Rotor(String rotorName, String wiringSequence, char notchLocation) {
        this(rotorName, wiringSequence, notchLocation, 'A');
    }

    public Rotor(String rotorName, String wiringSequence, char notchLocation, char keyPosition) {

        try {
            FileHandler fh = new FileHandler("logs/rotorLog.txt");
            LOGGER.addHandler(fh);
            LOGGER.setLevel(Level.SEVERE);
        } catch (Exception e) {
            System.out.println("LOG FILE ERROR");
            //Ignore error in log file
        }

        this.rotorName = rotorName;
        this.leftWiringSequence = wiringSequence;
        this.notchLocation = notchLocation;
        this.currentKeyPosition = keyPosition;

        //If the rotor is started on its notch key, then set it to step next time it is used
        if (this.notchLocation == this.currentKeyPosition) {
            this.stepNext = true;
        }

        LOGGER.log(Level.FINER, "Rotor Name: ", this.rotorName);
        LOGGER.log(Level.FINER, "Right Wiring Sequence: ", this.rightWiringSequence);
        LOGGER.log(Level.FINER, "Left Wiring Sequence: ", this.leftWiringSequence);
        LOGGER.log(Level.FINER, "Notch Position: ", this.notchLocation);
        LOGGER.log(Level.FINER, "Current Key Position: ", this.currentKeyPosition);
    }
//</editor-fold>

    //<editor-fold desc="Getters">
    public String getRotorName() {
        return this.rotorName;
    }

    public char getLeftChar(int position) {

        position = getOffset(position);

        //DEBUG
        LOGGER.log(Level.FINE, "Left char = ", leftWiringSequence.charAt(position));

        //Return outcome of input at position on right
        return leftWiringSequence.charAt(position);
    }

    public char getRightChar(int position) {

        position = getOffset(position);

        //DEBUG
        LOGGER.log(Level.FINE, "Right char = ", rightWiringSequence.charAt(position));

        return rightWiringSequence.charAt(position);
    }

    private int getOffset(int position) {
        //Find offset based off current key position
        //Offset will be:  A=0, B=1, etc
        int offset = rightWiringSequence.indexOf(this.currentKeyPosition);

        //DEBUG
        LOGGER.log(Level.FINEST, "Current key position = ", rightWiringSequence.indexOf(this.currentKeyPosition));

        //DEBUG
        LOGGER.log(Level.FINEST, "Offset = ", offset);

        //Finds pin position in relation to current key offset keeping circular nature of rotor in mind
        return ((offset + position) % 26);
    }

    public char getKeyPosition() {
        return this.currentKeyPosition;
    }
    //</editor-fold>

    public void setKeyPosition(char position) {
        //TODO - Sanitize input
        this.currentKeyPosition = position;
    }

    public boolean stepRotor() {

        //Add one to the current key position (see ASCII table for ref)
        this.currentKeyPosition += 1;

        //Wrap key around uppercase letter range
        if (currentKeyPosition > 90) {
            currentKeyPosition = 65;
        }

        //DEBUG
        LOGGER.log(Level.FINEST, "Current Key Position = ", this.currentKeyPosition);
        LOGGER.log(Level.FINEST, "Current Key Position (int) = ", (int) this.currentKeyPosition);
        LOGGER.log(Level.INFO, "At notch point?", this.notchLocation == currentKeyPosition);

        if (this.stepNext) {
            stepNext = false;
            return true;
        } else if (this.notchLocation == currentKeyPosition) {
            this.stepNext = true;
            return false;
        } else {
            return false;
        }
    }

    private int rightOutput(char leftInput) {
        //TODO - Implement
        return -1;
    }

    private int leftOutput(char rightInput) {
        //TODO - Implement
        return -1;
    }
}
