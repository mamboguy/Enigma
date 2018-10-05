package Model.Enigma.Storages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ComponentStorage {

    private Map storageCache;

    public ComponentStorage(){
        storageCache = new HashMap<String, IEnigmaComponent>();
    }

    public void addComponent(IEnigmaComponent component){
        storageCache.put(component.getName(), component);
    }

    public IEnigmaComponent getComponent(String key){
        return (IEnigmaComponent) storageCache.get(key);
    }

    public boolean hasComponent(String key){
        return storageCache.containsKey(key);
    }

    public String[] getAllNames(){
        //Holder for the rotor names
        String[] temp = new String[storageCache.size()];

        //Get all the available rotor names
        Iterator allObjects = storageCache.values().iterator();
        int i = 0;

        while (allObjects.hasNext()){
            temp[i] = ((IEnigmaComponent) allObjects.next()).getName();
            i++;
        }

        return temp;
    }
}
