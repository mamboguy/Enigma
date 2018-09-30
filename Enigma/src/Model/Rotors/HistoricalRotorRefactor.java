package Model.Rotors;

public class HistoricalRotorRefactor implements IRotor{

    private CircularLinkedList<RotorSlot> rotor;

    private static final String BASE_SEQUENCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String name;
    private boolean stepNextUse;

    public HistoricalRotorRefactor(String name, String wiringSequence, String notchLocations) {
        rotor = new CircularLinkedList<RotorSlot>();

        this.name = name;

        createRotor(wiringSequence, notchLocations);
    }

    private void createRotor(String wiringSequence, String notchLocations) {

        //Create right rotor from base input wiring sequence
        createRightRotorHalf(wiringSequence, notchLocations);

        //Now set the left offset based on right offset
        createLeftRotorHalf();

        //Set rotor to Label = "A"
        setDefaults();
    }

    private void createLeftRotorHalf() {
        for (int i = 0; i < rotor.getSize(); i++) {

            System.out.println("char = " + (char)('A' + i));

            //Go to the next position to assign
            while(rotor.getData().getDefaultChar() != (char)('A' + i)){
                rotor.stepNext();
                System.out.println("Stepping: " + rotor.getData().getDefaultChar());
            }

            //Get the offset at that position
            int offset = rotor.getData().getRightToLeftOffset();

            //Step forward or backward to that offset's end point
            for (int j = 0; j < Math.abs(offset); j++) {
                if (offset < 0){
                    rotor.stepBack();
                } else {
                    rotor.stepNext();
                }
            }

            rotor.getData().setLeftToRightOffset(offset * -1);
        }
    }

    private void createRightRotorHalf(String wiringSequence, String notchLocations) {
        for (int i = 0; i < wiringSequence.length(); i++) {
            rotor.insert(new RotorSlot(BASE_SEQUENCE.charAt(i), wiringSequence.charAt(i), notchLocations.contains(wiringSequence.charAt(i) + "")));
        }
    }

    private void setDefaults() {
        while (rotor.getData().getDefaultChar() != 'A') {
            rotor.stepNext();
        }
    }

    public void printRotor() {

        System.out.println("Right Input");

        for (int i = 0; i < rotor.getSize(); i++) {
            printOutAligned(rotor.getData().getRightToLeftOffset());
            rotor.stepNext();
        }

        System.out.println();
        System.out.println("Left Input");

        for (int i = 0; i < rotor.getSize(); i++) {
            printOutAligned(rotor.getData().getLeftToRightOffset());
            rotor.stepNext();
        }

    }

    private void printOutAligned(int leftToRightOffset) {

        String temp = " <-> ";

        if (leftToRightOffset > 0){
            temp = " " + temp;
        }

        if (leftToRightOffset < 10){
            temp = " " + temp;
        }

        System.out.print(leftToRightOffset + temp);
    }

    @Override
    public int getLeftOutput(int rightPinInput) {
        return (rotor.getNodeAtPosition(rightPinInput).getData().getRightToLeftOffset() + rightPinInput) % 26;
    }

    @Override
    public int getRightOutput(int leftPinInput) {
        return (rotor.getNodeAtPosition(leftPinInput).getData().getRightToLeftOffset() + leftPinInput) % 26;
    }

    @Override
    public char getKeyPosition() {
        return rotor.getData().getDefaultChar();
    }

    @Override
    public boolean willStepNextUse() {
        return rotor.getData().isNotch();
    }

    @Override
    public char getLabelPosition() {
        return 0;
    }

    @Override
    public void setKeyPosition(char position) {

    }

    @Override
    public void setLabelPosition(char position) {

    }

    @Override
    public boolean stepRotor() {
        return stepRotorForward();
    }

    @Override
    public boolean stepRotorForward() {
        rotor.stepNext();

        return rotor.getData().isNotch();
    }

    @Override
    public boolean stepRotorBackward() {
        rotor.stepBack();

        return rotor.getData().isNotch();
    }

    @Override
    public String getRotorName() {
        return name;
    }
}
