package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CharacterDisplayer extends Canvas {
    private final double SCALE =1.03;
    private int characterPositionRow;
    private int characterPositionCol;
    private int numberOfRows;
    private int numberOfColumns;
    StringProperty imageFileNameCharacter = new SimpleStringProperty();

    public String getImageFileNameCharacter() {
        return imageFileNameCharacter.get();
    }

    public CharacterDisplayer(){

    }
    public void setMaze(int numOfRows, int numOfColumns){
        this.numberOfRows = numOfRows;
        this.numberOfColumns = numOfColumns;
    }

    public void setCharacterPosition(int row, int col){
        this.characterPositionRow = row;
        this.characterPositionCol = col;
        redrew();
    }

    public void redrew(){

        double cancasH = getHeight();
        double canvasW = getWidth();

        double cellH = cancasH/numberOfRows;
        double cellW = canvasW/numberOfColumns;


        try {
            Image character = new Image(new FileInputStream(getImageFileNameCharacter()));
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0,0,getWidth(),getHeight());
            gc.drawImage(character,characterPositionCol*cellW,characterPositionRow*cellH,cellW,cellH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void zoom(ScrollEvent scrollEvent){
        if(scrollEvent.isControlDown() && scrollEvent.getDeltaY() < 0)
        {
            this.setScaleY(this.getScaleY()*(1/SCALE));
            this.setScaleX(this.getScaleX()*(1/SCALE));
        }
        else if(scrollEvent.isControlDown() && scrollEvent.getDeltaY() > 0)
        {
            this.setScaleY(this.getScaleY() * SCALE);
            this.setScaleX(this.getScaleY() * SCALE);
        }
    }

//    public void clear(){
//        double cancasH = getHeight();
//        double canvasW = getWidth();
//
//        double cellH = cancasH/numberOfRows;
//        double cellW = canvasW/numberOfColumns;
//
//        GraphicsContext gc = getGraphicsContext2D();
//        gc.clearRect(0,0,getWidth(),getHeight());
//    }

    public StringProperty imageFileNameCharacterProperty() {
        return imageFileNameCharacter;
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.imageFileNameCharacter.set(imageFileNameCharacter);
    }

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public void setCharacterPositionRow(int characterPositionRow) {
        this.characterPositionRow = characterPositionRow;
    }

    public int getCharacterPositionCol() {
        return characterPositionCol;
    }

    public void setCharacterPositionCol(int characterPositionCol) {
        this.characterPositionCol = characterPositionCol;
    }

}
