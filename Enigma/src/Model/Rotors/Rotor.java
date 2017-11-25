package Model.Rotors;

/**
 * Date Created Nov 12, 2017
 *
 * @author Michael C
 */
public class Rotor {

//TODO - Custom rotor creation
//		- More than 26 pins (a-z,A-Z,0-9,!@#$%^&*(){}?+][=/\|-_',." <>:; etc)
//		- Save custom rotors to file
//		- Import custom rotors
//TODO - Generate key sheet
    //<editor-fold desc="Constants">
    public static final int KRIEGSMARINE = 0;
    public static final int LUFTWAFFE = 1;
    public static final int WEHRMACHT = 2;

    //Used for internalWiringOffsets to determine where the internal wiring leads based off current pin input
    //LEFT is used for right side inputs and RIGHT is used for left side inputs
    private static final int LEFT = 1;
    private static final int RIGHT = 0;
    //</editor-fold>

    //<editor-fold desc="Class Variables">
    //Name of the rotor (used in selecting rotor)
    private final String rotorName;

    //Base scrambling sequence of the rotor
    //Ex: EKMFLGDQVZNTOWYHXUSPAIBRCJ is mapped as in A->E, B->K, C->M...
    private final String baseWiringSequence;

    //Math holding array for what the current internal wiring will adjust an input by
    private int[][] internalWiringOffsets = new int[2][26];

    //The location of the first notch
    private final char notchLocation;

    //The current label starting position (defaults to 'A')
    private char labelPosition;

    //The current key showing at the noon position on the notch
    //On a physical machine, it would be what is visible on the window for the current key setting
    private char currentKeyPosition;

    //Holds whether the rotor was used by the Kriegsmarine, Luftwaffe and Wehrmacht
    private final boolean[] usage;

    //True if rotor will step next rotor this step
    //False if rotor will not affect next rotor
    private boolean stepNext = false;
    //</editor-fold>

//<editor-fold desc="Constructors">
    //Basic rotor constructor.  Calls bigger constructor to implement inputs
    public Rotor(String rotorName, String wiringSequence, char notchLocation, int usage) {
        this(rotorName, wiringSequence, notchLocation, usage, 'A', 'A');
    }

    //Detailed rotor constructor
    public Rotor(String rotorName, String wiringSequence, char notchLocation, int usage, char labelPosition, char keyPosition) {

        //Initialize usage to all false
        this.usage = new boolean[3];
        for (int i = 0; i < this.usage.length; i++) {
            this.usage[i] = false;
        }

        //Set usage flags
        if (usage >= 100) {
            this.usage[KRIEGSMARINE] = true;
            usage -= 100;
        }

        if (usage >= 10) {
            this.usage[LUFTWAFFE] = true;
            usage -= 10;
        }

        if (usage == 1) {
            this.usage[WEHRMACHT] = true;
        }

        //Assign basic attributes of rotor
        this.rotorName = rotorName;
        this.baseWiringSequence = wiringSequence;

        //Calls the internal wiring method.  Will set the starting sequence of internal wiring based off the label setting and baseWiringSequence
        setInternalWiring(labelPosition);

        //Set the notch location
        //TODO - add second notch location
        this.notchLocation = notchLocation;

        //Set the current label position
        this.labelPosition = labelPosition;

        //Set the current key position
        this.currentKeyPosition = keyPosition;

        //If the rotor is started on its notch key, then set it to step next time it is used
        //TODO - Adjust for second notch
        if (this.notchLocation == this.currentKeyPosition) {
            this.stepNext = true;
        }
    }
//</editor-fold>

    //<editor-fold desc="Helpers">
    /**
     * Performs the math necessary to determine the internal wiring scrambling
     * with regards to the currently selected label
     *
     * char labelOffset - the character that will be matched to the permanent
     * 'A' slot on the physical rotor
     */
    private void setInternalWiring(char labelOffset) {
        String temp = "";

        //start is equivalent to the label offset (so if A=0, Z=1, Y=2, etc since label rotation is
        //performed counterclockwise to create proper functionality)
        //Complicated maths for ensuring that it offsets in the proper direction
        int start = (-1 * (labelOffset - 65) + 26) % 26;

        //array counter
        int j = 0;

        //Takes the base wiring sequence (which is with respect to the right side of the rotor) and creates the base wiring sequence with respect to the left side of the rotor
        for (int i = 0; i < baseWiringSequence.length(); i++) {
            temp += (char) (baseWiringSequence.indexOf(i + 65) + 65);
        }

        //Starts counting at the label offset, but assigning the value to the equivalent of the 'A' slot (j=0)
        for (int i = start; i < baseWiringSequence.length(); i++) {
            internalWiringOffsets[LEFT][j] = -1 * ((i + 65) - baseWiringSequence.charAt(i)) % 26;
            internalWiringOffsets[RIGHT][j] = -1 * ((i + 65) - temp.charAt(i)) % 26;
            j++;
        }

        //Finishes the loop through of the wiring sequences by starting at 0 (if label offset = 'A', then the above loop is not run
        for (int i = 0; i < start; i++) {
            internalWiringOffsets[LEFT][j] = -1 * ((i + 65) - baseWiringSequence.charAt(i)) % 26;
            internalWiringOffsets[RIGHT][j] = -1 * ((i + 65) - temp.charAt(i)) % 26;
            j++;
        }
    }
//</editor-fold>

//<editor-fold desc="Getters">
    /**
     * return - The rotor's name
     */
    public String getRotorName() {
        return this.rotorName;
    }

