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
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

import java.util.Date;
import static cukesman.EnvPropertyReader.readEnvVarOrProperty;
import static cukesman.EnvPropertyReader.readCukesmanUser;
import static cukesman.EnvPropertyReader.readCukesmanPassword;

public class ReportUploader {

    private String url;

    private String username;

    private String password;

    public static ReportUploader fromEnv() {
        final ReportUploader reportUploader = new ReportUploader();
        reportUploader.url = readEnvVarOrProperty("CUKESMAN_URL", "cukesmanUrl");
        reportUploader.username = readCukesmanUser();
        reportUploader.password = readCukesmanPassword();
        return reportUploader;
    }

    public void upload(final FeatureReport featureReport) {
        final ExecutionReport executionReport = ExecutionReport.createNew().withFeature(featureReport);
        upload(executionReport);
    }

    public void upload(final ExecutionReport executionReport) {
        final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new ISO8601DateSerializer()).create();
        final CukesmanReportAPI cukesmanReportAPI = Feign.builder()
                .client(new OkHttpClient())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .requestInterceptor(new BasicAuthRequestInterceptor(username, password))
                .encoder(new GsonEncoder(gson))
                .decoder(new GsonDecoder(gson))
                .target(CukesmanReportAPI.class, url);
        cukesmanReportAPI.reportExecution(executionReport);
    }

}
