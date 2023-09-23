package com.onebyte.life4cut.picture.repository;

import com.onebyte.life4cut.picture.domain.PictureTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.onebyte.life4cut.picture.domain.QPictureTag.pictureTag;

@Repository
public class PictureTagQueryRepository {

    private final JPAQueryFactory query;

    public PictureTagQueryRepository(JPAQueryFactory queryFactory) {
        this.query = queryFactory;
    }

    public List<PictureTag> findByNames(List<String> names) {
        return query
                .selectFrom(pictureTag)
                .where(
                        pictureTag.name.in(names),
                        pictureTag.deletedAt.isNull()
                ).fetch();
    }
}
