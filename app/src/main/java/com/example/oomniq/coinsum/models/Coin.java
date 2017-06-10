package com.example.oomniq.coinsum.models;

/**
 * Created by OomniQ on 10.06.2017.
 */

public class Coin {
    private static String baseImageUrl;
    private long id;
    private String ticker;
    private String coinName;
    private String fullName;
    private String imageUrl;
    private int sortOrder;

    public static String getBaseImageUrl() {
        return baseImageUrl;
    }

    public static void setBaseImageUrl(String baseImageUrl) {
        Coin.baseImageUrl = baseImageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageUrl() {
        return baseImageUrl + imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
