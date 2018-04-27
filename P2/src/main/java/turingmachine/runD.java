package turingmachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class runD {
    public static final String dtm = "d";
    public static final String ntm = "nd";
    public static int move = 0;

    /**
     * runs deterministic turning machine
     * @param path tm description file path
     * @param inputFile txt file containing the input string
     * @return boolean denoting accept or reject
     * @throws Exception file error
     */
    public static boolean runTm(String path, String inputFile) throws Exception {
        DTMReader r = new DTMReader();
        try{
            File file = new File(inputFile);
            Scanner sc = new Scanner(file);

            boolean result = false;
            if(sc.hasNext()) {
                r.setFilePath(path);
                String read = sc.next();
                r.readFile();
                r.buildTM();
                DTM dtm = r.getTM();
                result = dtm.read(read);
                move = dtm.getMoves();
                return result;
            }

        }
        catch (FileNotFoundException e){
            System.out.println("tDTM description file is not found");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid format of file");
            Utils.printErrorMessage(r.getError());
        }
        return false;
    }

    /**
     * runs non-deterministic Turing machine
     * @param path tm description file path
     * @param inputFile txt file containing the input string
     * @return boolean denoting accept or reject
     * @throws Exception file error
     */
    public static boolean runNtm(String path, String inputFile) throws Exception {
        NDTMReader r = new NDTMReader();
        try{
            File file = new File(inputFile);
            Scanner sc = new Scanner(file);

            boolean result = false;
            if(sc.hasNext()) {
                r.setFilePath(path);
                String read = sc.next();
                r.readFile();
                r.buildTM();
                NDTM dtm = r.getNtm();
                result = dtm.read(read);
                move = dtm.getMoves();
                return result;
            }

        }
        catch (FileNotFoundException e){
            System.out.println("NDTM description file is not found");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid format of file");
            Utils.printErrorMessage(r.getError());
        }
        return false;
    }

    public static int getMove(){return move;}

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.deepToString(args));
        String path = "";
        String input = "";
        String type = "";
        if(args.length == 0){
            System.out.println("No Arguments");
        }
        else if(args.length > 3 || args.length < 2){
            System.out.println("Invalid number of arguments!");
        }
        else{
            path = args[0];
            input = args[1];
            type = args[2];

        }
        File file = new File(input);
        Scanner sc = new Scanner(file);

        boolean result = false;
        while(sc.hasNext()){
            if(type.equals(dtm)){
                DTMReader r = new DTMReader(path);
                String read = sc.next();
                r.readFile();
                r.buildTM();
                DTM dtm = r.getTM();
                result = dtm.read(read);
                move = dtm.getMoves();
            }
            else{
                NDTMReader r = new NDTMReader(path);
                String read = sc.next();
                r.readFile();
                System.out.println(Arrays.deepToString(r.state));
                r.buildTM();
                NDTM dtm = r.getNtm();
                result = dtm.read(read);
                move = dtm.getMoves();
            }


        }
        if(result){
            System.out.println("PASS");
        }
        else{
            System.out.println("FAIL");
        }
    }
}
