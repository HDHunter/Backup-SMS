package com.testSSM.test.service;

import com.testSSM.test.dao.ContactsMapper;
import com.testSSM.test.model.Contacts;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContactsSer implements ContactsMapper {
    @Resource
    @Autowired
    private ContactsMapper contactsMapper;


    @Override
    public int add(Contacts contacts) {
        System.out.println(contacts.toString());
        return contactsMapper.add(contacts);
    }

    @Override
    public int add(List<Contacts> contacts) {
        System.out.println(contacts.toString());
        return contactsMapper.add(contacts);
    }
}
