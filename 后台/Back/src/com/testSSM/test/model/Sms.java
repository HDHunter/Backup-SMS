package com.testSSM.test.model;

public class Sms {
    private int smsid;
    private String phonenum;
    private String sms;
    private String smsdate;
    private int sms_id;
    private int sms_huihua;

    public int getSms_huihua() {
        return sms_huihua;
    }

    public void setSms_huihua(int sms_huihua) {
        this.sms_huihua = sms_huihua;
    }

    public int getSms_id() {
        return sms_id;
    }

    public void setSms_id(int sms_id) {
        this.sms_id = sms_id;
    }

    public int getSmsid() {
        return smsid;
    }

    public void setSmsid(int smsid) {
        this.smsid = smsid;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getSmsdate() {
        return smsdate;
    }

    public void setSmsdate(String smsdate) {
        this.smsdate = smsdate;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "smsid=" + smsid +
                ", phonenum='" + phonenum + '\'' +
                ", sms='" + sms + '\'' +
                ", smsdate='" + smsdate + '\'' +
                ", sms_id=" + sms_id +
                ", sms_huihua=" + sms_huihua +
                '}';
    }
}
