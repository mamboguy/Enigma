package Model.Rotors.RotorSubAssemblies;

public class RotorSlot {

    private boolean isNotch;
    private int rightToLeftOffset;
    private int leftToRightOffset;
    private char currentLabel;

    //BUG - DOES NOT HANDLE ANYTHING ABOVE STANDARD ALPHABET

    public RotorSlot(char characterAtRightLocation, char characterAtLeftLocation, boolean isNotch){
        this.isNotch = isNotch;

        rightToLeftOffset = getOffset(characterAtRightLocation, characterAtLeftLocation);

        currentLabel = characterAtRightLocation;
    }

    private int getOffset(char characterAtRightLocation, char characterAtLeftLocation) {

        int offset = characterAtLeftLocation - characterAtRightLocation + 26;

        if (offset % 26 >= 13){
            return (offset % 26) - 26;
        } else {
            return (offset % 26);
        }
    }

    public void setLeftToRightOffset(int offset){
        this.leftToRightOffset = offset;
    }

    public boolean isNotch() {
        return isNotch;
    }

    public int getRightToLeftOffset() {
        return rightToLeftOffset;
    }

    public int getLeftToRightOffset() {
        return leftToRightOffset;
    }

    public char getCurrentLabel() {
        return currentLabel;
    }
}
