package com.example.appjo.cryptoinfo.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.appjo.cryptoinfo.Models.Graph.CoinHistory;
import com.example.appjo.cryptoinfo.Repository.AppRepository;

import java.util.List;

public class CoinDetailViewModel extends AndroidViewModel {

    private AppRepository appRepository;
    private MutableLiveData<String> coinPrice;
    private MutableLiveData<String> coinMarketCap;
    private MutableLiveData<String> coinHigh;
    private MutableLiveData<String> coinLow;
    private LiveData<List<CoinHistory>> coinHistory;

    public CoinDetailViewModel(Application application){
        super(application);
        appRepository = AppRepository.getInstance(application);
        coinPrice = appRepository.getCoinPrice();
        coinMarketCap = appRepository.getCoinMarketCap();
        coinHigh = appRepository.getCoinHigh();
        coinLow = appRepository.getCoinLow();
    }

    public void setGraphDataRequest(String coin, String currency){
        coinHistory = appRepository.getCoinGraphData(coin, currency);
    }
    public void scheduleCoinUpdateService(String coin, String currency){
        appRepository.scheduleService(coin, currency);
    }

    public void stopCoinUpdateService(){
        appRepository.stopService();
    }

    public MutableLiveData<String> getPrice() {
        return coinPrice;
    }

    public MutableLiveData<String> getMarketCap() {
        return coinMarketCap;
    }

    public MutableLiveData<String> getHigh() {
        return coinHigh;
    }

    public MutableLiveData<String> getLow() {
        return coinLow;
    }

    public LiveData<List<CoinHistory>> getCoinHistory() {
        return coinHistory;
    }
}
