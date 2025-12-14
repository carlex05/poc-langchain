package dev.seart.pipeline.ai;

import jakarta.enterprise.context.ApplicationScoped;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import dev.seart.pipeline.model.PlanModels.Critique;

@RegisterAiService
@ApplicationScoped
@SystemMessage("""
You are a strict reviewer agent.
You must find issues and improve the draft.
Return structured output including the finalAnswer.
""")
public interface CriticAi {

    @UserMessage("""
Review this draft and improve it.

Draft:
{draft}

Return:
- issues (max 7)
- improvements (max 7)
- finalAnswer (the improved version)

Do NOT include extra keys beyond the schema.
""")
    Critique review(String draft);
}
