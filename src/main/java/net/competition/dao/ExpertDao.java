package net.competition.dao;

import net.competition.exception.ServerException;
import net.competition.model.Application;
import net.competition.model.Expert;

import java.util.List;

public interface ExpertDao {
    String registerExpert(Expert expert) throws ServerException;

    void delete(Expert expert);

    String login(String login, String password) throws ServerException;

    void logout(Expert expert);

    void putRating(Expert expert, Application application, int rating) throws ServerException;

    void changeRating(Expert expert, Application application, int rating);

    void deleteRating(Expert expert, Application application);

    List<Application> getApplicationsWithRating(Expert expert);

    List<Application> getApplicationsByDirection(List<String> directions);

    List<Application> summarizing(int grantFund, double minRating);

    Expert getExpertByToken(String token) throws ServerException;

    void clearDataBase();

}
