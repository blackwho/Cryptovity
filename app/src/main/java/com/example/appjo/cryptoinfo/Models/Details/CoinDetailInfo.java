package com.example.appjo.cryptoinfo.Models.Details;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class CoinDetailInfo {

    @SerializedName("DISPLAY")
    private HashMap<String, HashMap<String, CoinData>> coinDataHashMap;

    public HashMap<String, HashMap<String, CoinData>> getCoinDataHashMap() {
        return coinDataHashMap;
    }

    public void setCoinDataHashMap(HashMap<String, HashMap<String, CoinData>> data) {
        this.coinDataHashMap = data;
    }
}
