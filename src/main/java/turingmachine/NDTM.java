package turingmachine;

import java.util.*;

/**
 * This is an extended class of TM for Non-deterministic Turing
 * Machine simulator.
 */
public class NDTM extends TM{
    private List<String> tape;
    private List<String> addressTape;
    private int addressHead;
    private Node<String> root;
    private Node<String> current;

    private HashMap<Tuple, Node<String>> tree = new HashMap<>();

    public NDTM(){
        head = 0;
        addressHead = 0;
        currentSize = FIRSTSIZE;
    }


    public void setTree(HashMap<Tuple, Node<String>> tree) {
        this.tree = tree;
    }

    /**
     * Reads input according from the defined state and symbol.
     * Use transition table to find next, builds tree from node as it progresses.
     * Keeps track of ith child node it is at at every level using addressTape
     * Uses backtracking algorithm by returning to the calling function and starts from the previous step
     * again and try the next child. If it reaches the end where all nodes of the root is tested but
     * not accepting it will reject.
     * @param state current state
     * @param symbol current symbol
     * @return
     */
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
                if(!getTapeAlph().contains(symbol)){
                    System.out.println("it doesn't contain symbol");
                    addressHead = 0;
                    addressTape.set(addressHead, Integer.toString(current.getChildren().size()));
                    setMoves(getMoves()-1);
                    return false;
                }
                System.out.println("can't find the key");
                current.currentChild = 0;
                if(addressHead-1 > 0) {
                    head -= Utils.getDirection(current.getDir());
                    current = current.getParent();
                    //addressTape.set(--addressHead, "_");
                    tape.set(head, current.getReadSymbol());
                    currentState = current.getReadState();
                    setMoves(getMoves()-1);
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
                setMoves(getMoves()+1);
                int move = Utils.getDirection(current.getDir());
                System.out.println("move is " + move);
                System.out.println("next state is " + currentState + " and write to head " + toWrite);
                if (head == 0 && move == -1) move = 0;
                head += move;

                if (head+1 == tape.size()){//tape.get(head) == null) {
                    for (int i = 0; i < FIRSTSIZE; i++) {
                        //tape.set(head + i, "_");
                        tape.add("_");
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

    /**
     * Reads input string, ready to be processed by loading to the tape.
     * Calls test method until it reaches the end of rejecting or accepting conclusion.
     * Checks rejection to terminate
     * @param input string to read
     * @return boolean denoting whether to accept or reject
     */
    public boolean read(String input){
        System.out.println("Start reading inside ndtm and input is " + input);
        boolean accept = false;
        //String[] nums = getNumberArray(maxChild);
        //System.out.println(Arrays.deepToString(nums));
        //getPossibleStrings(input.length(), nums, "");
        addressHead = 0;
        currentSize = FIRSTSIZE + input.length();
        tape = new ArrayList<>(Collections.nCopies(currentSize, "_"));
        addressTape = new ArrayList<>(Collections.nCopies(currentSize, "_"));
        //for(int i=0; i<num; i++){
        for(int j=0; j<input.length(); j++){
            tape.set(j, Character.toString(input.charAt(j)));
        }
        currentState = getStates()[0];
        while(!accept){
            accept = test(currentState, tape.get(head));
            if(!accept) {
                System.out.println("!accept");
                //if(addressHead < 0 ) return false; // case when completely invalid alphabet encountered
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
