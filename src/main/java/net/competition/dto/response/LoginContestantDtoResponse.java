package net.competition.dto.response;

public class LoginContestantDtoResponse {
    private String token;

    public LoginContestantDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
