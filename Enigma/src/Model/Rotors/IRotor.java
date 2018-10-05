package Model.Rotors;

import Model.Enigma.Storages.IEnigmaComponent;
import Model.Setting.RotorSetting;

public interface IRotor extends IEnigmaComponent {

    public String getName();

    public int getLeftOutput(int rightPinInput);

    public int getRightOutput(int leftPinInput);

    public char getKeyPosition();

    public char getLabelPosition();

    public boolean willStepNextUse();

    public void stepRotor();

    public void stepRotorForward();

    public void stepRotorBackward();

    public void keyToSetting(RotorSetting newSetting);
}
