package com.example.appjo.cryptoinfo.Utils;

import android.net.Uri;
import android.support.annotation.Nullable;

/*
* Custom Url builder class used for all the api request
*/
public class UrlBuilder {
    private static final String TAG = UrlBuilder.class.getSimpleName();
    private static UrlBuilder mInstance;

    public static UrlBuilder getInstance(){
        if (mInstance == null){
            mInstance = new UrlBuilder();
        }
        return mInstance;
    }
    public String getUrl(@Nullable String coin, @Nullable String currency, String route){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("min-api.cryptocompare.com")
                .appendPath("data");
        if (route.equals("coins") && coin == null && currency == null){
            builder.appendPath("top")
                    .appendPath("totalvol")
                    .appendQueryParameter("limit", "100")
                    .appendQueryParameter("page", "0")
                    .appendQueryParameter("tsym", "USD");
            return builder.build().toString();
        }else if (route.equals("details") && coin != null && currency != null){
            builder.appendPath("pricemultifull")
                    .appendQueryParameter("fsyms", coin)
                    .appendQueryParameter("tsyms", currency);
            return builder.build().toString();
        }else {
            builder.appendPath("histoday")
                    .appendQueryParameter("fsym", coin)
                    .appendQueryParameter("tsym", currency);
            return builder.build().toString();
        }
    }
}
