package cukesman.reporter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static cukesman.reporter.model.Status.*;

public class ScenarioReport implements Serializable {

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
            final List<Status> statuses = steps.stream()
                    .map(s -> s.getStatus())
                    .collect(Collectors.toList());
            status = calcProgress(statuses);
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

    public static Status calcProgress(final List<Status> statuses) {
        if (statuses.size() == 0) {
            return success;
        }

        final List<Status> unskipped = statuses.stream()
                .filter(status -> status != skipped)
                .collect(Collectors.toList());
        if (unskipped.isEmpty()) {
            return skipped;
        }

        final boolean hasFailed = unskipped.stream()
                .anyMatch(status -> status == failed);
        if (hasFailed) {
            return failed;
        }

        final boolean hasPending = unskipped.stream()
                .anyMatch(status -> status == pending);
        if (hasPending) {
            return pending;
        }

        final boolean hasInProgress = unskipped.stream()
                .anyMatch(status -> status == in_progress);
        if (hasInProgress) {
            return in_progress;
        }

        final boolean allSuccess = unskipped.stream()
                .allMatch(status -> status == success);
        if (allSuccess) {
            return success;
        }

        throw new IllegalStateException(String.format("Could not calculate progress for statuses %s.", statuses));
    }
}
