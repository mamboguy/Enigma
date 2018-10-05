package Model.Setting;

public class EnigmaSetting {

    private RotorAssemblySetting assemblySetting;
    private PlugboardSetting plugboardSetting;

    public EnigmaSetting(RotorAssemblySetting assemblySetting, PlugboardSetting plugboardSetting) {
        this.assemblySetting = assemblySetting;
        this.plugboardSetting = plugboardSetting;
    }

    public RotorAssemblySetting getAssemblySetting() {
        return assemblySetting;
    }

    public void setAssemblySetting(RotorAssemblySetting assemblySetting) {
        this.assemblySetting = assemblySetting;
    }

    public PlugboardSetting getPlugboardSetting() {
        return plugboardSetting;
    }

    public void setPlugboardSetting(PlugboardSetting plugboardSetting) {
        this.plugboardSetting = plugboardSetting;
    }
}
