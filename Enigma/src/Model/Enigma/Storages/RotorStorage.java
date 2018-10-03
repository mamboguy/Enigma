package Model.Enigma.Storages;

import Model.Rotors.IRotor;
import Model.Rotors.Name;

import java.util.HashMap;
import java.util.Map;

public class RotorStorage {

    private Map rotorCache;

    public RotorStorage(){
        rotorCache = new HashMap<Name, IRotor>();
    }

    public void addRotor(IRotor rotor){
        rotorCache.put(rotor.getRotorName(), rotor);
    }

    public IRotor getRotor(Name rotorName){
        return (IRotor) rotorCache.get(rotorName);
    }

    public boolean hasRotor(Name rotorName){
        return rotorCache.containsKey(rotorName);
    }
}
