package View;

import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import algorithms.mazeGenerators.Maze;
public class MazeDisplayer extends Canvas {

    private final double SCALE =1.03;
    private Maze M;
    private int [][] maze;
    private double cancasH;
    private double canvasW;
    StringProperty imageFileNameTWall = new SimpleStringProperty();
    StringProperty imageFileNameBWall = new SimpleStringProperty();
    StringProperty imageFileNameRWall = new SimpleStringProperty();
    StringProperty imageFileNameLWall = new SimpleStringProperty();
    StringProperty imageFileNameBRWall = new SimpleStringProperty();
    StringProperty imageFileNameBLWall = new SimpleStringProperty();
    StringProperty imageFileNameTRWall = new SimpleStringProperty();
    StringProperty imageFileNameTLWall = new SimpleStringProperty();
    StringProperty imageFileNameRLWall = new SimpleStringProperty();
    StringProperty imageFileNameTBWall = new SimpleStringProperty();
    StringProperty imageFileNameBRLWall = new SimpleStringProperty();
    StringProperty imageFileNameTRLWall = new SimpleStringProperty();
    StringProperty imageFileNameTBRWall = new SimpleStringProperty();
    StringProperty imageFileNameTBLWall = new SimpleStringProperty();



    StringProperty imageFileNameGoalPoint = new SimpleStringProperty();

    public void setMaze(Maze M){
        this.M = M;
        this.maze = M.getMaze();
        redraw();
    }

