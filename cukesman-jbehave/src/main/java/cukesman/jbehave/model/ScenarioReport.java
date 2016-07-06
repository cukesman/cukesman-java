package cukesman.jbehave.model;

import java.io.Serializable;

public class ScenarioReport implements Serializable {

    private String token;

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
