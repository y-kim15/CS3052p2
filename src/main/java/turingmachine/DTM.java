package turingmachine;

import javafx.util.Pair;

import java.util.*;
public class DTM extends TM {
    private HashMap<Tuple,Pair<Tuple,String>> transition = new HashMap<Tuple, Pair<Tuple, String>>();

    public DTM(){
        head =0;
    }
    public DTM(String[] states, String[] tapeAlph){
        super(states, tapeAlph);
        head = 0;
    }


    public void addTransition(String[] trans){
        Tuple current = new Tuple(trans[0], trans[1]);
        Tuple next = new Tuple(trans[2], trans[3]);
        transition.put(current, new Pair<Tuple, String>(next, trans[4]));
    }

    public boolean checkTransition(String state, String symbol){
        Tuple read = new Tuple(state, symbol);
        return transition.containsKey(read);
    }

    public boolean process(String state, String symbol){
        //System.out.println("current state is " + state + " current read is " + in);
        //System.out.println("current head index is " + head);
        //System.out.println("tape: " + tape.toString());
        boolean accept = false;
        while(!accept){
            Tuple read = new Tuple(state, symbol);
            if(!transition.containsKey(read)){
                System.out.println("Reject, no such transition");
                break;
            }
            Pair<Tuple,String> next = transition.get(read);
            Tuple tuple = next.getKey();
            int move = Utils.getDirection(next.getValue());
            //System.out.println("movement is " + next.getValue() + " in no is " + move);
            String nextState = tuple.getState();
            String toWrite = tuple.getAlphabet();
            //System.out.println("next state is " + nextState + " to write is " + toWrite);
            //Set<String> stateSet = new HashSet<String>(Arrays.asList(states));

            // acceptStates state
            if(getAcceptStates().contains(nextState)){//stateSet.contains(currentState+"+")){
                System.out.println("accept");
                accept = true;
                break;
            }
            currentState = nextState;
                //System.out.println("not acceptStates, write " + toWrite + " to head " + head);
            tape.set(head, toWrite);

            if(head == 0 && move == -1){
                move = 0;
            }
            head += move;
            if(tape.get(head) == null){
                for(int i=0; i<FIRSTSIZE; i++){
                    tape.set(head, "_");
                }
                currentSize += FIRSTSIZE;
            }
            state = currentState;
            symbol = tape.get(head);
        }
        return accept;
    }


    public boolean read(String input){
        currentState = getStates()[0];
        currentSize = FIRSTSIZE + input.length();
        tape = new ArrayList<String>(Collections.nCopies(currentSize, "_"));
        for(int i=0; i<input.length(); i++){
            tape.set(i,Character.toString(input.charAt(i)));
        }
        return process(currentState, tape.get(head)); //read
    }
}
