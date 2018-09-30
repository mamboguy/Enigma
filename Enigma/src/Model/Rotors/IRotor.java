package Model.Rotors;

public interface IRotor {

    public int getLeftOutput(int rightPinInput);

    public int getRightOutput(int leftPinInput);

    public char getKeyPosition();

    public boolean willStepNextUse();

    public char getLabelPosition();

    public void setKeyPosition(char position);

    public void setLabelPosition(char position);

    public boolean stepRotor();

    public boolean stepRotorForward();

    public boolean stepRotorBackward();

    public String getRotorName();
}
