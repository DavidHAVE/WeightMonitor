package com.weightmonitorid.android.weightmonitor.model;

public class Weight {
    private int id;
    private String date;
    private long weight;

    public Weight(){
    }

    public Weight(String date, long weight){
        this.date = date;
        this.weight = weight;
    }

    public Weight(int id, String date, long weight){
        this.id = id;
        this.date = date;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
}
