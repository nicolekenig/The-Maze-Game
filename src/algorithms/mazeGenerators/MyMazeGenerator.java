package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {
    public MyMazeGenerator() {
    }

    /**
     *  step 1 : init all maze to 1.
     *  step 2 : choose start and ending points.
     *  step 3 : call Prim.
     * @param i
     * @param j
     * @return Maze.
     */

    @Override
    public Maze generate(int i, int j) {

        Maze myMaze = new Maze(i,j);
        setMazeToOne(myMaze);
        myMaze = Prim(myMaze);
        setStartPoint(myMaze);
        setEndingPoint(myMaze);
//        myMaze.Print();

        return myMaze;
    }


    private void setMazeToOne(Maze maze)
    {
        if (maze == null) {
            System.out.println("maze given is null at setMazeToOne function");
        }
        for (int row = 0; row < maze.row; row++) {
            for (int col = 0; col < maze.col; col++) {
                if(row == 0 || row == maze.row-1 || col == 0 || col == maze.col - 1)
                    maze.setPath(new Position(row,col), 2);
                maze.setPath(new Position(row,col), 1);
            }
        }
    }

    private Maze Prim(Maze m)
    {
        if(m == null)
        {
            System.out.println("maze value provided is null");
            return null;
        }
        int rowStart = (int)(Math.random() * m.row);
        int colStart = (int)(Math.random() * m.col);
        Position begin = new Position(rowStart,colStart);

        ArrayList<Position> neighbours = new ArrayList();
        m.setPath(begin, 0);
        chooseRandomNeighbour(m, neighbours,begin,true);
        while(!neighbours.isEmpty())
        {
            Random rand = new Random();
            int randomIndex = rand.nextInt(neighbours.size());
            Position chosen = neighbours.get(randomIndex);
            neighbours.remove(randomIndex);
            chooseRandomNeighbour(m,neighbours,chosen,false);
        }
        return m;
    }

    private void findNeighbours(Maze m, Position p, ArrayList<Position> neighbours)
    {
        if(p.row - 2 >= 0 && !((new Position(p.row-2,p.col)).contains(neighbours)) && m.maze[p.row-2][p.col] == 1 && !p.isVisited)
            neighbours.add(new Position(p.row-2,p.col));
        if(p.row + 2 < m.row && !((new Position(p.row+2,p.col)).contains(neighbours)) && m.maze[p.row+2][p.col] == 1 && !p.isVisited)
            neighbours.add(new Position(p.row+2,p.col));
        if(p.col + 2 < m.col && !((new Position(p.row,p.col+2)).contains(neighbours)) && m.maze[p.row][p.col+2] == 1 && !p.isVisited)
            neighbours.add(new Position(p.row,p.col+2));
        if(p.col - 2 >= 0 && !((new Position(p.row,p.col-2)).contains(neighbours)) && m.maze[p.row][p.col-2] == 1 && !p.isVisited)
            neighbours.add(new Position(p.row,p.col-2));
    }

    private void chooseRandomNeighbour(Maze m, ArrayList<Position> neighbours,Position chosen, boolean addBetween)
    {
        findNeighbours(m,chosen,neighbours);
        m.setPath(chosen, 0);
        boolean top = false;
        boolean bottom = false;
        boolean right = false;
        boolean left = false;

        if(chosen.row - 2 >= 0)
            top = true;
        if(chosen.row  +2  < m.row)
            bottom = true;
        if(chosen.col + 2 < m.col )
            right = true;
        if(chosen.col -2 >= 0 )
            left = true;

        while (!addBetween){
            Random rand = new Random();
            int randomIndex1 = rand.nextInt(4);
            if(randomIndex1 ==0 &&  top)
                if(m.maze[chosen.row-2][chosen.col] == 0 )
                {
                    m.setPath(new Position(chosen.row-1, chosen.col), 0);
                    addBetween = true;
                }

            if(randomIndex1 == 1 && bottom)
                if(m.maze[chosen.row+2][chosen.col] == 0 )
                {
                    m.setPath(new Position(chosen.row+1, chosen.col), 0);
                    addBetween = true;
                }

            if(randomIndex1 == 2 && right)
                if(m.maze[chosen.row][chosen.col+2] == 0 )
                {
                    m.setPath(new Position(chosen.row, chosen.col+1), 0);
                    addBetween = true;
                }

            if(randomIndex1 == 3 && left)
                if(m.maze[chosen.row][chosen.col-2] == 0 )
                {
                    m.setPath(new Position(chosen.row, chosen.col-1), 0);
                    addBetween =true;
                }

        }

        chosen.isVisited = true;
    }


    private  void  setStartPoint(Maze m){
        boolean topFrameBlocked = true;
        Random rand = new Random();
        for(int i = 1; i < (m.col-2)/2; ++i)
        {
            int a = rand.nextInt(m.col-2);
            while(a == 0)
            {
                a = rand.nextInt(m.col-2);
            }

            if(m.maze[0][a] != 1 && (m.maze[0][a-1] == 0 || m.maze[0][a+1] == 0 || m.maze[1][a] == 0))
            {
                m.setStartingPoint(0,a);
                topFrameBlocked = false;
                break;
            }

        }
        if(topFrameBlocked)
        {
            for(int i = 1; i < m.col-2; ++i)
            {
                if(m.maze[1][i] != 1 )
                {
                    m.maze[0][i]=0;
                    m.setStartingPoint(0,i);
                    break;
                }
            }
        }


//        Random rand = new Random();
//        int randRow;
//        int randCol;
//        boolean startPointFound = false;
//        boolean topFrame = false;
//        boolean bottomFrame = false;
//        boolean rigthFrame = false;
//        boolean leftFrame = false;
//        while(true) {
//            randRow = rand.nextInt(m.row);
//            randCol = rand.nextInt(m.col);
//            topFrame = randRow == 0;
//            bottomFrame= randRow == m.row-1;
//            rigthFrame = randCol == m.col-1;
//            leftFrame = randCol == 0;
//            startPointFound = m.maze[randRow][randCol] == 0;
//
//
//            if((topFrame|| bottomFrame || rigthFrame || leftFrame) && startPointFound)
//            {
//                m.setStartingPoint(randRow, randCol);
//                break;
//            }
//        }
    }

    private  void  setEndingPoint(Maze m) {
        boolean bottomFrameBlocked = true;
        Random rand = new Random();
        for(int i = 1; i < m.col-2; ++i)
        {
            int a = rand.nextInt(m.col-2);
            while(a == 0)
            {
                a = rand.nextInt(m.col-2);
            }
            if(m.maze[m.row-1][a] != 1 && (m.maze[m.row-1][a-1] == 0 || m.maze[m.row-1][a+1] == 0 || m.maze[m.row-2][a] == 0))
            {
                m.setEndingPoint(m.row-1,a);
                bottomFrameBlocked = false;
                break;
            }
        }


        if(bottomFrameBlocked)
        {
            for(int i = 1; i < m.col-2; ++i)
            {
                if(m.maze[m.row-2][i] != 1 )
                {
                    m.maze[m.row-1][i]=0;
                    m.setEndingPoint(m.row-1,i);
                    break;
                }
            }
        }
//        Random rand = new Random();
//        int randRow;
//        int randCol;
//        boolean endPointFound = false;
//        boolean s_topFrame = m.getStartPosition().getRowIndex() == 0;
//        boolean s_bottomFrame = m.getStartPosition().getRowIndex() == m.row - 1;
//        boolean s_rigthFrame = m.getStartPosition().getColumnIndex() == m.col - 1;
//        boolean s_leftFrame = m.getStartPosition().getColumnIndex() == 0;
//        while (true) {
//            randRow = rand.nextInt(m.row);
//            randCol = rand.nextInt(m.col);
//            endPointFound = m.maze[randRow][randCol] == 0;
//            if (s_topFrame && (randRow != 0) && endPointFound) // Start Point is on the top frame
//            {
//                if (randRow == m.row - 1 || randCol == m.col - 1 || randCol == 0) //ending point bottom/right/left frame
//                {
//                    m.setEndingPoint(randRow , randCol);
//                    break;
//                }
//            }
//            else if (s_bottomFrame && (randRow != m.row - 1)&& endPointFound) // Start Point is on the bottom frame
//            {
//                if (randRow == 0 || randCol == m.col - 1 || randCol == 0) //Ending point is on the top/right/left frame
//                {
//                    m.setEndingPoint(randRow , randCol);
//                    break;
//                }
//            }
//            else if (s_rigthFrame && (randCol != m.col - 1)&& endPointFound) // Start Point is on the right frame
//            {
//                if (randCol == 0 || randRow == 0 ||randRow == m.row - 1) //ending point left/top/bottom frame
//                {
//                    m.setEndingPoint(randRow, randCol);
//                    break;
//                }
//            }
//            else if (s_leftFrame && (randCol != 0)&& endPointFound) // Start Point is on the left frame
//            {
//                if (randCol == m.col - 1 ||randRow == 0 || randRow == m.row - 1) //ending point right/top/bottom frame
//                {
//                    m.setEndingPoint(randRow, randCol);
//                    break;
//                }
//            }
//        }
    }



}
