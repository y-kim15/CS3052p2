package turingmachine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class containing some utility functions
 */
public class Utils {
    public static final int RANDOM_SEED_1 = 0;
    public static final int RANDOM_SEED_2 = 1;
    public static final int RANDOM_SEED_3 = 2;
    public static final int RANDOM_SEED_4 = 3;
    public static final int RANDOM_SEED_5 = 4;
    public static Random r1 = new Random(RANDOM_SEED_1);
    public static Random r2 = new Random(RANDOM_SEED_2);
    public static Random r3 = new Random(RANDOM_SEED_3);
    public static Random r4 = new Random(RANDOM_SEED_4);
    public static Random r5 = new Random(RANDOM_SEED_5);

    /**
     * Creates palindrome
     * @param length of given length
     * @param seed seed to get reproducible result
     * @param type to get a mix of even and odd length
     * @return palindrome formed
     */
    public static String getPalindrome(int length, int seed, boolean type){
        length = length/2;
        Random r = new Random(seed);
        String front = "";
        String reverse = "";
        StringBuilder sb = new StringBuilder(front);
        StringBuilder sb2 = new StringBuilder(reverse);
        for(int i=0; i<length; i++){
            sb.append(Integer.toString(r.nextInt(3)));
        }
        front = sb.toString();
        for(int i=1; i<=length; i++){
            sb2.append(Character.toString(front.charAt(length-i)));
        }
        reverse = sb2.toString();
        // type == false, we want odd length palindrome
        if(!type){
            String mid = Integer.toString(r.nextInt(3));
            reverse = mid + reverse;
        }
        return front+reverse;
    }
    // length denote length of front string in palindrome
    public static String generatePalindrome(boolean correct, int length){
        Random r = new Random(r1.nextInt());
        String p = getPalindrome(length, r.nextInt(), r.nextBoolean());
        if(!correct){
            int errorType = r.nextInt(1);
            int i = r.nextInt(p.length());
            StringBuilder sb = new StringBuilder(p);
            switch(errorType){
                case 0: p = sb.toString();
                        int j = r.nextInt(p.length());
                        String str = Character.toString(p.charAt(j));
                        if(str.equals("0")){
                            if(r.nextBoolean()) sb.setCharAt(j, '1');
                            else sb.setCharAt(j, '2');
                        }
                        else if(str.equals("1")){
                            if(r.nextBoolean()) sb.setCharAt(j, '0');
                            else sb.setCharAt(j, '2');
                        }
                        else{
                            if(r.nextBoolean()) sb.setCharAt(j, '0');
                            else sb.setCharAt(j, '1');
                        }
                        p = sb.toString();
                        break;
                case 1: int random = r.nextInt(7)+3;
                        sb.append(Integer.toString(random));
                        p = sb.toString();
                        break;
            }


        }
        return p;
    }

    /**
     * Generates binary string consisting of random placement of 0 and 1s
     * @param length total length of the string as a whole so this will be divided by 3;
     * @param seed
     * @return string made
     */
    public static String getBinary(int length, int seed){
        length /= 3;
        Random r = new Random(seed);
        String b = "";
        StringBuilder sb = new StringBuilder(b);
        int len = r.nextInt(length-1)+1;
        for(int i=0; i<len; i++){
            sb.append(Integer.toString(r.nextInt(2)));
        }
        return sb.toString();
    }

    /**
     * Conducts binary addition
     * @param s1
     * @param s2
     * @return returns added sum
     */
    public static String sum(String s1, String s2){
        int len;
        if(s1.length() >= s2.length()) len = s1.length();
        else len = s2.length();
        String sum = "";
        StringBuilder sb = new StringBuilder(sum);
        boolean carry = false;
        for(int i=0; i<len; i++){
            int n1; int n2;
            if(s1.length() <= i){
                n1 = 0;
            }
            else n1 = Integer.parseInt(Character.toString(s1.charAt(i)));
            if(s2.length() <= i){
                n2 = 0;
            }
            else n2 = Integer.parseInt(Character.toString(s2.charAt(i)));
            int plus = n1 + n2;

            if(carry){
                plus++;
                carry = false;
            }
            if(plus == 3){
                plus = 1;
                carry = true;
            }
            else if(plus == 2){
                plus = 0;
                carry = true;
            }
            sb.append(Integer.toString(plus));

        }
        if(carry) sb.append(Integer.toString(1));
        //System.out.println("made sum is " + sb.toString());
        return sb.toString();
    }



