package com.onebyte.life4cut.picture.repository;

import com.onebyte.life4cut.picture.domain.Picture;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PictureRepositoryImpl implements PictureRepository {

    private final EntityManager em;

    @Override
    public Picture save(Picture picture) {
        em.persist(picture);
        return picture;
    }
}
