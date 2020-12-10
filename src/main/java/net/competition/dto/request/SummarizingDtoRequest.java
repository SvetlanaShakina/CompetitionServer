package net.competition.dto.request;

public class SummarizingDtoRequest {
    private String token;
    private int generalGrantFund;
    private double minRating;

    public SummarizingDtoRequest(String token, int generalGrantFund, double minRating) {
        this.token = token;
        this.generalGrantFund = generalGrantFund;
        this.minRating = minRating;
    }

    public String getToken() {
        return token;
    }

    public int getGeneralGrantFund() {
        return generalGrantFund;
    }

    public double getMinRating() {
        return minRating;
    }
}
