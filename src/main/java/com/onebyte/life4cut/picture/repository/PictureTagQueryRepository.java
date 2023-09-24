package com.onebyte.life4cut.picture.repository;

import com.onebyte.life4cut.picture.domain.PictureTag;

import java.util.List;

public interface PictureTagQueryRepository {

    List<PictureTag> findByNames(Long albumId, List<String> names);
}
