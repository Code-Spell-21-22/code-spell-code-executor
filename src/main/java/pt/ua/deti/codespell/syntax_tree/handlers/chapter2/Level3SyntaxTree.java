package pt.ua.deti.codespell.syntax_tree.handlers.chapter2;

import com.sun.source.tree.Tree;
import pt.ua.deti.codespell.syntax_tree.handlers.AbstractLevelSyntaxTree;
import pt.ua.deti.codespell.syntax_tree.handlers.LevelSyntaxTreeHandler;
import pt.ua.deti.codespell.syntax_tree.scanners.IfsScanner;
import pt.ua.deti.codespell.syntax_tree.scanners.VariableScanner;
import pt.ua.deti.codespell.syntax_tree.scanners.utils.Variable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Level3SyntaxTree extends AbstractLevelSyntaxTree {

    public Level3SyntaxTree() {
        super(LevelSyntaxTreeHandler.getLevelClassFile(2,3), 3);
    }

    @Override
    public boolean isStepValid(int step) {

        switch (step) {
            case 1:
                return testStep1();
            case 2:
                return testStep2();
            case 3:
                return testStep3();
            default:
                return false;
        }

    }

    @Override
    public List<String> getStepArgs(int step) {
        switch (step) {
            case 1:
                return getStep1Args();
            case 2:
                return getStep2Args();
            case 3:
                return getStep3Args();
            default:
                return new ArrayList<>();
        }

    }

    private boolean testStep1() {

        VariableScanner variableScanner = LevelSyntaxTreeHandler.scanFile(fileToAnalyze, new VariableScanner())[0];
        IfsScanner ifsScanner = LevelSyntaxTreeHandler.scanFile(fileToAnalyze, new IfsScanner())[0];

        List<Boolean> conditions = new ArrayList<>();
        List<Variable> expectedVariables = new ArrayList<>();

        expectedVariables.add(new Variable("day", "boolean", new HashSet<>(), "false"));
        expectedVariables.add(new Variable("stars", "int", new HashSet<>(), null));

        boolean containsDayVariable = variableScanner.getVariables().stream()
                .anyMatch(variable -> variable.getName().equalsIgnoreCase(expectedVariables.get(0).getName()) && variable.getKind().equals(expectedVariables.get(0).getKind()));

        conditions.add(containsDayVariable);

        if (!containsDayVariable) {
            stepTips.add("The code is missing the following variable: 'boolean day;'");
            return false;
        }

        boolean containsStarsVariable = variableScanner.getVariables().stream()
                .anyMatch(variable -> variable.getName().equalsIgnoreCase(expectedVariables.get(1).getName()) && variable.getKind().equals(expectedVariables.get(1).getKind()));

        conditions.add(containsStarsVariable);

        if (!containsStarsVariable) {
            stepTips.add("The code is missing the following variable: 'int stars;'");
            return false;
        }

        boolean containsIfInstruction = !ifsScanner.getIfStatements().isEmpty();

        conditions.add(containsIfInstruction);

        if (!containsIfInstruction) {
            stepTips.add("The code is missing an 'if' structure.");
            return false;
        }

        boolean ifStructContainsDay = ifsScanner.getIfStatements()
                .stream()
                .anyMatch(ifStatement -> ifStatement.getConditionExpression() != null && ifStatement.getConditionExpression().toString().toLowerCase().contains(expectedVariables.get(0).getName()));

        conditions.add(ifStructContainsDay);

        if (!ifStructContainsDay) {
            stepTips.add("The condition in 'if' must contain the 'day' variable.");
            return false;
        }

        boolean ifStructContainsComparator = ifsScanner.getIfStatements()
                .stream()
                .anyMatch(ifStatement -> ifStatement.getConditionKind() != null && (ifStatement.getConditionKind() == Tree.Kind.EQUAL_TO ||
                        ifStatement.getConditionKind() == Tree.Kind.NOT_EQUAL_TO || ifStatement.getConditionKind() == Tree.Kind.IDENTIFIER));

        conditions.add(ifStructContainsComparator);

        if (!ifStructContainsComparator) {
            stepTips.add("The condition in 'if' must be from kind ==, != or none.");
            return false;
        }

        List<String> output = new ArrayList<>();
        File outputFile = new File(File.separator + "output.txt");

        try {
            output = Files.readAllLines(outputFile.toPath());
        } catch (IOException ignored) {
            System.out.println("Error reading output lines");
        }

        conditions.add(!output.isEmpty());

        if (output.isEmpty()) {
            stepTips.add("You must call the provided support methods.");
            return false;
        }

        conditions.add(output.contains("[System Output] Night"));

        if (!output.contains("[System Output] Night")) {
            stepTips.add("The value in the variable 'day' may not be correct.");
            return false;
        }

        score += conditions.stream().filter(aBoolean -> aBoolean).count() * 10;
        return true;

    }

    private boolean testStep2() {

        if (!testStep1()) return false;

        IfsScanner ifsScanner = LevelSyntaxTreeHandler.scanFile(fileToAnalyze, new IfsScanner())[0];

        List<Boolean> conditions = new ArrayList<>();

        boolean containsAtLeastTwoIfInstruction = ifsScanner.getIfStatements().size() >= 2;

        conditions.add(containsAtLeastTwoIfInstruction);

        if (!containsAtLeastTwoIfInstruction) {
            stepTips.add("The code is missing a second 'if' structure.");
            return false;
        }

        boolean ifStructContainsStars = ifsScanner.getIfStatements()
                .stream()
                .anyMatch(ifStatement -> ifStatement.getConditionExpression() != null && ifStatement.getConditionExpression().toString().toLowerCase().contains("stars"));

        conditions.add(ifStructContainsStars);

        if (!ifStructContainsStars) {
            stepTips.add("The condition in one of the 'if' must contain the 'stars' variable.");
            return false;
        }

        boolean ifStructContainsComparator = ifsScanner.getIfStatements()
                .stream()
                .anyMatch(ifStatement -> ifStatement.getConditionKind() != null && (ifStatement.getConditionKind() == Tree.Kind.GREATER_THAN_EQUAL ||
                        ifStatement.getConditionKind() == Tree.Kind.GREATER_THAN || ifStatement.getConditionKind() == Tree.Kind.LESS_THAN || ifStatement.getConditionKind() == Tree.Kind.LESS_THAN_EQUAL));

        conditions.add(ifStructContainsComparator);

        if (!ifStructContainsComparator) {
            stepTips.add("The condition in 'if' must be from kind >, <, >=, <=.");
            return false;
        }

        boolean ifStructContains40 = ifsScanner.getIfStatements()
                .stream()
                .anyMatch(ifStatement -> ifStatement.getConditionExpression() != null && ifStatement.getConditionExpression().toString().toLowerCase().contains("40"));

        conditions.add(ifStructContains40);

        if (!ifStructContains40) {
            stepTips.add("The condition in 'if' must be comparing something with '40'.");
            return false;
        }

        List<String> output = new ArrayList<>();
        File outputFile = new File(File.separator + "output.txt");

        try {
            output = Files.readAllLines(outputFile.toPath());
        } catch (IOException ignored) {
            System.out.println("Error reading output lines");
        }

        conditions.add(!output.isEmpty());

        if (output.isEmpty()) {
            stepTips.add("You must do some prints and not remove the support methods.");
            return false;
        }

        boolean containsStarsValueOutput = output.stream().anyMatch(s -> s.contains("[System Output] Stars Value ::"));

        if (containsStarsValueOutput) {

            List<String> finalOutput = output;

            OptionalInt starsValueIdx = IntStream.range(0, output.size())
                    .filter(i -> finalOutput.get(i).contains("[System Output] Stars Value ::"))
                    .findFirst();

            if (starsValueIdx.isEmpty()) {
                stepTips.add("Please do not remove or change the support methods.");
                return false;
            }

            String[] starsOutputSplit = output.get(starsValueIdx.getAsInt()).split("::");

            if (starsOutputSplit.length != 2 || !output.get(starsValueIdx.getAsInt()).split("::")[1].matches("\\d+")) {
                stepTips.add("Please do not change the support methods.");
                return false;
            }

            int starsValue = Integer.parseInt(output.get(starsValueIdx.getAsInt()).split("::")[1]);

            if (starsValue != 50) {
                if (starsValue > 40 ) {
                    if (output.stream().noneMatch("What a starry sky!"::equalsIgnoreCase)) {
                        stepTips.add("Please perform the mandatory prints.");
                        return false;
                    }
                } else {
                    if (output.stream().noneMatch("What a beautiful sky!"::equalsIgnoreCase)) {
                        stepTips.add("Please perform the mandatory prints.");
                        return false;
                    }
                }
            }

        } else {
            stepTips.add("Please do not remove or change the support methods.");
            return false;
        }

        score += conditions.stream().filter(aBoolean -> aBoolean).count() * 10;
        return true;

    }

    private boolean testStep3() {

        if (!testStep1() || !testStep2()) return false;

        IfsScanner ifsScanner = LevelSyntaxTreeHandler.scanFile(fileToAnalyze, new IfsScanner())[0];

        List<Boolean> conditions = new ArrayList<>();

        boolean containsAtLeastThreeIfInstruction = ifsScanner.getIfStatements().size() >= 3;

        conditions.add(containsAtLeastThreeIfInstruction);

        if (!containsAtLeastThreeIfInstruction) {
            stepTips.add("The code is missing a third 'if' structure.");
            return false;
        }

        List<String> output = new ArrayList<>();
        File outputFile = new File(File.separator + "output.txt");

        try {
            output = Files.readAllLines(outputFile.toPath());
        } catch (IOException ignored) {
            System.out.println("Error reading output lines");
        }

        conditions.add(!output.isEmpty());

        if (output.isEmpty()) {
            stepTips.add("You must do some prints and not remove the support methods.");
            return false;
        }

        boolean containsStarsValueOutput = output.stream().anyMatch(s -> s.contains("[System Output] Stars Value ::"));

        if (containsStarsValueOutput) {

            List<String> finalOutput = output;

            OptionalInt starsValueIdx = IntStream.range(0, output.size())
                    .filter(i -> finalOutput.get(i).contains("[System Output] Stars Value ::"))
                    .findFirst();

            if (starsValueIdx.isEmpty()) {
                stepTips.add("Please do not remove or change the support methods.");
                return false;
            }

            String[] starsOutputSplit = output.get(starsValueIdx.getAsInt()).split("::");

            if (starsOutputSplit.length != 2 || !output.get(starsValueIdx.getAsInt()).split("::")[1].matches("\\d+")) {
                stepTips.add("Please do not change the support methods.");
                return false;
            }

            int starsValue = Integer.parseInt(output.get(starsValueIdx.getAsInt()).split("::")[1]);

            if (starsValue == 50) {
                if (output.stream().noneMatch("So many stars!"::equalsIgnoreCase)) {
                    stepTips.add("Your conditions are wrong, or you are not printing the mandatory information.");
                    return false;
                }
            }else if (starsValue > 40) {
                if (output.stream().noneMatch("What a starry sky!"::equalsIgnoreCase)) {
                    stepTips.add("Your conditions are wrong, or you are not printing the mandatory information.");
                    return false;
                }
            } else {
                if (output.stream().noneMatch("What a beautiful sky!"::equalsIgnoreCase)) {
                    stepTips.add("Your conditions are wrong, or you are not printing the mandatory information.");
                    return false;
                }
            }

        } else {
            stepTips.add("Please do not remove or change the support methods.");
            return false;
        }

        score += conditions.stream().filter(aBoolean -> aBoolean).count() * 10;
        return true;

    }

    private List<String> getStep1Args() {

        List<String> output = new ArrayList<>();
        File outputFile = new File(File.separator + "output.txt");

        try {
            output = Files.readAllLines(outputFile.toPath());
        } catch (IOException ignored) {
            System.out.println("Error reading output lines");
        }

        if (output.contains("[System Output] Night")) {
            return Collections.singletonList("false");
        } else {
            return Collections.singletonList("true");
        }

    }

    private List<String> getStep2Args() {

        List<String> output = new ArrayList<>();
        File outputFile = new File(File.separator + "output.txt");

        try {
            output = Files.readAllLines(outputFile.toPath());
        } catch (IOException ignored) {
            System.out.println("Error reading output lines");
        }

        List<String> finalOutput = output;
        OptionalInt starsValueIdx = IntStream.range(0, output.size())
                .filter(i -> finalOutput.get(i).contains("[System Output] Stars Value ::"))
                .findFirst();

        int starsValue;

        if (starsValueIdx.isPresent())
            starsValue = Integer.parseInt(output.get(starsValueIdx.getAsInt()).split("::")[1]);
        else
            starsValue = 0;

        List<String> args = output.stream().filter(s -> !s.contains("[System Output]")).collect(Collectors.toList());
        args.add(String.valueOf(starsValue));

        return args;

    }

    private List<String> getStep3Args() {

        List<String> output = new ArrayList<>();
        File outputFile = new File(File.separator + "output.txt");

        try {
            output = Files.readAllLines(outputFile.toPath());
        } catch (IOException ignored) {
            System.out.println("Error reading output lines");
        }

        return output.isEmpty() ? new ArrayList<>() : output.stream().filter(s -> !s.contains("[System Output]")).collect(Collectors.toList());

    }

}
