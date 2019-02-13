package com.example.appjo.cryptoinfo.Models.Graph;

import com.google.gson.annotations.SerializedName;

public class CoinGraphData {

    @SerializedName("Response")
    private String response;

    @SerializedName("Data")
    private CoinHistory[] coinHistory;

    @SerializedName("TimeTo")
    private String timeTo;

    @SerializedName("TimeFrom")
    private String timeFrom;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public CoinHistory[] getCoinHistory() {
        return coinHistory;
    }

    public void setCoinHistory(CoinHistory[] coinHistory) {
        this.coinHistory = coinHistory;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }
}