    // length denote length of one binary word (at most with min 1)

    /**
     * Generates input strings for binary addition problem
     * @param correct boolean denoting whether to generate valid or invalid string for testing
     * @param length total length of input string
     * @return input string created
     */
    public static String generateBinaryForm(boolean correct, int length) {
        Random r = new Random(r2.nextInt());
        String w1 = getBinary(length, r.nextInt());
        String w2 = getBinary(length, r.nextInt());

        String p = "";
        String  w3 = sum(w1, w2);

        if(correct) {
            p = w1 + "#" + w2 + "#" + w3;
        }
        else{
            int errorType = r.nextInt(7);
            switch(errorType){
                case 0: p = w1 + w2 + "#" + w3;
                    break;
                case 1: p = w1 + "#" + w2 + w3;
                    break;
                case 2: p = w1 + "##" + w3;
                    break;
                case 3: p = w1 + "#" + w2 + "#";
                    break;
                case 4: p = "#" + w2 + "#" + w3;
                    break;
                case 5: p = w1 + "#" + w2 + "#" + w3;
                        List<Integer> list = IntStream.rangeClosed(0, p.length()-1)
                                .boxed().collect(Collectors.toList());
                    Collections.shuffle(list);
                    String shuffled = "";
                    StringBuilder sb3 = new StringBuilder(shuffled);
                    for(int i=0; i < p.length(); i++){
                        sb3.append(p.charAt(list.get(i)));
                    }
                    p = sb3.toString();
                    break; // complete shuffle
                case 6: p = w1 + "#" + w2 + "#" + w3;
                        StringBuilder sb4 = new StringBuilder(p);
                        int random = r.nextInt(7)+3;
                        sb4.append(Integer.toString(random));
                        p = sb4.toString();
                        break;
            }
        }
        return p;
    }

    /**
     * Generates string consisting of 0s and 1s for the problem 3
     * @param length the total length of string
     * @return string built
     */
    public static String getEqualBinary(int length){
        String zeros = "";
        StringBuilder sb = new StringBuilder(zeros);
        String ones = "";
        StringBuilder sb2 = new StringBuilder(ones);
        for(int i=0; i<length; i++){
            sb.append("0");
            sb2.append("1");
        }
        return sb.toString() + sb2.toString();
    }
    public static String generateEqualBinary(boolean correct, int length) {
        length /= 2;
        String concat = getEqualBinary(length);
        Random r = new Random(r3.nextInt());
        if(!correct){
            int errorType = r.nextInt(7);
            switch (errorType){
                case 0: concat = concat.substring(1,2*length); //-1 from 0
                        break;
                case 1: concat = concat.substring(0, 2*length-1); // -1 from 1
                        break;
                case 2: concat = concat.substring(0,length); //no 1s
                        break;
                case 3: concat = concat.substring(length, 2*length); //no 0s
                        break;
                case 4: int rmv = r.nextInt(length-1)+1;
                        concat = concat.substring(rmv, 2*length); // -rmv of 0s from string
                        break;
                case 5: rmv = r.nextInt(length-1)+1;
                        concat = concat.substring(length+rmv, 2*length); // -rmv of 1s from string
                        break;
                case 6: int random = r.nextInt(7)+3;
                        concat += Integer.toString(random);
                        break;
            }
        }
        boolean shuffle = r.nextBoolean();
        if(shuffle){
            // generates random sequence of indices to place values
            List<Integer> list = IntStream.rangeClosed(0, concat.length()-1)
                    .boxed().collect(Collectors.toList());
            Collections.shuffle(list);
            String shuffled = "";
            StringBuilder sb3 = new StringBuilder(shuffled);
            for(int i=0; i < concat.length(); i++){
                sb3.append(concat.charAt(list.get(i)));
            }
            concat = sb3.toString();
        }
        return concat;
    }

