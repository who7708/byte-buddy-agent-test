package com.maggie.measure.controller;

import com.maggie.measure.service.ApiService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/24
 */
@RestController
public class ApiController {
    @Autowired
    ApiService apiService;

    @GetMapping("/testweb")
    public String web(HttpServletRequest request) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("/testweb2");
        CloseableHttpResponse execute = client.execute(httpGet);
        return execute.toString();
    }

    @GetMapping("/testweb2")
    public String web2(HttpServletRequest request) {
        return "testhttp2";
    }

    @GetMapping("/testservice")
    public String service(HttpServletRequest request) {
        apiService.testservice(request);
        return "testhttp";
    }

}