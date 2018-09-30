package Model.Rotors;

public interface IRotor {

    public String getRotorName();

    public int getLeftOutput(int rightPinInput);

    public int getRightOutput(int leftPinInput);

    public char getKeyPosition();

    public char getLabelPosition();

    public void setKeyPosition(char position);

    public void setLabelPosition(char position);

    public boolean willStepNextUse();

    public void stepRotor();

    public void stepRotorForward();

    public void stepRotorBackward();
}
