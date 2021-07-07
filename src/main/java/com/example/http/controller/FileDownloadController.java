package com.example.http.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;

@RestController("file")
public class FileDownloadController {

    @Autowired
    RestTemplate restTemplate;

    String fileUrl = "https://cdn.mysql.com//Downloads/MySQLInstaller/mysql-installer-community-5.7.33.0.msi";

    @RequestMapping("download")
    public JSONObject download() throws IOException {
        // 获取文件名
        String[] split = fileUrl.split("/");
        String fileName = split[split.length - 1];

        StopWatch sw = new StopWatch("download");
        sw.start("download - from net");
        // 开始下载
        ResponseEntity<byte[]> entity = restTemplate.getForEntity(fileUrl, byte[].class);
        sw.stop();
        sw.start("download - to local");
        byte[] result = entity.getBody();

        // 创建本地文件
        File file = new File("D:/temp/" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        // 保存至本地
        try (InputStream in = new ByteArrayInputStream(result);
             OutputStream out = new FileOutputStream(file);) {
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.flush();
        }
        sw.stop();

        String costTime = sw.prettyPrint();

        // 返回文件信息
        HttpHeaders headers = entity.getHeaders();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("file-name", fileName);
        jsonObject.put("file-size", headers.getContentLength());
        jsonObject.put("LastModified", headers.getLastModified());
        jsonObject.put("Cost Time", costTime);

        return jsonObject;
    }

    @RequestMapping("download2")
    public JSONObject download2() throws IOException {

        // 获取请求头信息
        HttpHeaders headers = restTemplate.headForHeaders(fileUrl);
        // 判断是否支持断点续传等
        JSONObject jsonObject = new JSONObject();
        List<String> acceptRanges = headers.get("Accept-Ranges");
        jsonObject.put("是否支持断点续传", acceptRanges.contains("bytes"));
        jsonObject.put("文件大小", headers.getContentLength());

//        File tempFile = File.createTempFile("download", null);
//        StreamUtils.copy((byte[]) null, null);
//        System.out.println(tempFile.getPath());
//        System.out.println(tempFile.getName());

        return jsonObject;
    }
}
