package com.example.appjo.cryptoinfo.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.example.appjo.cryptoinfo.Models.Details.CoinData;
import com.example.appjo.cryptoinfo.Models.Details.CoinDetailInfo;
import com.example.appjo.cryptoinfo.Utils.ApiRequest;
import com.example.appjo.cryptoinfo.Utils.UrlBuilder;
import com.example.appjo.cryptoinfo.Utils.VolleySingleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

//service class for frequent coin data update
public class CoinDataUpdateService extends Service {
    private static final String TAG = CoinDataUpdateService.class.getSimpleName();
    private Timer timer = new Timer();
    private RequestQueue requestQueue;
    private String coin;
    private String currency;
    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // Code to execute when the service is first created
        Log.v(TAG, "Service started");
        context = getApplicationContext();
        requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
        if (requestQueue != null){
            Log.v(TAG, "RequestQueue" + requestQueue);
        }
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "Service started");
        if (timer != null){
            timer.cancel();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        Log.v(TAG, "I am here");
        coin = intent.getStringExtra("coin");
        currency = intent.getStringExtra("currency");
        String url = UrlBuilder.getInstance().getUrl(coin, currency, "details");
        //schedules the specified task for repeated fixed-rate execution
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ApiRequest<CoinDetailInfo> mRequest = new ApiRequest<>(Request.Method.GET,
                        url,
                        CoinDetailInfo.class,
                        null,
                        null,
                        new Response.Listener<CoinDetailInfo>() {
                            @Override
                            public void onResponse(CoinDetailInfo response) {
                                try{
                                    if (response != null){
                                        Log.v(TAG, "Response " + response);
                                        if (response.getCoinDataHashMap().containsKey(coin)){
                                            HashMap<String, CoinData> dataHashMap = response.getCoinDataHashMap().get(coin);
                                            if (dataHashMap.containsKey(currency)){
                                                CoinData coinData = dataHashMap.get(currency);
                                                Log.v(TAG, "CoinData " + coinData);
                                                Intent broadcastIntent = new Intent();
                                                broadcastIntent.setAction("action.background.data.receiver");
                                                broadcastIntent.putExtra("price", coinData.getPrice());
                                                broadcastIntent.putExtra("market_cap", coinData.getMkt_cap());
                                                broadcastIntent.putExtra("high", coinData.getHigh());
                                                broadcastIntent.putExtra("low", coinData.getLow());
                                                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                                            }
                                        }
                                    }

                                }catch (NullPointerException e){
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.v(TAG, "Error... Please look into the code");
                            }
                        });
                mRequest.setShouldCache(false);
                requestQueue.add(mRequest);
            }
        }, 0, 30000);

        return START_REDELIVER_INTENT;
    }
}
