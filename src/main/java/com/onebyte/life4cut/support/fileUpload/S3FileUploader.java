package com.onebyte.life4cut.support.fileUpload;

import com.onebyte.life4cut.support.fileUpload.config.S3Env;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
class S3FileUploader implements FileUploader {

    private final S3Client s3Client;

    private final S3Env s3Env;

    public S3FileUploader(S3Client s3Client, S3Env s3Env) {
        this.s3Client = s3Client;
        this.s3Env = s3Env;
    }

    @Nonnull
    @Override
    public FileUploadResponse upload(@Nonnull FileUploadRequest fileUploadRequest) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(s3Env.bucket())
                        .key(fileUploadRequest.getFileName())
                        .contentType(fileUploadRequest.getContentType())
                        .contentLength(fileUploadRequest.getContentLength())
                        .build(),
                RequestBody.fromContentProvider(fileUploadRequest::getInputStream, fileUploadRequest.getContentLength(), fileUploadRequest.getContentType())
        );
        return new FileUploadResponse(fileUploadRequest.getFileName());
    }

}
