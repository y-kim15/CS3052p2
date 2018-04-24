package turingmachine;

import java.util.*;

public class NDTM extends TM{
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
            array[i] = Integer.toString(i);
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
        while(!accept){
            Tuple read = new Tuple(state, symbol);
            if(!tree.containsKey(read)){
                System.out.println("Reject, no such transition");
                break;
            }
            Node current = tree.get(read);
            int childOrder = Integer.parseInt(addressTape.get(addressHead++));
            List<Node> children = current.getChildren();
            if(children.size() < childOrder) break;
            Node child = children.get(childOrder);
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
        }
        return accept;
    }

    public boolean read(String input){
        String[] nums = getNumberArray(input.length());
        getPossibleStrings(input.length(), nums, "");
        addressHead = 0;
        for(int i=0; i<num; i++){
            currentState = getStates()[0];
            for(int j=0; j<input.length(); j++){
                tape.set(j, Character.toString(input.charAt(j)));
            }
            if(process(currentState, tape.get(head))) return true;
            tape.clear();
            currentSize = FIRSTSIZE + input.length();
            tape.addAll(Collections.nCopies(currentSize, "_"));
            while(!addressTape.get(addressHead).equals("#")){
                addressHead++;
            }
            addressHead++;
        }
        return false;

    }
}
