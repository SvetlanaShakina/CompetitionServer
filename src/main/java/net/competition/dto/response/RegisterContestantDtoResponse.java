package net.competition.dto.response;

public class RegisterContestantDtoResponse {
    private String token;

    public RegisterContestantDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
