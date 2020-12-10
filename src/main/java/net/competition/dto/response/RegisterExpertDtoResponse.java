package net.competition.dto.response;

public class RegisterExpertDtoResponse {
    private String token;

    public RegisterExpertDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
