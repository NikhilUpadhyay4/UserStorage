package com.userstorage.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.userstorage.config.BucketName;
import com.userstorage.exception.custome.FileNotFoundException;
import com.userstorage.exception.custome.FileUploadException;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FileUploadService {

    @Autowired
    private AmazonS3 amazonS3;

    private final Logger LOGGER  = LoggerFactory.getLogger(FileUploadService.class);

    public void upload(String path, String fileName, @NonNull Optional<Map<String, String>> optionalMetaData, InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(
                map -> {
                    if (!map.isEmpty())
                        map.forEach(objectMetadata::addUserMetadata);
                });
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);

        } catch (AmazonServiceException e) {
            throw new FileUploadException("Failed to upload the file");
        }
    }


    public byte[] download(String path, String key,String username) {
        try {
            S3Object s3Object = amazonS3.getObject(BucketName.BUCKET_NAME.getBucketName()+"/"+username, key);
            S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
            return IOUtils.toByteArray(objectInputStream);
        } catch (AmazonServiceException | IOException e) {
            throw new FileNotFoundException("Failed to download the file");
        }
    }

    public void delete(String path, String keys,String username) {
        String key = username+"/"+keys;
        System.out.println(path+"    "+key);
        String bucket = BucketName.BUCKET_NAME.getBucketName();
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket ,key).withKey(key);
        System.out.println(deleteObjectRequest);
        this.amazonS3.deleteObject(deleteObjectRequest);
    }

    public List<S3ObjectSummary> getAll(String username){
        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(BucketName.BUCKET_NAME.getBucketName()).withPrefix(username+"/").withDelimiter("");
        ListObjectsV2Result listing = amazonS3.listObjectsV2(req);
        return listing.getObjectSummaries();
    }


}