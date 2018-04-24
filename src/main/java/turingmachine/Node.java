package turingmachine;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String state;
    private String symbol;
    private String dir;
    private int n;
    private List<Node> children;
    private Node parent;

    public Node(String state, String symbol) {
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

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChildNote(Node child){
        children.add(child);
        n++;
    }

    public void setDir(String dir){this.dir = dir;}
    public String getDir(){return dir;}

    public List<Node> getChildren() {
        return children;
    }
}
