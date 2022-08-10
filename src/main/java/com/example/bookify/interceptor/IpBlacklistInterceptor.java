package com.example.bookify.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class IpBlacklistInterceptor implements HandlerInterceptor {

    private List<String> blacklistedIpAddresses = new ArrayList<>();

    public IpBlacklistInterceptor(){
        blacklistedIpAddresses.add("127.0.0.1");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ipAddress = request.getRemoteAddr();

        System.out.println(ipAddress);

        if(blacklistedIpAddresses.contains(ipAddress)) {
            response.sendRedirect("/error");
        }

        return true;
    }
}
