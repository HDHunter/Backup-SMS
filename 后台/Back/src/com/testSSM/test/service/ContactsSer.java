package com.testSSM.test.service;

import com.testSSM.test.biz.Utils;
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
        Utils.logD("ContactsSer", "add " + contacts);
        return contactsMapper.add(contacts);
    }

    @Override
    public int update(Contacts contacts) {
        Utils.logD("ContactsSer", "update " + contacts);
        return contactsMapper.update(contacts);
    }

    @Override
    public List<Contacts> get() {
        Utils.logD("ContactsSer", "start get");
        return contactsMapper.get();
    }

}
