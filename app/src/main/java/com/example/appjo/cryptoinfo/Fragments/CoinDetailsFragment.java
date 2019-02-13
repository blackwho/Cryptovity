package com.example.appjo.cryptoinfo.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.appjo.cryptoinfo.R;
import com.example.appjo.cryptoinfo.ViewModels.CoinDetailViewModel;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CoinDetailsFragment extends Fragment {
    private static final String TAG = CoinDetailsFragment.class.getSimpleName();
    private ProgressBar mProgressBar;
    private String coin;
    private String currency;
    private CandleStickChart candleStickChart;
    private CoinDetailViewModel mViewModel;
    private TextView priceTv;
    private TextView mktCapTv;
    private TextView highTv;
    private TextView lowTv;
    private ArrayList<String> xAxisLabel = new ArrayList<>();
    ArrayList<CandleEntry> yValsCandleStick= new ArrayList<CandleEntry>();
    public CoinDetailsFragment(){}

    @Override
    public void onResume(){
        mViewModel.scheduleCoinUpdateService(coin, currency);
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.coin_detail_fragment, viewGroup, false);
        try{
             coin = getArguments().getString("coin");
             currency = getArguments().getString("currency");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        Description description = new Description();
        description.setText("");
        candleStickChart = rootView.findViewById(R.id.chart);
        candleStickChart.setDescription(description);
        candleStickChart.setNoDataText("Loading " + coin + " data...");
        candleStickChart.setHighlightPerDragEnabled(true);
        candleStickChart.setDrawBorders(true);
        YAxis yAxis = candleStickChart.getAxisLeft();
        YAxis rightAxis = candleStickChart.getAxisRight();
        yAxis.setTextColor(getResources().getColor(R.color.white));
        yAxis.setDrawGridLines(true);
        yAxis.setGridColor(getResources().getColor(R.color.light_gray));
        yAxis.setDrawLabels(true);
        rightAxis.setDrawGridLines(true);
        rightAxis.setGridColor(getResources().getColor(R.color.light_gray));
        rightAxis.setTextColor(getResources().getColor(R.color.white));
        candleStickChart.requestDisallowInterceptTouchEvent(true);
        XAxis xAxis = candleStickChart.getXAxis();
        xAxis.setTextColor(getResources().getColor(R.color.white));
        xAxis.setDrawGridLines(true);//x axis grid lines
        xAxis.setGridColor(getResources().getColor(R.color.light_gray));
        xAxis.setDrawLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);
        Legend l = candleStickChart.getLegend();
        l.setEnabled(false);

        priceTv = rootView.findViewById(R.id.price_tv);
        mktCapTv = rootView.findViewById(R.id.market_cap_tv);
        highTv = rootView.findViewById(R.id.high_value_tv);
        lowTv = rootView.findViewById(R.id.low_value_tv);
        mViewModel = ViewModelProviders.of(this).get(CoinDetailViewModel.class);
        mViewModel.setGraphDataRequest(coin, currency);
        mViewModel.getCoinHistory().observe(this, data -> {
            if (data != null){
                for (int i = 0; i < data.size(); i++){
                    Date date = new Date(Long.valueOf(data.get(i).getTime()) * 1000);
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM d", Locale.US);
                    String formattedDate = sdf.format(date);
                    Log.v(TAG, "Time Watcher..." + formattedDate);
                    yValsCandleStick.add(new CandleEntry(i, Float.valueOf(data.get(i).getPriceHigh()), Float.valueOf(data.get(i).getPriceLow()), Float.valueOf(data.get(i).getPriceOpen()), Float.valueOf(data.get(i).getPriceClose())));
                    xAxisLabel.add(formattedDate);
                }
                CandleDataSet set = new CandleDataSet(yValsCandleStick, "Set");
                set.setColor(Color.rgb(80, 80, 80));
                set.setShadowColor(getResources().getColor(R.color.light_gray2));
                set.setShadowWidth(0.8f);
                set.setDecreasingColor(getResources().getColor(R.color.red_deep));
                set.setDecreasingPaintStyle(Paint.Style.FILL);
                set.setIncreasingColor(getResources().getColor(R.color.green));
                set.setIncreasingPaintStyle(Paint.Style.FILL);
                set.setNeutralColor(Color.LTGRAY);
                set.setDrawValues(false);
                xAxis.setValueFormatter(new IAxisValueFormatter(){
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        if (value <= -1){
                            return "";
                        }else if(value >= data.size()){
                            return "";
                        }else {
                            return xAxisLabel.get((int) value);
                        }
                    }
                });
                CandleData candleData = new CandleData(set);
                candleStickChart.setData(candleData);
                candleStickChart.invalidate();
            }
        });
        mViewModel.getPrice().observe(this, data ->{
            if (data != null){
                priceTv.setText(data);
            }
        });
        mViewModel.getMarketCap().observe(this, data -> {
            if (data != null){
                mktCapTv.setText(data);
            }
        });
        mViewModel.getHigh().observe(this, data -> {
            if (data != null){
                highTv.setText(data);
            }
        });
        mViewModel.getLow().observe(this, data -> {
            if (data != null){
                lowTv.setText(data);
            }
        });
        return rootView;
    }

    @Override
    public void onPause(){
        mViewModel.stopCoinUpdateService();
        super.onPause();
    }

    public CoinDetailViewModel getViewModel(){
        return mViewModel;
    }

}
