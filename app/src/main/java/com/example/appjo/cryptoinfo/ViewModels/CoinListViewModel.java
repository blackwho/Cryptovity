package com.example.appjo.cryptoinfo.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.appjo.cryptoinfo.Models.List.CoinListModel;
import com.example.appjo.cryptoinfo.Repository.AppRepository;

import java.util.List;

public class CoinListViewModel extends AndroidViewModel {

    private AppRepository appRepository;
    private LiveData<List<CoinListModel>> basicInfoList;

    public CoinListViewModel(Application application){
        super(application);
        appRepository = AppRepository.getInstance(application);
    }

    //initializes the api call
    public void init(){
        basicInfoList = appRepository.getCoins();
    }

    //returns the basicInfoList of each coin
    public LiveData<List<CoinListModel>> getBasicInfoList() {
        return basicInfoList;
    }
}
