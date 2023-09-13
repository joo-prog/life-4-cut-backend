package com.onebyte.life4cut.support.fileUpload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Component
public class FileUploadConfiguration {

    @Bean
    S3Client s3Client(S3Env s3Env) {
        return S3Client.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(s3Env.region()))
                .build();
    }
}
