package algorithms.search;

import algorithms.mazeGenerators.Position;
import javafx.geometry.Pos;

import java.io.Serializable;

public abstract class AState implements Serializable {

    Position position;
    AState father;
    int cost;

    public AState(){}

    public AState(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        AState tmp = (AState)obj;
        if(this.position.equals(tmp.position))
            return true;
        return false;
    }


    public void setFather(AState f)
    {
        this.father = f;
    }

    public AState getFather() {
        return father;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return  position.toString() ;

    }
}
