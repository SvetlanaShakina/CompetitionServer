package net.competition.dto.request;

public class LogoutExpertDtoRequest {
    private String token;

    public LogoutExpertDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
