package com.onebyte.life4cut.picture.repository;

import com.onebyte.life4cut.picture.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository {

    Picture save(Picture picture);
}
