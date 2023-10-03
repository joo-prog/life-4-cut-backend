package com.onebyte.life4cut.picture.repository;

import com.onebyte.life4cut.picture.domain.Picture;

public interface PictureRepository {

  Picture save(Picture picture);
}
