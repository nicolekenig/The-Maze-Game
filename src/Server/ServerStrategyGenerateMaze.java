package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{

    public ServerStrategyGenerateMaze() {
    }

    @Override
    public void handleClient(InputStream inputStream, OutputStream outPutStream) {
        try{
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outPutStream);
            toClient.flush();
            int[] data = (int[])(int[])fromClient.readObject();

            String mazeGenerat= Configurations.getProperty("MazeGenerator");
            Object mg = null;
            if(mazeGenerat != null ){
                if(mazeGenerat.equals("SimpleMazeGenerator"))
                    mg = new SimpleMazeGenerator();
                else if(mazeGenerat.equals("MyMazeGenerator"))
                    mg = new MyMazeGenerator();
                else if(mazeGenerat.equals("EmptyMazeGenerator"))
                    mg = new EmptyMazeGenerator();
            }
//            AMazeGenerator mg = new MyMazeGenerator();
            Maze m = ((AMazeGenerator)mg).generate(data[0], data[1]);
            if(m==null)
            {
                return;
            }
            byte[] notCompressByteArrayMaze = m.toByteArray();
            ByteArrayOutputStream whereToWriteMaze = new ByteArrayOutputStream();
            MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(whereToWriteMaze);
            compressorOutputStream.write(notCompressByteArrayMaze);
            toClient.writeObject(whereToWriteMaze.toByteArray());
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
