package pt.ua.deti.codespell.utils;

import lombok.Data;

import java.util.List;

@Data
public class StepGroup {
    private final List<Step> stepsList;
    private final List<String> tips;
}
