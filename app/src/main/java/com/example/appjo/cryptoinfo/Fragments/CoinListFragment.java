package com.example.appjo.cryptoinfo.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.example.appjo.cryptoinfo.Adapters.CoinListAdapter;
import com.example.appjo.cryptoinfo.R;
import com.example.appjo.cryptoinfo.ViewModels.CoinListViewModel;

public class CoinListFragment extends Fragment{

    public static final String TAG = CoinListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private CoinListAdapter mAdapter;
    private CoinListViewModel mViewModel;
    private ProgressBar mProgressBar;
    public CoinListFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance){
        View rootView = layoutInflater.inflate(R.layout.coin_list_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(CoinListViewModel.class);
        mViewModel.init();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mProgressBar = rootView.findViewById(R.id.indeterminate_progress_bar);
        mRecyclerView = rootView.findViewById(R.id.coin_list_rv);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CoinListAdapter(getContext(), listener);
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.VISIBLE);
        viewModelAttach();
        return rootView;
    }

    private void viewModelAttach(){

        mViewModel.getBasicInfoList().observe(CoinListFragment.this, data -> {
            Log.v(TAG, "List" + data);
            if (data != null){
                mAdapter.submitList(data);
                mProgressBar.setVisibility(View.GONE);
            }

        });
    }

    CoinListAdapter.OnCoinClickListener listener = new CoinListAdapter.OnCoinClickListener() {
        @Override
        public void onCoinClick(String coin,String currency) {
            CoinDetailsFragment coinDetailsFragment = new CoinDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("coin",coin);
            bundle.putString("currency",currency);
            coinDetailsFragment.setArguments(bundle);
            try {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.dashboard_fragment_container, coinDetailsFragment, "CoinDetailsFrag")
                        .addToBackStack(null)
                        .commit();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    };

    //for testing
    public CoinListViewModel getViewModel(){
        return mViewModel;
    }

}
