package net.competition.dto.request;

import net.competition.model.Application;

public class PutRatingDtoRequest {

    private String token;
    private Application application;
    private int rating;

    public PutRatingDtoRequest(String token, Application application, int rating) {
        this.token = token;
        this.application = application;
        this.rating = rating;
    }

    public String getToken() {
        return token;
    }

    public Application getApplication() {
        return application;
    }

    public int getRating() {
        return rating;
    }

}


