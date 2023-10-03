package com.onebyte.life4cut.support.fileUpload;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
class S3FileUploader implements FileUploader {

  private final S3Client s3Client;

  public S3FileUploader(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  @Nonnull
  @Override
  public FileUploadResponse upload(@Nonnull FileUploadRequest fileUploadRequest) {
    s3Client.putObject(
        PutObjectRequest.builder()
            .bucket(fileUploadRequest.getBucket())
            .key(fileUploadRequest.getFileName())
            .contentType(fileUploadRequest.getContentType())
            .contentLength(fileUploadRequest.getContentLength())
            .build(),
        RequestBody.fromContentProvider(
            fileUploadRequest::getInputStream,
            fileUploadRequest.getContentLength(),
            fileUploadRequest.getContentType()));
    return new FileUploadResponse(fileUploadRequest.getFileName());
  }
}
