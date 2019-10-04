package com.jsoncomparator.demo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

interface AmazonGateway {
    public List<String> getDirectories();
}

@Service
public class AWSGateway implements AmazonGateway {
    private AmazonS3 client;
    public AWSGateway() {
        client = AmazonS3ClientBuilder.standard().build();
    }

    public List<String> getDirectories() {
        List<Bucket> buckets = client.listBuckets();
        List<String> bucketNames = new ArrayList<>();
        for(Bucket b : buckets) {
            bucketNames.add(b.getName());
        }
        return bucketNames;
    }

}
