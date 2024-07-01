package com.testSSM.test.dao;

import com.testSSM.test.model.Contacts;

import java.util.List;


public interface ContactsMapper {
    int add(Contacts contacts);
    int update(Contacts contacts);

    List<Contacts> get();
}
