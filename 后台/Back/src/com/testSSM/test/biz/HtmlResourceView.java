package com.testSSM.test.biz;

import org.springframework.web.servlet.view.InternalResourceView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Locale;

public class HtmlResourceView extends InternalResourceView {


    @Override
    protected RequestDispatcher getRequestDispatcher(HttpServletRequest request, String path) {
        return super.getRequestDispatcher(request, path);
    }

    @Override
    protected boolean isUrlRequired() {
        return super.isUrlRequired();
    }

    @Override
    public boolean checkResource(Locale locale) {
        File file = new File(this.getServletContext().getRealPath("/") + getUrl());
        Utils.logE("check file:" + file.getAbsoluteFile());
        return file.exists();
    }

}