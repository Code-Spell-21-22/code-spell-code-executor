package pt.ua.deti.codespell.chapters.chapter_1;

import java.io.*;

public class Level_1 {

    public void execute() throws IOException {

        File outputFile = new File(File.separator + "output.txt");
        PrintStream outputPrintStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
        PrintStream originalStdout = System.out;

        System.setOut(outputPrintStream);

        try {
            HelloWorldApp.main(new String[] {});
        } catch (Exception e) {
            System.out.println("Error");
        }

        System.setOut(originalStdout);

        outputPrintStream.close();

    }


}

// INJECT CODE HERE
