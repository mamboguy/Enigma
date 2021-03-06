package Model.Enigma;

import Model.Reflectors.IReflector;
import Model.Rotors.IRotor;
import java.util.ArrayList;

public class RotorAssembly {

    private ArrayList<IRotor> rotors;
    private IReflector reflector;
    private int size;

    public RotorAssembly(int size){
        rotors = new ArrayList<>();
        reflector = null;
        this.size = size;

        // TODO: 10/4/2018 FIX ME
//        components = RotorFileReader.readRotorFile();
//        ComponentFactory reflectorsAvailable = ReflectorFileReader.readReflectorFile();
//
//        String[] names = reflectorsAvailable.getAllNames();
//
//        for (int i = 0; i < names.length; i++) {
//            components.addComponent(reflectorsAvailable.getComponent(names[i]));
//        }
//
//        setting = new RotorAssemblySetting(size);
    }

    public boolean changeReflector(IReflector newReflector){

        if (newReflector == null){
            return false;
        }

        reflector = newReflector;
        return true;
    }

    public IReflector getReflector(){
        return reflector;
    }

    public void addNewSlot(){
        this.addNewSlot(null);
    }

    public void addNewSlot(IRotor newRotor){
        this.size++;
        rotors.add(newRotor);
    }

    public void removeSlot(int slot){
        if (isValidSlot(slot)) {
            rotors.remove(slot);
            size--;
        }
    }

    public IRotor getRotor(int slot) {
        if (isValidSlot(slot)) {
            return rotors.get(slot);
        }

        return null;
    }

    public void changeRotor(IRotor newRotor, int slot) {
        if (isValidSlot(slot)) {
            rotors.set(slot, newRotor);
        }
    }

    private boolean isValidSlot(int slot) {
        return (slot < size && slot >= 0);
    }

    private boolean hasRightRotor(int slot){
        return (slot - 1 >= 0);
    }

    private boolean rotorSetToStep(int slot){
        return getRotor(slot).willStepNextUse();
    }

    public void stepAssembly(){
        // TODO: 10/3/2018 fix for just reaching notch??

        for (int i = 0; i < rotors.size(); i++) {

            if (hasRightRotor(i)){
                //If not the rightmost rotor, check if the current rotor is set to step OR if the last rotor is set to step
                if (rotorSetToStep(i) || rotorSetToStep(i - 1)) {
                    getRotor(i).stepRotor();
                }

            } else {
                //The right most rotor will always step
                getRotor(i).stepRotor();
            }
        }
    }

    public int processCharacter(int input){

        for (int i = 0; i < rotors.size(); i++) {
            input = rotors.get(i).getLeftOutput(input);
        }

        input = reflector.getReflection(input);

        for (int i = rotors.size() - 1; i >= 0; i--) {
            input = rotors.get(i).getRightOutput(input);
        }

        return input;
    }
}
