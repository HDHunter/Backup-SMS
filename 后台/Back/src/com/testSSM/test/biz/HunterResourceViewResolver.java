package com.testSSM.test.biz;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class HunterResourceViewResolver extends InternalResourceViewResolver {

    @Override
    protected Class<?> requiredViewClass() {
        return super.requiredViewClass();
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        return super.buildView(viewName);
    }

    @Override
    protected boolean isContextRequired() {
        return super.isContextRequired();
    }

    @Override
    protected boolean isRedirectContextRelative() {
        return super.isRedirectContextRelative();
    }

    @Override
    protected boolean isRedirectHttp10Compatible() {
        return super.isRedirectHttp10Compatible();
    }
}
