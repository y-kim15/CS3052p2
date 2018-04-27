package turingmachine;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is an extended class of Reader for reading Non-deterministic turing machine
 * description file reader. It builds the machine with the read information.
 */
public class NDTMReader extends Reader {
    private HashMap<Tuple, Node<String>> tree = new HashMap<>();
    private NDTM ntm;
    private Node<String> root;

    public NDTMReader(){
        ntm = new NDTM();
    }

    public NDTMReader(String filePath){
        super(filePath);
        ntm = new NDTM();
    }


    public void readFile() throws Exception{
        try {
            File file = new File(getFilePath());
            Scanner sc = new Scanner(file);
            sc.useDelimiter("\\n");
            int i = 0, states = -1;
            Pattern p;
            Matcher m;
            while (sc.hasNext()) {
                String read = sc.next();
                String[] line = read.split("\\s");
                if (i == 0) {
                    states = readStateNumber(read, line, states);
                } else if (i > 0 && i <= states) {
                    readStates(read, line, i);
                } else if (i == states + 1) {
                    readAlphabet(read, line);
                } else {
                    p = Pattern.compile("((s[0-9]+)\\s([a-z]+|[_#*]|[0-9]+|[A-Z]+)\\s){2}[RLS]");
                    m = p.matcher(read);
                    if (m.find()) {
                        String state1 = line[0];
                        String state2 = line[2];
                        String input1 = line[1];
                        String input2 = line[3];
                        String dir = line[4];
                        Set<String> st = new HashSet<String>(Arrays.asList(state));
                        Set<String> al = new HashSet<String>(Arrays.asList(alph));

                        if (!st.contains(state1) || !st.contains(state2) || !al.contains(input1) || !al.contains(input2)
                                || !getDirections().contains(dir)) {
                            setError(3);
                            throw new NumberFormatException();
                        }
                        addTransition(state1, state2, input1, input2, dir);


                    } else {
                        setError(3);
                        throw new NumberFormatException();
                    }
                }
                i++;
            }

        }
        catch (FileNotFoundException e){
            System.out.println("TM description file is not found");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid format of file");
            Utils.printErrorMessage(getError());
        }

    }

    /**
     * Add transition to the transition table represented as a tree
     * Every node will have its current state and current input recorded
     * If there exists more than one transition for the given pair, the node
     * representing that combination will have multiple child nodes.
     * @param state1 current state
     * @param state2 next state
     * @param input1 current input
     * @param input2 input to write
     * @param dir direction to move the head
     */
    public void addTransition(String state1, String state2, String input1, String input2, String dir){
        Tuple t = new Tuple(state1, input1);
        Node<String> child = new Node<String>(state2, input2);
        child.setDir(dir);
        if(tree.containsKey(t)){
            Node<String> parent = tree.get(t);
            child.setParent(parent);
            parent.addChild(child);
            tree.put(t, parent);
        }
        else{
            Node<String> n = new Node<String>(state1, input1);
            child.setParent(n);
            n.addChild(child);
            tree.put(t, n);
        }

    }

    public void buildTM(){
       ntm.addStates(state);
       ntm.addTapeAlph(alph);
       ntm.addAccepts(accept);
       ntm.setTree(tree);

    }

    public NDTM getNtm(){return ntm; }

}
