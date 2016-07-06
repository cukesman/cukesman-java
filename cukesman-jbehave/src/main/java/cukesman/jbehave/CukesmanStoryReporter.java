package cukesman.jbehave;

import cukesman.jbehave.client.model.FeatureReport;
import cukesman.jbehave.client.model.ScenarioReport;
import cukesman.jbehave.client.model.Status;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.GivenStories;
import org.jbehave.core.model.Lifecycle;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.OutcomesTable;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.model.StoryDuration;
import org.jbehave.core.reporters.StoryReporter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CukesmanStoryReporter implements StoryReporter {

    private FeatureReport featureReport;

    private ScenarioReport currentScenarioReport;

    private boolean givenStoryContext;

    private boolean dryRun;

    @Override
    public void storyNotAllowed(Story story, String filter) {}

    @Override
    public void storyCancelled(Story story, StoryDuration storyDuration) {}

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        if (givenStory) {
            givenStoryContext = true;
            return;
        }

        final Optional<String> token = token(story.getMeta());
        if (token.isPresent()) {
            featureReport = new FeatureReport();
            featureReport.setToken(token.get());
        }
    }

    @Override
    public void afterStory(boolean givenStory) {
        if (givenStory) {
            return;
        }
     }

    @Override
    public void narrative(Narrative narrative) {}

    @Override
    public void lifecyle(Lifecycle lifecycle) {}

    @Override
    public void scenarioNotAllowed(Scenario scenario, String filter) {}

    @Override
    public void beforeScenario(String scenarioTitle) {}

    @Override
    public void scenarioMeta(final Meta meta) {
        final Optional<String> token = token(meta);
        if (!token.isPresent()) {
            return;
        }
        currentScenarioReport = new ScenarioReport();
        currentScenarioReport.setToken(token.get());
        currentScenarioReport.setStatus(Status.in_progress);
        featureReport.getScenarios().add(currentScenarioReport);
    }

    @Override
    public void afterScenario() {
        currentScenarioReport = null;
    }

    @Override
    public void givenStories(GivenStories givenStories) {}

    @Override
    public void givenStories(List<String> storyPaths) {}

    @Override
    public void beforeExamples(List<String> steps, ExamplesTable table) {
    }

    @Override
    public void example(Map<String, String> tableRow) {}

    @Override
    public void afterExamples() {}

    @Override
    public void beforeStep(String step) {}

    @Override
    public void successful(String step) {
        handleStep(Status.success);
    }

    @Override
    public void ignorable(String step) {
        handleStep(Status.skipped);
    }

    @Override
    public void pending(String step) {
        handleStep(Status.pending);
    }

    @Override
    public void notPerformed(String step) {
        handleStep(Status.pending);
    }

    @Override
    public void failed(String step, Throwable cause) {
        handleStep(Status.failed);
    }

    @Override
    public void failedOutcomes(String step, OutcomesTable table) {
        handleStep(Status.failed);
    }

    @Override
    public void restarted(String step, Throwable cause) {

    }

    @Override
    public void restartedStory(Story story, Throwable cause) {

    }

    @Override
    public void dryRun() {
        dryRun = true;
    }

    @Override
    public void pendingMethods(List<String> methods) {
    }

    public FeatureReport getFeatureReport() {
        return featureReport;
    }

    private void handleStep(final Status status) {
        if (givenStoryContext) {
            return;
        }

        final Status newStatus = Collections.max(
                Arrays.asList(currentScenarioReport.getStatus(), status)
        );
        currentScenarioReport.setStatus(newStatus);
    }

    private Optional<String> token(final Meta meta) {
        if (meta == null) {
            return Optional.empty();
        }

        return meta.getPropertyNames().stream()
                .filter(p -> p.startsWith("cukesman"))
                .map(p -> p.substring("cukesman".length() + 1))
                .findFirst();
    }

}
