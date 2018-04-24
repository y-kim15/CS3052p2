package turingmachine;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NDTMReader extends Reader {
    private HashMap<Tuple, Node> tree = new HashMap<>();
    private NDTM ntm;
    private int maxChild;

    public NDTMReader(){
        maxChild = 0;
        ntm = new NDTM();
    }

    public NDTMReader(String filePath){
        super(filePath);
    }


    public void readFile() throws Exception{
        try{
        File file = new File(getFilePath());
        Scanner sc = new Scanner(file);
        //System.out.println("read");
        sc.useDelimiter("\\n");
        int i = 0, states = -1, size = -1, nChild = 0;
        Pattern p;
        Matcher m;
        String currentState = "", symbol= "";
        while(sc.hasNext()){
            String read = sc.next();
            //System.out.println("read: " + read);
            String[] line = read.split("\\s");
            //System.out.println("i: " + i);
            if (i == 0){
                p = Pattern.compile("states\\s[1-9][0-9]*");
                m = p.matcher(read);
                if(m.find()){
                    states = Integer.parseInt(line[1]);
                    state = new String[states];
                    //System.out.println("number of states: " + states);
                }
                //if (line.length != STATE) throw new IndexOutOfBoundsException();
                else{
                    setError(0);
                    throw new NumberFormatException(); // create custom ill-format file exception
                }
            }
            else if(i > 0 && i <= states){
                p = Pattern.compile("s[0-9]+\\s\\+?");
                m = p.matcher(read);
                if(m.find()){

                    if (line.length == STATE){
                        //state[i-1] = line[0]+line[1];
                        //System.out.println("Accept state: " + line[0]);
                        accept.add(line[0]);
                    }

                    state[i-1] = line[0];

                    //System.out.println("state: " + state[i-1]);
                }
                else{
                    setError(1);
                    throw new NumberFormatException();
                }
            }
            else if(i == states + 1){
                p = Pattern.compile("alphabet\\s[1-9]+(\\s([a-z]+|[_#*]|[0-9]+|[A-Z]+))+");
                m = p.matcher(read);
                if(m.find()){
                    size = Integer.parseInt(line[1]);
                    if(!new HashSet<String>(Arrays.asList(line)).contains("_")){
                        //System.out.println("it doesn't have blank character!");
                        size++;
                    }
                    alph = new String[size];
                    //System.out.println("Alphabet");
                    for(int j=0; j<size; j++){
                        if(j == size-1 && line.length-2<size){
                            alph[j] = "_";
                        }
                        else{
                            alph[j] = line[2+j];
                        }

                        //System.out.println(alph[j]);
                    }
                }
                else{
                    setError(2);
                    throw new NumberFormatException();
                }
            }
            else{
                p = Pattern.compile("((s[0-9]+)\\s([a-z]+|[_#*]|[0-9]+|[A-Z]+)\\s){2}[RLS]");
                m = p.matcher(read);
                if(m.find()){
                    //System.out.println("trans");
                    String state1 = line[0];
                    String state2 = line[2];
                    String input1 = line[1];
                    String input2 = line[3];
                    String dir = line[4];
                    Set<String> st = new HashSet<String>(Arrays.asList(state));
                    Set<String> al = new HashSet<String>(Arrays.asList(alph));

                    if(!st.contains(state1) || !st.contains(state2) || !al.contains(input1) || !al.contains(input2)
                            ||!getDirections().contains(dir)){
                        setError(3);
                        throw new NumberFormatException();
                    }
                    if(i == states + 2){
                        currentState = state1;
                        symbol = input1;
                    }
                    else{
                        if(state1.equals(currentState) && input1.equals(symbol)) nChild++;
                        else {
                            if(nChild > maxChild) maxChild = nChild;
                            nChild = 0;
                        }
                    }
                    addTransition(state1, state2, input1, input2, dir);


                }
                else{
                    //System.out.println("NOT FOUND!");
                    setError(3);
                    throw new NumberFormatException();
                }
            }

            //System.out.println(Arrays.deepToString(line));
            i++;
        }

    }
        catch (FileNotFoundException e){
            System.out.println("turingmachine.DTM description file is not found");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid format of file");
            Utils.printErrorMessage(getError());
        }

    }

    public void addTransition(String state1, String state2, String input1, String input2, String dir){
        Tuple t = new Tuple(state1, input1);
        Node child = new Node(state2, input2);
        child.setDir(dir);
        if(tree.containsKey(t)){
            Node parent = tree.get(t);
            child.setParent(parent);
            parent.addChildNote(child);
            tree.put(t, child);
        }
        else{
            Node n = new Node(state1, input1);
            child.setParent(n);
            n.addChildNote(child);
            tree.put(t, n);
        }
    }

    public void buildTM(){
       ntm.addStates(state);
       ntm.addTapeAlph(alph);
       ntm.addAccepts(accept);
       ntm.setMaxChild(maxChild);
       ntm.setTree(tree);

    }

}