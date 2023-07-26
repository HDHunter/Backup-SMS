package com.testSSM.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class PagesCon {


    @RequestMapping(value = {"/html", "/html/*"}, method = RequestMethod.GET)
    @ResponseBody
    public void html(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String contextPath = request.getContextPath();
        try {
            if (servletPath.endsWith("/html")) {
                response.sendRedirect(contextPath + servletPath + "/index.html");
            } else {
                response.sendRedirect("index.html");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = {"/jsp", "/jsp/*"}, method = RequestMethod.GET)
    @ResponseBody
    public void jsp(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String contextPath = request.getContextPath();
        try {
            if (servletPath.endsWith("/jsp")) {
                response.sendRedirect(contextPath + servletPath + "/index.jsp");
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = {"/", "/*"}, method = RequestMethod.GET)
    @ResponseBody
    public void home(HttpServletRequest request, HttpServletResponse response) {
        String contextPath = request.getContextPath();
        try {
            response.sendRedirect(contextPath + "/jsp/index.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
