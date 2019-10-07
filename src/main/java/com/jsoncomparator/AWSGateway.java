package com.jsoncomparator;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AWSGateway implements AmazonGateway {
    private AmazonS3 client;

    public AWSGateway() {
        client = AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();
    }

    public List<String> getDirectories() {
        List<Bucket> buckets = client.listBuckets();
        List<String> bucketNames = new ArrayList<>();
        for (Bucket b : buckets) {
            bucketNames.add(b.getName());
        }
        return bucketNames;
    }

    public List<String> getObjectKeysInBucket(String bucketName) throws IllegalArgumentException {
        if (!client.doesBucketExist(bucketName))
            throw new IllegalArgumentException("No such bucket exists");
        List<String> objectKeys = new ArrayList<>();
        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName);
        ListObjectsV2Result result;
        result = client.listObjectsV2(req);
        //TODO: Deal with pagination case
        for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
            objectKeys.add(objectSummary.getKey());
        }
        return objectKeys;
    }

    public String getJson(String bucketName, String fileName) throws IllegalArgumentException, IOException {
        if (!client.doesBucketExist(bucketName))
            throw new IllegalArgumentException("No such bucket exists");
        if (!client.doesObjectExist(bucketName, fileName))
            throw new IllegalArgumentException("No such file exists in the bucket");

        S3Object response = client.getObject(bucketName, fileName);
        S3ObjectInputStream stream = response.getObjectContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        StringBuilder resp = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            resp.append(line);
        }
        return resp.toString();
    }

}
