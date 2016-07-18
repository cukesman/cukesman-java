package cukesman.reporter.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ScenarioReport {

    private String token;

    private Status status;

    private Date updatedAt;

    private List<StepReport> stepReports = new ArrayList<>();

    public ScenarioReport withStep(final StepReport stepReport) {
        stepReports.add(stepReport);
        final Status newStatus = Collections.max(
                Arrays.asList(status, stepReport.getStatus())
        );
        status = newStatus;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Transient
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<StepReport> getStepReports() {
        return stepReports;
    }

    public void setStepReports(List<StepReport> stepReports) {
        this.stepReports = stepReports;
    }

}
