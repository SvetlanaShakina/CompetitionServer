package net.competition;

import com.google.gson.Gson;
import net.competition.dto.request.*;
import net.competition.dto.response.ErrorDtoResponse;
import net.competition.dto.response.LoginContestantDtoResponse;
import net.competition.dto.response.RegisterContestantDtoResponse;
import net.competition.dto.response.SuccessDtoResponse;
import net.competition.server.Server;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class TestContestant extends TestBase {
    private final Gson gson = new Gson();
    private final Server server = new Server();

    @Test
    public void testRegisterContestant() {
        RegisterContestantDtoRequest registerRequest = new RegisterContestantDtoRequest("Петр",
                "Иванов", "КомпанияА", "PetrI", "111111");
        String jsonRequest = gson.toJson(registerRequest);
        String jsonResponse = server.registerContestant(jsonRequest);
        RegisterContestantDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterContestantDtoResponse.class);
        assertEquals(jsonResponse, gson.toJson(registerResponse));
    }

    @Test
    public void testDeleteContestant() {
        RegisterContestantDtoRequest registerRequest = new RegisterContestantDtoRequest("Иван",
                "Петров", "КомпанияС", "IvanP", "000000");
        String jsonRequest = gson.toJson(registerRequest);
        String jsonResponse = server.registerContestant(jsonRequest);
        RegisterContestantDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterContestantDtoResponse.class);
        assertEquals(jsonResponse, gson.toJson(registerResponse));
        DeleteContestantDtoRequest deleteContestantDtoRequest = new DeleteContestantDtoRequest(registerResponse.getToken());
        String jsonDeleteRequest = gson.toJson(deleteContestantDtoRequest);
        String jsonDeleteResponse = server.deleteContestant(jsonDeleteRequest);
        SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
        assertEquals(gson.toJson(successDtoResponse), jsonDeleteResponse);
    }

    @Test
    public void testRegisterContestantWithShortPassword() {
        RegisterContestantDtoRequest registerRequest = new RegisterContestantDtoRequest("Олег",
                "Орлов", "КомпанияА", "OlegO", "00");
        String jsonRequest = gson.toJson(registerRequest);
        String jsonResponse = server.registerContestant(jsonRequest);
        ErrorDtoResponse errorDtoResponse = gson.fromJson(jsonResponse, ErrorDtoResponse.class);
        assertEquals(gson.toJson(errorDtoResponse), jsonResponse);
    }

    @Test
    public void testRegisterContestantWithEmptyFirstname() {
        RegisterContestantDtoRequest registerRequest = new RegisterContestantDtoRequest("",
                "Петров", "КомпанияБ", "IvanP", "000000");
        String jsonRequest = gson.toJson(registerRequest);
        String jsonResponse = server.registerContestant(jsonRequest);
        ErrorDtoResponse errorDtoResponse3 = gson.fromJson(jsonResponse, ErrorDtoResponse.class);
        assertEquals(gson.toJson(errorDtoResponse3), jsonResponse);
    }

    @Test
    public void testRegisterContestantsWithSameLogin() {
        RegisterContestantDtoRequest registerRequest = new RegisterContestantDtoRequest("Петр",
                "Иванов", "КомпанияА", "PetrI", "111111");
        String jsonRequest = gson.toJson(registerRequest);
        String jsonResponse = server.registerContestant(jsonRequest);
        RegisterContestantDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterContestantDtoResponse.class);
        assertEquals(jsonResponse, gson.toJson(registerResponse));
        RegisterContestantDtoRequest registerRequest2 = new RegisterContestantDtoRequest("Петр", "Исаев", "КомпанияZ", "PetrI", "1111111");
        String jsonRequest2 = gson.toJson(registerRequest2);
        String jsonResponse2 = server.registerContestant(jsonRequest2);
        ErrorDtoResponse errorDtoResponse = gson.fromJson(jsonResponse2, ErrorDtoResponse.class);
        assertEquals(gson.toJson(errorDtoResponse), jsonResponse2);
    }

    @Test
    public void testLoginContestant() {
        RegisterContestantDtoRequest registerRequest = new RegisterContestantDtoRequest("Иван", "Петров", "КомпанияС", "IvanP", "000000");
        String jsonRequest = gson.toJson(registerRequest);
        String jsonResponse = server.registerContestant(jsonRequest);
        RegisterContestantDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterContestantDtoResponse.class);
        assertEquals(jsonResponse, gson.toJson(registerResponse));
        LoginContestantDtoRequest loginRequest = new LoginContestantDtoRequest("IvanP", "000000");
        String jsonLoginRequest = gson.toJson(loginRequest);
        String jsonLoginResponse = server.loginContestant(jsonLoginRequest);
        LoginContestantDtoResponse loginResponse = gson.fromJson(jsonLoginResponse, LoginContestantDtoResponse.class);
        assertEquals(jsonLoginResponse, gson.toJson(loginResponse));
    }

    @Test
    public void testLoginContestantWithWrongLogin() {
        RegisterContestantDtoRequest registerRequest = new RegisterContestantDtoRequest("Иван", "Петров", "КомпанияС", "IvanP", "000000");
        String jsonRequest = gson.toJson(registerRequest);
        String jsonResponse = server.registerContestant(jsonRequest);
        RegisterContestantDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterContestantDtoResponse.class);
        assertEquals(jsonResponse, gson.toJson(registerResponse));
        LoginContestantDtoRequest loginRequest = new LoginContestantDtoRequest("Ivvan", "000000");
        String jsonLoginRequest = gson.toJson(loginRequest);
        String jsonLoginResponse = server.loginContestant(jsonLoginRequest);
        ErrorDtoResponse errorDtoResponse = gson.fromJson(jsonLoginResponse, ErrorDtoResponse.class);
        assertEquals(gson.toJson(errorDtoResponse), jsonLoginResponse);
    }

    @Test
    public void testAddApplication() {
        RegisterContestantDtoRequest registerRequest = new RegisterContestantDtoRequest("Иван", "Петров", "КомпанияС", "IvanP", "000000");
        String jsonRequest = gson.toJson(registerRequest);
        server.registerContestant(jsonRequest);
        LoginContestantDtoRequest loginRequest = new LoginContestantDtoRequest("IvanP", "000000");
        String jsonLoginRequest = gson.toJson(loginRequest);
        String jsonLoginResponse = server.loginContestant(jsonLoginRequest);
        LoginContestantDtoResponse loginResponse = gson.fromJson(jsonLoginResponse, LoginContestantDtoResponse.class);
        ApplicationDtoRequest applicationDtoRequest = new ApplicationDtoRequest("Заявка1", "Новая заявка",
                new ArrayList<>(Collections.singletonList("Химия")), 500);
        AllApplicationDtoRequest allApplicationDtoRequest = new AllApplicationDtoRequest(loginResponse.getToken(),
                applicationDtoRequest);
        String jsonAddApplicationResponse = server.addApplication(gson.toJson(allApplicationDtoRequest));
        SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
        assertEquals(gson.toJson(successDtoResponse), jsonAddApplicationResponse);
    }

    @Test
    public void testCancelApplicationAndLogout() {
        RegisterContestantDtoRequest registerRequest = new RegisterContestantDtoRequest("Иван", "Петров", "КомпанияС", "IvanP", "000000");
        String jsonRequest = gson.toJson(registerRequest);
        server.registerContestant(jsonRequest);
        LoginContestantDtoRequest loginRequest = new LoginContestantDtoRequest("IvanP", "000000");
        String jsonLoginRequest = gson.toJson(loginRequest);
        String jsonLoginResponse = server.loginContestant(jsonLoginRequest);
        LoginContestantDtoResponse loginResponse = gson.fromJson(jsonLoginResponse, LoginContestantDtoResponse.class);
        ApplicationDtoRequest applicationDtoRequest = new ApplicationDtoRequest("Заявка1", "Новая заявка",
                new ArrayList<>(Collections.singletonList("Химия")), 500);
        AllApplicationDtoRequest allApplicationDtoRequest = new AllApplicationDtoRequest(loginResponse.getToken(),
                applicationDtoRequest);
        server.addApplication(gson.toJson(allApplicationDtoRequest));
        String jsonCancelApplicationResponse = server.cancelApplication(gson.toJson(allApplicationDtoRequest));
        SuccessDtoResponse successDtoResponse = new SuccessDtoResponse();
        assertEquals(gson.toJson(successDtoResponse), jsonCancelApplicationResponse);
        LogoutContestantDtoRequest logoutRequest = new LogoutContestantDtoRequest(loginResponse.getToken());
        server.logoutContestant(gson.toJson(logoutRequest));
        String response = server.addApplication(gson.toJson(allApplicationDtoRequest));
        ErrorDtoResponse errorDtoResponse = gson.fromJson(response, ErrorDtoResponse.class);
        assertEquals(gson.toJson(errorDtoResponse), response);
    }
}