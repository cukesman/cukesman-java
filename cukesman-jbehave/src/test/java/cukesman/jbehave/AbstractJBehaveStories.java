/*
 * Copyright © 2014 HanseMerkur Krankenversicherung AG All Rights Reserved.
 */
package cukesman.jbehave;

import cukesman.jbehave.CukesmanStoryReporter;
import cukesman.jbehave.model.FeatureReport;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.failures.SilentlyAbsorbingFailure;
import org.jbehave.core.io.AbsolutePathCalculator;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.gherkin.GherkinStoryParser;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.ReportsCount;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;

import static org.jbehave.core.reporters.Format.CONSOLE;

//@RunWith(JUnitReportingRunner.class)
public abstract class AbstractJBehaveStories extends JUnitStories {

    private CukesmanStoryReporter cukesmanReporter = new CukesmanStoryReporter();

    public AbstractJBehaveStories() {
        final Embedder embedder = configuredEmbedder();
//        embedder.useMetaFilters(asList("cukesman*"));
        embedder.embedderControls().useStoryTimeoutInSecs(2400);
        embedder.useEmbedderFailureStrategy(new Embedder.EmbedderFailureStrategy() {

            @Override
            public void handleFailures(BatchFailures failures) {

            }

            @Override
            public void handleFailures(ReportsCount count) {

            }
        });
        //JUnitReportingRunner.recommandedControls(embedder);
    }

    @Override
    public abstract InjectableStepsFactory stepsFactory();

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();

        return new MostUsefulConfiguration()
                .usePathCalculator(new AbsolutePathCalculator())
                .useFailureStrategy(new SilentlyAbsorbingFailure())
                .useStoryParser(new GherkinStoryParser())
                .useParameterConverters(new ParameterConverters())
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryPathResolver(new UnderscoredCamelCaseResolver())
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                        .withDefaultFormats()
                        .withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
                        .withFormats(CONSOLE)
                        .withFailureTrace(true)
                        .withFailureTraceCompression(true)
                        .withReporters(cukesmanReporter));
    }

    protected FeatureReport getFeatureReport() {
        return cukesmanReporter.getFeatureReport();
    }

}
