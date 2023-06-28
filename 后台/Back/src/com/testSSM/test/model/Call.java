package com.testSSM.test.model;

public class Call {

    private int id;
    private String number;
    private String date;
    private int duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", date='" + date + '\'' +
                ", duration=" + duration +
                '}';
    }

}
