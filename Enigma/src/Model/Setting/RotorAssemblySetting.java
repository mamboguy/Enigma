package Model.Setting;

import Model.Reflectors.IReflector;

import java.util.ArrayList;

public class RotorAssemblySetting {

    // TODO: 10/4/2018 Different data structure that sorts on insert?
    private ArrayList<RotorSetting> rotorSettings;
    private ReflectorSetting reflectorUsed;

    private static final String ROTOR_DEFAULT_ORDERING = "I II III IV V VI VII VII";
    private static final String DEFAULT_REFLECTOR = "UKW-B";

    public RotorAssemblySetting(int size) {

        rotorSettings = new ArrayList<>();

        String[] temp = ROTOR_DEFAULT_ORDERING.split(" ");

        for (int i = 0; i < size; i++) {
            rotorSettings.add(new RotorSetting(temp[i], 'A', 'A'));
        }

        reflectorUsed = new ReflectorSetting(DEFAULT_REFLECTOR);
    }

    public RotorAssemblySetting(ArrayList<RotorSetting> rotorSettings, ReflectorSetting reflectorUsed) {
        this.rotorSettings = rotorSettings;
        this.reflectorUsed = reflectorUsed;
    }

    public void addRotorSetting(RotorSetting newSetting) {
        rotorSettings.add(newSetting);
    }

    public ReflectorSetting getReflectorUsed() {
        return reflectorUsed;
    }

    public void setReflectorUsed(ReflectorSetting reflectorUsed) {
        this.reflectorUsed = reflectorUsed;
    }

    public void setRotorSetting(int slot, RotorSetting newSetting) {
        rotorSettings.set(slot, newSetting);
    }

    public int getRotorCount() {
        return rotorSettings.size();
    }

    public String getRotorNameAtSlot(int slot) {
        return rotorSettings.get(slot).getName();
    }
}
