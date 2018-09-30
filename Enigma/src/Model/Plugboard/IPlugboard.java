package Model.Plugboard;

public interface IPlugboard {

    void resetPlugboard();

    int convertInput(char charInput);

    char convertOutput(int pinInput);

    void steckerPattern(String pattern);
}
