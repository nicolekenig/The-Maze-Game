package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution implements Serializable {

    private AState sol;
    private ArrayList<AState> path = new ArrayList<>();

    public Solution(AState sol) {
        this.sol = sol;
    }

    public Solution(byte[] sol)
    {
        boolean startRow = true;
        boolean startCol = false;
        int i = 0;
        int currSum = 0;
        int row = 0;
        int col = 0;
        while (i < sol.length){

            while(true)
            {
                currSum += sol[i] & 0xFF;
                i++;
                if(sol[i] == 0 && sol[i+1] == 0)
                    break;
            }
            i+=2;
            if(startRow)
            {
               row = currSum;
                startRow = false;
                startCol = true;
                currSum = 0;
            }
            else if(startCol)
            {
                col = currSum;
                startCol = false;
                currSum = 0;
                Position currPosition = new Position(row,col);
                MazeState mazeState = new MazeState(currPosition);
                path.add(mazeState);
            }
        }
    }

    public ArrayList<AState> getSolutionPath(){
        path.add(sol);

        while (sol.getFather()!= null){
            path.add(0,sol.father);
            sol = sol.getFather();
        }
        return path;
    }

    public byte[] solutionToByteArray()
    {
        List<AState> solPath = Collections.synchronizedList( this.getSolutionPath()) ;
        List<Byte> tmp = Collections.synchronizedList(new ArrayList<>());
        for(AState position: solPath){

            int row = position.getPosition().getRowIndex();
            while (row > 255){
                tmp.add((byte)255);
                row -= 255;
            }
            tmp.add((byte)row);
            tmp.add((byte)0);
            tmp.add((byte)0);

            int col =position.getPosition().getColumnIndex();
            while (col > 255){
                tmp.add((byte)255);
                col -= 255;
            }
            tmp.add((byte)col);
            tmp.add((byte)0);
            tmp.add((byte)0);
        }

        byte[] b = new byte[tmp.size()];
        for(int i=0 ; i< tmp.size(); i++)
            b[i] = tmp.get(i);

        return b;


    }
}
