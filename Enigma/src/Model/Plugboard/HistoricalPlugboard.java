/*
 * //todo - fix class headers to meet standards
 */
package Model.Plugboard;

import java.util.ArrayList;

/**
 * Date Created Nov 19, 2017
 *
 * @author Michael C
 */
public class HistoricalPlugboard implements IPlugboard {

    private static final String BASE_SEQUENCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //pairings holds the current switched letters
    private ArrayList<HistoricalPlugboardSlot> plugboard = new ArrayList<>();

    public HistoricalPlugboard() {
        for (int i = 0; i < BASE_SEQUENCE.length(); i++) {
            plugboard.add(new HistoricalPlugboardSlot(BASE_SEQUENCE.charAt(i)));
        }
    }

    /**
     * Implementation of reset for plugboard.  Sets everything to map to itself as if no pairing existed
     */
    @Override
    public void resetPlugboard() {
        for (int i = 0; i < plugboard.size(); i++) {
            plugboard.get(i).resetPairing();
        }
    }

    @Override
    public int convertInput(char charInput) {
        return getSlot(charInput).getPinInput();
    }

    private HistoricalPlugboardSlot getSlot(char charInput) {

        charInput = Character.toUpperCase(charInput);

        for (int i = 0; i < plugboard.size(); i++) {
            if (plugboard.get(i).getPermanentChar() == charInput) {
                return plugboard.get(i);
            }
        }

        return plugboard.get(0);
    }

    private HistoricalPlugboardSlot getInput(int pinInput) {


        for (int i = 0; i < plugboard.size(); i++) {
            if (plugboard.get(i).getPermanentPin() == pinInput) {
                return plugboard.get(i);
            }
        }

        return plugboard.get(0);
    }

    @Override
    public char convertOutput(int pinInput) {
        return getInput(pinInput).getCharOutput();
    }

    /**
     * Stecker the plugboard based off the given string
     *
     * @param pattern - the steckering pair string
     */
    public void steckerPattern(String pattern) {
        //Resets plugboard to blank state
        resetPlugboard();

        pattern = pattern.trim();

        //Ignore on empty pattern
        if (pattern.length() > 0) {

            //Split the list on spaces
            String[] pairs = pattern.split(" ");

            //Steckers individual pairs
            for (int i = 0; i < pairs.length; i++) {
                steckerPair(pairs[i]);
            }
        }
    }

    /**
     * Takes a pair of letters and steckers them together on the plugboard
     *
     * @param pair - Input pair of letters.  Ex: "AE", "BC", "NR"
     */
    private void steckerPair(String pair) {
        pair = sanitize(pair);

        HistoricalPlugboardSlot pair1 = getSlot(pair.charAt(0));
        HistoricalPlugboardSlot pair2 = getSlot(pair.charAt(1));

        pair1.steckerPair(pair2);
    }

    /**
     * Sanitizes string input before being passed on
     *
     * @param pair - the string to sanitize
     * @return - sanitized string
     */
    private String sanitize(String pair) {

        //Set all to uppercase
        pair = pair.toUpperCase();

        //Replace all whitespace
        pair = pair.replaceAll(" ", "");

        return pair;
    }
}
