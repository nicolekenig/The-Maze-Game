package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Maze implements Serializable {

    int maze[][];
    int row;
    int col;
    Position startingPoint;
    Position endingPoint;

    public Maze(int i, int j) {
        row = i;
        col = j;
        startingPoint = new Position();
        endingPoint = new Position();
        maze = new int[this.row][this.col];
    }

    public Maze() {
        row = 0;
        col = 0;
        startingPoint = new Position();
        endingPoint = new Position();
        maze = null;
    }

    public Maze(byte[] b) {
        int[] copyB = new int[b.length];

        int index;
        for(index = 0; index < b.length; ++index) {
            if (b[index] < 0) {
                copyB[index] = b[index] + 256;
            } else {
                copyB[index] = b[index];
            }
        }

        this.row = copyB[0] * 256 + copyB[1];
        this.col = copyB[2] * 256 + copyB[3];
        this.startingPoint = new Position(0, copyB[4] * 256 + copyB[5]);
        this.endingPoint = new Position(this.row - 1, copyB[6] * 256 + copyB[7]);
        this.maze = new int[this.row][this.col];
        index = 8;

        for(int i = 0; i < this.row && index < b.length; ++i) {
            for(int j = 0; j < this.col && index < b.length; ++j) {
                this.maze[i][j] = b[index];
                ++index;
            }
        }
    }

    public Maze(int[][] maze, Position startingPoint, Position endingPoint) {
        this.maze = maze;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
    }

    public void setPath(Position p, int num)
    {

        maze[p.row][p.col] = num;
    }

    public void setStartingPoint(int i, int j)
    {
        startingPoint.setRow(i);
        startingPoint.setCol(j);
    }

    public void setEndingPoint(int i, int j)
    {
        endingPoint.setRow(i);
        endingPoint.setCol(j);
    }



    public Position getStartPosition()
    {
        return startingPoint;
    }



    public Position getGoalPosition()
    {
        return endingPoint;
    }

    public int[][] getMaze(){return maze;}


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    public void Print()
    {
        for (int i =0; i < maze.length;i++)
        {
            for (int j = 0; j < maze[0].length; j++)
            {
                if(this.startingPoint.getRowIndex() == i && this.startingPoint.getColumnIndex() == j && this.startingPoint.getColumnIndex() < maze[0].length-1)
                    System.out.print("S,");
                else if (this.startingPoint.getRowIndex() == i && this.startingPoint.getColumnIndex() == j && this.startingPoint.getColumnIndex() == maze[0].length-1)
                    System.out.print("S");
                else if(this.endingPoint.getRowIndex() == i && this.endingPoint.getColumnIndex() == j && this.endingPoint.getColumnIndex() < maze[0].length-1)
                    System.out.print("E,");
                else if(this.endingPoint.getRowIndex() == i && this.endingPoint.getColumnIndex() == j && this.endingPoint.getColumnIndex() == maze[0].length-1)
                    System.out.print("E");
                else if(j < maze[0].length - 1)
                    System.out.print(maze[i][j]+",");
                else
                    System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
    public void addToArr(ArrayList<Byte> arr, int num){
        while(num > 255){
            arr.add(Integer.valueOf(255).byteValue());
            arr.add(Integer.valueOf(0).byteValue());
            num -= 255;
        }
        arr.add(Integer.valueOf(num).byteValue());
    }

    public byte[] toByteArray() {
        double mazeSize = (double)(this.col * this.row);
        int arraySize = (int)mazeSize + 8;
        byte[] result = new byte[arraySize];
        result[0] = Integer.valueOf(this.row / 256).byteValue();
        result[1] = Integer.valueOf(this.row % 256).byteValue();
        result[2] = Integer.valueOf(this.col / 256).byteValue();
        result[3] = Integer.valueOf(this.col % 256).byteValue();
        result[4] = Integer.valueOf(this.startingPoint.getColumnIndex() / 256).byteValue();
        result[5] = Integer.valueOf(this.startingPoint.getColumnIndex() % 256).byteValue();
        result[6] = Integer.valueOf(this.endingPoint.getColumnIndex() / 256).byteValue();
        result[7] = Integer.valueOf(this.endingPoint.getColumnIndex() % 256).byteValue();
        int index = 8;

        for(int i = 0; i < this.maze.length && index < result.length; ++i) {
            for(int j = 0; j < this.maze[i].length && index < result.length; ++j) {
                result[index] = Integer.valueOf(this.maze[i][j]).byteValue();
                ++index;
            }
        }
//        System.out.println("size:"+result.length);
        return result;


    }

    public boolean equal(Maze other){
        boolean startRow = this.startingPoint.getRowIndex() == other.startingPoint.getRowIndex();
        boolean startCol = this.startingPoint.getColumnIndex() == other.startingPoint.getColumnIndex();
        boolean goalRow = this.endingPoint.getRowIndex() == other.endingPoint.getRowIndex();
        boolean goalCol = this.endingPoint.getColumnIndex() == other.endingPoint.getColumnIndex();
        boolean rowsCount = this.row == other.row;
        boolean colsCount = this.col == other.col;
        boolean maze = Arrays.equals(this.getMaze(),other.getMaze());

        if(startRow && startCol && goalRow && goalCol && rowsCount && colsCount && maze)
            return true;

        return false;
    }
}