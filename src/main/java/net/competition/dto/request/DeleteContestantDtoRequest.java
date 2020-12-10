package net.competition.dto.request;

public class DeleteContestantDtoRequest {
    private String token;

    public DeleteContestantDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
