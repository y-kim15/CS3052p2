package turingmachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class runD {
    public static final String dtm = "d";
    public static final String ntm = "nd";
    public static int move = 0;

    public static boolean runTm(String path, String inputFile) throws Exception {
        DTMReader r = new DTMReader();
        try{
            File file = new File(inputFile);
            Scanner sc = new Scanner(file);
            //System.out.println("read");
            //sc.useDelimiter("\\n");
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
            System.out.println("turingmachine.DTM description file is not found");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid format of file");
            Utils.printErrorMessage(r.getError());
        }
        return false;
    }

    public static boolean runNtm(String path, String inputFile) throws Exception {
        NDTMReader r = new NDTMReader();
        try{
            File file = new File(inputFile);
            Scanner sc = new Scanner(file);
            //System.out.println("read");
            //sc.useDelimiter("\\n");
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
            System.out.println("turingmachine.NDTM description file is not found");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid format of file");
            Utils.printErrorMessage(r.getError());
        }
        return false;
    }

    public static int getMove(){return move;}

    public static void main(String[] args) throws Exception {
        System.out.println("runD main printing args...");
        System.out.println(Arrays.deepToString(args));
        String path = "";
        String input = "";
        String type = "";
        if(args.length == 0){
            System.out.println("No Arguments");
            //path = "testData/t4.txt";
            //input = "t4_in.txt";
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
        System.out.println("read");
        //sc.useDelimiter("\\n");
        boolean result = false;
        while(sc.hasNext()){
            if(type.equals(dtm)){
                System.out.println("we are in d zone");
                DTMReader r = new DTMReader(path);
                String read = sc.next();
                r.readFile();
                r.buildTM();
                DTM dtm = r.getTM();
                result = dtm.read(read);
                move = dtm.getMoves();
            }
            else{
                System.out.println("we are in nd zone");
                NDTMReader r = new NDTMReader(path);
                String read = sc.next();
                r.readFile();
                System.out.println("print states");
                System.out.println(Arrays.deepToString(r.state));
                r.buildTM();
                NDTM dtm = r.getNtm();
                result = dtm.read(read);
                move = dtm.getMoves();
            }


        }
        //path = "testData/t4.txt";
        //input = "aaabbbbbbbccccccccccccccccccccc";

        if(result){
            System.out.println("PASS");
        }
        else{
            System.out.println("FAIL");
        }
    }
}
