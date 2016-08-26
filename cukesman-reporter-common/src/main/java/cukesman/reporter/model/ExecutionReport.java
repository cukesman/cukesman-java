package cukesman.reporter.model;

import cukesman.reporter.ContinuousIntegrationService;

import java.util.ArrayList;
import java.util.List;

public class ExecutionReport {

    private Build build;

    private List<FeatureReport> features = new ArrayList<>();

    public static ExecutionReport createNew() {
        final ExecutionReport executionReport = new ExecutionReport();
        executionReport.build = ContinuousIntegrationService.readBuild();
        return executionReport;
    }

    public ExecutionReport withFeature(final FeatureReport featureReport) {
        this.features.add(featureReport);
        return this;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public List<FeatureReport> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureReport> features) {
        this.features = features;
    }

}
