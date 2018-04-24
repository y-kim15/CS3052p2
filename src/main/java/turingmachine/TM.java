package turingmachine;

import javafx.util.Pair;

import java.util.*;
public class TM {
    private static final int FIRSTSIZE = 10;
    private List<String> tape; //= new ArrayList<String>(Collections.nCopies(FIRSTSIZE, "_"));
    private int head;
    private String currentState;
    private int currentSize;
    private String[] states;
    private Set<String> tapeAlph;
    private ArrayList<String> acceptStates;
    private HashMap<Tuple,Pair<Tuple,String>> transition = new HashMap<Tuple, Pair<Tuple, String>>();

    public TM(){
        head=0;
    }
    public TM(String[] states, String[] tapeAlph){
        head=0;
        this.states = states;
        this.tapeAlph = new HashSet<String>(Arrays.asList(tapeAlph));
    }

    public void addStates(String[] states){
        this.states = states;
    }

    public void addTapeAlph(String[] tapeAlph){
        this.tapeAlph = new HashSet<String>(Arrays.asList(tapeAlph));
    }

    public void addAccepts(ArrayList<String> accept){
        this.acceptStates = accept;
    }

    public void addTransition(String[] trans){
        Tuple current = new Tuple(trans[0], trans[1]);
        Tuple next = new Tuple(trans[2], trans[3]);
        transition.put(current, new Pair<Tuple, String>(next, trans[4]));
    }


    public boolean move(String state, String in){
        //System.out.println("current state is " + state + " current read is " + in);
        //System.out.println("current head index is " + head);
        //System.out.println("tape: " + tape.toString());
        Tuple read = new Tuple(state, in);
        if(!transition.containsKey(read)){
            //System.out.println("no such transition");
            //System.out.println("reject, invalid transition");
            return false;
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
        if(acceptStates.contains(nextState)){//stateSet.contains(currentState+"+")){
            //System.out.println("acceptStates, reached acceptStates state");
            return true;
        }
        else{
            currentState = nextState;
            //System.out.println("not acceptStates, write " + toWrite + " to head " + head);
            tape.set(head, toWrite);

        }

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
        //System.out.println("head is " + head + " value on head is " + tape.get(head));
        return move(currentState, tape.get(head));
    }

    public boolean process(String state, String in){
        //System.out.println("current state is " + state + " current read is " + in);
        //System.out.println("current head index is " + head);
        //System.out.println("tape: " + tape.toString());
        boolean accept = false;
        boolean running = true;
        while(!accept){
            Tuple read = new Tuple(state, in);
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


    public boolean read(String input){
        currentState = states[0];
        currentSize = FIRSTSIZE + input.length();
        tape = new ArrayList<String>(Collections.nCopies(currentSize, "_"));
        for(int i=0; i<input.length(); i++){
            tape.set(i,Character.toString(input.charAt(i)));
        }
        return process(currentState, tape.get(head)); //read
    }
}
