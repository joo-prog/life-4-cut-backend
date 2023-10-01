package com.onebyte.life4cut.pictureTag.repository;

import com.onebyte.life4cut.picture.domain.PictureTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.onebyte.life4cut.picture.domain.QPictureTag.pictureTag;

@Repository
@RequiredArgsConstructor
public class PictureTagRepositoryImpl implements PictureTagRepository {

    private final JPAQueryFactory query;
    private final EntityManager em;

    public List<PictureTag> findByNames(Long albumId, List<String> names) {
        return query
            .selectFrom(pictureTag)
            .where(
                pictureTag.albumId.eq(albumId),
                pictureTag.name.value.in(names)

            ).fetch();
    }

    @Override
    public List<PictureTag> saveAll(Iterable<PictureTag> pictureTags) {
        List<PictureTag> results = new ArrayList<>();

        for (PictureTag pictureTag : pictureTags) {
            results.add(save(pictureTag));
        }

        return results;
    }

    @Override
    public PictureTag save(PictureTag pictureTag) {
        em.persist(pictureTag);
        return pictureTag;
    }
}
