package Model.Enigma.Storages;

import Model.Reflectors.IReflector;
import Model.Rotors.Name;

import java.util.HashMap;
import java.util.Map;

public class ReflectorStorage {

    private Map rotorCache;

    public ReflectorStorage(){
        rotorCache = new HashMap<Name, IReflector>();
    }

    public void addReflector(IReflector rotor){
        rotorCache.put(rotor.getReflectorName(), rotor);
    }

    public IReflector getReflector(Name rotorName){
        return (IReflector) rotorCache.get(rotorName);
    }

    public boolean hasReflector(Name rotorName){
        return rotorCache.containsKey(rotorName);
    }
}
