package pt.ua.deti.codespell.chapters.chapter_2;

import java.io.*;

public class Level_3 {

    private final File outputFile;
    private final File runtimeLogsFile;

    public Level_3(File[] outputFiles) {
        this.outputFile = outputFiles[0];
        this.runtimeLogsFile = outputFiles[1];
    }

    public void execute() {

        PrintStream outputPrintStream;
        PrintStream runtimeLogsStream;

        try {
            outputPrintStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
            runtimeLogsStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(runtimeLogsFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        PrintStream originalStdout = System.out;

        System.setOut(outputPrintStream);

        try {
            /* DO NOT CHANGE LINE BELOW */
            MyClass.main(new String[] {});
        } catch (Exception e) {
            runtimeLogsStream.println(e.getMessage());
        }

        System.setOut(originalStdout);

        outputPrintStream.flush();
        runtimeLogsStream.flush();

        outputPrintStream.close();
        runtimeLogsStream.close();

    }

}

// INJECT CODE HERE
