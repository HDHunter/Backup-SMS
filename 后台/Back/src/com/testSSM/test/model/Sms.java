package com.testSSM.test.model;

public class Sms {

    private String address;
    private String date;
    private String body;
    private String status;
    private String error_code;
    private int id;
    private String thread_id;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }


    @Override
    public String toString() {
        return "Sms{" +
                "address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", body='" + body + '\'' +
                ", status='" + status + '\'' +
                ", error_code='" + error_code + '\'' +
                ", id=" + id +
                ", thread_id='" + thread_id + '\'' +
                '}';
    }
}
