package pt.ua.deti.codespell.syntax_tree.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import pt.ua.deti.codespell.utils.Step;
import pt.ua.deti.codespell.utils.StepGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLevelSyntaxTree {

    protected File fileToAnalyze;
    protected int stepsCount;
    @Getter protected List<String> stepTips;

    @Getter protected int score = 0;
    
    public AbstractLevelSyntaxTree(File fileToAnalyze, int stepsCount) {
        this.fileToAnalyze = fileToAnalyze;
        this.stepsCount = stepsCount;
        this.stepTips = new ArrayList<>();
    }

    public List<Step> generateStepsReport() {

        List<Step> stepList = new ArrayList<>();

        for (int stepIdx = 1; stepIdx <= stepsCount; stepIdx++)
            stepList.add(new Step(stepIdx, isStepValid(stepIdx), getStepArgs(stepIdx)));
        return stepList;

    }

    public void writeStepsReport(List<Step> stepList) {

        File resultsFile = new File(File.separator + "stepsReport.txt");
        StepGroup stepGroup = new StepGroup(stepList, stepTips);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(resultsFile, stepGroup);
        } catch (IOException e) {
            System.out.println("Error writing steps report");
        }

    }

    public abstract boolean isStepValid(int step);

    public abstract List<String> getStepArgs(int step);

}
