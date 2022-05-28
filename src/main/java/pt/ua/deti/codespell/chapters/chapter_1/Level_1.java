package pt.ua.deti.codespell.chapters.chapter_1;

import java.io.*;

public class Level_1 {

    private final File outputFile;
    private final File runtimeLogsFile;

    public Level_1(File[] outputFiles) {
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
            HelloWorldApp.main(new String[] {});
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
