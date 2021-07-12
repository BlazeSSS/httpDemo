package com.example.http.controller;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.*;

@RestController
@RequestMapping("webclient")
public class WebClientController {

        String fileUrl = "https://cdn.mysql.com//Downloads/MySQLInstaller/mysql-installer-community-5.7.33.0.msi";
//    String fileUrl = "http://10.191.6.21/cas/images/DEMO/demo_login_logo_yamaha.png";

    @RequestMapping("hello")
    public String hello() {
//        WebClient webClient = WebClient.create("localhost:8080");
        WebClient webClient = WebClient.create();
        WebClient.RequestBodyUriSpec request = webClient.method(HttpMethod.GET);
        request.uri(uriBuilder -> uriBuilder
                .scheme("http")
                .host("localhost")
                .port(8090)
                .path("http/hello")
                .build());

        WebClient.ResponseSpec response = request.retrieve();
        Mono<String> stringMono = response.bodyToMono(String.class);
        return stringMono.block();
    }

    @RequestMapping("hello2")
    public String hello2() {
        WebClient webClient = WebClient.create("http://localhost:8090");
        WebClient.RequestBodyUriSpec request = webClient.method(HttpMethod.GET);
        WebClient.ResponseSpec response = request.uri("http/hello").retrieve();

        Mono<String> stringMono = response.bodyToMono(String.class);
        return stringMono.block();
    }

    @RequestMapping("hello3")
    public String hello3() {
        WebClient webClient = WebClient.create("http://localhost:8090");
        WebClient.ResponseSpec retrieve = webClient.get().uri("/http/hello").retrieve();

        Mono<String> stringMono = retrieve.bodyToMono(String.class);
        return stringMono.block();
    }

    @RequestMapping("download")
    public String download() throws IOException {
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> {
                            configurer.defaultCodecs()
                                    .maxInMemorySize(16 * 1024 * 1024);
                        })
                        .build())
                .build();
        Mono<ClientResponse> clientResponse = webClient.get().uri(fileUrl).exchange();

        ClientResponse response = clientResponse.block();
        ContentDisposition contentDisposition = response.headers().asHttpHeaders().getContentDisposition();
        // 获取文件名
        String filename = contentDisposition.getFilename();
        if (filename == null || filename.trim() == "") {
            String[] split = fileUrl.split("/");
            filename = split[split.length - 1];
        }

        byte[] bytes = response.bodyToMono(byte[].class).block();

        File file = new File("D:/temp/" + filename);
        FileOutputStream out = new FileOutputStream(file);
        StreamUtils.copy(bytes, out);
        out.close();
        return file.getPath();
    }
}
