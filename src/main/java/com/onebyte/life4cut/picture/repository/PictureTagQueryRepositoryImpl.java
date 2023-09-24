package com.onebyte.life4cut.picture.repository;

import com.onebyte.life4cut.picture.domain.PictureTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.onebyte.life4cut.picture.domain.QPictureTag.pictureTag;

@Repository
public class PictureTagQueryRepositoryImpl implements PictureTagQueryRepository{

    private final JPAQueryFactory query;

    public PictureTagQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.query = queryFactory;
    }

    public List<PictureTag> findByNames(Long albumId, List<String> names) {
        return query
                .selectFrom(pictureTag)
                .where(
                        pictureTag.albumId.eq(albumId),
                        pictureTag.name.value.in(names)

                ).fetch();
    }
}
