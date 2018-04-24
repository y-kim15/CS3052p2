package turingmachine;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NDTM {
    private ArrayList<Tuple> transitionIn;
    private ArrayList<ArrayList<Pair<Tuple, String>>> transitionOut;


    public NDTM(){};

    public boolean process(String state, String in){
        //System.out.println("current state is " + state + " current read is " + in);
        //System.out.println("current head index is " + head);
        //System.out.println("tape: " + tape.toString());
        boolean accept = false;
        boolean running = true;
        while(!accept){
            Tuple read = new Tuple(state, in);
            if(!transitionIn.contains(read)){
                System.out.println("Reject, no such transition");
                break;
            }
            int index = transitionIn.indexOf(read);
            ArrayList<Pair<Tuple,String>> next = transitionOut.get(index);
            Tuple tuple = next.getKey();
            int move = Utils.getDirection(next.getValue());
            //System.out.println("movement is " + next.getValue() + " in no is " + move);
            String nextState = tuple.getState();
            String toWrite = tuple.getAlphabet();
            //System.out.println("next state is " + nextState + " to write is " + toWrite);
            //Set<String> stateSet = new HashSet<String>(Arrays.asList(states));

            // acceptStates state
            if(acceptStates.contains(nextState)){//stateSet.contains(currentState+"+")){
                System.out.println("acceptStates, reached acceptStates state");
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
            in = tape.get(head);
        }
        return accept;
    }
}
