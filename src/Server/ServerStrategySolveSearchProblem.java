package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Objects;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    private File tempDirectory;
    private File mazesDir;
    private File solutionsDir;
    int numOfMazes;

    public ServerStrategySolveSearchProblem() {
        this.tempDirectory = new File(this.tempDirectoryPath, "tempDir");
        boolean tempDCreated = this.tempDirectory.mkdir();
        this.mazesDir = new File(this.tempDirectory.getPath(), "Mazes");
        boolean mazesDCreated = this.mazesDir.mkdir();
        this.solutionsDir = new File(this.tempDirectory.getPath(), "Solutions");
        boolean solutionsDCreated = this.solutionsDir.mkdir();
        if ((!tempDCreated || !mazesDCreated || !solutionsDCreated) && (!this.tempDirectory.exists() || !this.tempDirectory.isDirectory() || !this.mazesDir.exists() || !this.mazesDir.isDirectory() || !this.solutionsDir.exists() || !this.solutionsDir.isDirectory())) {
            System.out.println("There was a problem creating the directory");
        }

        this.numOfMazes = 0;
    }

    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Maze mazeToSolve = (Maze)fromClient.readObject();
            int mazeIndex = this.findMaze(mazeToSolve);
            Solution solutionToClient;
            if (mazeIndex != -1) {
                solutionToClient = this.loadSolution(mazeIndex);
            } else {
                solutionToClient = this.solveMaze(mazeToSolve);
            }

            toClient.writeObject(solutionToClient);
            toClient.flush();
            toClient.close();
        } catch (ClassNotFoundException | IOException var8) {
            var8.printStackTrace();
        }

    }

    private synchronized Solution loadSolution(int mazeIndex) {
        Solution solutionToClient = null;

        try {
            ObjectInputStream solutionFile = new ObjectInputStream(new FileInputStream(this.solutionsDir.getPath() + "/" + mazeIndex));
            solutionToClient = (Solution)solutionFile.readObject();
        } catch (ClassNotFoundException | IOException var4) {
            var4.printStackTrace();
        }

        return solutionToClient;
    }

    private synchronized Solution solveMaze(Maze mazeToSolve) {
        Solution solutionToClient = null;

        try {
            OutputStream addMaze = new FileOutputStream(this.mazesDir.getPath() + "/" + this.numOfMazes);
            MyCompressorOutputStream compressedMaze = new MyCompressorOutputStream(addMaze);
            compressedMaze.write(mazeToSolve.toByteArray());
            addMaze.flush();
            addMaze.close();
            ObjectOutputStream addSolution = new ObjectOutputStream(new FileOutputStream(this.solutionsDir.getPath() + "/" + this.numOfMazes));
            SearchableMaze searchableMaze = new SearchableMaze(mazeToSolve);
            ASearchingAlgorithm searchingAlgorithm = this.getSearchingAlgorithm();
//            ASearchingAlgorithm searchingAlgorithm = new BestFirstSearch();
            solutionToClient = searchingAlgorithm.solve(searchableMaze);
            addSolution.writeObject(solutionToClient);
            addSolution.flush();
            addSolution.close();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        return solutionToClient;
    }

    private synchronized int findMaze(Maze mazeToSolve) {
        try {
            FileOutputStream out = new FileOutputStream(this.tempDirectory.getPath() + "/mazeToSolve");
            MyCompressorOutputStream newMaze = new MyCompressorOutputStream(out);
            newMaze.write(mazeToSolve.toByteArray());
            out.flush();
            out.close();
        } catch (IOException var12) {
            var12.printStackTrace();
        }

        int mazeIndex = -1;
        File[] mazesDirContent = this.mazesDir.listFiles();

        assert mazesDirContent != null;

        this.numOfMazes = mazesDirContent.length;

        for(int i = 0; i < this.numOfMazes; ++i) {
            boolean areEqual = true;
            File mazeFile = new File(this.mazesDir.getPath() + "/" + i);

            try {
                BufferedReader br1 = new BufferedReader(new FileReader(this.tempDirectory.getPath() + "/mazeToSolve"));
                BufferedReader br2 = new BufferedReader(new FileReader(this.mazesDir.getPath() + "/" + i));

                String line1;
                String line2;
                while((line1 = br1.readLine()) != null | (line2 = br2.readLine()) != null) {
                    if (line1 == null || line2 == null) {
                        areEqual = false;
                        break;
                    }

                    if (!line1.equals(line2)) {
                        areEqual = false;
                    }
                }
            } catch (IOException var13) {
                var13.printStackTrace();
            }

            if (areEqual) {
                try {
                    mazeIndex = Integer.parseInt(mazeFile.getName());
                } catch (NumberFormatException var11) {
                }
                break;
            }
        }

        return mazeIndex;
    }

    private ASearchingAlgorithm getSearchingAlgorithm() {
        if (Objects.equals(Configurations.getProperty("SearchingAlgorithm"), "BestFirstSearch"))
            return new BestFirstSearch();
        else if (Objects.equals(Configurations.getProperty("SearchingAlgorithm"), "DepthFirstSearch"))
            return new DepthFirstSearch();
        else if(Objects.equals(Configurations.getProperty("SearchingAlgorithm"), "BreadthFirstSearch"))
            return new BreadthFirstSearch();
        else
            return new BestFirstSearch();
    }
}
