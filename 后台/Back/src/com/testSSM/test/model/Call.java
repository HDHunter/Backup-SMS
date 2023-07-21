package com.testSSM.test.model;

public class Call {

    private int id;
    /**
     * incoming=1
     * outgoing=2
     * missed=3
     */
    private int type;
    private String number;
    private String date;
    private int duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        return "Call{" +
                "id=" + id +
                ", type=" + type +
                ", number='" + number + '\'' +
                ", date='" + date + '\'' +
                ", duration=" + duration +
                '}';
    }
}
