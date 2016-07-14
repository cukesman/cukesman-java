package cukesman.reporter;

import cukesman.reporter.model.ExecutionReport;
import cukesman.reporter.model.FeatureReport;

import java.util.Objects;

public class ReportUploader {

    private String url;

    private String username;

    private String password;

    public static ReportUploader fromEnv() {
        final ReportUploader reportUploader = new ReportUploader();
        reportUploader.url = readEnvVar("CUKESMAN_URL");
        reportUploader.username = readEnvVar("CUKESMAN_USER");
        reportUploader.password = readEnvVar("CUKESMAN_PASSWORD");
        return reportUploader;
    }

    public void upload(final FeatureReport featureReport) {
        final ExecutionReport executionReport = ExecutionReport.createNew().withFeature(featureReport);
        upload(executionReport);
    }

    public void upload(final ExecutionReport executionReport) {

    }

    private static String readEnvVar(final String name) {
        return Objects.requireNonNull(
                System.getenv(name),
                String.format("Missing environment variable %s.", name)
        );
    }

}
