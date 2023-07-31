package com.testSSM.test.model;

import java.util.List;

public class CallJspResponse {
    private List<Call> calls;
    private int size;

    public List<Call> getCalls() {
        return calls;
    }

    public void setCalls(List<Call> calls) {
        this.calls = calls;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "CallJspResponse{" +
                "calls=" + calls +
                ", size=" + size +
                '}';
    }
}
