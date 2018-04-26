package turingmachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DTMReader extends Reader {
    private DTM dtm = new DTM();

    public DTMReader(){}

    public DTMReader(String filePath){
        super(filePath);
    }

    public DTM getTM(){
        return dtm;
    }

    /**
     * Accepts TM description file path to read details
     * Each separate calls to other functions as necessary
     * Any invalid format of the input description file will be
     * detected by thrown exceptions and appropriate error messages.
     * @throws Exception
     */
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

                        // some sanity checks, also check that every transition is unique
                        if (!st.contains(state1) || !st.contains(state2) || !al.contains(input1) || !al.contains(input2)
                                || !getDirections().contains(dir) || dtm.checkTransition(state1, input1)) {
                            System.out.println("error here");
                            setError(3);
                            throw new NumberFormatException();
                        }
                        dtm.addTransition(line);
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
     * Builds Turing machine by assigning its fields
     */
    public void buildTM(){
        dtm.addStates(state);
        dtm.addTapeAlph(alph);
        dtm.addAccepts(accept);
    }
}
