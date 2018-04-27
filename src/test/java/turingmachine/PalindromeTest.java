package turingmachine;


import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is a parametrised test class for running tm simulator tests
 */
@RunWith(Parameterized.class)
public class PalindromeTest {
    private static String TMDescriptionFile = "testData/t1.txt";
    private static String inputFile = "./t1_in.txt";
    private static String outputFile = "outputs/t1_output.csv";
    private static FileWriter writer;
    private static List<String> inputBuffer = new ArrayList<String>();
    private static long totalTime = 0;
    private static int totalMove = 0;
    private static int count = 0;
    private static int repeat = 20;
    private static int min = 100;
    private static int max = 1000;
    private static int step = 100;
    private static boolean form = true;
    @Parameterized.Parameters()
    public static Iterable<Object[]> data()throws IOException {
        String problem = System.getProperty("problem");
        String errors = System.getProperty("errors");
        int type = 1;
        boolean test = true;
        if(problem != null) {
            type = Integer.parseInt(problem);
            TMDescriptionFile = "testData/t" + problem + ".txt";
            inputFile = "./t" + problem + "_in.txt";
            outputFile = "outputs/t" + problem + "_output.csv";

        }
        if(errors != null){
            if(errors.equals("f")) test = false;
            if(problem != null) outputFile = "outputs/t" + problem + "_errors_output.csv";
            else outputFile = "outputs/t1_errors_output.csv";
        }
        String det = System.getProperty("type");
        if(det != null){
            if(det.equals("nd")){
                if(!test) outputFile = "outputs/tm4_700_errors_output.csv";
                else outputFile = "outputs/tm4_700_output.csv";
                form = false;
                TMDescriptionFile = "testData/tm4.txt";
                inputFile = "./tm4_in.txt";
                type = 1;
                min = 100;
                max = 700;
                step = 100;
            }
        }
        if(problem == null && errors == null && det == null){
            type = 1;
            min = 100;
            max = 700;
            step = 100;
            outputFile = "outputs/tm1_700_output.csv";
        }
        return Utils.getParamsByConditions(type, test, min,max,repeat, step);
    }

    private String input;
    private boolean correct;

    public PalindromeTest(String input, boolean correct){
        this.input = input;
        this.correct = correct;
    }

    @BeforeClass
    public static void prepare() throws IOException
    {
        try {
            Files.deleteIfExists(Paths.get(outputFile));
            writer = new FileWriter(outputFile);
            Utils.writeCSVLine(writer, Arrays.asList("Length","Time", "Moves"));
        }
        catch(IOException e){
            e.getMessage();
            System.out.println(e.getStackTrace());
        }

    }

    @Before
    public void createInputFile() throws IOException
    {
        try{
            try (PrintWriter out = new PrintWriter(inputFile)) {
                out.println(input);
            }
        }
        catch(IOException e){
            e.getMessage();
            System.out.println(e.getStackTrace());
        }
    }

    @Test
    public void test() throws Exception {
        long startTime = System.nanoTime();
        boolean result;
        if(form) result = runD.runTm(TMDescriptionFile, inputFile);
        else result = runD.runNtm(TMDescriptionFile, inputFile);
        long endTime   = System.nanoTime();
        long time = (endTime - startTime)/100000;
        totalTime += time;
        totalMove += runD.getMove();
        count++;
        assertEquals(correct, result);
    }

    @After
    public void writeToCSV() throws IOException {
        if(count==repeat){
            inputBuffer.add(Integer.toString(min));
            long average = totalTime/repeat;
            inputBuffer.add(Long.toString(average));
            totalTime =0;
            long avMove = totalMove/repeat;
            inputBuffer.add(Long.toString(avMove));
            totalMove = 0;
            Utils.writeCSVLine(writer,inputBuffer);
            inputBuffer.clear();
            count =0;
            min += step;
        }
        File file = new File(inputFile);

        if(file.delete())
        {
            //System.out.println("File deleted successfully");
        }
        else
        {
            //System.out.println("Failed to delete the file");
        }
    }


    @AfterClass
    public static void tidy()throws IOException{
        try{
            writer.close();
        }
        catch (IOException e){
            e.getMessage();
            System.out.println(e.getStackTrace());
        }
    }


}
