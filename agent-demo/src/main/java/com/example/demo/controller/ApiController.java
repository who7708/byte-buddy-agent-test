package com.example.demo.controller;

import com.example.demo.service.ApiService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String web(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:7001/testweb2");
        CloseableHttpResponse execute = client.execute(httpGet);
        //取出返回体
        HttpEntity entity = execute.getEntity();
        // // 得到给前台的响应流
        // ServletOutputStream out = response.getOutputStream();
        // //将返回体通过响应流写到前台
        // entity.writeTo(out);
        // out.flush();
        return execute.toString();
    }

    @GetMapping("/testweb2")
    public String web2(HttpServletRequest request) {
        return "testhttp2";
    }

    @GetMapping("/testservice")
    public String service(HttpServletRequest request, HttpServletResponse response) {
        apiService.testservice(request);
        return "testhttp";
    }

}