package net.competition.dto.request;

public class DeleteExpertDtoRequest {
    private String token;

    public DeleteExpertDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
