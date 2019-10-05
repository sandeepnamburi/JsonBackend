package com.jsoncomparator.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class JsonController {
    @Autowired
    AWSGateway gateway;

    @GetMapping(path = "/buckets")
    @ResponseBody
    public List<String> getBucketList() {
        return gateway.getDirectories();
    }

    @GetMapping(path = "/getFile")
    @ResponseBody
    public String response(@RequestParam(name = "bucket") String bucket, @RequestParam(name = "file") String file) {
        try {
            System.out.println(bucket.trim());
            System.out.println(file.trim());
            return gateway.getJson(bucket, file);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
