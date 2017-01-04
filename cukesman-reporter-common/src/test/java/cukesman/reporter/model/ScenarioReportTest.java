package cukesman.reporter.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static cukesman.reporter.model.ScenarioReport.*;
import static cukesman.reporter.model.Status.*;

public class ScenarioReportTest {

    @Test
    public void calcScenrioProgress() {
        assertEquals(success, calcProgress(Arrays.asList(success, success)));
        assertEquals(success, calcProgress(Arrays.asList(success, success, skipped)));
        assertEquals(failed, calcProgress(Arrays.asList(success, pending, skipped, failed, in_progress)));
        assertEquals(in_progress, calcProgress(Arrays.asList(success, success, in_progress)));
        assertEquals(in_progress, calcProgress(Arrays.asList(success, skipped, in_progress)));
        assertEquals(pending, calcProgress(Arrays.asList(success, pending)));
        assertEquals(pending, calcProgress(Arrays.asList(in_progress, pending)));
        assertEquals(in_progress, calcProgress(Arrays.asList(in_progress, skipped)));
        assertEquals(skipped, calcProgress(Arrays.asList(skipped, skipped)));
    }

}
