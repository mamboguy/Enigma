package Model.Plugboard;

import Model.Enigma.Storages.IEnigmaComponent;
import Model.Setting.PlugboardSetting;

public interface IPlugboard extends IEnigmaComponent {

    int convertInput(char charInput);

    char convertOutput(int pinInput);

    void keyComponent(PlugboardSetting plugboardSetting);
}