    /**
     * Gives the left pin output for a given right pin input
     *
     * int rightPinInput- the right pin input return - the left pin output
     */
    public int getLeftOutput(int rightPinInput) {

        //left pin output based off the current pin position plus the math lookup table to see how it is affected as it passes through the rotor
        int temp = (rightPinInput + this.internalWiringOffsets[LEFT][rightPinInput]) % 26;

        //if left pin output is negative, wrap around
        if (temp < 0) {
            temp += 26;
        }

        return temp;
    }

    /**
     * Gives the right pin output for a given left pin input
     *
     * int leftPinInput - the left pin input return - the right pin output
     */
    public int getRightOutput(int leftPinInput) {

        //right pin output based off the current pin position plus the math lookup table to see how it is affected as it passes through the rotor
        int temp = (leftPinInput + this.internalWiringOffsets[RIGHT][leftPinInput]) % 26;

        //If the right pin output is negative, wrap around
        if (temp < 0) {
            temp += 26;
        }

        return temp;
    }

//TODO - change variable names
    /**
     *
     * return - character at the output position on the right side of the rotor
     * given a left pin input
     */
    public char getRightCharOutput(int leftPinInput) {
        return ((char) (65 + getRightOutput(leftPinInput)));
    }

    /**
     *
     * return - character at the output position on the right side of the rotor
     * given a left pin input
     */
    public char getLeftCharOutput(int rightPinInput) {
        return ((char) (65 + getLeftOutput(rightPinInput)));
    }
    
//Returns the current key position
    public char getKeyPosition() {
        return this.currentKeyPosition;
    }

//Check to see if rotor is set to step nearby rotor on next use
    public boolean willStepNextUse() {
        return this.stepNext;
    }

//Returns the current label position
    public char getLabelPosition() {
        return this.labelPosition;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setKeyPosition(char position) {
        //TODO - Sanitize input
        while (currentKeyPosition != position) {

            //Will ignore any stepping of nearby rotors while adjusting key
            stepRotor();
        }
    }

//Set the label ring and then adjust the internal wiring properly
    public void setLabelPosition(char position) {
        this.labelPosition = position;
        setInternalWiring(position);
    }
//</editor-fold>

    /**
     *
     * @return true if rotor just stepped over notch, false if regular step
     */
    public boolean stepRotor() {

        //Add one to the current key position (see ASCII table for ref)
        this.currentKeyPosition += 1;

        //Wrap key around uppercase letter range (65-90)
        if (currentKeyPosition > 90) {
            currentKeyPosition = 65;
        }

        //Save the wiring configs in the 'A' slot temporarily
        int tempLeft = internalWiringOffsets[LEFT][0];
        int tempRight = internalWiringOffsets[RIGHT][0];

        //Cycle all the internal wiring offsets to the left on the array
        for (int i = 0; i < internalWiringOffsets[LEFT].length - 1; i++) {
            internalWiringOffsets[LEFT][i] = internalWiringOffsets[LEFT][i + 1];
            internalWiringOffsets[RIGHT][i] = internalWiringOffsets[RIGHT][i + 1];
        }

        //Set the last to the original first value, effectively wrapping the values
        internalWiringOffsets[LEFT][internalWiringOffsets[LEFT].length - 1] = tempLeft;
        internalWiringOffsets[RIGHT][internalWiringOffsets[RIGHT].length - 1] = tempRight;

        //If the rotor is set to step a nearby rotor
        if (this.stepNext) {

            //Set the step flag to false and return a true value to tell the rotor's master it should step
            stepNext = false;
            return true;

            //Otherwise, if the notch location equals the current key position
        } else if (this.notchLocation == currentKeyPosition) {

            //Set the step flag to be true, but return false so that it steps next pass
            this.stepNext = true;
        }

        //Otherwise, indicate no stepping needed
        return false;
    }

//<editor-fold desc="Print methods">
    @Override
    public String toString() {

        return "Rotor{" + "rotorName=" + rotorName + ", baseWiringSequence=" + baseWiringSequence + ", notchLocation= "
                + notchLocation + ", currentKeyPosition=" + currentKeyPosition + ", currentLabelPosition" + labelPosition
                + ", Kriegsmarine =" + usage[0] + ", Luftwaffe = " + usage[1] + ", Wehrmacht = " + usage[2] + "}";
    }

    //Simple way to print the rotor with a single call rather than creating a new System.out.println every call
    public void printRotor() {
        System.out.println("Rotor = " + this.toString());
    }

    //Prints the wiring configuration
    public void printWiring() {
        System.out.println("With A on top, the left:right wiring is:");
        for (int i = 0; i < 26; i++) {
            System.out.println(((char) (i + 65)) + " :: " + internalWiringOffsets[LEFT][i] + ":" + internalWiringOffsets[RIGHT][i]);
        }
    }
//</editor-fold>

}
