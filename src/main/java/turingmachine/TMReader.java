package turingmachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TMReader {
    private String filePath;
    private static final int STATE = 2; // number of words for defining states
    private static final int TRANS = 5; // number of words for transition
    private int error = -1; //0: first line, 1: state listing line, 2: alphabet line, 3: transition listing line
    private String[] state;
    private String[] alph;
    private Set<String> directions = new HashSet<String>(Arrays.asList(new String[]{"L", "R", "S"}));
    private TM tm = new TM();
    private ArrayList<String> accept = new ArrayList<String>();

    public TMReader(){}

    public TMReader(String filePath){
        this.filePath = filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
    /*
    * Method : read*/
    public void readFile() throws Exception{
        //try{
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            //System.out.println("read");
            sc.useDelimiter("\\n");
            int i = 0, states = -1, size = -1; // set start line index
            Pattern p;
            Matcher m;

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
                        error = 0;
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
                        error = 1;
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
                        error = 2;
                        throw new NumberFormatException();
                    }
                }
                else{
                    if(i == states + 2){
                        buildTM();
                    }
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
                                ||!directions.contains(dir)){
                            error = 3;
                            throw new NumberFormatException();
                        }
                        //System.out.println(Arrays.deepToString(line));
                        tm.addTransition(line);
                    }
                    else{
                        //System.out.println("NOT FOUND!");
                        error = 3;
                        throw new NumberFormatException();
                    }
                }

                //System.out.println(Arrays.deepToString(line));
                i++;
            }

        }
        /*catch (FileNotFoundException e){
            System.out.println("turingmachine.TM description file is not found");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid format of file");
            printErrorMessage();
        }

    }*/

    public void printErrorMessage(){
        switch (error){
            case 0:
                System.out.println("States + Number line error");
                break;
            case 1:
                System.out.println("State listing line error");
                break;
            case 2:
                System.out.println("Alphabet definition line error");
                break;
            case 3:
                System.out.println("Transition listing line error");
                break;
            default:
                System.out.println("Error in turingmachine.TM description file");
                break;
        }
    }
    /*
    * Method : build turingmachine.TM*/
    public void buildTM(){
        tm.addStates(state);
        tm.addTapeAlph(alph);
        tm.addAccepts(accept);
        //System.out.println(Arrays.deepToString(state));
        //System.out.println(Arrays.deepToString(alph));
        //turingmachine.TM(int[] accept, String[] states, String[] tapeAlph
    }

    public TM getTM(){
        return tm;
    }
}
