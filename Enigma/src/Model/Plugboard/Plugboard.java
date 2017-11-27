/*
 * //todo - fix class headers to meet standards
 */
package Model.Plugboard;

import Controller.BasicGUIController;
import Controller.BasicGUIMenuController;
import View.Basic.BasicInputScreen;

/**
 * Date Created Nov 19, 2017
 *
 * @author Michael C
 */
public class Plugboard {

    //pairings holds the current switched letters
    private char[] pairings = new char[BasicGUIController.VALID_CHARS];
    
    //holds the last passed pairlist
    private String pairList;

    public Plugboard() {
        //Defaults to a blank plugboard
        resetPlugboardHelper();
    }

    /**
     * Public call to reset the plugboard
     */
    public void resetPlugboard() {
        resetPlugboardHelper();
    }

    /**
     * Implementation of reset for plugboard.  Sets everything to map to itself as if no pairing existed
     */
    private void resetPlugboardHelper() {
        
        for (int i = 0; i < pairings.length; i++) {
            //Set every pairing to be its capitalized self
            pairings[i] = (char) (i + 65);
        }

        //Set the pairlist to be blank
        pairList = "";
    }

    /**
     * Gives the steckered letter for a given input
     * @param letter - the original letter
     * @return - its steckered pair
     */
    public char getPairedLetter(char letter) {

        //Throw exception if letter is out of bounds
        //todo - rework when implementing case sensitive/expand character pool
        if (letter < 65 || letter > 90) {
            throw new UnsupportedOperationException("Letter out of bounds");
        }
       
        return pairings[letter - 65];
    }

    /**
     * Stecker the plugboard based off the given string
     * @param pattern - the steckering pair string
     */
    public void steckerPattern(String pattern) {
        //Resets plugboard to blank state
        resetPlugboardHelper();

        //Ignore on empty pattern
        if (pattern.length() > 0) {

            //Trim any beginning or end off the list
            pairList = pattern.trim();

            //Split the list on spaces
            String[] pairs = pairList.split(" ");

            //Steckers individual pairs
            for (int i = 0; i < pairs.length; i++) {
                steckerPair(pairs[i]);
            }
        }
    }

    /**
     * Takes a pair of letters and steckers them together on the plugboard
     * @param pair - Input pair of letters.  Ex: "AE", "BC", "NR"
     */
    private void steckerPair(String pair) {
        //Sanitize the pair of letters
        pair = sanitize(pair);

        //Get the characters in the pair
        char first = pair.charAt(0);
        char second = pair.charAt(1);

        //Set the pairing of each to the other letter
        pairings[first - 65] = second;
        pairings[second - 65] = first;
    }

    /**
     * Sanitizes string input before being passed on
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

    @Override
    public String toString() {
        return "Plugboard{" + "pairList=" + pairList + '}';
    }
}
