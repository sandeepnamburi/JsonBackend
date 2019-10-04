package com.jsoncomparator.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
