package cukesman.reporter.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ScenarioReport {

    private String token;

    private String title;

    private Status status;

    private Date updatedAt;

    private List<StepReport> steps = new ArrayList<>();

    public ScenarioReport withStep(final StepReport stepReport) {
        steps.add(stepReport);
        if (status == null) {
            status = stepReport.getStatus();
        } else {
            final Status newStatus = Collections.max(
                    Arrays.asList(status, stepReport.getStatus())
            );
            status = newStatus;
        }
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<StepReport> getSteps() {
        return steps;
    }

    public void setSteps(List<StepReport> steps) {
        this.steps = steps;
    }

}
