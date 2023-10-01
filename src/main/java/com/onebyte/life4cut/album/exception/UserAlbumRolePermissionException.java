package com.onebyte.life4cut.album.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class UserAlbumRolePermissionException extends CustomException {

  public UserAlbumRolePermissionException() {
    super(ErrorCode.USER_ALBUM_ROLE_PERMISSION);
  }

  public UserAlbumRolePermissionException(ErrorCode errorCode) {
    super(errorCode);
  }
}
