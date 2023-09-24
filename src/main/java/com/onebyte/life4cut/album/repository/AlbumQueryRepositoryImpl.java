package com.onebyte.life4cut.album.repository;

import com.onebyte.life4cut.album.domain.Album;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.onebyte.life4cut.album.domain.QAlbum.album;

@Repository
public class AlbumQueryRepositoryImpl implements AlbumQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public AlbumQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Optional<Album> findById(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(album)
                .where(
                        album.id.eq(id),
                        album.deletedAt.isNull()
                )
                .fetchOne());
    }

}