    public void redraw(){
        if(maze != null){
//            clear();
            cancasH = getHeight();
            canvasW = getWidth();

            int numberOfRows = this.maze.length;
            int numberOfColumns = this.maze[0].length;

            double cellH = cancasH/numberOfRows;
            double cellW = canvasW/numberOfColumns;


            try {
                Image topWall = new Image(new FileInputStream(getImageFileNameTWall()));
                Image bottumWall = new Image(new FileInputStream(getImageFileNameBWall()));
                Image rigthWall = new Image(new FileInputStream(getImageFileNameRWall()));
                Image leftWall = new Image(new FileInputStream(getImageFileNameLWall()));

                Image BRWall = new Image(new FileInputStream(getImageFileNameBRWall()));
                Image BLWall = new Image(new FileInputStream(getImageFileNameBLWall()));
                Image TRWall = new Image(new FileInputStream(getImageFileNameTRWall()));
                Image TLWall = new Image(new FileInputStream(getImageFileNameTLWall()));
                Image RLWall = new Image(new FileInputStream(getImageFileNameRLWall()));
                Image TBWall = new Image(new FileInputStream(getImageFileNameTBWall()));

                Image BRLWall = new Image(new FileInputStream(getImageFileNameBRLWall()));
                Image TRLWall = new Image(new FileInputStream(getImageFileNameTRLWall()));
                Image TBRWall = new Image(new FileInputStream(getImageFileNameTBRWall()));
                Image TBLWall = new Image(new FileInputStream(getImageFileNameTBLWall()));

                Image goalPoint = new Image(new FileInputStream(getImageFileNameGoalPoint()));

                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0,0,cancasH,canvasW);
                gc.setFill(Color.BLACK);
                for(int i=0; i<numberOfRows; i++)
                {
                    for( int j=0; j< numberOfColumns; j++)
                    {
                        gc.fillRect(j * cellW, i * cellH, cellW, cellH);
                    }
                }

                boolean top , rigth, left, bottum;
                for(int i=0; i<numberOfRows; i++)
                {
                    for( int j=0; j< numberOfColumns; j++)
                    {
                        if(maze[i][j] == 1){
                            if( i == 0 )
                                top = true;
                            else if(i > 0)
                                top = (maze[i-1][j] == 0);
                            else
                                top = false;

                            if( j ==maze[i].length-1 )
                                rigth = true;
                            else if(j<maze[i].length-1)
                                rigth = (maze[i][j+1] == 0);
                            else
                                rigth = false;

                            if(i == maze.length-1)
                                bottum = true;
                            else if(i < maze.length-1)
                                bottum = (maze[i+1][j] == 0);
                            else
                                bottum = false;
                            if(j == 0)
                                left = true;
                            else if(j > 0)
                                left = (maze[i][j-1] == 0);
                            else
                                left = false;

                            if(top && !bottum && !rigth && !left)
                                gc.drawImage(topWall, j * cellW, i * cellH, cellW, cellH);
                            else if(bottum && !top && !rigth && !left)
                                gc.drawImage(bottumWall, j * cellW, i * cellH, cellW, cellH);
                            else if( rigth && !top && !bottum && !left)
                                gc.drawImage(rigthWall, j * cellW, i * cellH, cellW, cellH);
                            else if(left && !top && !bottum && !rigth )
                                gc.drawImage(leftWall, j * cellW, i * cellH, cellW, cellH);

                            else if(top && rigth && !bottum  && !left)
                                gc.drawImage(TRWall, j * cellW, i * cellH, cellW, cellH);
                            else if(top  && left && !bottum && !rigth)
                                gc.drawImage(TLWall, j * cellW, i * cellH, cellW, cellH);
                            else if(top && bottum & !rigth & !left)
                                gc.drawImage(TBWall, j * cellW, i * cellH, cellW, cellH);

                            else if(bottum && rigth && !top &&!left)
                                gc.drawImage(BRWall, j * cellW, i * cellH, cellW, cellH);
                            else if( bottum && left && !top &&!rigth)
                                gc.drawImage(BLWall, j * cellW, i * cellH, cellW, cellH);

                            else if(rigth && left && !top && !bottum )
                                gc.drawImage(RLWall, j * cellW, i * cellH, cellW, cellH);

                            else if(top  && rigth && left && !bottum)
                                gc.drawImage(TRLWall, j * cellW, i * cellH, cellW, cellH);
                            else if(bottum && rigth && left && !top )
                                gc.drawImage(BRLWall, j * cellW, i * cellH, cellW, cellH);
                            else if(top && bottum && rigth && !left)
                                gc.drawImage(TBRWall, j * cellW, i * cellH, cellW, cellH);
                            else if(top && bottum &&  left && !rigth)
                                gc.drawImage(TBLWall, j * cellW, i* cellH, cellW, cellH);
                            else
                                gc.fillRect(j * cellW, i * cellH, cellW, cellH);

                        }
                    }
                }

                gc.drawImage(goalPoint,M.getGoalPosition().getColumnIndex()*cellW,M.getGoalPosition().getRowIndex()*cellH,cellW,cellH);
//                gc.setFill(Color.RED);
//                gc.fillRect(M.getGoalPosition().getColumnIndex()*cellW,M.getGoalPosition().getRowIndex()*cellH,cellW,cellH);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
//        int numberOfRows = this.maze.length;
//        int numberOfColumns = this.maze[0].length;
//
//        double cellH = cancasH/numberOfRows;
//        double cellW = canvasW/numberOfColumns;
//
//        GraphicsContext gc = getGraphicsContext2D();
//        gc.clearRect(0,0,cancasH,canvasW);
//    }

    public String getImageFileNameTWall() {
        return imageFileNameTWall.get();
    }

    public String getImageFileNameBWall() {
        return imageFileNameBWall.get();
    }

    public String getImageFileNameRWall() {
        return imageFileNameRWall.get();
    }

    public String getImageFileNameLWall() {
        return imageFileNameLWall.get();
    }

    public String getImageFileNameBRWall() {
        return imageFileNameBRWall.get();
    }

    public String getImageFileNameBLWall() {
        return imageFileNameBLWall.get();
    }

    public String getImageFileNameTRWall() {
        return imageFileNameTRWall.get();
    }

    public String getImageFileNameTLWall() {
        return imageFileNameTLWall.get();
    }

    public String getImageFileNameRLWall() {
        return imageFileNameRLWall.get();
    }

    public String getImageFileNameTBWall() {
        return imageFileNameTBWall.get();
    }

    public String getImageFileNameBRLWall() {
        return imageFileNameBRLWall.get();
    }

    public String getImageFileNameTRLWall() {
        return imageFileNameTRLWall.get();
    }

    public String getImageFileNameTBRWall() {
        return imageFileNameTBRWall.get();
    }

    public String getImageFileNameTBLWall() {
        return imageFileNameTBLWall.get();
    }

    public void setImageFileNameTWall(String imageFileNameTWall) {
        this.imageFileNameTWall.set(imageFileNameTWall);
    }

    public void setImageFileNameBWall(String imageFileNameBWall) {
        this.imageFileNameBWall.set(imageFileNameBWall);
    }

    public void setImageFileNameRWall(String imageFileNameRWall) {
        this.imageFileNameRWall.set(imageFileNameRWall);
    }

    public void setImageFileNameLWall(String imageFileNameLWall) {
        this.imageFileNameLWall.set(imageFileNameLWall);
    }

    public void setImageFileNameBRWall(String imageFileNameBRWall) {
        this.imageFileNameBRWall.set(imageFileNameBRWall);
    }

    public void setImageFileNameBLWall(String imageFileNameBLWall) {
        this.imageFileNameBLWall.set(imageFileNameBLWall);
    }

    public void setImageFileNameTRWall(String imageFileNameTRWall) {
        this.imageFileNameTRWall.set(imageFileNameTRWall);
    }

    public void setImageFileNameTLWall(String imageFileNameTLWall) {
        this.imageFileNameTLWall.set(imageFileNameTLWall);
    }

    public void setImageFileNameRLWall(String imageFileNameRLWall) {
        this.imageFileNameRLWall.set(imageFileNameRLWall);
    }

    public void setImageFileNameTBWall(String imageFileNameTBWall) {
        this.imageFileNameTBWall.set(imageFileNameTBWall);
    }

    public void setImageFileNameBRLWall(String imageFileNameBRLWall) {
        this.imageFileNameBRLWall.set(imageFileNameBRLWall);
    }

    public void setImageFileNameTRLWall(String imageFileNameTRLWall) {
        this.imageFileNameTRLWall.set(imageFileNameTRLWall);
    }

    public void setImageFileNameTBRWall(String imageFileNameTBRWall) {
        this.imageFileNameTBRWall.set(imageFileNameTBRWall);
    }

    public void setImageFileNameTBLWall(String imageFileNameTBLWall) {
        this.imageFileNameTBLWall.set(imageFileNameTBLWall);
    }

    public String getImageFileNameGoalPoint() {
        return imageFileNameGoalPoint.get();
    }

    public StringProperty imageFileNameGoalPointProperty() {
        return imageFileNameGoalPoint;
    }

    public void setImageFileNameGoalPoint(String imageFileNameGoalPoint) {
        this.imageFileNameGoalPoint.set(imageFileNameGoalPoint);
    }
}

