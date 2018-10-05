package Model.Setting;

public class PlugboardSetting {

    private String steckeredPattern;

    public PlugboardSetting(String steckeredPattern) {
        this.steckeredPattern = steckeredPattern;
    }

    public PlugboardSetting() {
        this.steckeredPattern = "";
    }

    public String getSteckeredPattern() {
        return steckeredPattern;
    }

    public boolean steckerPattern(String steckeredPattern) {

        if (validatePattern(steckeredPattern)) {
            this.steckeredPattern = steckeredPattern;
            return true;
        }

        return false;
    }

    public String[] getPairings(){
        return steckeredPattern.split(" ");
    }

    private boolean validatePattern(String steckeredPattern) {

        steckeredPattern = steckeredPattern.toUpperCase();

        String[] split = steckeredPattern.split(" ");

        for (int i = 0; i < split.length; i++) {
            if (split[i].trim().length() != 2){
                return false;
            }
        }

        this.steckeredPattern = steckeredPattern;

        return true;
    }

    public void clear() {
        steckeredPattern = "";
    }
}
