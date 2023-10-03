package com.onebyte.life4cut.album.repository;

import static com.onebyte.life4cut.album.domain.QUserAlbum.userAlbum;

import com.onebyte.life4cut.album.domain.UserAlbum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserAlbumRepositoryImpl implements UserAlbumRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public UserAlbumRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  public Optional<UserAlbum> findByUserIdAndAlbumId(Long userId, Long albumId) {
    return Optional.ofNullable(
        jpaQueryFactory
            .selectFrom(userAlbum)
            .where(
                userAlbum.userId.eq(userId),
                userAlbum.albumId.eq(albumId),
                userAlbum.deletedAt.isNull())
            .fetchOne());
  }
}
