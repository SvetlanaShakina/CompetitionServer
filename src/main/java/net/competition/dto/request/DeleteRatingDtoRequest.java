package net.competition.dto.request;

import net.competition.model.Application;

public class DeleteRatingDtoRequest {
    private String token;
    private Application application;

    public DeleteRatingDtoRequest(String token, Application application) {
        this.token = token;
        this.application = application;
    }

    public String getToken() {
        return token;
    }

    public Application getApplication() {
        return application;
    }
}
