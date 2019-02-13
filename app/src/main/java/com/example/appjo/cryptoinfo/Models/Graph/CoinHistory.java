package com.example.appjo.cryptoinfo.Models.Graph;

import android.text.format.DateFormat;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Locale;

public class CoinHistory {
    @SerializedName("time")
    private String time;

    @SerializedName("high")
    private String priceHigh;

    @SerializedName("low")
    private String priceLow;

    @SerializedName("open")
    private String priceOpen;

    @SerializedName("close")
    private String priceClose;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(String priceHigh) {
        this.priceHigh = priceHigh;
    }

    public String getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(String priceLow) {
        this.priceLow = priceLow;
    }

    public void setPriceClose(String priceClose) {
        this.priceClose = priceClose;
    }

    public String getPriceClose() {
        return priceClose;
    }

    public void setPriceOpen(String priceOpen) {
        this.priceOpen = priceOpen;
    }

    public String getPriceOpen() {
        return priceOpen;
    }
}
