package Model.Rotors;

/**
 * Date Created Nov 12, 2017
 *
 * @author Michael C
 */
public class Rotor {

    //<editor-fold desc="Constants">
    public static final int KRIEGSMARINE = 0;
    public static final int LUFTWAFFE = 1;
    public static final int WEHRMACHT = 2;

    private static final int LEFT = 1;
    private static final int RIGHT = 0;
    //</editor-fold>

    //<editor-fold desc="Class Variables">
    private final String rotorName;
    private final String baseWiringSequence;
    private int[][] internalWiringOffsets = new int[2][26];
    private final char notchLocation;
    private char labelPosition;
    private char currentKeyPosition;
    private final boolean[] usage;
    private boolean stepNext = false;
    //</editor-fold>

//<editor-fold desc="Constructors">
    public Rotor(String rotorName, String wiringSequence, char notchLocation, int usage) {
        this(rotorName, wiringSequence, notchLocation, usage, 'C', 'A');
    }

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

        setInternalWiring(labelPosition);

        this.notchLocation = notchLocation;
        this.labelPosition = labelPosition;

        this.currentKeyPosition = keyPosition;

        //Find math for which pin position on the right to left
        //For left to right, its multiplied by -1
        //If the rotor is started on its notch key, then set it to step next time it is used
        if (this.notchLocation == this.currentKeyPosition) {
            this.stepNext = true;
        }
    }
//</editor-fold>

//    //<editor-fold desc="Helpers">
    private void setInternalWiring(char labelOffset) {
        String temp = "";

        //Complicated maths for ensuring offsets proper direction
        int start = (-1 * (labelOffset - 65) + 26) % 26;

        int j = 0;

        for (int i = 0; i < baseWiringSequence.length(); i++) {
            temp += (char) (baseWiringSequence.indexOf(i + 65) + 65);
        }

        for (int i = start; i < baseWiringSequence.length(); i++) {
            internalWiringOffsets[LEFT][j] = -1 * ((i + 65) - baseWiringSequence.charAt(i)) % 26;
            internalWiringOffsets[RIGHT][j] = -1 * ((i + 65) - temp.charAt(i)) % 26;
            j++;
        }

        for (int i = 0; i < start; i++) {
            internalWiringOffsets[LEFT][j] = -1 * ((i + 65) - baseWiringSequence.charAt(i)) % 26;
            internalWiringOffsets[RIGHT][j] = -1 * ((i + 65) - temp.charAt(i)) % 26;
            j++;
        }
    }
//</editor-fold>

//<editor-fold desc="Getters">
    public String getRotorName() {
        return this.rotorName;
    }

    public int getLeftOutput(int position) {
        int temp = (position + this.internalWiringOffsets[LEFT][position]) % 26;

        if (temp < 0) {
            temp += 26;
        }

        return temp;
    }

    public int getRightOutput(int position) {
        int temp = (position + this.internalWiringOffsets[RIGHT][position]) % 26;

        if (temp < 0) {
            temp += 26;
        }

        return temp;
    }

    public char getRightCharOutput(int position) {
        return ((char) (65 + getRightOutput(position)));
    }

    public char getRightCharOutput(char input) {
        return getRightCharOutput(input - 65);
    }

    public char getLeftCharOutput(int position) {
        return ((char) (65 + getLeftOutput(position)));
    }

    public char getLeftCharOutput(char input) {
        return getLeftCharOutput(input - 65);
    }

    public char getKeyPosition() {
        return this.currentKeyPosition;
    }

    public boolean willStepNextUse() {
        return this.stepNext;
    }

    public char getLabelPosition() {
        return this.labelPosition;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setKeyPosition(char position) {
        //TODO - Sanitize input
        while (currentKeyPosition != position) {
            stepRotor();
        }
    }

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

        //Wrap key around uppercase letter range
        if (currentKeyPosition > 90) {
            currentKeyPosition = 65;
        }

        int tempLeft = internalWiringOffsets[LEFT][0];
        int tempRight = internalWiringOffsets[RIGHT][0];

        for (int i = 0; i < internalWiringOffsets[LEFT].length - 1; i++) {
            internalWiringOffsets[LEFT][i] = internalWiringOffsets[LEFT][i + 1];
            internalWiringOffsets[RIGHT][i] = internalWiringOffsets[RIGHT][i + 1];
        }

        internalWiringOffsets[LEFT][internalWiringOffsets[LEFT].length - 1] = tempLeft;
        internalWiringOffsets[RIGHT][internalWiringOffsets[RIGHT].length - 1] = tempRight;

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

    //<editor-fold desc="Print methods">
    @Override
    public String toString() {

        return "Rotor{" + "rotorName=" + rotorName + ", baseWiringSequence=" + baseWiringSequence + ", notchLocation= "
               + notchLocation + ", currentKeyPosition=" + currentKeyPosition + ", currentLabelPosition" + labelPosition
               + ", Kriegsmarine =" + usage[0] + ", Luftwaffe = " + usage[1] + ", Wehrmacht = " + usage[2] + "}";
    }

    public void printRotor() {
        System.out.println("Rotor = " + this.toString());
    }

    public void printWiring() {
        System.out.println("With A on top, the left:right wiring is:");
        for (int i = 0; i < 26; i++) {
            System.out.println(((char) (i + 65)) + " :: " + internalWiringOffsets[LEFT][i] + ":" + internalWiringOffsets[RIGHT][i]);
        }
    }
//</editor-fold>

}
