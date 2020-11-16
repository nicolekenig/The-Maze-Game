package ViewModel;

import Model.IModel;
import View.IView;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel  extends Observable implements Observer{

    private IModel model;
    private int characterRowPositionNew;
    private int characterColPositionNew;

    public StringProperty characterRowPositionToUpdate = new SimpleStringProperty("0"); //For Binding
    public StringProperty characterColPositionToUpdate = new SimpleStringProperty("0"); //For Binding

    public MyViewModel(IModel model){
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o==model){
            characterRowPositionNew = model.getCharacterPositionRow();
            characterRowPositionToUpdate.set(characterRowPositionNew + "");
            characterColPositionNew = model.getCharacterPositionColumn();
            characterColPositionToUpdate.set(characterColPositionNew + "");
            setChanged();
            notifyObservers();
        }
    }

    public void generateMaze(int rowsNumber, int columsNumber){
        model.generateMaze(rowsNumber, columsNumber);
    }

    public void moveCharacter(KeyCode movement){
        model.moveCharacter(movement);
    }

    public Maze getMaze(){
        return model.getMaze();
    }

    public Solution getSolution(){
        return model.getSolution();
    }

    public int getCharacterRowPosition() {
        return characterRowPositionNew;
    }

    public int getCharacterColPosition() {
        return characterColPositionNew;
    }

    public boolean checkIfArraveToEndingPoint(){
        return model.checkIfArraveToEndingPoint();
    }

    public void save(File file){
        model.save(file);
    }

    public void load(File file){
        model.load(file);
    }

    public void exit(){
        model.exit();
    }


}
