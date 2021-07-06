package com.example.http.configTest;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTemplateConfigTests {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void restTemplate_httpGet() {
        // url
        String url = "http://localhost:8080/http/hello";
        // res
        String res = restTemplate.getForObject(url, String.class);
        // print
        System.out.println(res);
        // assert
        TestCase.assertEquals(res, "hello");
    }
}
