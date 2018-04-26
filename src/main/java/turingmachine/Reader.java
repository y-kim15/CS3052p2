package turingmachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a super class of Turing machine simulator file reader
 */
public class Reader {
    private String filePath;
    public static final int STATE = 2; // number of words in state definition line for an accept state
    private int error = -1; //0: first line, 1: state listing line, 2: alphabet line, 3: transition listing line
    public String[] state;
    public String[] alph;
    private Set<String> directions = new HashSet<String>(Arrays.asList(new String[]{"L", "R", "S"}));
    public ArrayList<String> accept = new ArrayList<String>();

    public Reader(){}
    public Reader(String filePath){this.filePath = filePath;}

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setState(String[] state) {
        this.state = state;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getError() {
        return error;
    }

    public Set<String> getDirections() {
        return directions;
    }

    public int readStateNumber(String read, String[] line, int states){
        Pattern p = Pattern.compile("states\\s[1-9][0-9]*");
        Matcher m = p.matcher(read);
        if(m.find()){
            states = Integer.parseInt(line[1]);
            state = new String[states];
        }
        else{
            setError(0);
            throw new NumberFormatException();
        }
        return states;
    }

    public void readStates(String read, String[] line, int i){
        Pattern p = Pattern.compile("s[0-9]+(\\s\\+)?");
        Matcher m = p.matcher(read);
        if(m.find()){

            if (line.length == STATE){
                accept.add(line[0]);
            }

            state[i-1] = line[0];
        }
        else{
            setError(1);
            throw new NumberFormatException();
        }
    }

    public void readAlphabet(String read, String[] line){
        int size = 0;
        Pattern p = Pattern.compile("alphabet\\s[1-9]+(\\s([a-z]+|[_#*]|[0-9]+|[A-Z]+))+");
        Matcher m = p.matcher(read);
        if(m.find()){
            size = Integer.parseInt(line[1]);
            if(!new HashSet<String>(Arrays.asList(line)).contains("_")){
                size++;
            }
            alph = new String[size];
            for(int j=0; j<size; j++){
                if(j == size-1 && line.length-2<size){
                    alph[j] = "_";
                }
                else{
                    alph[j] = line[2+j];
                }
            }
        }
        else{
            setError(2);
            throw new NumberFormatException();
        }
    }
}
