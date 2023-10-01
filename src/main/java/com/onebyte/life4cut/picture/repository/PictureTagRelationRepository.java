package com.onebyte.life4cut.picture.repository;

import com.onebyte.life4cut.picture.domain.PictureTagRelation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureTagRelationRepository {

    PictureTagRelation save(PictureTagRelation pictureTagRelation);

    List<PictureTagRelation> saveAll(Iterable<PictureTagRelation> pictureTagRelations);
}
