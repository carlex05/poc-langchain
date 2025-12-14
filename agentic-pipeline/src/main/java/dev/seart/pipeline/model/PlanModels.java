package dev.seart.pipeline.model;

import java.util.List;

public interface PlanModels {

    public record PipelineRequest(String goal, 
        String audience, 
        String constraints) {}

    public record Plan(String intent,
        List<String> assumptions,
        List<Step> steps,
        List<String> risks) {}

    public record Step(String title,
        String rationale,
        List<String> inputs,
        List<String> outputs) {}

    public record Critique(List<String> issues,
        List<String> improvements,
        String finalAnswer) {}

}