package cukesman.reporter.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

public class FeatureReport {

    private String token;

    private String title;

    private List<ScenarioReport> scenarios = new ArrayList<>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Transient
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ScenarioReport> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ScenarioReport> scenarios) {
        this.scenarios = scenarios;
    }

}