    /**
     * Generates string consisting of a,b,c to be tested on the problem 4
     * @param length total length of string
     * @param i number of as
     * @param j number of bs
     * @param seed
     * @return string built
     */
    public static String getAlphabetWord(int length, int i, int j, int seed){
        Random r = new Random(seed);
        int k = i*j;
        String s = "";
        StringBuilder sb = new StringBuilder(s);
        for(int a=0; a<i; a++){
            sb.append("a");
        }
        for(int b=0; b<j; b++){
            sb.append("b");
        }
        for(int c=0; c<k; c++){
            sb.append("c");
        }
        return sb.toString();
    }
    public static String generateAlphabetWord(boolean correct, int length){
        length /= 10;
        Random r = new Random(r4.nextInt());
        int i = r.nextInt(length-1)+1;
        int j = r.nextInt(length-1)+1;

        String p = getAlphabetWord(length, i, j, r.nextInt());
        if(!correct){
            int errorType = r.nextInt(7);
            switch (errorType){
                case 0: p = p.substring(1,p.length()); // rmv 1 a
                        break;
                case 1: p = p.substring(0,i) + p.substring(i+1,p.length()); // rmv 1 b
                        break;
                case 2: p = p.substring(0,p.length()-1); //rmv 1 c
                        break;
                case 3: p = p.substring(i,p.length()); // bc only, no as
                        break;
                case 4: p = p.substring(0,i) + p.substring(i+j, p.length()); //ac only, no bs
                        break;
                case 5: p = p.substring(0, i+j); //ab only, no cs
                        break;
                case 6: List<Integer> list = IntStream.rangeClosed(0, p.length()-1)
                            .boxed().collect(Collectors.toList());
                        Collections.shuffle(list);
                        String shuffled = "";
                        StringBuilder sb3 = new StringBuilder(shuffled);
                        for(int n=0; n < p.length(); n++){
                            sb3.append(p.charAt(list.get(n)));
                        }
                        p = sb3.toString();
                        break;
                case 7: int random = r.nextInt();
                        p += Integer.toString(random);
                        break;
            }
        }

        return p;

    }

    /**
     * Generates list of parameter pairs to be used in PalindromeTest test suite
     * @param type which problem tested
     * @param correct whether to generate correct only or mix
     * @param min minimum string length
     * @param max maximum
     * @param nEach the number of strings of equal length to generate
     * @param step
     * @return list consisting of all pairs
     */
    public static List<Object[]> getParamsByConditions(int type, boolean correct, int min, int max, int nEach, int step){
        List<Object[]> list = new ArrayList<>();
        for(int i=min; i<=max; i+=step){
            // how many matrices of equal length we will have
            for(int o=0; o<nEach; o++){
                Object[] ob = new Object[2];
                switch (type){
                    case 1: if(correct) ob[0]= generatePalindrome(correct, i);

                            else {
                                boolean which = r5.nextBoolean();
                                ob[0] = generatePalindrome(which, i);
                                ob[1] = which;
                            }
                            break;
                    case 2: if(correct) ob[0] = generateBinaryForm(correct, i);
                             else {
                                boolean which = r5.nextBoolean();
                                ob[0] = generateBinaryForm(which, i);
                                ob[1] = which;
                            }
                            break;
                    case 3: if(correct) ob[0] = generateEqualBinary(correct, i);
                            else {
                                boolean which = r5.nextBoolean();
                                ob[0] = generateEqualBinary(which, i);
                                ob[1] = which;
                            }
                            break;
                    case 4: if(correct) ob[0] = generateAlphabetWord(correct, i);
                            else {
                                boolean which = r5.nextBoolean();
                                ob[0] = generateAlphabetWord(which, i);
                                ob[1] = which;
                            }
                            break;
                }

                if(correct) ob[1] = correct;
                list.add(ob);
            }
        }
        return list;
    }

    /**
     * writes input line to a file
     * attributed to : https://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
     * @param writer file to write
     * @param inputs strings to write
     * @throws IOException when file is not defined
     */
    public static void writeCSVLine(FileWriter writer, List<String> inputs)throws IOException{
        StringBuilder sb = new StringBuilder();
        sb.append(inputs.get(0));
        for(int i=1; i<inputs.size(); i++){
            sb.append(",");
            sb.append(inputs.get(i));
        }
        sb.append("\n");
        try{
            writer.append(sb.toString());
        }
        catch (IOException e){
            e.getMessage();
            System.out.println("Failed to write input line to CSV");
        }

    }

    public static int getDirection(String dir){
        if(dir.equals("L")){
            return -1;
        }
        else if(dir.equals("R")){
            return 1;
        }
        else{
            return 0;
        }
    }

    public static void printErrorMessage(int error){
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
                System.out.println("Error in turingmachine.DTM description file");
                break;
        }
    }

}
