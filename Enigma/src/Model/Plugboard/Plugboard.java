/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Plugboard;

/**
 * Date Created Nov 19, 2017
 *
 * @author Michael C
 */
public class Plugboard {

    private char[] pairings = new char[26];
    private String pairList;

    public Plugboard() {
        resetPlugboardHelper();
    }

    public void resetPlugboard() {
        resetPlugboardHelper();
    }

    private void resetPlugboardHelper() {
        for (int i = 0; i < pairings.length; i++) {
            pairings[i] = (char) (i + 65);
        }

        pairList = "";
    }

    public char getPairedLetter(char letter) {

        if (letter < 65 || letter > 90) {
            throw new UnsupportedOperationException("Letter out of bounds");
        }

        return pairings[letter - 65];
    }

    public void steckerPattern(String pattern) {

        pairList = pattern.trim();
        
        System.out.println("pairList = " + pairList);

        String[] pairs = pairList.split(" ");

        resetPlugboardHelper();

        for (int i = 0; i < pairs.length; i++) {
            steckerPair(pairs[i]);
        }
    }

    private void steckerPair(String pair) {
        pair = sanitize(pair);
        
        System.out.println("Pair = " + pair);

        char first = pair.charAt(0);
        char second = pair.charAt(1);

        pairings[first - 65] = second;
        pairings[second - 65] = first;
    }

    private String sanitize(String pair) {
        pair = pair.trim();
        pair = pair.toUpperCase();
        pair = pair.replaceAll(" ", "");

        return pair;
    }

    @Override
    public String toString() {
        return "Plugboard{" + "pairList=" + pairList + '}';
    }
}
