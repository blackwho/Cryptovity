package com.example.appjo.cryptoinfo.Models.List;

import com.google.gson.annotations.SerializedName;

public class CoinBulkModel {

    @SerializedName("Message")
    private String message;

    @SerializedName("Type")
    private String result_count;

    @SerializedName("Data")
    private CoinListModel [] coinListModels;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult_count() {
        return result_count;
    }

    public void setResult_count(String result_count) {
        this.result_count = result_count;
    }

    public CoinListModel[] getCoinListModels() {
        return coinListModels;
    }

    public void setCoinListModels(CoinListModel[] coinListModels) {
        this.coinListModels = coinListModels;
    }
}
