package com.jsoncomparator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class JsonController {
    @Autowired
    AWSGateway gateway;

    @GetMapping(path = "/buckets")
    @ResponseBody
    public List<String> getBucketList() {
        return gateway.getDirectories();
    }

    @GetMapping(path = "/listObjects")
    @ResponseBody
    public List<String> getObjectList(@RequestParam(name = "bucket") String bucket) {
        try {
            return gateway.getObjects(bucket);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @GetMapping(path = "/getFile")
    @ResponseBody
    public String response(@RequestParam(name = "bucket") String bucket, @RequestParam(name = "file") String file) {
        try {
            log.info(bucket.trim());
            log.info(file.trim());
            return gateway.getJson(bucket, file);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
