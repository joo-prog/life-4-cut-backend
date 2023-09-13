package com.onebyte.life4cut.support.fileUpload;

import java.io.InputStream;

public interface FileUploadRequest {

    InputStream getInputStream();

    String getFileName();

    String getContentType();

    long getContentLength();
}
