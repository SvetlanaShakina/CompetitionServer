package net.competition;

import com.google.gson.Gson;
import net.competition.dto.request.*;
import net.competition.dto.response.*;
import net.competition.server.Server;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class TestExpert extends TestBase {
    private final Gson gson = new Gson();
    private final Server server = new Server();

    @Test
    public void testRegisterExpert() {
        RegisterExpertDtoRequest registerRequest = new RegisterExpertDtoRequest("Олег", "Шульгин",
                new ArrayList<>(Arrays.asList("Математика", "Физика", "Литература")), "Oleg_Sh", "888888");
        String jsonRequest = gson.toJson(registerRequest);
        String jsonResponse = server.registerExpert(jsonRequest);
        RegisterExpertDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterExpertDtoResponse.class);
        assertEquals(jsonResponse, gson.toJson(registerResponse));
    }

    @Test
    public void testRegisterExpertWithEmptyDirectionsList() {
        RegisterExpertDtoRequest registerRequest = new RegisterExpertDtoRequest("Виктор", "Иванов",
                new ArrayList<>(), "Ivanov_Viktor", "123456");
        String jsonRequest = gson.toJson(registerRequest);
        String jsonResponse = server.registerExpert(jsonRequest);
        ErrorDtoResponse errorDtoResponse = gson.fromJson(jsonResponse, ErrorDtoResponse.class);
        assertEquals(gson.toJson(errorDtoResponse), jsonResponse);
    }

    @Test
    public void testExpertLoginAndLogout() {
        RegisterExpertDtoRequest registerExpertRequest = new RegisterExpertDtoRequest("Олег", "Шульгин",
                new ArrayList<>(Arrays.asList("Математика", "Физика")), "Oleg_Sh", "888888");
        String jsonExpertRegisterRequest = gson.toJson(registerExpertRequest);
        String jsonExpertRegisterResponse = server.registerExpert(jsonExpertRegisterRequest);
        RegisterExpertDtoResponse registerExpertResponse = gson.fromJson(jsonExpertRegisterResponse, RegisterExpertDtoResponse.class);
        assertEquals(jsonExpertRegisterResponse, gson.toJson(registerExpertResponse));
        LoginExpertDtoRequest loginExpertDtoRequest = new LoginExpertDtoRequest("Oleg_Sh", "888888");
        String jsonLoginRequest = gson.toJson(loginExpertDtoRequest);
        String jsonLoginResponse = server.loginExpert(jsonLoginRequest);
        LoginExpertDtoResponse loginExpertDtoResponse = gson.fromJson(jsonLoginResponse, LoginExpertDtoResponse.class);
        assertEquals(jsonLoginResponse, gson.toJson(loginExpertDtoResponse));
        LogoutExpertDtoRequest logoutExpertDtoRequest = new LogoutExpertDtoRequest(registerExpertResponse.getToken());
        server.logoutExpert(gson.toJson(logoutExpertDtoRequest));
        GetApplicationByDirectionDtoRequest getApplicationByDirectionDtoRequest = new GetApplicationByDirectionDtoRequest(registerExpertResponse.getToken(),
                new ArrayList<>(Collections.singletonList("Математика")));
        String result = server.getApplicationsByDirection(gson.toJson(getApplicationByDirectionDtoRequest));
        ErrorDtoResponse errorDtoResponse = gson.fromJson(result, ErrorDtoResponse.class);
        assertEquals(result, gson.toJson(errorDtoResponse));
    }

    @Test
    public void testExpertPutAndChangeAndDeleteRating() {
        //регистрация конкурсанта
        RegisterContestantDtoRequest registerContestantRequest = new RegisterContestantDtoRequest("Иван", "Петров", "КомпанияС", "IvanP", "000000");
        String jsonContestantRegisterRequest = gson.toJson(registerContestantRequest);
        String jsonContestantRegisterResponse = server.registerContestant(jsonContestantRegisterRequest);
        RegisterContestantDtoResponse registerContestantResponse = gson.fromJson(jsonContestantRegisterResponse, RegisterContestantDtoResponse.class);
        assertEquals(jsonContestantRegisterResponse, gson.toJson(registerContestantResponse));
        //подача заявок
        ApplicationDtoRequest appReq1 = new ApplicationDtoRequest("Заявка1", "Новая заявка",
                new ArrayList<>(Collections.singletonList("Математика")), 500);
        AllApplicationDtoRequest allArrReq1 = new AllApplicationDtoRequest(registerContestantResponse.getToken(), appReq1);
        server.addApplication(gson.toJson(allArrReq1));
        ApplicationDtoRequest appReq2 = new ApplicationDtoRequest("Заявка2", "Новая заявка 2",
                new ArrayList<>(Collections.singletonList("Физика")), 200);
        AllApplicationDtoRequest allArrReq2 = new AllApplicationDtoRequest(registerContestantResponse.getToken(), appReq2);
        server.addApplication(gson.toJson(allArrReq2));
        //регистрация эксперта
        RegisterExpertDtoRequest registerExpertRequest = new RegisterExpertDtoRequest("Олег", "Шульгин",
                new ArrayList<>(Arrays.asList("Математика", "Физика")), "Oleg_Sh", "888888");
        String jsonExpertRegisterRequest = gson.toJson(registerExpertRequest);
        String jsonExpertRegisterResponse = server.registerExpert(jsonExpertRegisterRequest);
        RegisterExpertDtoResponse registerExpertResponse = gson.fromJson(jsonExpertRegisterResponse, RegisterExpertDtoResponse.class);
        assertEquals(jsonExpertRegisterResponse, gson.toJson(registerExpertResponse));
        //получение заявок по профилю
        GetApplicationByDirectionDtoRequest getApplicationByDirectionDtoRequest = new GetApplicationByDirectionDtoRequest(registerExpertResponse.getToken(),
                new ArrayList<>(Arrays.asList("Математика", "Физика")));
        String result = server.getApplicationsByDirection(gson.toJson(getApplicationByDirectionDtoRequest));
        ApplicationByDirectionDtoResponse applByDirResp = gson.fromJson(result, ApplicationByDirectionDtoResponse.class);
        assertEquals(2, applByDirResp.getApplications().size());
        //выставление оценки
        PutRatingDtoRequest putRatingDtoRequest = new PutRatingDtoRequest(registerExpertResponse.getToken(),
                applByDirResp.getApplications().get(0), 5);
        String jsonPutRatingResponse = server.putRating(gson.toJson(putRatingDtoRequest));
        SuccessDtoResponse successPutDtoResponse = new SuccessDtoResponse();
        assertEquals(jsonPutRatingResponse, gson.toJson(successPutDtoResponse));
        //выставление второй оценки
        PutRatingDtoRequest putRatingDtoRequest1 = new PutRatingDtoRequest(registerExpertResponse.getToken(),
                applByDirResp.getApplications().get(1), 5);
        String jsonPutRatingResponse1 = server.putRating(gson.toJson(putRatingDtoRequest1));
        SuccessDtoResponse successPutDtoResponse1 = new SuccessDtoResponse();
        assertEquals(jsonPutRatingResponse1, gson.toJson(successPutDtoResponse1));
        //изменение оценки
        ChangeRatingDtoRequest changeRatingRequest = new ChangeRatingDtoRequest(registerExpertResponse.getToken(),
                applByDirResp.getApplications().get(0), 4);
        String jsonChangeRatingResponse = server.changeRating(gson.toJson(changeRatingRequest));
        SuccessDtoResponse successChangeDtoResponse = new SuccessDtoResponse();
        assertEquals(jsonChangeRatingResponse, gson.toJson(successChangeDtoResponse));
        //подведение итогов
        SummarizingDtoRequest sumDtoReq = new SummarizingDtoRequest(registerExpertResponse.getToken(), 700, 3);
        String jsonReq = gson.toJson(sumDtoReq);
        String winners = server.summarizing(jsonReq);
        SummarizingDtoResponse summarizingDtoResponse = gson.fromJson(winners, SummarizingDtoResponse.class);
        assertEquals(2, summarizingDtoResponse.getListWinningApplication().size());
        Assert.assertEquals("Заявка2", summarizingDtoResponse.getListWinningApplication().get(0).getTitle());
        Assert.assertEquals("Заявка1", summarizingDtoResponse.getListWinningApplication().get(1).getTitle());
    }

    @Test
    public void testExpertGetApplicationsWithRating() {
        //регистрация конкурсанта
        RegisterContestantDtoRequest registerContestantRequest = new RegisterContestantDtoRequest("Иван", "Петров", "КомпанияС", "IvanP", "000000");
        String jsonContestantRegisterRequest = gson.toJson(registerContestantRequest);
        String jsonContestantRegisterResponse = server.registerContestant(jsonContestantRegisterRequest);
        RegisterContestantDtoResponse registerContestantResponse = gson.fromJson(jsonContestantRegisterResponse, RegisterContestantDtoResponse.class);
        assertEquals(jsonContestantRegisterResponse, gson.toJson(registerContestantResponse));
        //подача заявок
        ApplicationDtoRequest appReq1 = new ApplicationDtoRequest("Заявка1", "Новая заявка",
                new ArrayList<>(Collections.singletonList("Математика")), 500);
        AllApplicationDtoRequest allArrReq1 = new AllApplicationDtoRequest(registerContestantResponse.getToken(), appReq1);
        server.addApplication(gson.toJson(allArrReq1));
        ApplicationDtoRequest appReq2 = new ApplicationDtoRequest("Заявка2", "Новая заявка 2",
                new ArrayList<>(Collections.singletonList("Физика")), 200);
        AllApplicationDtoRequest allArrReq2 = new AllApplicationDtoRequest(registerContestantResponse.getToken(), appReq2);
        server.addApplication(gson.toJson(allArrReq2));
        //регистрация эксперта
        RegisterExpertDtoRequest registerExpertRequest = new RegisterExpertDtoRequest("Олег", "Шульгин",
                new ArrayList<>(Arrays.asList("Математика", "Физика")), "Oleg_Sh", "888888");
        String jsonExpertRegisterRequest = gson.toJson(registerExpertRequest);
        String jsonExpertRegisterResponse = server.registerExpert(jsonExpertRegisterRequest);
        RegisterExpertDtoResponse registerExpertResponse = gson.fromJson(jsonExpertRegisterResponse, RegisterExpertDtoResponse.class);
        assertEquals(jsonExpertRegisterResponse, gson.toJson(registerExpertResponse));
        //получение заявок по профилю
        GetApplicationByDirectionDtoRequest getApplicationByDirectionDtoRequest = new GetApplicationByDirectionDtoRequest(registerExpertResponse.getToken(),
                new ArrayList<>(Arrays.asList("Математика", "Физика")));
        String result = server.getApplicationsByDirection(gson.toJson(getApplicationByDirectionDtoRequest));
        ApplicationByDirectionDtoResponse applByDirResp = gson.fromJson(result, ApplicationByDirectionDtoResponse.class);
        assertEquals(2, applByDirResp.getApplications().size());
        //выставление оценок
        PutRatingDtoRequest putRatingDtoRequest = new PutRatingDtoRequest(registerExpertResponse.getToken(),
                applByDirResp.getApplications().get(0), 5);
        server.putRating(gson.toJson(putRatingDtoRequest));
        PutRatingDtoRequest putRatingDtoRequest1 = new PutRatingDtoRequest(registerExpertResponse.getToken(),
                applByDirResp.getApplications().get(1), 4);
        server.putRating(gson.toJson(putRatingDtoRequest1));
        //получение заявок с оценками
        GetApplicationsWithRatingDtoRequest getApplicationsWithRatingRequest = new GetApplicationsWithRatingDtoRequest(registerExpertResponse.getToken());
        String jsonResponse = server.getApplicationsWithRating(gson.toJson(getApplicationsWithRatingRequest));
        ApplicationsWithRatingDtoResponse applicationsWithRating = gson.fromJson(jsonResponse, ApplicationsWithRatingDtoResponse.class);
        applicationsWithRating.getApplications().sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
        assertEquals(2, applicationsWithRating.getApplications().size());
        Assert.assertEquals("Заявка1", applicationsWithRating.getApplications().get(0).getTitle());
    }
}
