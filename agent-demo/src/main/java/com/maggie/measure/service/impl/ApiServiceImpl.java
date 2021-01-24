package com.maggie.measure.service.impl;

import com.maggie.measure.service.ApiService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/24
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Override
    public void testservice(HttpServletRequest request) {
        System.out.println(request.getRequestedSessionId());
    }
}