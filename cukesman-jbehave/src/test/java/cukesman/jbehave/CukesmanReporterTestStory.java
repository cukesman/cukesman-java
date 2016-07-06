package cukesman.jbehave;

//import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

import cukesman.jbehave.model.FeatureReport;
import cukesman.jbehave.model.ScenarioReport;
import cukesman.jbehave.model.Status;
import cukesman.jbehave.step.GrocerySteps;
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

        final ScenarioReport scenario1Report = featureReport.getScenarios().get(0);
        assertEquals("456", scenario1Report.getToken());
        assertEquals(Status.pending, scenario1Report.getStatus());

        final ScenarioReport scenario2Report = featureReport.getScenarios().get(1);
        assertEquals("789", scenario2Report.getToken());
        assertEquals(Status.failed, scenario2Report.getStatus());
    }

}
