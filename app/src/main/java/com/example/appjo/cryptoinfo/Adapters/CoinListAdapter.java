package com.example.appjo.cryptoinfo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.appjo.cryptoinfo.Models.List.CoinBasicInfo;
import com.example.appjo.cryptoinfo.Models.List.CoinListModel;
import com.example.appjo.cryptoinfo.R;

import java.util.ArrayList;
import java.util.List;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CoinViewHolder> {
    private List<CoinListModel> coins = new ArrayList<>();
    private Context context;
    private String currency = "USD";
    private final OnCoinClickListener mClickHandler;

    public CoinListAdapter(Context mCtx, OnCoinClickListener clickListener){
        mClickHandler = clickListener;
        this.context = mCtx;
    }
    public interface OnCoinClickListener{
         void onCoinClick(String coin, String currency);
    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coin_list_item, parent, false);
        CoinViewHolder viewHolder = new CoinViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        CoinBasicInfo coinBasicInfo = coins.get(position).getCoinBasicInfo();
        if (coinBasicInfo != null){
            Glide.with(context)
                    .load("https://www.cryptocompare.com" + coinBasicInfo.getImage_url())
                    .into(holder.coinIv);
            holder.coinFullNameTv.setText(coinBasicInfo.getFull_name());
            holder.coinNameTv.setText(coinBasicInfo.getName());
            holder.coinCurrencyTv.setText(currency);
        }
    }

    @Override
    public int getItemCount() {
        if (coins.size() > 0 ){
           return coins.size();
        }else {
            return 0;
        }
    }

    public void submitList(List<CoinListModel> data){
        coins = data;
        notifyDataSetChanged();
    }
    public class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView coinIv;
        public TextView coinFullNameTv;
        public TextView coinNameTv;
        public TextView coinCurrencyTv;
        public CoinViewHolder(View view){
            super(view);
            coinIv = view.findViewById(R.id.coin_iv);
            coinFullNameTv = view.findViewById(R.id.coin_full_name_tv);
            coinNameTv = view.findViewById(R.id.coin_name_tv);
            coinCurrencyTv = view.findViewById(R.id.coin_currency_tv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            CoinBasicInfo coinBasicInfo = coins.get(adapterPosition).getCoinBasicInfo();
            String coinName = coinBasicInfo.getName();
            String coinCurrency  = "USD";
            mClickHandler.onCoinClick(coinName, coinCurrency);
        }
    }
}
