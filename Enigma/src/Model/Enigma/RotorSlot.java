package Model.Enigma;

import Model.Rotors.IRotor;

public class RotorSlot {

    private IRotor rotor;
    private int stepDirection;

    //STEPPING MOTION?

    private static final int STEP_FORWARD = 1;
    private static final int STEP_BACKWARD = -1;

    public RotorSlot(){
        this(null);
    }

    public RotorSlot(IRotor rotor){
        this.rotor = rotor;
    }

    public void replaceRotor(IRotor newRotor){
        this.rotor = newRotor;
    }

    public IRotor getRotor(){
        return rotor;
    }

    public void setRotorStepForward(){
        stepDirection = STEP_FORWARD;
    }

    public void setRotorStepBackwards(){
        stepDirection = STEP_BACKWARD;
    }

    public String getSteppingBehavior(){
        return (stepDirection == STEP_BACKWARD) ? "Stepping Backwards" : "Stepping Forward";
    }

    public void stepRotor(){
        if (stepDirection == STEP_FORWARD){
            rotor.stepRotorForward();
        } else {
            rotor.stepRotorBackward();
        }
    }

    public void emptySlot(){
        rotor = null;
    }

    public boolean hasRotor(){
        return rotor != null;
    }
}
