package Model.Setting;

import java.util.Objects;

public class RotorSetting {

    private String name;
    private int slot;
    private char labelPosition;
    private char keyPosition;

    public RotorSetting(String name, char labelPosition, char keyPosition) {
        this.name = name;
        this.slot = -1;
        this.labelPosition = labelPosition;
        this.keyPosition = keyPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public char getLabelPosition() {
        return labelPosition;
    }

    public void setLabelPosition(char labelPosition) {
        this.labelPosition = labelPosition;
    }

    public char getKeyPosition() {
        return keyPosition;
    }

    public void setKeyPosition(char keyPosition) {
        this.keyPosition = keyPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RotorSetting that = (RotorSetting) o;
        return  labelPosition == that.labelPosition &&
                keyPosition == that.keyPosition &&
                Objects.equals(name, that.name);
    }
}
