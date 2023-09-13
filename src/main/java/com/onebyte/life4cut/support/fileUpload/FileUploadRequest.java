package com.onebyte.life4cut.support.fileUpload;

import java.io.InputStream;

public interface FileUploadRequest {

    InputStream getInputStream();

    String getFileName();

    String getPath();

    String getContentType();
}
