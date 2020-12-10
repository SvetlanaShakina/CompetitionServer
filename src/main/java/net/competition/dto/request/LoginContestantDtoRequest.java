package net.competition.dto.request;

public class LoginContestantDtoRequest {
    private String login;
    private String password;

    public LoginContestantDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
