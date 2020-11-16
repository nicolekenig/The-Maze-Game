package View;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SolutionDisplayer extends Canvas {

    private final double SCALE =1.03;

    private Solution mazeSolution;
    private int numberOfRows;
    private int numberOfColumns;
    StringProperty imageFileNameSolution = new SimpleStringProperty();

    public String getImageFileNameSolotion() {
        return imageFileNameSolution.get();
    }

    public void setMaze(int rows, int cols) {
        this.numberOfRows = rows;
        this.numberOfColumns = cols;
    }

    public void setSolution(Solution mazeSolution) {
        this.mazeSolution = mazeSolution;
        redrew();
    }

    public void redrew() {

        double cancasH = getHeight();
        double canvasW = getWidth();

        double cellH = cancasH / numberOfRows;
        double cellW = canvasW / numberOfColumns;


        double x;
        double y;
//
        try {
            Image solotionImage = new Image(new FileInputStream(getImageFileNameSolotion()));
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());
            gc.setFill(Color.AQUA);
            ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
            for (int i = 0; i < mazeSolutionSteps.size(); ++i) {
                x = (mazeSolutionSteps.get(i)).getPosition().getColumnIndex() * cellW;
                y = mazeSolutionSteps.get(i).getPosition().getRowIndex() * cellH;
                gc.drawImage(solotionImage, x, y, cellW, cellH);
//                gc.fillRect(x, y, cellW, cellH);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void zoom(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown() && scrollEvent.getDeltaY() < 0) {
            this.setScaleY(this.getScaleY() * (1 / SCALE));
            this.setScaleX(this.getScaleX() * (1 / SCALE));
        }
        else if (scrollEvent.isControlDown() && scrollEvent.getDeltaY() > 0) {
            this.setScaleY(this.getScaleY() * SCALE);
            this.setScaleX(this.getScaleY() * SCALE);
        }
    }
    public void clear(){
        double cancasH = getHeight();
        double canvasW = getWidth();

        double cellH = cancasH / numberOfRows;
        double cellW = canvasW / numberOfColumns;

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

    }



    public void setImageFileNameSolotion(String imageFileNameSolotion) {
        this.imageFileNameSolution.set(imageFileNameSolotion);
    }

    public StringProperty imageFileNameSolotionProperty() {
        return imageFileNameSolution;
    }


}
