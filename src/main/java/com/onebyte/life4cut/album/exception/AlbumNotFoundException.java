package com.onebyte.life4cut.album.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class AlbumNotFoundException extends CustomException {
    public AlbumNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlbumNotFoundException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
