package turingmachine;

import java.util.ArrayList;
import java.util.List;

public class Node1 {
    private String state;
    private String symbol;
    private String dir;
    private int n;
    private List<Node1> children;
    private Node1 parent;

    public Node1(String state, String symbol) {
        this.state = state;
        this.symbol = symbol;
        children = new ArrayList<>();
        n = 0;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Node1 getParent() {
        return parent;
    }

    public void setParent(Node1 parent) {
        this.parent = parent;
    }

    public void addChildNote(Node1 child){
        children.add(child);
        n++;
    }

    public void setDir(String dir){this.dir = dir;}
    public String getDir(){return dir;}

    public List<Node1> getChildren() {
        return children;
    }
}
