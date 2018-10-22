package Model.Rotors;

import Model.Storages.IEnigmaComponent;
import View.Observer.IObservable;

public interface IRotor extends IEnigmaComponent, IObservable {

    public String getName();

    public int getLeftOutput(int rightPinInput);

    public int getRightOutput(int leftPinInput);

    public char getKeyPosition();

    public char getLabelPosition();

    public boolean willStepNextUse();

    public void stepRotor();

    public void stepRotorForward();

    public void stepRotorBackward();
}
