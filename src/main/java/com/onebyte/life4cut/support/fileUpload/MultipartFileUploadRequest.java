package com.onebyte.life4cut.support.fileUpload;

import jakarta.annotation.Nonnull;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

public class MultipartFileUploadRequest implements FileUploadRequest{

    private static final String DEFAULT_CONTENT_TYPE = MediaType.APPLICATION_OCTET_STREAM_VALUE;
    private static final String DEFAULT_FILE_NAME = "file";

    @Nonnull
    private final MultipartFile multipartFile;

    @Nonnull
    private final String bucket;

    private MultipartFileUploadRequest(@Nonnull MultipartFile multipartFile, @Nonnull String bucket) {
        this.multipartFile = multipartFile;
        this.bucket = bucket;
    }

    public static FileUploadRequest of(@Nonnull MultipartFile multipartFile, @Nonnull String bucket) {
        return new MultipartFileUploadRequest(multipartFile, bucket);
    }

    @Nonnull
    @Override
    public String getBucket() {
        return this.bucket;
    }

    @Override
    public InputStream getInputStream() {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @Nonnull
    @Override
    public String getFileName() {
        if (Objects.isNull(multipartFile.getOriginalFilename())) {
            return String.format("/%s/%s", UUID.randomUUID(), DEFAULT_FILE_NAME);
        }
        return String.format("/%s/%s", UUID.randomUUID(), multipartFile.getOriginalFilename());
    }

    @Nonnull
    @Override
    public String getContentType() {
        if (Objects.isNull(multipartFile.getContentType())) {
            return DEFAULT_CONTENT_TYPE;
        }
        return multipartFile.getContentType();

    }

    @Override
    public long getContentLength() {
        return multipartFile.getSize();
    }
}
