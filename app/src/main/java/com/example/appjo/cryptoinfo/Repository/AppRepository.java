package com.example.appjo.cryptoinfo.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.example.appjo.cryptoinfo.Models.Graph.CoinGraphData;
import com.example.appjo.cryptoinfo.Models.Graph.CoinHistory;
import com.example.appjo.cryptoinfo.Models.List.CoinBulkModel;
import com.example.appjo.cryptoinfo.Models.List.CoinListModel;
import com.example.appjo.cryptoinfo.Services.CoinDataUpdateService;
import com.example.appjo.cryptoinfo.Utils.ApiRequest;
import com.example.appjo.cryptoinfo.Utils.UrlBuilder;
import com.example.appjo.cryptoinfo.Utils.VolleySingleton;

import java.util.Arrays;
import java.util.List;

public class AppRepository {

    private static final String TAG = AppRepository.class.getSimpleName();
    private static AppRepository mInstance;
    private static Context context;
    private RequestQueue mRequestQueue;
    private Intent serviceIntent;
    private ServiceReceiver serviceReceiver;
    private MutableLiveData<String> coinPrice = new MutableLiveData<>();
    private MutableLiveData<String> coinMarketCap = new MutableLiveData<>();
    private MutableLiveData<String> coinHigh = new MutableLiveData<>();
    private MutableLiveData<String> coinLow = new MutableLiveData<>();

    private AppRepository(Application application){
        mRequestQueue = VolleySingleton.getInstance(application.getApplicationContext()).getRequestQueue();
        context = application.getApplicationContext();
    }

    public static AppRepository getInstance(Application application){
        if (mInstance == null){
            mInstance = new AppRepository(application);
        }
        return mInstance;
    }

    public MutableLiveData<String> getCoinPrice() {
        return coinPrice;
    }

    public MutableLiveData<String> getCoinMarketCap() {
        return coinMarketCap;
    }

    public MutableLiveData<String> getCoinHigh() {
        return coinHigh;
    }

    public MutableLiveData<String> getCoinLow() {
        return coinLow;
    }

    //schedule background api-call service
    public void scheduleService(String coin, String currency){
        registerServiceReceiver();
        serviceIntent = new Intent(context, CoinDataUpdateService.class);
        serviceIntent.putExtra("coin", coin);
        serviceIntent.putExtra("currency", currency);
        serviceIntent.setAction("action.background.coin.service");
        context.startService(serviceIntent);
    }

    //stop background api-call service
    public void stopService(){
        unregisterServiceReceiver();
        context.stopService(serviceIntent);
        coinPrice.setValue("$ 0");
        coinLow.setValue("$ 0");
        coinHigh.setValue("$ 0");
        coinMarketCap.setValue("$ 0 B");
    }

    //Retrieving coin-list for dashboard using volley
    public LiveData<List<CoinListModel>> getCoins(){
        final MutableLiveData<List<CoinListModel>> coinList = new MutableLiveData<>();
        String url = UrlBuilder.getInstance().getUrl(null, null, "coins");
        ApiRequest<CoinBulkModel> mRequest = new ApiRequest<>(Request.Method.GET,
                url,
                CoinBulkModel.class,
                null,
                null,
                new Response.Listener<CoinBulkModel>() {
                    @Override
                    public void onResponse(CoinBulkModel response) {
                        try{
                            if (response != null && response.getMessage().equals("Success")){
                                Log.v(TAG, "Total " + response.getResult_count());
                                coinList.postValue(Arrays.asList((CoinListModel[]) response.getCoinListModels()));
                            }else if (response == null || !response.getMessage().equals("Success")){
                                Toast.makeText(context, "Server Failure", Toast.LENGTH_LONG).show();
                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "No Network Connection", Toast.LENGTH_LONG).show();
                    }
                });
        mRequest.setShouldCache(true);
        mRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.v(TAG, "Failed! Now Retrying...");
            }
        });

        mRequestQueue.add(mRequest);
        return coinList;
    }

    //Retrieving graph data for specific coin
    public LiveData<List<CoinHistory>> getCoinGraphData(String coin, String currency){

        MutableLiveData<List<CoinHistory>> coinGraphData = new MutableLiveData<>();
        String url = UrlBuilder.getInstance().getUrl(coin, currency, "graph");
        ApiRequest<CoinGraphData> mRequest = new ApiRequest<>(Request.Method.GET,
                url,
                CoinGraphData.class,
                null,
                null,
                new Response.Listener<CoinGraphData>() {
                    @Override
                    public void onResponse(CoinGraphData response) {
                        try {
                           if (response != null && response.getResponse().equals("Success")){
                               Log.v(TAG, "Response " + response);
                               coinGraphData.postValue(Arrays.asList((CoinHistory[]) response.getCoinHistory()));
                           }else if (response == null || !response.getResponse().equals("Success")){
                               Toast.makeText(context, "Server Down", Toast.LENGTH_LONG).show();
                           }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "No Network Connection", Toast.LENGTH_LONG).show();
                    }
                });
        mRequest.setShouldCache(false);
        mRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.v(TAG, "Failed! Now Retrying...");
            }
        });

        mRequestQueue.add(mRequest);
        return coinGraphData;
    }



    //register the receiver
    private void registerServiceReceiver(){
        serviceReceiver = new ServiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.background.data.receiver");
        LocalBroadcastManager.getInstance(context).registerReceiver(serviceReceiver, intentFilter);
    }

    //unregister the receiver
    private void unregisterServiceReceiver(){
        if (serviceReceiver != null){
            LocalBroadcastManager.getInstance(context).unregisterReceiver(serviceReceiver);
        }
    }

    public class ServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String price = intent.getStringExtra("price");
            String marketCap = intent.getStringExtra("market_cap");
            String high = intent.getStringExtra("high");
            String low = intent.getStringExtra("low");
            if (price != null){
                coinPrice.postValue(price);
            }
            if (marketCap != null){
                coinMarketCap.postValue(marketCap);
            }
            if (high != null){
                coinHigh.postValue(high);
            }
            if (low != null){
                coinLow.postValue(low);
            }
        }
    }
}
