package turingmachine;

import java.util.*;

public class NDTM extends TM{
    private String input;
    private List<String> tape;
    private List<String> addressTape;
    private int addressHead;
    private int maxChild; // maximum number of child nodes of a node
    private int num; // number of node combination to check

    private HashMap<Tuple, Node> tree = new HashMap<>();

    public NDTM(){
        head = 0;
        addressHead = 0;
        currentSize = FIRSTSIZE;
        tape = new ArrayList<>(Collections.nCopies(currentSize, "_"));
        addressTape = new ArrayList<>();

    }


    public void setMaxChild(int maxChild){this.maxChild = maxChild;}

    public void setTree(HashMap<Tuple, Node> tree) {
        this.tree = tree;
    }

    public String[] getNumberArray(int n){
        String[] array = new String[n];
        for(int i=0; i < n; i++){
            array[i] = Integer.toString(i+1);
        }
        return array;
    }

    //method to initialise address tape with combinations, delimited by #
    //write every string combination
    public void writeToAddress(String curr){
        for(int i=0; i<curr.length(); i++){
            addressTape.add(addressHead++, Character.toString(curr.charAt(i)));
        }
        addressTape.add(addressHead++, "#");
        num++;
    }

    // https://codereview.stackexchange.com/questions/41510/calculate-all-possible-combinations-of-given-characters
    public void getPossibleStrings(int maxLength, String[] alphabet, String curr) {

        // If the current string has reached it's maximum length
        if(curr.length() == maxLength) {
            System.out.println(curr);
            writeToAddress(curr);

            // Else add each letter from the alphabet to new strings and process these new strings again
        } else {
            for(int i = 0; i < alphabet.length; i++) {
                String oldCurr = curr;
                curr += alphabet[i];
                getPossibleStrings(maxLength,alphabet,curr);
                curr = oldCurr;
            }
        }
    }

    public boolean process(String state, String symbol){
        boolean accept = false;
        int count = 0;
        while(!accept){
            System.out.println("state " + state +  " and symbol " + symbol);
            Tuple read = new Tuple(state, symbol);
            if(!tree.containsKey(read)){
                System.out.println("Reject, no such transition");
                break;
            }
            Node current = tree.get(read);
            if(count == input.length()){
                System.out.println("went over number of transitions possible");
                break;
            }
            int childOrder = Integer.parseInt(addressTape.get(addressHead++));
            List<Node> children = current.getChildren();
            System.out.println("current has " + children.size() + "children and order is " + childOrder);
            if(children.size() < childOrder){
                System.out.println("fewer number of children, invalid");
                break;}
            Node child = children.get(childOrder-1);
            String nextState = child.getState();
            String toWrite = child.getSymbol();
            int move = Utils.getDirection(child.getDir());
            if(getAcceptStates().contains(nextState)){
                System.out.println("accept");
                accept = true;
                break;
            }

            currentState = nextState;
            tape.set(head, toWrite);
            System.out.println("next state is " + currentState + " and write to head " + toWrite);
            if(head == 0 && move == -1) move = 0;
            head += move;

            if(tape.get(head) == null){
                for(int i=0; i<FIRSTSIZE; i++){
                    tape.set(head+i, "_");
                }
                currentSize += FIRSTSIZE;
            }
            state = currentState;
            symbol = tape.get(head);
            count++;
        }
        return accept;
    }

    public boolean read(String input){
        this.input = input;
        System.out.println("Start reading inside ndtm");
        String[] nums = getNumberArray(maxChild);
        System.out.println(Arrays.deepToString(nums));
        getPossibleStrings(input.length(), nums, "");
        addressHead = 0;
        System.out.println("there are " + num + " combinations");
        for(int i=0; i<num; i++){
            System.out.println("i is " + i + "-----------");
            currentState = getStates()[0];
            for(int j=0; j<input.length(); j++){
                tape.set(j, Character.toString(input.charAt(j)));
            }
            if(process(currentState, tape.get(head))) return true;
            tape.clear();
            currentSize = FIRSTSIZE + input.length();
            tape.addAll(0, Collections.nCopies(currentSize, "_"));
            while(!addressTape.get(addressHead).equals("#")){
                addressHead++;
            }
            addressHead++;
            head = 0;
        }
        return false;

    }
}
