package turingmachine;

import org.junit.internal.JUnitSystem;
import org.junit.internal.RealSystem;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class Runruntm {
    public static void callRuntm (String TMDescriptionFile, String inputFile) {
        System.setProperty("TMDescription", TMDescriptionFile);
        System.setProperty("Input", inputFile);

        JUnitCore junit = new JUnitCore();
        JUnitSystem system = new RealSystem();
        junit.addListener(new TextListener(system));
        junit.run(runD.class);


    }
}
