package net.competition.dto.request;

public class GetApplicationsWithRatingDtoRequest {
    private String token;

    public GetApplicationsWithRatingDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
