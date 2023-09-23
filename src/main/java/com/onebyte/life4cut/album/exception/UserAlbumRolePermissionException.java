package com.onebyte.life4cut.album.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class UserAlbumRolePermissionException extends CustomException {

    public UserAlbumRolePermissionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UserAlbumRolePermissionException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
