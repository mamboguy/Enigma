package Model.Plugboard;

class HistoricalPlugboardSlot {

    private int pinInput;
    private char permanentChar;
    private HistoricalPlugboardSlot steckeredPair;

    HistoricalPlugboardSlot(char permanentChar){

        // TODO: 9/30/2018 Fix this to handle different alphabets?

        permanentChar = Character.toUpperCase(permanentChar);

        if (permanentChar < 65 || permanentChar > 90){
            throw new IllegalArgumentException();
        }

        pinInput = permanentChar - 65;
        this.permanentChar = permanentChar;
        steckeredPair = this;
    }

    /**
     * Returns the pin conversion of the steckered pair (itself if nothing assigned)
     * @return - the character pin to begin encoding
     */
    public int getPinInput(){
        return steckeredPair.pinInput;
    }

    public char getCharOutput() {
        return steckeredPair.permanentChar;
    }

    /**
     * Pairs two plugboard slots together
     * Handles cases where either one or both had original pairings
     * @param pairing - The new slot to pair to
     */
    public void steckerPair(HistoricalPlugboardSlot pairing){

        //Resets the pairing's connection first
        pairing.resetPairing();

        //Resets its own
        resetPairing();

        //The above two should effectively remove any existing connections to either end

        //Pair the two slots together
        steckeredPair = pairing;
        pairing.steckeredPair = this;
    }

    /**
     * Resets a pair of plugboard slots to be themselves
     */
    public void resetPairing(){
        steckeredPair.steckeredPair = steckeredPair;
        this.steckeredPair = this;
    }

    /**
     * Return the character permanently associated with this slot
     */
    public char getPermanentChar(){
        return permanentChar;
    }

    /**
     * Return what pin is permanently associated with this slot
     */
    public int getPermanentPin(){
        return pinInput;
    }
}
