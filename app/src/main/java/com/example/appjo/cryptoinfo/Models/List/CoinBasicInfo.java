package com.example.appjo.cryptoinfo.Models.List;

import com.google.gson.annotations.SerializedName;

public class CoinBasicInfo {

    @SerializedName("Id")
    private String id;

    @SerializedName("FullName")
    private String full_name;

    @SerializedName("Name")
    private String name;

    @SerializedName("ImageUrl")
    private String image_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
