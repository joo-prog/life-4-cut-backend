package com.onebyte.life4cut.album.repository;

import com.onebyte.life4cut.album.domain.UserAlbum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.onebyte.life4cut.album.domain.QUserAlbum.userAlbum;

@Repository
public class UserAlbumQueryRepositoryImpl implements UserAlbumQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;
    public UserAlbumQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Optional<UserAlbum> findByUserIdAndAlbumId(Long userId, Long albumId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(userAlbum)
                .where(
                        userAlbum.userId.eq(userId),
                        userAlbum.albumId.eq(albumId),
                        userAlbum.deletedAt.isNull()
                )
                .fetchOne());
    }
}
