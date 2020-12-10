package net.competition.dto.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetApplicationByDirectionDtoRequest {
    private String token;
    private List<String> directions;

    public GetApplicationByDirectionDtoRequest(String token, List<String> directions) {
        this.token = token;
        this.directions = directions;
    }

    public GetApplicationByDirectionDtoRequest(String token, String direction) {
        this(token, new ArrayList<>(Collections.singletonList(direction)));
    }

    public String getToken() {
        return token;
    }

    public List<String> getDirections() {
        return directions;
    }
}
