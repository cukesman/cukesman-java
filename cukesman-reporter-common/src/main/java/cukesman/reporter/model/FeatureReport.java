package cukesman.reporter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FeatureReport implements Serializable {

    private String token;

    private String title;

    private List<ScenarioReport> scenarios = new ArrayList<>();

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

    public List<ScenarioReport> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ScenarioReport> scenarios) {
        this.scenarios = scenarios;
    }

}
