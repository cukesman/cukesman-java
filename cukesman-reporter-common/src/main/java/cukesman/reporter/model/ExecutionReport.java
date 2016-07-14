package cukesman.reporter.model;

import cukesman.reporter.ContinuousIntegrationService;

import java.util.ArrayList;
import java.util.List;

public class ExecutionReport {

    private String versionNumber;

    private Build build;

    private List<FeatureReport> featureReports = new ArrayList<>();

    public static ExecutionReport createNew() {
        final ExecutionReport executionReport = new ExecutionReport();
        executionReport.build = ContinuousIntegrationService.readBuild();
        return executionReport;
    }

    public ExecutionReport withFeature(final FeatureReport featureReport) {
        this.featureReports.add(featureReport);
        return this;
    }

}
