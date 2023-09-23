package com.onebyte.life4cut.album.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class SlotNotFoundException extends CustomException {
    public SlotNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SlotNotFoundException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
