package net.competition.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Application {
    private int id;
    private String title;
    private String description;
    private List<String> directions;
    private int requestedAmount;

    public Application(int id, String title, String description, List<String> directions, int requestedAmount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.directions = directions;
        this.requestedAmount = requestedAmount;
    }

    public Application(String title, String description, List<String> directions, int requestedAmount) {
        this(0, title, description, directions, requestedAmount);
    }

    public Application(int id, String title, String description, int requestedAmount) {
        this(id, title, description, new ArrayList<>(), requestedAmount);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getDirections() {
        return directions;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Application)) return false;
        Application that = (Application) o;
        return id == that.id &&
                getRequestedAmount() == that.getRequestedAmount() &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getDirections(), that.getDirections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getTitle(), getDescription(), getDirections(), getRequestedAmount());
    }
}
