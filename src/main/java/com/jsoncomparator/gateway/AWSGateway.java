package com.jsoncomparator.gateway;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
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

    public List<String> getObjects(String bucket) throws Exception {
        if (!client.doesBucketExist(bucket))
            throw new Exception("No such bucket exists");
        List<String> listOfObjectNames = new ArrayList<>();
        for (S3ObjectSummary summary : client.listObjects(bucket).getObjectSummaries()) {
            listOfObjectNames.add(summary.getKey());
        }
        return listOfObjectNames;
    }

    public String getFileContentAsString(String bucketName, String fileName) throws IllegalArgumentException, IOException {
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
