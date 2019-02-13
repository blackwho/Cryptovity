package com.example.appjo.cryptoinfo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;

import com.example.appjo.cryptoinfo.Fragments.CoinListFragment;
import com.example.appjo.cryptoinfo.Utils.UrlBuilder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UrlBuilderExampleUnitTest {
    private UrlBuilder urlBuilder = UrlBuilder.getInstance();

    @Test
    public void coinListUrlTest(){
        String url = "https://min-api.cryptocompare.com/data/top/totalvol?limit=100&page=0&tsym=USD";
        assertEquals(urlBuilder.getUrl(null, null, "coins"), url);
    }

    @Test
    public void coinDetailUrlTest(){
        String url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=ETH&tsyms=USD";
        assertEquals(urlBuilder.getUrl("ETH", "USD", "details"), url);
    }

    @Test
    public void coinGraphDataUrlTest(){
        String url = "https://min-api.cryptocompare.com/data/histoday?fsym=ETH&tsym=USD";
        assertEquals(urlBuilder.getUrl("ETH", "USD", "graph"), url);
    }
}
