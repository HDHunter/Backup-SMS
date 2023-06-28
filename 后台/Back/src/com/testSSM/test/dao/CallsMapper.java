package com.testSSM.test.dao;

import com.testSSM.test.model.Call;

import java.util.List;

public interface CallsMapper {

    List<Call> getAllCalls();

    int add(Call call);

    int add(List<Call> calls);
}
