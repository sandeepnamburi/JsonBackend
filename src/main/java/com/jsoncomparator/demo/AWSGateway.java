package com.jsoncomparator.demo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

interface AmazonGateway {
    public List<String> getDirectories() throws Exception;
}

@Service
public class AWSGateway implements AmazonGateway {
    private AmazonS3 client;
    public AWSGateway() {
        client = AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();
    }

    public List<String> getDirectories() {
        List<Bucket> buckets = client.listBuckets();
        List<String> bucketNames = new ArrayList<>();
        for(Bucket b : buckets) {
            bucketNames.add(b.getName());
        }
        return bucketNames;
    }

    public String getJson(String bucketName, String fileName) throws Exception{
        if(!client.doesBucketExist(bucketName))
            throw new Exception("No such bucket exists");
        if(!client.doesObjectExist(bucketName, fileName))
            throw new Exception("No such file exists in the bucket");

        S3Object response = client.getObject(bucketName, fileName);
        S3ObjectInputStream stream = response.getObjectContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        StringBuilder resp = new StringBuilder();
        while((line = reader.readLine()) != null) {
            resp.append(line);
        }
        return resp.toString();
    }

}