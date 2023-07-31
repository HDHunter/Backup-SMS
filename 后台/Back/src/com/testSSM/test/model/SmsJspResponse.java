package com.testSSM.test.model;

import java.util.List;

public class SmsJspResponse {
    private List<Sms> sms;
    private int size;

    public List<Sms> getSms() {
        return sms;
    }

    public void setSms(List<Sms> sms) {
        this.sms = sms;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "SmsJspResponse{" +
                "sms=" + sms +
                ", size=" + size +
                '}';
    }
}
