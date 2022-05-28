package pt.ua.deti.codespell;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import pt.ua.deti.codespell.chapters.chapter_1.Level_1;
import pt.ua.deti.codespell.utils.*;

import pt.ua.deti.codespell.syntax_tree.handlers.chapter1.Level1SyntaxTree;

import java.io.*;
import java.util.List;
import java.util.UUID;

@Log4j2
public class Main {

    private static CodeExecution currentCodeExecution;

    public static void main(String[] args) throws IOException {

         currentCodeExecution = getCurrentCodeExecution();

        if (currentCodeExecution == null) {
            writeResults(new CodeExecutionResult.Builder(null).withExecutionStatus(ExecutionStatus.PRE_CHECK_ERROR).build());
            return;
        }

        try {
            writeResults(remoteCodeExecutor());
        } catch (Exception e) {
            System.out.println("Exception thrown while running remote code: " + e.getMessage());
            writeResults(new CodeExecutionResult.Builder(currentCodeExecution.getCodeUniqueId()).withExecutionStatus(ExecutionStatus.RUNTIME_ERROR).build());
        }

    }

    /**
     * Remote Code Executor method
     *
     * This method will have the responsibility of running the remote code.
     */
    private static CodeExecutionResult remoteCodeExecutor() {

        String chapterNumberVar = System.getenv("CHAPTER_NUMBER");
        String levelNumberVar = System.getenv("LEVEL_NUMBER");

        if (!chapterNumberVar.matches("\\d") || !levelNumberVar.matches("\\d")) {
            return new CodeExecutionResult.Builder(currentCodeExecution.getCodeUniqueId()).withExecutionStatus(ExecutionStatus.PRE_CHECK_ERROR).build();
        }

        int chapterNumber = Integer.parseInt(chapterNumberVar);
        int levelNumber = Integer.parseInt(levelNumberVar);

        if (chapterNumber == 1) {

            if (levelNumber == 1) {

                Level_1 level_1 = new Level_1(initCodeExecOutputFiles());
                level_1.execute();

                Level1SyntaxTree level1SyntaxTree = new Level1SyntaxTree();
                level1SyntaxTree.writeStepsReport(level1SyntaxTree.generateStepsReport());

            }

        }

        return new CodeExecutionResult.Builder(currentCodeExecution.getCodeUniqueId()).withExecutionStatus(ExecutionStatus.SUCCESS).build();

    }

    private static CodeExecution getCurrentCodeExecution() {

        String codeUniqueIdVar = System.getenv("CODE_ID");
        String chapterNumberVar = System.getenv("CHAPTER_NUMBER");
        String levelNumberVar = System.getenv("LEVEL_NUMBER");

        if (!chapterNumberVar.matches("\\d") || !levelNumberVar.matches("\\d")) return null;

        int chapterNumber = Integer.parseInt(chapterNumberVar);
        int levelNumber = Integer.parseInt(levelNumberVar);

        Level codeLevel = new Level(chapterNumber, levelNumber);
        return new CodeExecution(UUID.fromString(codeUniqueIdVar), codeLevel);

    }

    private static void writeResults(CodeExecutionResult codeExecutionResult) throws IOException {

        File resultsFile = new File(File.separator + "results.txt");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resultsFile, codeExecutionResult);

    }

    private static File[] initCodeExecOutputFiles() {

        File outputFile = new File(File.separator + "output.txt");
        File runtimeLogsFile = new File(File.separator + "runtimeLogs.txt");

        try {
            if (!outputFile.exists())
                outputFile.createNewFile();
            if (!runtimeLogsFile.exists())
                runtimeLogsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File[] {runtimeLogsFile, outputFile};

    }

}
