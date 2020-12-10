package net.competition.dao;

import net.competition.exception.ServerException;
import net.competition.model.Application;
import net.competition.model.Contestant;

import java.util.List;

public interface ContestantDao {
    String registerContestant(Contestant contestant) throws ServerException;

    void delete(Contestant contestant);

    String login(String login, String password) throws ServerException;

    void logout(Contestant contestant);

    void addApplication(Contestant contestant, List<Application> applications) throws ServerException;

    void cancelApplication(Contestant contestant, List<Application> applications);

    Contestant getContestantByToken(String token) throws ServerException;

}
