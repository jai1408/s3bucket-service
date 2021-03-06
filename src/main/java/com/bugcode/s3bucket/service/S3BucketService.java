package com.bugcode.s3bucket.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.bugcode.s3bucket.config.PropertiesConfig;
import com.bugcode.s3bucket.config.S3BucketConfig;
import com.bugcode.s3bucket.model.S3Obj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class S3BucketService {

  @Autowired S3BucketConfig s3BucketConfig;
  @Autowired PropertiesConfig propertiesConfig;

  public List<Bucket> getBuckets() {
    List<Bucket> bucketList = null;
    try {
      bucketList = s3BucketConfig.getS3Client().listBuckets();
    } catch (AmazonServiceException e) {
      log.error("Exception occurred in getBuckets {} ", e.getLocalizedMessage());
    }
    return bucketList;
  }

  public String createBucket(String bucketName) {
    Bucket bucket = null;
    String message;
    if (s3BucketConfig.getS3Client().doesBucketExistV2(bucketName)) {
      log.info("Bucket {} already exists ", bucketName);
      return "Bucket already exists.";
    } else {
      try {
        bucket = s3BucketConfig.getS3Client().createBucket(bucketName);
      } catch (AmazonS3Exception e) {
        log.error("Exception occurred in createBucket {} ", e.getLocalizedMessage());
      }
    }
    if (bucket == null) {
      log.info("Error creating Bucket {}  ", bucketName);
      message = "Error creating bucket!";
    } else {
      log.info("Bucket Created Successfully! {}  ", bucketName);
      message = "Bucket Created Successfully!";
    }
    return message;
  }

  public List<S3Obj> listFiles(String bucketName) {
    List<S3Obj> s3ObjList = new ArrayList<>();
    try {
      ObjectListing objectListing =
          s3BucketConfig.getS3Client().listObjects(bucketName);
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
      log.error("Exception occurred in listFiles {} ", e.getLocalizedMessage());
    }
    return s3ObjList;
  }

  public String upload(String localPath, String bucketName) {
    String keyName = Paths.get(localPath).getFileName().toString();
    try {
      s3BucketConfig.getS3Client().putObject(bucketName, keyName, new File(localPath));
    } catch (AmazonServiceException e) {
      log.error("Exception occurred in uploading {}", e.getLocalizedMessage());
    }
    return "File Uploaded successfully";
  }

  public List<String> download(String fileName, String type) {
    ArrayList<String> songList = new ArrayList<>();
    ArrayList<String> artistList = new ArrayList<>();

    S3ObjectInputStream inputStream;
    File file = new File(fileName);
    try (S3Object s3Object =
        s3BucketConfig.getS3Client().getObject(propertiesConfig.getBucketName(), fileName)) {
      inputStream = s3Object.getObjectContent();
      IOUtils.copy(inputStream, new FileOutputStream(fileName));
    } catch (IOException e) {
      log.error("Exception occurred in downloading {} ", e.getLocalizedMessage());
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        songList.add(line.split("/")[0]);
        artistList.add(line.split("/")[1]);
      }
    } catch (IOException e) {
      log.error("Exception occurred in downloading {}  ", e.getLocalizedMessage());
    }
    if (type.equalsIgnoreCase("songs")) {
      return songList;
    } else if (type.equalsIgnoreCase("artists")) {
      return artistList;
    } else return new ArrayList<>();
  }
}
