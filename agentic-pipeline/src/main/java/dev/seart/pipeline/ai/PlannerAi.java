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
You are a senior solutions architect agent.
Your job: create an execution plan as structured JSON.
Be concise, concrete, and avoid vague steps. No markdown. No extra text.
""")
public interface PlannerAi {

    @UserMessage("""
Goal: {req.goal}
Audience: {req.audience}
Constraints: {req.constraints}

Return a Plan with:
- intent (1 sentence)
- assumptions (max 5)
- steps (3-7 steps), each with: title, rationale, inputs, outputs
- risks (max 5)

Do NOT include extra keys beyond the schema.
""")
    Plan plan(PipelineRequest req);
}
