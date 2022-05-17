package pt.ua.deti.codespell.chapters.chapter_1;

import java.io.*;

public class Level_1 {

    public void execute() throws IOException {

        File outputFile = new File(File.separator + "output.txt");
        PrintStream outputPrintStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)));

        System.setOut(outputPrintStream);

        try {
            HelloWorld.main(new String[] {});
        } catch (Exception e) {
            System.out.println("Error");
        }

    }

}

// INJECT CODE HERE
