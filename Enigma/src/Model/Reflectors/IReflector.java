package Model.Reflectors;


import Model.Enigma.Storages.IEnigmaComponent;

public interface IReflector extends IEnigmaComponent {

    int getReflection(int input);

    String getReflectorName();
}
