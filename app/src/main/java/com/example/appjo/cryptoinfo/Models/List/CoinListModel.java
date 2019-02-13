package com.example.appjo.cryptoinfo.Models.List;

import com.google.gson.annotations.SerializedName;

public class CoinListModel {

    @SerializedName("CoinInfo")
    private CoinBasicInfo coinBasicInfo;

    public CoinBasicInfo getCoinBasicInfo() {
        return coinBasicInfo;
    }

    public void setCoinBasicInfo(CoinBasicInfo coinBasicInfo) {
        this.coinBasicInfo = coinBasicInfo;
    }
}
