package dev.seart.pipeline.ai;

import jakarta.enterprise.context.ApplicationScoped;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import dev.seart.pipeline.model.PlanModels.Plan;
import dev.seart.pipeline.model.PlanModels.PipelineRequest;

@RegisterAiService
@ApplicationScoped
@SystemMessage("""
You are a delivery-focused writer agent.
Follow the plan strictly. Produce an actionable answer.
""")
public interface WriterAi {

    @UserMessage("""
Goal: {req.goal}
Audience: {req.audience}
Constraints: {req.constraints}

Plan (structured):
{plan}

Write the draft answer.
Rules:
- Use headings and bullet points.
- Include implementation details and example outputs.
- If something is unknown, state the assumption explicitly (do not hallucinate).
""")
    String draft(PipelineRequest req, Plan plan);
}
