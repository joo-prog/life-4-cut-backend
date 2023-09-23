package com.onebyte.life4cut.picture.repository;

import com.onebyte.life4cut.picture.domain.PictureTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureTagRepository extends JpaRepository<PictureTag, Long> {
}
