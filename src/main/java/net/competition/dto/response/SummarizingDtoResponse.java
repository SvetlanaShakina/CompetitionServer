package net.competition.dto.response;

import net.competition.model.Application;

import java.util.List;

public class SummarizingDtoResponse {
    private List<Application> listWinningApplication;

    public SummarizingDtoResponse(List<Application> listWinningApplication) {
        this.listWinningApplication = listWinningApplication;
    }

    public List<Application> getListWinningApplication() {
        return listWinningApplication;
    }
}
