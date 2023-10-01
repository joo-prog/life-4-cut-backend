package com.onebyte.life4cut.album.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class AlbumDoesNotHaveSlotException extends CustomException {

  public AlbumDoesNotHaveSlotException() {
    super(ErrorCode.ALBUM_DOES_NOT_HAVE_SLOT);
  }

  public AlbumDoesNotHaveSlotException(ErrorCode errorCode) {
    super(errorCode);
  }
}
