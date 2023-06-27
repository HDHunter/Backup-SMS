package com.testSSM.test.dao;

import com.testSSM.test.model.User;

import java.util.List;

public interface UserDao {

    List<User> findUserList();
}
