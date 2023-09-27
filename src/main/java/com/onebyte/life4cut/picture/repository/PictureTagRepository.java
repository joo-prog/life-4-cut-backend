package com.onebyte.life4cut.picture.repository;

import com.onebyte.life4cut.picture.domain.PictureTag;
import java.util.List;

public interface PictureTagRepository {

    List<PictureTag> findByNames(Long albumId, List<String> names);

    List<PictureTag> saveAll(Iterable<PictureTag> pictureTags);

    PictureTag save(PictureTag pictureTag);
}
