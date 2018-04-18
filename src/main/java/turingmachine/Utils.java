package turingmachine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static String getPalindrome(int length, int seed, boolean type){
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
            int errorType = r.nextInt(3);
            int i = r.nextInt(p.length());
            StringBuilder sb = new StringBuilder(p);
            switch (errorType){
                case 0: sb = sb.deleteCharAt(i);
                        p = sb.toString();
                        break;
                case 1: sb = sb.replace(i, i+1, "_");
                        p = sb.toString();
                        break;
                case 2: List<Integer> list = IntStream.rangeClosed(0, p.length()-1)
                            .boxed().collect(Collectors.toList());
                        Collections.shuffle(list);
                        String shuffled = "";
                        StringBuilder sb3 = new StringBuilder(shuffled);
                        for(int j=0; j < p.length(); j++){
                            sb3.append(p.charAt(list.get(j)));
                        }
                        p = sb3.toString();
                        break;
            }

        }
        return p;
    }

    public static String getBinary(int length, int seed){
        Random r = new Random(seed);
        String b = "";
        StringBuilder sb = new StringBuilder(b);
        int len = r.nextInt(length-1)+1;
        for(int i=0; i<len; i++){
            sb.append(Integer.toString(r.nextInt(2)));
        }
        return sb.toString();
    }

    // length denote length of one binary word (at most with min 1)
    public static String generateBinaryForm(boolean correct, int length) {
        Random r = new Random(r2.nextInt());
        String w1 = getBinary(length, r.nextInt());
        String w2 = getBinary(length, r.nextInt());
        int n1 = Integer.parseInt(w1, 2);
        int n2 = Integer.parseInt(w2, 2);
        int sum = n1+n2;
        if(!correct) {

            // 0: w1+1, 1: w2-1, 2: w3+1, 3: #
            int errorType = r.nextInt(3);
            switch (errorType) {
                case 0: n1++;
                        sum = n1+n2;
                        break;
                case 1: n2--;
                        sum = n1+n2;
                        break;
                case 2: sum++;
                        break;
            }
        }
        String w3 = Integer.toString(sum);
        String p = "";
        if(correct) {
            p = Integer.toString(n1) + "#" + Integer.toString(n2) + "#" + w3;
        }
        else{
            int errorType = r.nextInt(6);
            switch (errorType) {
                case 0:  p = Integer.toString(n1) + Integer.toString(n2) + "#" + w3;
                    break;
                case 1:  p = Integer.toString(n1) + "#" + Integer.toString(n2) + w3;
                    break;
                case 2:  p = Integer.toString(n1) + "#" + "#" + w3;
                    break;
                case 3: p = Integer.toString(n1) + "#" + Integer.toString(n2) + "#" ;
                    break;
                case 4:  p = "#" + Integer.toString(n2) + "#" + w3;
                    break;
                case 5:  p = Integer.toString(n1) + "#" + Integer.toString(n2) + "#" + w3;
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
            }

        }
        return p;
    }
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
        String concat = getEqualBinary(length);
        Random r = new Random(r3.nextInt());
        if(!correct){
            int errorType = r.nextInt(6);
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
            }
        }
        return p;

    }

    public static List<Object[]> getParamsByConditions(int type, boolean correct, int min, int max, int nEach, int step){
        List<Object[]> list = new ArrayList<>();
        for(int i=min; i<=max; i+=step){
            // how many matrices of equal length we will have
            for(int o=0; o<nEach; o++){
                Object[] ob = new Object[2];
                switch (type){
                    case 0: if(correct) ob[0]= generatePalindrome(correct, i);

                            else {
                                boolean which = r5.nextBoolean();
                                ob[0] = generatePalindrome(which, i);
                                ob[1] = which;
                            }
                            break;
                    case 1: if(correct) ob[0] = generateBinaryForm(correct, i);
                             else {
                                boolean which = r5.nextBoolean();
                                ob[0] = generateBinaryForm(which, i);
                                ob[1] = which;
                            }
                            break;
                    case 2: if(correct) ob[0] = generateEqualBinary(correct, i);
                            else {
                                boolean which = r5.nextBoolean();
                                ob[0] = generateEqualBinary(which, i);
                                ob[1] = which;
                            }
                            break;
                    case 3: if(correct) ob[0] = generateAlphabetWord(correct, i);
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

}
