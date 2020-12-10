package net.competition.dto.response;

public class LoginExpertDtoResponse {
    private String token;

    public LoginExpertDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
