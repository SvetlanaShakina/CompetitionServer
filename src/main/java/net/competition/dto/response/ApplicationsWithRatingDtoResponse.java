package net.competition.dto.response;

import net.competition.model.Application;

import java.util.List;

public class ApplicationsWithRatingDtoResponse {
    private List<Application> applications;

    public ApplicationsWithRatingDtoResponse(List<Application> applications) {
        this.applications = applications;
    }

    public List<Application> getApplications() {
        return applications;
    }
}
