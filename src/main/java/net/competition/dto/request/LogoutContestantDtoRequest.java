package net.competition.dto.request;

public class LogoutContestantDtoRequest {
    private String token;

    public LogoutContestantDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
