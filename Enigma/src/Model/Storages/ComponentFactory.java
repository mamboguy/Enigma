package Model.Storages;

import Controller.SubFrames.IController;
import Controller.SubFrames.IReflectorController;
import Controller.SubFrames.IRotorController;
import Model.Reflectors.IReflector;
import Model.Reflectors.ReflectorFileReader;
import Model.Rotors.IRotor;
import Model.Rotors.RotorFileReader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ComponentFactory {

    private Map rotorCache;
    private Map reflectorCache;

    private static ComponentFactory instance;

    public static ComponentFactory getInstance(){
        if (instance == null){
            instance = new ComponentFactory();
        }

        return instance;
    }

    private ComponentFactory() {
        rotorCache = new HashMap<String, CheckedOut>();
        reflectorCache = new HashMap<String, CheckedOut>();
    }

    public void addComponent(IEnigmaComponent component) {
        if (component instanceof IReflector){
            reflectorCache.put(component.getName(),new CheckedOut(component, null));
        } else if(component instanceof IRotor){
            rotorCache.put(component.getName(), new CheckedOut(component, null));
        } else {
            //Do nothing
        }
    }

    public IRotor getRotor(String key, IRotorController owner) {
        return (IRotor) getComponent(rotorCache, key, owner);
    }

    public IReflector getReflector(String key, IReflectorController owner) {
        return (IReflector) getComponent(reflectorCache, key, owner);
    }

    private IEnigmaComponent getComponent(Map cache, String key, IController owner) {
        //Get the current owned component slot
        CheckedOut newComponentCheckedOut = (CheckedOut) (cache.get(key));

        if (owner.getModel() != null){

            //Get the component desired's slot
            CheckedOut currentComponentCheckedOut = (CheckedOut) (cache.get(owner.getModel().getName()));

            //Swap the owners of the two
            newComponentCheckedOut.swapComponentOwner(currentComponentCheckedOut);
        }

        //return the requested component
        return ((CheckedOut) cache.get(key)).component;
    }

    public boolean hasComponent(String key) {
        return (reflectorCache.containsKey(key) || rotorCache.containsKey(key));
    }

    public String[] getAllRotorNames() {
        return getNames(rotorCache);
    }

    public String[] getAllReflectorNames(){
        return getNames(reflectorCache);
    }

    private String[] getNames(Map cache){
        //Holder for the rotor names
        String[] temp = new String[cache.size()];

        //Get all the available rotor names
        Iterator allObjects = cache.values().iterator();
        int i = 0;

        while (allObjects.hasNext()) {
            temp[i] = (((IEnigmaComponent) ((CheckedOut) allObjects.next()).component).getName());
            i++;
        }

        return alphabeticallyOrder(temp);
    }

    private String[] alphabeticallyOrder(String[] temp) {

        String holder = "";

        //Bubble sort b/c lazy
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < (temp.length - i - 1); j++) {

                //Sort back to front first
                if (compareStrings(temp[j], temp[j+1])) {
                    holder = temp[j+1];
                    temp[j+1] = temp[j];
                    temp[j] = holder;
                }
            }
        }

        return temp;
    }

    boolean testCompareStrings(String firstString, String currentIndexString){
        return compareStrings(firstString, currentIndexString);
    }

    /**
     *
     * @param firstString
     * @param currentIndexString
     * @return - true if firstString is alphabetically behind currentIndexString
     */
    private boolean compareStrings(String firstString, String currentIndexString) {

        firstString = firstString.toUpperCase();
        currentIndexString = currentIndexString.toUpperCase();

        if (firstString == currentIndexString) {
            //If equal, stay same
            return false;

        } else if (firstString.length() == currentIndexString.length()) {

                //Strings same length
                    //If not equal, then whichever is higher alphabetically moves backward
                for (int i = 0; i < firstString.length(); i++) {
                if (firstString.charAt(i) != currentIndexString.charAt(i)) {
                    return (firstString.charAt(i) > currentIndexString.charAt(i));
                }
            }
        } else {
            //Strings different length
                //If equal to length of shorter string
                    //Longer string moves backward
            int length = -1;
            boolean firstIsLonger = false;

            if (firstString.length() > currentIndexString.length()) {
                length = currentIndexString.length();
                firstIsLonger = true;
            } else {
                length = firstString.length();
            }

            for (int i = 0; i < length; i++) {
                if (firstString.charAt(i) != currentIndexString.charAt(i)){
                    return (firstString.charAt(i) > currentIndexString.charAt(i));
                }
            }

            return firstIsLonger;
        }


        return false;
    }

    public IRotor getRandomRotor(IRotorController owner) {
        String[] temp = getAllRotorNames();

        int random = (int) (Math.random() * temp.length);

        return getRotor(temp[random], owner);
    }

    public IReflector getRandomReflector(IReflectorController owner){
        String[] temp = getAllReflectorNames();

        int random = (int) (Math.random() * temp.length);

        return getReflector(temp[random], owner);
    }

    private class CheckedOut {

        private IEnigmaComponent component;
        private IController owner;

        public CheckedOut(IEnigmaComponent component, IController owner) {
            this.component = component;
            this.owner = owner;
        }

        public void swapComponentOwner(CheckedOut secondCheckedOut){
            CheckedOut temp = secondCheckedOut;

            secondCheckedOut.owner = this.owner;
            this.owner = temp.owner;
        }

        public IEnigmaComponent getComponent() {
            return component;
        }
    }
}
