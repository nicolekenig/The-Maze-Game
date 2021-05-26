package test;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

import java.util.ArrayList;

/**
 * Created by Aviadjo on 3/22/2017.
 */
public class RunSearchOnMaze {
    public static void main(String[] args) {

        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(200, 200);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        long time1,time2;
//        maze.Print();
        time1 =  System.currentTimeMillis();
        solveProblem(searchableMaze, new BreadthFirstSearch());
        time2 =  System.currentTimeMillis();
        System.out.println(String.format("BFS search time(ms): %s",time2-time1));

        time1 =  System.currentTimeMillis();
        solveProblem(searchableMaze, new DepthFirstSearch());
        time2 =  System.currentTimeMillis();
        System.out.println(String.format("DFS search time(ms): %s",time2-time1));

        time1 =  System.currentTimeMillis();
        solveProblem(searchableMaze, new BestFirstSearch());
        time2 =  System.currentTimeMillis();
        System.out.println(String.format("BEST search time(ms): %s",time2-time1));
        // prints the time it takes the algorithm to run

    }

    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {

        //Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
        //Printing Solution Path
//        System.out.println("Solution path:");
//        ArrayList<AState> solutionPath = solution.getSolutionPath();
//        for (int i = 0; i < solutionPath.size(); i++) {
//            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
//        }
    }
}