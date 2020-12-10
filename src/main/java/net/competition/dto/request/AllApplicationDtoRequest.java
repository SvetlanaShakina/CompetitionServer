package net.competition.dto.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllApplicationDtoRequest {
    private String token;
    private List<ApplicationDtoRequest> applicationList;

    public AllApplicationDtoRequest(String token, ArrayList<ApplicationDtoRequest> applicationList) {
        this.token = token;
        this.applicationList = applicationList;
    }

    public AllApplicationDtoRequest(String token, ApplicationDtoRequest application) {
        this(token, new ArrayList<>(Collections.singletonList(application)));
    }

    public String getToken() {
        return token;
    }

    public List<ApplicationDtoRequest> getApplicationList() {
        return applicationList;
    }


}
