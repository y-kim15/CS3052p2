package turingmachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class runtm {
    public static boolean runTm(String TMDescription, String input) throws Exception {
        TMReader r = new TMReader();
        String path=TMDescription;
        boolean result = false;
        try{
            r = new TMReader(path);
            r.readFile();
            TM tm = r.getTM();
            result = tm.read(input);
            return result;

        }
        catch (FileNotFoundException e){
            System.out.println("turingmachine.TM description file is not found");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid format of file");
            r.printErrorMessage();
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("printing args...");
        System.out.println(Arrays.deepToString(args));
        String path = "";
        String input = "";
        if(args.length == 0){
            System.out.println("No Arguments");
            //path = "testData/t4.txt";
            //input = "t4_in.txt";
        }
        else if(args.length > 2){
            System.out.println("Too many arguments!");
        }
        else{
            path = args[0];
            input = args[1];
        }
        File file = new File(input);
        Scanner sc = new Scanner(file);
        //System.out.println("read");
        //sc.useDelimiter("\\n");
        boolean result = false;
        while(sc.hasNext()){
            String read = sc.next();
            result = runTm(path, read);
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
