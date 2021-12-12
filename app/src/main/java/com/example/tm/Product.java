package com.example.tm;

public class Product {
    int id;
    String name;
    int min_player;
    int max_player;
    int price;
    double latitude;
    double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin_player() {
        return min_player;
    }

    public void setMin_player(int min_player) {
        this.min_player = min_player;
    }

    public int getMax_player() {
        return max_player;
    }

    public void setMax_player(int max_player) {
        this.max_player = max_player;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
