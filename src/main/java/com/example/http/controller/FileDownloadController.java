package com.example.http.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;

@RestController("file")
public class FileDownloadController {

    @Autowired
    RestTemplate restTemplate;

    //        String fileUrl = "https://github.com/progit/progit2/releases/download/2.1.317/progit.pdf";
    String fileUrl = "https://cdn.mysql.com//Downloads/MySQLInstaller/mysql-installer-community-5.7.33.0.msi";

    @RequestMapping("download")
    public JSONObject download() throws IOException {

        String[] split = fileUrl.split("/");
        String fileName = split[split.length - 1];

        ResponseEntity<byte[]> entity = restTemplate.getForEntity(fileUrl, byte[].class);
        byte[] result = entity.getBody();

        File file = new File("D:/temp/" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }



        try (InputStream in = new ByteArrayInputStream(result);
             OutputStream out = new FileOutputStream(file);) {
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.flush();
        }

        HttpHeaders headers = entity.getHeaders();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("file-name", fileName);
        jsonObject.put("file-size", headers.getContentLength());
        jsonObject.put("LastModified", headers.getLastModified());

        return jsonObject;
    }

    @RequestMapping("download2")
    public JSONObject download2() throws IOException {

        HttpHeaders httpHeaders = restTemplate.headForHeaders(fileUrl);

        JSONObject jsonObject = new JSONObject();
        List<String> strings = httpHeaders.get("Accept-Ranges");
        jsonObject.put("Accept-Ranges", strings);
        return jsonObject;
    }
}
