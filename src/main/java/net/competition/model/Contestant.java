package net.competition.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Contestant extends User {
    private String companyName;
    private List<Application> applications = new ArrayList<>();

    public Contestant(String firstName, String lastName, String companyName, String login, String password) {
        super(0, firstName, lastName, login, password);
        this.companyName = companyName;
    }

    public Contestant(int id, String firstName, String lastName, String companyName, String login, String password) {
        super(id, firstName, lastName, login, password);
        this.companyName = companyName;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void addApplication(List<Application> applications) {
        this.applications.addAll(applications);
    }

    public void cancelApplication(List<Application> applications) {
        this.applications.removeAll(applications);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contestant)) return false;
        if (!super.equals(o)) return false;
        Contestant that = (Contestant) o;
        return Objects.equals(companyName, that.companyName) &&
                Objects.equals(getApplications(), that.getApplications());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName, getApplications());
    }
}
