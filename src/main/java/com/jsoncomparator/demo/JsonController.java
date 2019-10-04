package com.jsoncomparator.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(path = "/getFile/{bucket}/{file}")
    @ResponseBody
    public String response(@PathVariable String bucket, @PathVariable String file) {
        try {
            System.out.println(bucket.trim());
            System.out.println(file.trim());
            return gateway.getJson(bucket, file);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
