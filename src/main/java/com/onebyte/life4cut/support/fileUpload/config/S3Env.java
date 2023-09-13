package com.onebyte.life4cut.support.fileUpload.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "aws.s3")
@Validated
public record S3Env (
      @NotBlank String bucket,
      @NotBlank String region
) {
}
