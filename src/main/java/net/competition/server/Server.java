package net.competition.server;

import net.competition.service.ContestantService;
import net.competition.service.ExpertService;


public class Server {
    private ContestantService contestantService = new ContestantService();
    private ExpertService expertService = new ExpertService();

    public String registerContestant(String requestJsonString) {
        return contestantService.registerContestant(requestJsonString);
    }

    public String deleteContestant(String requestJsonString) {
        return contestantService.deleteContestant(requestJsonString);
    }

    public String loginContestant(String requestJsonString) {
        return contestantService.loginContestant(requestJsonString);
    }

    public String logoutContestant(String requestJsonString) {
        return contestantService.logoutContestant(requestJsonString);
    }

    public String addApplication(String requestJsonString) {
        return contestantService.addApplication(requestJsonString);
    }

    public String cancelApplication(String requestJsonString) {
        return contestantService.cancelApplication(requestJsonString);
    }

    public String registerExpert(String requestJsonString) {
        return expertService.registerExpert(requestJsonString);
    }

    public String deleteExpert(String requestJsonString) {
        return expertService.deleteExpert(requestJsonString);
    }

    public String loginExpert(String requestJsonString) {
        return expertService.loginExpert(requestJsonString);
    }

    public String logoutExpert(String requestJsonString) {
        return expertService.logoutExpert(requestJsonString);
    }

    public String putRating(String requestJsonString) {
        return expertService.putRating(requestJsonString);
    }

    public String changeRating(String requestJsonString) {
        return expertService.changeRating(requestJsonString);
    }

    public String deleteRating(String requestJsonString) {
        return expertService.deleteRating(requestJsonString);
    }

    public String getApplicationsWithRating(String requestJsonString) {
        return expertService.getApplicationsWithRating(requestJsonString);
    }

    public String getApplicationsByDirection(String requestJsonString) {
        return expertService.getApplicationsByDirection(requestJsonString);
    }

    public String summarizing(String requestJsonString) {
        return expertService.summarizing(requestJsonString);
    }

    public String clearDataBase() {
        return expertService.clearDataBase();
    }

}


