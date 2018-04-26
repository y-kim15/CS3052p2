package turingmachine;

import javafx.util.Pair;

import java.util.*;

/**
 * This is an extended class of TM for Deterministic Turing
 * Machine simulator.
 */
public class DTM extends TM {
    private HashMap<Tuple,Pair<Tuple,String>> transition = new HashMap<Tuple, Pair<Tuple, String>>();

    public DTM(){
        head =0;
    }

    public void addTransition(String[] trans){
        Tuple current = new Tuple(trans[0], trans[1]);
        Tuple next = new Tuple(trans[2], trans[3]);
        transition.put(current, new Pair<>(next, trans[4]));
    }

    /**
     * Checks whether given transition already exists,
     * required to find any invalid transitions noted in the description file
     * @param state
     * @param symbol
     * @return boolean denoting the existence
     */
    public boolean checkTransition(String state, String symbol){
        Tuple read = new Tuple(state, symbol);
        return transition.containsKey(read);
    }

    /**
     * Reads the input through the turing machine until it accepts or rejects.
     * this is done iteratively.
     * @param state current state
     * @param symbol current symbol read from input
     * @return boolean denoting the result, accept or reject
     */
    public boolean process(String state, String symbol){
        boolean accept = false;
        while(!accept){
            Tuple read = new Tuple(state, symbol);
            if(!transition.containsKey(read)){
                //System.out.println("Reject, no such transition");
                break;
            }
            Pair<Tuple,String> next = transition.get(read);
            Tuple tuple = next.getKey();
            int move = Utils.getDirection(next.getValue());
            String nextState = tuple.getState();
            String toWrite = tuple.getAlphabet();
            if(getAcceptStates().contains(nextState)){
                //System.out.println("accept");
                accept = true;
                break;
            }
            currentState = nextState;
            tape.set(head, toWrite);
            setMoves(getMoves()+1);

            if(head == 0 && move == -1){
                move = 0;
            }
            head += move;
            if(tape.get(head) == null){
                for(int i=0; i<FIRSTSIZE; i++){
                    tape.set(head+i, "_");
                }
                currentSize += FIRSTSIZE;
            }
            state = currentState;
            symbol = tape.get(head);
        }
        return accept;
    }

    /**
     * Reads input ready for processing by adding it to the tape
     * @param input input string read
     * @return boolean denoting whether to reject or accept the string
     */
    public boolean read(String input){
        currentState = getStates()[0];
        currentSize = FIRSTSIZE + input.length();
        tape = new ArrayList<String>(Collections.nCopies(currentSize, "_"));
        for(int i=0; i<input.length(); i++){
            tape.set(i,Character.toString(input.charAt(i)));
        }
        return process(currentState, tape.get(head));
    }
}
