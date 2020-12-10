package net.competition.service;

import com.google.gson.Gson;
import net.competition.dao.ExpertDao;
import net.competition.dto.request.*;
import net.competition.dto.response.*;
import net.competition.model.Application;
import net.competition.model.Expert;
import net.competition.daoimpl.ExpertDaoImpl;
import net.competition.exception.ServerErrorCode;
import net.competition.exception.ServerException;

import java.lang.reflect.Type;
import java.util.List;


public class ExpertService {
    private ExpertDao expertDao = new ExpertDaoImpl();
    private Gson gson = new Gson();

    public String registerExpert(String requestJsonString) {
        try {
            RegisterExpertDtoRequest reg = getClassInstanceFromJson(new Gson(), requestJsonString, RegisterExpertDtoRequest.class);
            validateRegister(reg);
            Expert expert = new Expert(reg.getFirstName(), reg.getLastName(), reg.getDirections(), reg.getLogin(), reg.getPassword());
            String token = expertDao.registerExpert(expert);
            RegisterExpertDtoResponse registerExpertDtoResponse = new RegisterExpertDtoResponse(token);
            return gson.toJson(registerExpertDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String deleteExpert(String requestJsonString) {
        try {
            DeleteExpertDtoRequest delRequest = getClassInstanceFromJson(new Gson(), requestJsonString, DeleteExpertDtoRequest.class);
            Expert expert = expertDao.getExpertByToken(delRequest.getToken());
            expertDao.delete(expert);
            SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
            return gson.toJson(successDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String loginExpert(String requestJsonString) {
        try {
            LoginExpertDtoRequest loginRequest = getClassInstanceFromJson(new Gson(), requestJsonString, LoginExpertDtoRequest.class);
            validateLogin(loginRequest);
            String token = expertDao.login(loginRequest.getLogin(), loginRequest.getPassword());
            LoginExpertDtoResponse loginResponse = new LoginExpertDtoResponse(token);
            return gson.toJson(loginResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String logoutExpert(String requestJsonString) {
        try {
            LogoutExpertDtoRequest logoutRequest = getClassInstanceFromJson(new Gson(), requestJsonString, LogoutExpertDtoRequest.class);
            Expert expert = expertDao.getExpertByToken(logoutRequest.getToken());
            expertDao.logout(expert);
            SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
            return gson.toJson(successDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String putRating(String requestJsonString) {
        try {
            PutRatingDtoRequest putRatingReq = getClassInstanceFromJson(new Gson(), requestJsonString, PutRatingDtoRequest.class);
            Expert expert = expertDao.getExpertByToken(putRatingReq.getToken());
            validatePutRating(putRatingReq, expert);
            expertDao.putRating(expert, putRatingReq.getApplication(), putRatingReq.getRating());
            SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
            return gson.toJson(successDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String changeRating(String requestJsonString) {
        try {
            ChangeRatingDtoRequest changeRatingReq = getClassInstanceFromJson(new Gson(), requestJsonString, ChangeRatingDtoRequest.class);
            Expert expert = expertDao.getExpertByToken(changeRatingReq.getToken());
            validateChangeRating(changeRatingReq);
            expertDao.changeRating(expert, changeRatingReq.getApplication(), changeRatingReq.getRating());
            SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
            return gson.toJson(successDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String deleteRating(String requestJsonString) {
        try {
            DeleteRatingDtoRequest delRatingReq = getClassInstanceFromJson(new Gson(), requestJsonString, DeleteRatingDtoRequest.class);
            Expert expert = expertDao.getExpertByToken(delRatingReq.getToken());
            validateDeleteRating(delRatingReq);
            expertDao.deleteRating(expert, delRatingReq.getApplication());
            SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
            return gson.toJson(successDtoResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String getApplicationsWithRating(String requestJsonString) {
        try {
            GetApplicationsWithRatingDtoRequest getAppWithRating = getClassInstanceFromJson(new Gson(), requestJsonString, GetApplicationsWithRatingDtoRequest.class);
            Expert expert = expertDao.getExpertByToken(getAppWithRating.getToken());
            ApplicationsWithRatingDtoResponse appResponse = new ApplicationsWithRatingDtoResponse(expertDao.getApplicationsWithRating(expert));
            return gson.toJson(appResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String getApplicationsByDirection(String requestJsonString) {
        try {
            GetApplicationByDirectionDtoRequest getAppByDirReq = getClassInstanceFromJson(new Gson(), requestJsonString, GetApplicationByDirectionDtoRequest.class);
            Expert expert = expertDao.getExpertByToken(getAppByDirReq.getToken());
            validateGetApplicationsByDirection(expert, getAppByDirReq.getDirections());
            ApplicationByDirectionDtoResponse appResponse = new ApplicationByDirectionDtoResponse(expertDao.getApplicationsByDirection(getAppByDirReq.getDirections()));
            return gson.toJson(appResponse);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String summarizing(String requestJsonString) {
        SummarizingDtoRequest summarizingDtoRequest = gson.fromJson(requestJsonString, SummarizingDtoRequest.class);
        List<Application> result = expertDao.summarizing(summarizingDtoRequest.getGeneralGrantFund(), summarizingDtoRequest.getMinRating());
        SummarizingDtoResponse summarizingDtoResponse = new SummarizingDtoResponse(result);
        return gson.toJson(summarizingDtoResponse);
    }

    public String clearDataBase() {
        expertDao.clearDataBase();
        return gson.toJson(new SuccessDtoResponse());
    }

    public static <T> T getClassInstanceFromJson(Gson gson, String json, Class<T> clazz) throws ServerException {
        try {
            return gson.fromJson(json, (Type) clazz);
        } catch (Exception ex) {
            throw new ServerException(ServerErrorCode.WRONG_JSON);
        }
    }

    private static void validateRegister(RegisterExpertDtoRequest object) throws ServerException {
        if (object.getFirstName() == null || object.getFirstName().isEmpty())
            throw new ServerException(ServerErrorCode.FIRSTNAME_SHORT);
        if (object.getLastName() == null || object.getLastName().isEmpty())
            throw new ServerException(ServerErrorCode.LASTNAME_SHORT);
        if (object.getDirections().isEmpty())
            throw new ServerException(ServerErrorCode.DIRECTIONS_EMPTY);
        if (object.getLogin() == null || object.getLogin().isEmpty() || object.getLogin().length() < 4)
            throw new ServerException(ServerErrorCode.LOGIN_SHORT);
        if (object.getPassword() == null || object.getPassword().isEmpty() || object.getPassword().length() < 6)
            throw new ServerException(ServerErrorCode.PASSWORD_SHORT);
    }

    private static void validateLogin(LoginExpertDtoRequest object) throws ServerException {
        if (object.getLogin() == null || object.getLogin().isEmpty())
            throw new ServerException(ServerErrorCode.LOGIN_EMPTY);
        if (object.getPassword() == null || object.getPassword().isEmpty())
            throw new ServerException(ServerErrorCode.PASSWORD_EMPTY);
    }

    private static void validatePutRating(PutRatingDtoRequest object, Expert expert) throws ServerException {
        if (object.getApplication() == null)
            throw new ServerException(ServerErrorCode.APPLICATION_EMPTY);
        if (object.getRating() < 1 || object.getRating() > 5)
            throw new ServerException(ServerErrorCode.RATING_WRONG);
        if (!expert.getDirections().containsAll(object.getApplication().getDirections()))
            throw new ServerException(ServerErrorCode.DIRECTION_WRONG);
    }

    private static void validateChangeRating(ChangeRatingDtoRequest object) throws ServerException {
        if (object.getApplication() == null)
            throw new ServerException(ServerErrorCode.APPLICATION_EMPTY);
        if (object.getRating() < 1 || object.getRating() > 5)
            throw new ServerException(ServerErrorCode.RATING_WRONG);
    }

    private static void validateDeleteRating(DeleteRatingDtoRequest object) throws ServerException {
        if (object.getApplication() == null)
            throw new ServerException(ServerErrorCode.APPLICATION_EMPTY);
    }

    public static void validateGetApplicationsByDirection(Expert expert, List<String> directions) throws ServerException {
        if (!expert.getDirections().containsAll(directions))
            throw new ServerException(ServerErrorCode.DIRECTION_WRONG);
    }

}
