package turingmachine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author w w w. j a v a g i s t s . c o m
 *
 */
public class Node<T> {

    private T readState = null;
    private T readSymbol = null;
    private T nextState = null;
    private T nextSymbol = null;
    private T dir = null;

    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;
    public int currentChild = 0;

    public Node(T readState, T readSymbol) {
        this.readState = readState;
        this.readSymbol = readSymbol;
    }

    public Node<T> addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public void addChildren(List<Node<T>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public T getReadState() {
        return readState;
    }

    public void setNextState(T nextState){
        this.nextState = nextState;
    }

    public void setNextSymbol(T nextSymbol){
        this.nextSymbol = nextSymbol;
    }

    public void setReadSymbol(T readSymbol){
        this.readSymbol = readSymbol;
    }

    public void setReadState(T readState) {
        this.readState = readState;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public T getDir() {
        return dir;
    }

    public void setDir(T dir) {
        this.dir = dir;
    }

    public Node<T> getParent() {
        return parent;
    }

    public T getReadSymbol() {
        return readSymbol;
    }

    public T getNextState() {
        return nextState;
    }

    public T getNextSymbol() {
        return nextSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return readState.equals(node.readState) && readSymbol.equals(node.readSymbol) &&
                nextState.equals(node.nextState) && nextSymbol.equals(node.nextSymbol);
    }

    @Override
    public int hashCode() {
        int result = nextSymbol.hashCode() + readState.hashCode();
        result = 31 * result + nextSymbol.hashCode();
        return result;
    }
}