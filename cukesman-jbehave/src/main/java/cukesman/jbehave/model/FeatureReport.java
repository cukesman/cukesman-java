package cukesman.jbehave.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FeatureReport implements Serializable {
    private String token;

    private List<ScenarioReport> scenarios = new ArrayList<>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ScenarioReport> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ScenarioReport> scenarios) {
        this.scenarios = scenarios;
    }

}
