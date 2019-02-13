package com.example.appjo.cryptoinfo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.appjo.cryptoinfo.Fragments.CoinListFragment;

/*
    Author: Vishal Roy
    Date: 12/12/2018
    Description: Main launcher activity
 */

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addCoinListFragment();
    }

    private void addCoinListFragment(){
        CoinListFragment coinListFragment = new CoinListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.dashboard_fragment_container, coinListFragment, "CoinListFrag")
                .commit();
    }
}
