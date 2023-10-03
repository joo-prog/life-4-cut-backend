package com.onebyte.life4cut.common.constants;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "aws.s3")
@Validated
public record S3Env(@NotEmpty String bucket) {}
