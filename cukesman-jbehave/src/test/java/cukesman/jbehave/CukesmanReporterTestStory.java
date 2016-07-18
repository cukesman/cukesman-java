package cukesman.jbehave;

import cukesman.reporter.model.FeatureReport;
import cukesman.reporter.model.ScenarioReport;
import cukesman.reporter.model.Status;
import cukesman.jbehave.step.GrocerySteps;
import cukesman.reporter.model.StepReport;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.After;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CukesmanReporterTestStory extends AbstractJBehaveStories {

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new GrocerySteps());
    }

    @Override
    protected List<String> storyPaths() {
        return Arrays.asList("Grocery_Shopping.feature");
    }

    @After
    public void after() {
        final FeatureReport featureReport = getFeatureReport();
        assertNotNull(featureReport);

        assertEquals(2, featureReport.getScenarios().size());

        // Scenario 1
        final ScenarioReport scenario1Report = featureReport.getScenarios().get(0);
        assertEquals("456", scenario1Report.getToken());
        assertEquals(Status.pending, scenario1Report.getStatus());
        assertEquals(3, scenario1Report.getStepReports().size());

        final StepReport step1Report = scenario1Report.getStepReports().get(0);
        assertEquals(Status.success, step1Report.getStatus());
        assertEquals("given", step1Report.getKeyword());
        assertEquals("I go to the bakery", step1Report.getText());


        // Scenario 2
        final ScenarioReport scenario2Report = featureReport.getScenarios().get(1);
        assertEquals("789", scenario2Report.getToken());
        assertEquals(Status.failed, scenario2Report.getStatus());
        assertEquals(3, scenario2Report.getStepReports().size());

        assertEquals(3, scenario1Report.getStepReports().size());

        final StepReport step3Report = scenario2Report.getStepReports().get(2);
        assertEquals(Status.failed, step3Report.getStatus());
    }

}
