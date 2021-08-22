package com.bugcode.s3bucket.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.bugcode.s3bucket.config.PropertiesConfig;
import com.bugcode.s3bucket.model.S3Obj;
import com.bugcode.s3bucket.service.S3BucketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/s3")
public class S3BucketController {

  @Autowired S3BucketService service;
  @Autowired PropertiesConfig propertiesConfig;

  @GetMapping("/health")
  public String check() {
    log.info("s3bucket-service health check");
    return "ok";
  }

  @GetMapping("/getFiles")
  public List<S3Obj> getFiles(@RequestParam String bucketName) {
    log.info("default bucket {} ", propertiesConfig.getBucketName());
    log.info("getting objects from bucket {} ", bucketName);
    return service.listFiles(bucketName);
  }

  @PostMapping("/upload")
  public String upload(@RequestHeader String localPath, @RequestParam String bucketName) {
    log.info("Uploading file to bucket {} from local path {} ", bucketName, localPath);
    return service.upload(localPath, bucketName);
  }

  @GetMapping("/download")
  public List<String> getSongs(@RequestParam String fileName, @RequestParam String type) {
    log.info("Getting {} from file {} ", type, fileName);
    return service.download(fileName, type);
  }

  @GetMapping("/create")
  public String createBucket(@RequestParam String bucketName) {
    return service.createBucket(bucketName);
  }

  @GetMapping("/getBuckets")
  public List<Bucket> getBuckets() {
    return service.getBuckets();
  }
}
