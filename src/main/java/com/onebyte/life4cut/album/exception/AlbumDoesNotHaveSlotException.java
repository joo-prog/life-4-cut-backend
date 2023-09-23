package com.onebyte.life4cut.album.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class AlbumDoesNotHaveSlotException extends CustomException {

    public AlbumDoesNotHaveSlotException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlbumDoesNotHaveSlotException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
