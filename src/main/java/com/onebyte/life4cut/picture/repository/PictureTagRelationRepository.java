package com.onebyte.life4cut.picture.repository;

import com.onebyte.life4cut.picture.domain.PictureTagRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureTagRelationRepository extends JpaRepository<PictureTagRelation, Long> {
}
