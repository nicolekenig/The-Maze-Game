package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.io.File;

public interface IModel {

    void generateMaze(int rowsNumber, int columsNumber);
    void moveCharacter(KeyCode movement);
    Maze getMaze();
    Solution getSolution();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    boolean checkIfArraveToEndingPoint();
    void save(File filaToSave);
    void load(File filaToLoad);
    void exit();
}
