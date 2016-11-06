package cukesman.reporter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cukesman.reporter.gson.ISO8601DateSerializer;
import cukesman.reporter.model.ExecutionReport;
import cukesman.reporter.model.FeatureReport;
import feign.Feign;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

import java.util.Date;
import java.util.Objects;

public class ReportUploader {

    private String url;

    private String username;

    private String password;

    public static ReportUploader fromEnv() {
        final ReportUploader reportUploader = new ReportUploader();
        reportUploader.url = readEnvVarOrProperty("CUKESMAN_URL", "cukesmanUrl");
        reportUploader.username = readEnvVarOrProperty("CUKESMAN_USER", "cukesmanUser");
        reportUploader.password = readEnvVarOrProperty("CUKESMAN_PASSWORD", "cukesmanPassword");
        return reportUploader;
    }

    public void upload(final FeatureReport featureReport) {
        final ExecutionReport executionReport = ExecutionReport.createNew().withFeature(featureReport);
        upload(executionReport);
    }

    public void upload(final ExecutionReport executionReport) {
        final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new ISO8601DateSerializer()).create();
        final CukesmanReportAPI cukesmanReportAPI = Feign.builder()
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .requestInterceptor(new BasicAuthRequestInterceptor(username, password))
                .encoder(new GsonEncoder(gson))
                .decoder(new GsonDecoder(gson))
                .target(CukesmanReportAPI.class, url);
        cukesmanReportAPI.reportExecution(executionReport);
    }

    private static String readEnvVarOrProperty(final String envVarName, final String propertyName) {
        String value = System.getenv(envVarName);
        if (value == null) {
            value = System.getProperty(propertyName);
        }
        return Objects.requireNonNull(
                value,
                String.format("Missing environment variable %s or System Property.", envVarName, propertyName)
        );
    }

}
