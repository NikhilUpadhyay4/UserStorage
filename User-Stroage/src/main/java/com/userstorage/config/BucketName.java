package com.userstorage.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    BUCKET_NAME("file-storage-nikhil");
    private final String bucketName;
}
