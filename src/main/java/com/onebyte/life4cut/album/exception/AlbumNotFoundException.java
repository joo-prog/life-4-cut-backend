package com.onebyte.life4cut.album.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class AlbumNotFoundException extends CustomException {

  public AlbumNotFoundException() {
    super(ErrorCode.ALBUM_NOT_FOUND);
  }

  public AlbumNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
