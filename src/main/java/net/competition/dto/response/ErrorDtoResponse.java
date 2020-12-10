package net.competition.dto.response;

import net.competition.exception.ServerException;

public class ErrorDtoResponse {
    private ServerException error;

    public ErrorDtoResponse(ServerException error) {
        this.error = error;
    }

    public ServerException getError() {
        return error;
    }
}
