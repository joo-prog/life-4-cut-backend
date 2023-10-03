package com.onebyte.life4cut.album.repository;

import static com.onebyte.life4cut.album.domain.QAlbum.album;

import com.onebyte.life4cut.album.domain.Album;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumRepositoryImpl implements AlbumRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public AlbumRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  public Optional<Album> findById(Long id) {
    return Optional.ofNullable(
        jpaQueryFactory
            .selectFrom(album)
            .where(album.id.eq(id), album.deletedAt.isNull())
            .fetchOne());
  }
}
