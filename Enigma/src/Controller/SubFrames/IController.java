package Controller.SubFrames;

import Model.Storages.IEnigmaComponent;

import javax.swing.*;

public interface IController {

    public IEnigmaComponent getModel();

    public JPanel getView();
}
