package net.competition.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Expert extends User {
    private List<String> directions;

    public Expert(String firstName, String lastName, List<String> directions, String login, String password) {
        super(0, firstName, lastName, login, password);
        this.directions = directions;
    }

    public Expert(int id, String firstName, String lastName, List<String> directions, String login, String password) {
        super(id, firstName, lastName, login, password);
        this.directions = directions;
    }

    public Expert(int id, String firstName, String lastName, String login, String password) {
        this(id, firstName, lastName, new ArrayList<>(), login, password);
    }

    public Expert(String firstName, String lastName, String login, String password) {
        super(0, firstName, lastName, login, password);
    }

    public List<String> getDirections() {
        return directions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expert)) return false;
        if (!super.equals(o)) return false;
        Expert expert = (Expert) o;
        return Objects.equals(getDirections(), expert.getDirections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDirections());
    }
}
