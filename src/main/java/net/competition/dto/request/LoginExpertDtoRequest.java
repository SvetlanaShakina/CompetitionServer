package net.competition.dto.request;

public class LoginExpertDtoRequest {
    private String login;
    private String password;

    public LoginExpertDtoRequest(String login, String password) {
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
