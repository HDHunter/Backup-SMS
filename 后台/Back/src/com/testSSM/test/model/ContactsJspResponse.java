package com.testSSM.test.model;

import java.util.List;

public class ContactsJspResponse {
    private List<Contacts> contacts;
    private int size;

    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ContactsJspResponse{" +
                "contacts=" + contacts +
                ", size=" + size +
                '}';
    }
}
