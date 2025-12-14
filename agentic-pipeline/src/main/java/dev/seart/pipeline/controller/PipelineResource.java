package dev.seart.pipeline.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import dev.seart.pipeline.ai.PlannerAi;
import dev.seart.pipeline.ai.WriterAi;
import dev.seart.pipeline.ai.CriticAi;
import dev.seart.pipeline.model.PlanModels.*;

@Path("/pipeline")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PipelineResource {

    @Inject PlannerAi planner;
    @Inject WriterAi writer;
    @Inject CriticAi critic;

    public record PipelineResponse(Plan plan, String draft, Critique critique) {}

    @POST
    public PipelineResponse run(PipelineRequest req) {
        Plan plan = planner.plan(req);
        String draft = writer.draft(req, plan);
        Critique critique = critic.review(draft);
        return new PipelineResponse(plan, draft, critique);
    }
}
