package net.competition.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.competition.dao.ContestantDao;
import net.competition.dto.request.*;
import net.competition.dto.response.ErrorDtoResponse;
import net.competition.dto.response.LoginContestantDtoResponse;
import net.competition.dto.response.RegisterContestantDtoResponse;
import net.competition.dto.response.SuccessDtoResponse;
import net.competition.exception.ServerErrorCode;
import net.competition.exception.ServerException;
import net.competition.model.Application;
import net.competition.model.Contestant;
import net.competition.daoimpl.ContestantDaoImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ContestantService {
    private ContestantDao contestantDao = new ContestantDaoImpl();
    private Gson gson = new Gson();

    public String registerContestant(String requestJsonString) {
        try {
            RegisterContestantDtoRequest reg = getClassInstanceFromJson(new Gson(), requestJsonString, RegisterContestantDtoRequest.class);
            validateRegister(reg);
            Contestant contestant = new Contestant(reg.getFirstName(), reg.getLastName(), reg.getCompanyName(), reg.getLogin(), reg.getPassword());
            String token = contestantDao.registerContestant(contestant);
            RegisterContestantDtoResponse registerContestantDtoResponse = new RegisterContestantDtoResponse(token);
            return gson.toJson(registerContestantDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String deleteContestant(String requestJsonString) {
        try {
            DeleteContestantDtoRequest delRequest = getClassInstanceFromJson(new Gson(), requestJsonString, DeleteContestantDtoRequest.class);
            Contestant contestant = contestantDao.getContestantByToken(delRequest.getToken());
            contestantDao.delete(contestant);
            SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
            return gson.toJson(successDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String loginContestant(String requestJsonString) {
        try {
            LoginContestantDtoRequest loginRequest = getClassInstanceFromJson(new Gson(), requestJsonString, LoginContestantDtoRequest.class);
            validateLogin(loginRequest);
            String token = contestantDao.login(loginRequest.getLogin(), loginRequest.getPassword());
            LoginContestantDtoResponse loginResponse = new LoginContestantDtoResponse(token);
            return gson.toJson(loginResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String logoutContestant(String requestJsonString) {
        try {
            LogoutContestantDtoRequest logoutRequest = getClassInstanceFromJson(new Gson(), requestJsonString, LogoutContestantDtoRequest.class);
            Contestant contestant = contestantDao.getContestantByToken(logoutRequest.getToken());
            contestantDao.logout(contestant);
            SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
            return gson.toJson(successDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String addApplication(String requestJsonString) {
        try {
            AllApplicationDtoRequest allAppRequest = getClassInstanceFromJson(new Gson(), requestJsonString, AllApplicationDtoRequest.class);
            Contestant contestant = contestantDao.getContestantByToken(allAppRequest.getToken());
            validateApplicationList(allAppRequest);
            List<Application> checkedApplications = new ArrayList<>();
            for (ApplicationDtoRequest appRequest : allAppRequest.getApplicationList()) {
                Application application = new Application(appRequest.getTitle(), appRequest.getDescription(),
                        appRequest.getDirections(), appRequest.getRequestedAmount());
                checkedApplications.add(application);
            }
            contestantDao.addApplication(contestant, checkedApplications);
            SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
            return gson.toJson(successDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String cancelApplication(String requestJsonString) {
        try {
            AllApplicationDtoRequest allApp = getClassInstanceFromJson(new Gson(), requestJsonString, AllApplicationDtoRequest.class);
            Contestant contestant = contestantDao.getContestantByToken(allApp.getToken());
            validateApplicationList(allApp);
            ArrayList<Application> checkedApplications = new ArrayList<>();
            for (ApplicationDtoRequest appRequest : allApp.getApplicationList()) {
                Application application = new Application(appRequest.getTitle(), appRequest.getDescription(),
                        appRequest.getDirections(), appRequest.getRequestedAmount());
                checkedApplications.add(application);
            }
            contestantDao.cancelApplication(contestant, checkedApplications);
            SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
            return gson.toJson(successDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public static <T> T getClassInstanceFromJson(Gson gson, String json, Class<T> clazz) throws ServerException {
        try {
            return gson.fromJson(json, (Type) clazz);
        } catch (NullPointerException | JsonSyntaxException ex) {
            throw new ServerException(ServerErrorCode.WRONG_JSON);
        }
    }

    private static void validateRegister(RegisterContestantDtoRequest object) throws ServerException {
        if (object.getFirstName() == null || object.getFirstName().isEmpty())
            throw new ServerException(ServerErrorCode.FIRSTNAME_SHORT);
        if (object.getLastName() == null || object.getLastName().isEmpty())
            throw new ServerException(ServerErrorCode.LASTNAME_SHORT);
        if (object.getCompanyName() == null || object.getCompanyName().isEmpty())
            throw new ServerException(ServerErrorCode.COMPANY_NAME_SHORT);
        if (object.getLogin() == null || object.getLogin().isEmpty() || object.getLogin().length() < 4)
            throw new ServerException(ServerErrorCode.LOGIN_SHORT);
        if (object.getPassword() == null || object.getPassword().isEmpty() || object.getPassword().length() < 6)
            throw new ServerException(ServerErrorCode.PASSWORD_SHORT);
    }

    private static void validateLogin(LoginContestantDtoRequest object) throws ServerException {
        if (object.getLogin() == null || object.getLogin().isEmpty())
            throw new ServerException(ServerErrorCode.LOGIN_EMPTY);
        if (object.getPassword() == null || object.getPassword().isEmpty())
            throw new ServerException(ServerErrorCode.PASSWORD_EMPTY);
    }

    private static void validateApplicationList(AllApplicationDtoRequest allAppRequest) throws ServerException {
        if (allAppRequest.getApplicationList().isEmpty())
            throw new ServerException(ServerErrorCode.APPLICATION_LIST_EMPTY);
        for (ApplicationDtoRequest applicationRequest : allAppRequest.getApplicationList()) {
            validateApplication(applicationRequest);
        }
    }

    private static void validateApplication(ApplicationDtoRequest object) throws ServerException {
        if (object.getTitle() == null || object.getTitle().isEmpty())
            throw new ServerException(ServerErrorCode.TITLE_APPLICATION_EMPTY);
        if (object.getDescription() == null || object.getDescription().isEmpty())
            throw new ServerException(ServerErrorCode.DESCRIPTION_APPLICATION_EMPTY);
        if (object.getDirections().isEmpty())
            throw new ServerException(ServerErrorCode.DIRECTIONS_EMPTY);
        if (object.getRequestedAmount() == 0)
            throw new ServerException(ServerErrorCode.SUM_EMPTY);
    }
}
