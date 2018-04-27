package turingmachine;

import java.util.*;

/**
 * This is a superclass for Turing machine simulators
 */
public class TM {
    public static final int FIRSTSIZE = 10;
    public List<String> tape;
    public int head;
    public String currentState;
    public int currentSize;
    private String[] states;
    private Set<String> tapeAlph;
    private ArrayList<String> acceptStates;
    private int moves;

    public TM(){}
    public TM(String[] states, String[] tapeAlph) {
        this.states = states;
        this.tapeAlph = new HashSet<String>(Arrays.asList(tapeAlph));
        moves = 0;
    }


    public String[] getStates() {
        return states;
    }

    public Set<String> getTapeAlph() {
        return tapeAlph;
    }

    public void setTapeAlph(Set<String> tapeAlph) {
        this.tapeAlph = tapeAlph;
    }

    public ArrayList<String> getAcceptStates() {
        return acceptStates;
    }

    public void addStates(String[] states){
        this.states = states;
    }

    public void addTapeAlph(String[] tapeAlph){
        this.tapeAlph = new HashSet<String>(Arrays.asList(tapeAlph));
    }

    public void addAccepts(ArrayList<String> accept){

        acceptStates = accept;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }
}
