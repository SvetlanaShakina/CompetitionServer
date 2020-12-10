package net.competition.dto.request;

public class RegisterContestantDtoRequest {
    private String firstName;
    private String lastName;
    private String companyName;
    private String login;
    private String password;

    public RegisterContestantDtoRequest(String firstName, String lastName, String companyName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.login = login;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
