package net.competition.dto.request;

import java.util.List;

public class ApplicationDtoRequest {
    private String title;
    private String description;
    private List<String> directions;
    private int requestedAmount;

    public ApplicationDtoRequest(String title, String description, List<String> directions, int requestedAmount) {
        this.title = title;
        this.description = description;
        this.directions = directions;
        this.requestedAmount = requestedAmount;
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


}
