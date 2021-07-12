package com.example.http.webclient;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WebClientTest {

    @Test
    public void webClientHello() {
        // url
        String url = "https://www.baidu.com";
        WebClient webClient = WebClient.create();

        Mono<String> mono = webClient.get().uri(url).retrieve().bodyToMono(String.class);
        System.out.println(mono.block());
    }
}
