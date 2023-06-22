package com.testSSM.test.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.testSSM.test.model.Contacts;


public interface ContactsMaper {
    public int phone(Contacts con);
}
