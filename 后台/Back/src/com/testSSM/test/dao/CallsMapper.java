package com.testSSM.test.dao;

import com.testSSM.test.model.Call;

import java.util.List;

public interface CallsMapper {

    List<Call> get();

    int add(Call call);
}
