package com.onebyte.life4cut.support.fileUpload;

import jakarta.annotation.Nonnull;

public interface FileUploader {

  @Nonnull
  FileUploadResponse upload(@Nonnull FileUploadRequest fileUploadRequest);
}
