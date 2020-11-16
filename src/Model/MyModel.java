package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyModel extends Observable implements IModel {


    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private  int characterPositionRow;
    private  int characterPositionColumn;
    private  Maze maze;
    private  Solution mazeSolution;
    private Server mazeGeneratingServer;
    private Server solveSearchProblemServer;
//    private double canvasH = 800;
//    private double canvasW =1000 ;

    public MyModel() {
        this.mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        this.solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
    }

    public void startServers() {
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
    }

    public void stopServers() {
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
    }

    private void CommunicateWithServer_MazeGenerating(int rowsNumber, int columnsNumber) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rowsNumber, columnsNumber};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[])((byte[])fromServer.readObject());
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[mazeDimensions[0] * mazeDimensions[1] + 8];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                        characterPositionRow = maze.getStartPosition().getRowIndex();
                        characterPositionColumn = maze.getStartPosition().getColumnIndex();
                        //                        maze.Print();
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }

    }

    private  void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
//                        MyMazeGenerator mg = new MyMazeGenerator();
//                        Maze maze = mg.generate(4, 4);
                        toServer.writeObject(maze);
                        toServer.flush();
                        mazeSolution = (Solution)fromServer.readObject();
//                        System.out.println(String.format("Solution steps: %s", mazeSolution));
//                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
//                        for(int i = 0; i < mazeSolutionSteps.size(); ++i) {
//                            System.out.println(String.format("%s. %s", i, ((AState)mazeSolutionSteps.get(i)).toString()));
//                        }
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }

    }
    @Override
    public void generateMaze(int rowsNumber, int columsNumber) {
        CommunicateWithServer_MazeGenerating(rowsNumber, columsNumber);
        setChanged();
        notifyObservers();
    }



    @Override
    public void moveCharacter(KeyCode movement) {
        switch (movement){
            case UP:
                if(characterPositionRow-1 >=0 && maze.getMaze()[characterPositionRow-1][ characterPositionColumn]== 0)
                    characterPositionRow = characterPositionRow-1;
                break;

            case DOWN:
                if(characterPositionRow+1 < maze.getRow() && maze.getMaze()[characterPositionRow+1][ characterPositionColumn]== 0)
                    characterPositionRow = characterPositionRow+1;
                break;

            case RIGHT:
                if(characterPositionColumn+1 < maze.getCol() && maze.getMaze()[characterPositionRow][ characterPositionColumn+1]== 0)
                    characterPositionColumn = characterPositionColumn+1;
                break;

            case LEFT:
                if(characterPositionColumn-1 >= 0 && maze.getMaze()[characterPositionRow][ characterPositionColumn-1]== 0)
                    characterPositionColumn = characterPositionColumn-1;
                break;
            case NUMPAD8:
                if(characterPositionRow-1 >=0 && maze.getMaze()[characterPositionRow-1][ characterPositionColumn]== 0)
                    characterPositionRow = characterPositionRow-1;
                break;

            case NUMPAD2:
                if(characterPositionRow+1 < maze.getRow() && maze.getMaze()[characterPositionRow+1][ characterPositionColumn]== 0)
                    characterPositionRow = characterPositionRow+1;
                break;

            case NUMPAD6:
                if(characterPositionColumn+1 < maze.getCol() && maze.getMaze()[characterPositionRow][ characterPositionColumn+1]== 0)
                    characterPositionColumn = characterPositionColumn+1;
                break;

            case NUMPAD4:
                if(characterPositionColumn-1 >= 0 && maze.getMaze()[characterPositionRow][ characterPositionColumn-1]== 0)
                    characterPositionColumn = characterPositionColumn-1;
                break;
            case NUMPAD1:
                if(characterPositionRow+1 < maze.getRow() && characterPositionColumn-1 >= 0 && maze.getMaze()[characterPositionRow+1][ characterPositionColumn-1]== 0)
                {
                    characterPositionRow = characterPositionRow+1;
                    characterPositionColumn = characterPositionColumn-1;
                }
                break;
            case NUMPAD3:
                if(characterPositionRow+1 < maze.getRow() && characterPositionColumn+1 < maze.getCol() &&maze.getMaze()[characterPositionRow+1][ characterPositionColumn+1]== 0)
                {
                    characterPositionRow = characterPositionRow+1;
                    characterPositionColumn = characterPositionColumn+1;
                }
                break;
            case NUMPAD7:
                if(characterPositionRow-1 >=0 && characterPositionColumn-1 >= 0 && maze.getMaze()[characterPositionRow-1][ characterPositionColumn-1]== 0)
                {
                    characterPositionRow = characterPositionRow-1;
                    characterPositionColumn = characterPositionColumn-1;
                }
                break;
            case NUMPAD9:
                if(characterPositionRow-1 >=0 && characterPositionColumn+1 < maze.getCol() && maze.getMaze()[characterPositionRow-1][ characterPositionColumn+1]== 0)
                {
                    characterPositionRow = characterPositionRow-1;
                    characterPositionColumn = characterPositionColumn+1;
                }
                break;
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public Maze getMaze() {
        return this.maze;
    }

    @Override
    public Solution getSolution() {
        CommunicateWithServer_SolveSearchProblem();
        return this.mazeSolution;
    }

    @Override
    public int getCharacterPositionRow() {
        return this.characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return this.characterPositionColumn;
    }

    public boolean checkIfArraveToEndingPoint(){
        boolean rowIndex = this.characterPositionRow == maze.getGoalPosition().getRowIndex();
        boolean colIndex = this.characterPositionColumn == maze.getGoalPosition().getColumnIndex();
        return (rowIndex && colIndex);
    }

    @Override
    // like in solveMaze func from ServerStrategySolveSearchProblem.
    // write the maze and after write the character row position and col position
    public void save(File filaToSave) {
        if(filaToSave != null){
            System.out.println("Maze Saved");
            try {
                //save character position
                FileOutputStream fileOutputStream =new FileOutputStream(filaToSave.getAbsoluteFile());
                byte[] characterPosition = new byte[4];
                characterPosition[0] = Integer.valueOf(this.characterPositionRow / 256).byteValue();
                characterPosition[1] = Integer.valueOf(this.characterPositionRow % 256).byteValue();
                characterPosition[2] = Integer.valueOf(this.characterPositionColumn / 256).byteValue();
                characterPosition[3] = Integer.valueOf(this.characterPositionColumn % 256).byteValue();
                for(int i = 0; i < 4; i++) {
                    fileOutputStream.write(characterPosition[i]);
                }
                fileOutputStream.flush();

                //save made data (like in RunCompressDecompressMaze)
                OutputStream outputStream = new MyCompressorOutputStream(fileOutputStream);
                outputStream.write(maze.toByteArray());
                outputStream.flush();
                outputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    // like in loadSolution func from ServerStrategySolveSearchProblem
    // read first character position and the maze size , after read the maze data
    public void load(File file) {
        try {
            //read character position and the maze size first to initialize the byteArrayMaze size.
            FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
            System.out.println("initialize inputStream");
            byte[] b = new byte[8];
            int[] copyB = new int[b.length];
            int read;
            for(int i=0; i< 8; i++){
                read = fileInputStream.read() &255;
                b[i] = Integer.valueOf(read).byteValue();
                System.out.println("b["+i+"] = "+ b[i]);
            }

            for(int i=0; i<b.length ; i++){
                if(b[i]< 0)
                    copyB[i] = b[i] + 256;
                else
                    copyB[i] = b[i];
            }
            this.characterPositionRow = copyB[0] * 256 + copyB[1];
            this.characterPositionColumn = copyB[2] * 256 + copyB[3];
            int numberOfRow = copyB[4] * 256 + copyB[5];
            int numberOfCol = copyB[6] * 256 + copyB[7];
            byte[] savedMazeBytes = new byte[numberOfRow * numberOfCol+8];

            //read all maze data to byteArrayMaze
            //like in RunCompressDecompressMaze
            InputStream in = new MyDecompressorInputStream(new FileInputStream(file.getAbsoluteFile()));
            //character position data
            for( int i=0; i< 4; i++){
                int tmp = in.read() & 255;
            }
            in.read(savedMazeBytes);
            in.close();
            fileInputStream.close();
            this.maze = new Maze(savedMazeBytes);

            setChanged();
            notifyObservers();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exit() {
        stopServers();
    }


}