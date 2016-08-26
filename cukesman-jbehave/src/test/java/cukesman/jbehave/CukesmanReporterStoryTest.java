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

public class CukesmanReporterStoryTest extends AbstractJBehaveStories {

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
        assertEquals("456xxxxxxxxxxxxx", scenario1Report.getToken());
        assertEquals(Status.pending, scenario1Report.getStatus());
        assertEquals(3, scenario1Report.getSteps().size());

        final StepReport step1Report = scenario1Report.getSteps().get(0);
        assertEquals(Status.success, step1Report.getStatus());
        assertEquals("given", step1Report.getKeyword().name());
        assertEquals("I go to the bakery", step1Report.getText());


        // Scenario 2
        final ScenarioReport scenario2Report = featureReport.getScenarios().get(1);
        assertEquals("789xxxxxxxxxxxxx", scenario2Report.getToken());
        assertEquals(Status.failed, scenario2Report.getStatus());
        assertEquals(3, scenario2Report.getSteps().size());

        assertEquals(3, scenario1Report.getSteps().size());

        final StepReport step3Report = scenario2Report.getSteps().get(2);
        assertEquals(Status.failed, step3Report.getStatus());
    }

}
