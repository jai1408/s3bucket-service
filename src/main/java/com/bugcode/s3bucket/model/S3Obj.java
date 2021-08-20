package com.bugcode.s3bucket.model;

import com.amazonaws.services.s3.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class S3Obj {
  private long fileSize;
  private Date lastModifiedDate;
  private String keyName;
  private String storageClass;
  private String bucketName;
  private Owner owner;
}
