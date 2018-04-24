package turingmachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Reader {
    private String filePath;
    public static final int STATE = 2; // number of words for defining states
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
}
