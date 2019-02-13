package com.example.appjo.cryptoinfo.Models.Details;

import com.google.gson.annotations.SerializedName;

public class CoinData {

    @SerializedName("PRICE")
    private String price;

    @SerializedName("MKTCAP")
    private String mkt_cap;

    @SerializedName("HIGH24HOUR")
    private String high;

    @SerializedName("LOW24HOUR")
    private String low;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMkt_cap() {
        return mkt_cap;
    }

    public void setMkt_cap(String mkt_cap) {
        this.mkt_cap = mkt_cap;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }
}
