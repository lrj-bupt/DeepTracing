package org.tracing.demo;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;

import java.io.IOException;

public class HttpUtils {
    public void executeMethod(HttpMethod httpMethod){
        HttpClient httpClient = new HttpClient();
        System.out.println(httpMethod.getRequestHeader("carrier"));
        try {
            httpClient.executeMethod(httpMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
