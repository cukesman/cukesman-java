package cukesman.jbehave;

import cukesman.reporter.ReportUploader;
import cukesman.reporter.model.FeatureReport;
import cukesman.reporter.model.Keyword;
import cukesman.reporter.model.ScenarioReport;
import cukesman.reporter.model.Status;
import cukesman.reporter.model.StepReport;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.GivenStories;
import org.jbehave.core.model.Lifecycle;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.OutcomesTable;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.model.StoryDuration;
import org.jbehave.core.reporters.StackTraceFormatter;
import org.jbehave.core.reporters.StoryReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static cukesman.EnvPropertyReader.hasCukesmanOneOffFeatureId;
import static cukesman.reporter.ContinuousIntegrationService.isContinousIntegrationRun;

public class CukesmanStoryReporter implements StoryReporter {

    private Logger LOG = LoggerFactory.getLogger(CukesmanStoryReporter.class);

    private Story story;

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

        this.story = story;
        final Optional<String> token = token(story.getMeta());
        if (token.isPresent()) {
            featureReport = new FeatureReport();
            if (story.getDescription() != null) {
                featureReport.setTitle(story.getDescription().asString());
            } else {
                featureReport.setTitle(story.getName());
            }
            featureReport.setToken(token.get());
        }
    }

    @Override
    public void afterStory(boolean givenStory) {
        if (givenStory) {
            return;
        }

        story = null;

        if (featureReport != null && !dryRun
                && (isContinousIntegrationRun() || isOneOffTest())) {
            try {
                ReportUploader.fromEnv().upload(featureReport);
            } catch (Exception e) {
                final String message = String.format(
                        "Could not report execution for feature %s (Token %s) to cukesman.",
                        featureReport.getTitle(),
                        featureReport.getToken()
                );
                LOG.warn(message, e);
            }
        } else {
            LOG.info("Skipping report upload to cukesman (no report or no CI environment detected).");
        }
     }

    @Override
    public void narrative(Narrative narrative) {}

    @Override
    public void lifecyle(Lifecycle lifecycle) {}

    @Override
    public void scenarioNotAllowed(Scenario scenario, String filter) {}

    @Override
    public void beforeScenario(String scenarioTitle) {
        currentScenarioReport = new ScenarioReport();
        currentScenarioReport.setTitle(scenarioTitle);

        // TRICKY: The methocd scenarioMeta() is not called from JBehave
        // thats why we have to call it ourself.
        final Scenario scenario = story.getScenarios().stream()
                .filter(s -> s.getTitle().equals(scenarioTitle))
                .findFirst()
                .get();

        scenarioMeta(scenario.getMeta());
    }

    @Override
    public void scenarioMeta(final Meta meta) {
        final Optional<String> token = token(meta);
        if (!token.isPresent()) {
            return;
        }
        currentScenarioReport.setUpdatedAt(new Date());
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
    public void beforeExamples(List<String> steps, ExamplesTable table) {}

    @Override
    public void example(Map<String, String> tableRow) {}

    @Override
    public void afterExamples() {}

    @Override
    public void beforeStep(String step) {}

    @Override
    public void successful(String step) {
        handleStep(step, Status.success);
    }

    @Override
    public void ignorable(String step) {
        handleStep(step, Status.skipped);
    }

    @Override
    public void pending(String step) {
        handleStep(step, Status.pending);
    }

    @Override
    public void notPerformed(String step) {
        handleStep(step, Status.pending);
    }

    @Override
    public void failed(String step, Throwable cause) {
        handleStep(step, Status.failed, Optional.of(cause));
    }

    @Override
    public void failedOutcomes(String step, OutcomesTable table) {
        handleStep(step, Status.failed);
    }

    @Override
    public void restarted(String step, Throwable cause) {}

    @Override
    public void restartedStory(Story story, Throwable cause) {}

    @Override
    public void dryRun() {
        dryRun = true;
    }

    @Override
    public void pendingMethods(List<String> methods) {}

    public FeatureReport getFeatureReport() {
        return featureReport;
    }

    private void handleStep(final String text, final Status status) {
        handleStep(text, status, Optional.empty());
    }

    private void handleStep(final String text, final Status status, final Optional<Throwable> error) {
        if (givenStoryContext) {
            return;
        }

        final StepReport stepReport = new StepReport();
        final String[] keywordAndText = extractKeyword(text);
        stepReport.setKeyword(Keyword.valueOf(keywordAndText[0].toLowerCase()));
        stepReport.setText(keywordAndText[1]);
        stepReport.setStatus(status);

        error.ifPresent(t -> stepReport.setMessage(new StackTraceFormatter(false).stackTrace(t)));

        currentScenarioReport.withStep(stepReport);
    }

    private String[] extractKeyword(final String textWithKeyword) {
        Objects.requireNonNull(textWithKeyword);

        final String[] words = textWithKeyword.split("\\s");
        final String keyword = words[0].toLowerCase();
        final String text = textWithKeyword.substring(keyword.length()).trim();

        final String[] keywordAndText = new String[2];
        keywordAndText[0] = keyword;
        keywordAndText[1] = text;

        // TRICKY: Replace the JBehave marker characters for parameters.
        keywordAndText[1] = keywordAndText[1].replace("｟", "");
        keywordAndText[1] = keywordAndText[1].replace("｠", "");

        return keywordAndText;
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

    private static boolean isOneOffTest() {
        return hasCukesmanOneOffFeatureId();
    }

}
