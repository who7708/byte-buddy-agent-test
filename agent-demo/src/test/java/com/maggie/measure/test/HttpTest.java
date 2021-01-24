package com.maggie.measure.test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpTest {
    public static void main(String[] args) throws IOException {
        List<Long> longs = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            testQps(longs);
        }
        System.out.println(longs);
        System.out.println("avg = " + longs.stream().mapToLong(Long::longValue).average());
    }

    private static void testQps(List<Long> longs) throws IOException {
        String url = "http://localhost:8080/testservice";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        long start = System.currentTimeMillis();
        CloseableHttpResponse execute = client.execute(httpGet);
        longs.add(System.currentTimeMillis() - start);
    }
}
