package turingmachine;

import java.util.*;
import java.util.stream.Collectors;

public class NDTM extends TM{
    private String input;
    private List<String> tape;
    private List<String> addressTape;
    private int addressHead;
    private int maxChild; // maximum number of child nodes of a node
    private int num; // number of node combination to check
    private Node<String> root;
    private Node<String> current;
    private int order;

    private HashMap<Tuple, Node<String>> tree = new HashMap<>();

    public NDTM(){
        head = 0;
        addressHead = 0;
        currentSize = FIRSTSIZE;
        tape = new ArrayList<>(Collections.nCopies(currentSize, "_"));
        addressTape = new ArrayList<>(Collections.nCopies(currentSize, "_"));

    }


    public void setMaxChild(int maxChild){this.maxChild = maxChild;}

    public void setTree(HashMap<Tuple, Node<String>> tree) {
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
   /* public void writeToAddress(String curr){
        for(int i=0; i<curr.length(); i++){
            addressTape.add(addressHead++, Character.toString(curr.charAt(i)));
        }
        addressTape.add(addressHead++, "#");
        num++;
    }*/

    // https://codereview.stackexchange.com/questions/41510/calculate-all-possible-combinations-of-given-characters
    public void getPossibleStrings(int maxLength, String[] alphabet, String curr) {

        // If the current string has reached it's maximum length
        if(curr.length() == maxLength) {
            System.out.println(curr);
           // writeToAddress(curr);

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
            Node1 current = new Node1("","");//= tree.get(read);
            if(count == input.length()){
                System.out.println("went over number of transitions possible");
                break;
            }
            int childOrder = Integer.parseInt(addressTape.get(addressHead++));
            List<Node1> children = current.getChildren();
            System.out.println("current has " + children.size() + "children and order is " + childOrder);
            if(children.size() < childOrder){
                System.out.println("fewer number of children, invalid");
                break;}
            Node1 child = children.get(childOrder-1);
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
    // create a tree from root, given input, check if transition exist, if so add a node
    // level is depth of tree (which level) and order is (ith child?)
    public boolean test(String state, String symbol){
        while(true) {
            System.out.println("read state " + state + " and symbol " + symbol);
            Tuple read = new Tuple(state, symbol);
            if (root == null) {
                System.out.println("root is null");
                root = new Node<>(state, symbol);
                current = root;
                addressTape.set(addressHead++, "1");
                //addressTape.add(addressHead++, "#");
            }
            if (!tree.containsKey(read)) {
                System.out.println("can't find the key");
                current.currentChild = 0;
                if(addressHead-1 > 0) {
                    head -= Utils.getDirection(current.getDir());
                    current = current.getParent();
                    //addressTape.set(--addressHead, "_");
                    tape.set(head, current.getReadSymbol());
                    currentState = current.getReadState();
                }
                addressHead--; //keep the value
                //moving up to parent
                return false;
            } else {
                Node<String> node = tree.get(read);
                Node<String> newN = new Node<>(node.getReadState(), node.getReadSymbol());
                newN.setChildren(node.getChildren());
                newN.setParent(current);
                System.out.println("Node found with str " + node.getReadState() + " symbol " + node.getReadSymbol());
                List<Node<String>> children = node.getChildren();
                Node<String> child = new Node<>(null,null);
                String s = addressTape.get(addressHead);
                if(s.equals("_")){
                    addressTape.set(addressHead++, Integer.toString(1));
                    child = newN.getChildren().get(0);
                    current = child;
                    //new
                }
                else{
                    int value = Integer.parseInt(addressTape.get(addressHead));
                    if (value >= children.size()) {
                        System.out.println("no more children to test on");
                        //current = current.getParent();//newN.getParent();//node.getParent();
                        //addressTape.set(addressHead, "_");
                        System.out.println("current has " + current.getReadState() + " and symbol " + current.getReadSymbol());
                        addressHead--;
                        return false;
                    } else if (value >= 0 && value < children.size()) {
                        System.out.println("there exists somewhere to move!");
                        addressTape.set(addressHead++, Integer.toString(value+1));
                        child = newN.getChildren().get(value);
                        current = child;
                    }
                }
                String nextState = current.getReadState();
                String toWrite = current.getReadSymbol();
                if (getAcceptStates().contains(nextState)) {
                    System.out.println("accept");
                    return true;
                }
                currentState = nextState;
                tape.set(head, toWrite);
                int move = Utils.getDirection(current.getDir());
                System.out.println("move is " + move);
                System.out.println("next state is " + currentState + " and write to head " + toWrite);
                if (head == 0 && move == -1) move = 0;
                head += move;

                if (tape.get(head) == null) {
                    for (int i = 0; i < FIRSTSIZE; i++) {
                        tape.set(head + i, "_");
                    }
                    currentSize += FIRSTSIZE;
                }
                System.out.println("size is " + addressTape.size() + " and head is at " + addressHead);
                if(addressHead == addressTape.size()){
                    for (int i = 0; i < FIRSTSIZE; i++) {
                        addressTape.add("_");
                    }
                }
                state = currentState;
                symbol = tape.get(head);
                System.out.println("current state is " + state + " " + currentState + " and symbol is " + symbol + " " +
                    tape.get(head));


            }

        }
    }
    public boolean read(String input){
        this.input = input;
        System.out.println("Start reading inside ndtm and input is " + input);
        boolean accept = false;
        //String[] nums = getNumberArray(maxChild);
        //System.out.println(Arrays.deepToString(nums));
        //getPossibleStrings(input.length(), nums, "");
        addressHead = 0;
        //for(int i=0; i<num; i++){
        for(int j=0; j<input.length(); j++){
            tape.set(j, Character.toString(input.charAt(j)));
        }
        currentState = getStates()[0];
        while(!accept){
            accept = test(currentState, tape.get(head));
            if(!accept) {
                System.out.println("!accept");
                if(addressHead < 0 ) return false; // case when completely invalid alphabet encountered
                String i = addressTape.get(addressHead);
                System.out.println("addressHead is at index " + addressHead);
                System.out.println("size of children is " + current.getChildren().size() + " and  i is "+ i);
                if(current.getChildren().size() == Integer.parseInt(i) && addressHead == 0){
                    return false;
                }

            }
            else return true;

            //if(process(currentState, tape.get(head))) return true;
            //tape.clear();
            //currentSize = FIRSTSIZE + input.length();
            //tape.addAll(0, Collections.nCopies(currentSize, "_"));
            /*while(!addressTape.get(addressHead).equals("#")){
                addressHead++;
            }
            addressHead++;*/
            //head = 0;
        }
        return false;

    }
}
