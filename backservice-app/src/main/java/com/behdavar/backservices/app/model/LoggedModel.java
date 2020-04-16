package com.behdavar.backservices.app.model;

public class LoggedModel {

    private LoggedDetails details;
    private Boolean authenticated;
    private Principal principal;

    public LoggedDetails getDetails() {
        return details;
    }

    public void setDetails(LoggedDetails details) {
        this.details = details;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}
