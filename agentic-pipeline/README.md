# Agentic Pipeline (Quarkus + LangChain4j)

Proof of concept for the pipeline pattern applied to LLM agents. A REST endpoint orchestrates three specialized agents (planner → writer → critic) to deliver a structured answer with explicit feedback.

## Prerequisites
- JDK 21+
- Maven (wrapper `./mvnw` included)
- Ollama running locally at `http://localhost:11434` with the model in `src/main/resources/application.properties` (`qwen3:1.7b` by default)

## How it works (architecture)
- API: `POST /pipeline` accepts a `goal`, `audience`, and `constraints`.
- Agents:
  - `PlannerAi`: builds a structured JSON plan (intent, assumptions, steps, risks).
  - `WriterAi`: executes the plan to produce a draft answer with headings/bullets.
  - `CriticAi`: reviews the draft, returns issues, improvements, and `finalAnswer`.
- Pipeline: `PipelineResource` composes the agents in sequence and returns `{ plan, draft, critique }`, demonstrating a linear agentic pipeline.

## Running locally
```bash
./mvnw quarkus:dev
```
Quarkus Dev UI is available at `http://localhost:8080/q/dev` (dev mode only).

## Example request
```bash
curl -X POST http://localhost:8080/pipeline \
  -H "Content-Type: application/json" \
  -d '{
    "goal": "Draft a rollout plan for adding feature flags",
    "audience": "platform engineers",
    "constraints": "use existing CI/CD, rollout within 2 weeks"
  }'
```
Example shape of the response:
```json
{
  "plan": { "intent": "...", "assumptions": [], "steps": [], "risks": [] },
  "draft": "raw draft from writer",
  "critique": {
    "issues": ["..."],
    "improvements": ["..."],
    "finalAnswer": "improved output"
  }
}
```

## Configuration
- Ollama settings and model: `src/main/resources/application.properties`
  - `quarkus.langchain4j.ollama.base-url=http://localhost:11434`
  - `quarkus.langchain4j.ollama.chat-model.model-id=qwen3:1.7b`
- Adjust temperature/format/logging in the same file as needed.

## Packaging
```bash
# JVM runnable jar
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar

# Uber-jar
./mvnw package -Dquarkus.package.jar.type=uber-jar
java -jar target/*-runner.jar
```

Native build (optional, requires GraalVM or container build):
```bash
./mvnw package -Dnative
# or
./mvnw package -Dnative -Dquarkus.native.container-build=true
```
