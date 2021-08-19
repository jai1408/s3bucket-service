package com.oracle.s3bucket.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.oracle.s3bucket.config.PropertiesConfig;
import com.oracle.s3bucket.config.S3BucketConfig;
import com.oracle.s3bucket.model.S3Obj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class S3BucketService {

  @Autowired S3BucketConfig s3BucketConfig;
  @Autowired PropertiesConfig propertiesConfig;

  public List<S3Obj> listFiles() {
    List<S3Obj> s3ObjList = new ArrayList<>();

    try {
      ObjectListing objectListing = s3BucketConfig.getS3Client().listObjects(propertiesConfig.getBucketName());
      List<S3ObjectSummary> s3ObjectSummaryList = objectListing.getObjectSummaries();

      for (; ; ) {
        for (S3ObjectSummary s3ObjectSummary : s3ObjectSummaryList) {
          S3Obj s3obj = new S3Obj();
          s3obj.setFileSize(s3ObjectSummary.getSize());
          s3obj.setKeyName(s3ObjectSummary.getKey());
          s3obj.setLastModifiedDate(s3ObjectSummary.getLastModified());
          s3obj.setBucketName(s3ObjectSummary.getBucketName());
          s3obj.setStorageClass(s3ObjectSummary.getStorageClass());
          s3obj.setOwner(s3ObjectSummary.getOwner());
          s3ObjList.add(s3obj);
        }

        if (objectListing.isTruncated()) {
          objectListing = s3BucketConfig.getS3Client().listNextBatchOfObjects(objectListing);
        } else break;
      }
    } catch (AmazonServiceException e) {
      e.printStackTrace();
    }
    return s3ObjList;
  }

  /*public String upload(String localPath){
      String bucket_name = args[0];
      String file_path = args[1];
      String key_name = Paths.get(file_path).getFileName().toString();

      s3BucketConfig.getS3Client().putObject()
  }*/
}
