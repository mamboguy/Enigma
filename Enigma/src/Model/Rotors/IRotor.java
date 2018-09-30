package Model.Rotors;

public interface IRotor {

    public int getLeftOutput(int rightPinInput);

    public int getRightOutput(int leftPinInput);

    public char getKeyPosition();

    public boolean willStepNextUse();

    public char getLabelPosition();

    public void setKeyPosition(char position);

    public void setLabelPosition(char position);

    public void stepRotor();

    public void stepRotorForward();

    public void stepRotorBackward();

    public String getRotorName();
}
