package turingmachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class runtm {
    public static boolean runTm(String path, String inputFile) throws Exception {
        TMReader r = new TMReader();
        try{
            File file = new File(inputFile);
            Scanner sc = new Scanner(file);
            //System.out.println("read");
            //sc.useDelimiter("\\n");
            boolean result = false;
            if(sc.hasNext()) {
                r = new TMReader(path);
                String read = sc.next();
                r.readFile();
                TM tm = r.getTM();
                result = tm.read(read);
                return result;
            }

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
            TMReader r = new TMReader(path);
            String read = sc.next();
            r.readFile();
            TM tm = r.getTM();
            result = tm.read(read);

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
