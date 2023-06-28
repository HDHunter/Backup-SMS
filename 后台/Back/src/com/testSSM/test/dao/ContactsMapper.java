package com.testSSM.test.dao;

import com.testSSM.test.model.Contacts;

import java.util.List;


public interface ContactsMapper {
    int add(Contacts contacts);

    int add(List<Contacts> contacts);
}
