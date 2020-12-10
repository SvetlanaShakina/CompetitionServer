package net.competition.dto.response;

import net.competition.model.Application;

import java.util.List;

public class ApplicationByDirectionDtoResponse {
    private List<Application> applications;

    public ApplicationByDirectionDtoResponse(List<Application> applications) {
        this.applications = applications;
    }

    public List<Application> getApplications() {
        return applications;
    }
}
