package turingmachine;


import org.junit.*;
import org.junit.internal.JUnitSystem;
import org.junit.internal.RealSystem;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
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

@RunWith(Parameterized.class)
public class PalindromeTest {
    private static String TMDescriptionFile = "testData/t2.txt";
    private static String inputFile = "./t2_in.txt";
    private static String outputFile = "./t2_output.txt";
    private static FileWriter writer;
    private static List<String> inputBuffer = new ArrayList<String>();
    private static long totalTime = 0;
    private static int count = 0;
    private static int repeat = 1;
    private static int min = 3;
    private static int max = 5;
    private static int step = 1;
    @Parameterized.Parameters()
    public static Iterable<Object[]> data()throws IOException {

        return Utils.getParamsByConditions(2, true, min,max,repeat, step);
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
            Utils.writeCSVLine(writer, Arrays.asList("Length","Time"));
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
    public void test() throws ClassNotFoundException, Exception {
        System.out.println("--------------------- Test Started ------------------------------");

        String[] args = {TMDescriptionFile, inputFile};
        System.out.println("input is " + input);
        long startTime = System.nanoTime();
        boolean result = runtm.runTm(TMDescriptionFile, inputFile);
        long endTime   = System.nanoTime();
        long time = (endTime - startTime)/100000;
        totalTime += time;
        count++;
        if(result) System.out.println("PASS");
        else System.out.println("FAIL");
        assertEquals(result, correct);



    }

    @After
    public void writeToCSV() throws IOException {
        if(count==repeat){
            inputBuffer.add(Integer.toString(min));
            long average = totalTime/repeat;
            inputBuffer.add(Long.toString(average));
            totalTime =0;
            Utils.writeCSVLine(writer,inputBuffer);
            inputBuffer.clear();
            count =0;
            min += repeat;
        }
        File file = new File(inputFile);

        if(file.delete())
        {
            System.out.println("File deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the file");
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
