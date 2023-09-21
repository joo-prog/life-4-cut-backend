package com.onebyte.life4cut.album.repository;

import com.onebyte.life4cut.album.domain.Album;
import com.onebyte.life4cut.fixture.AlbumFixtureFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@EnableJpaAuditing
@DataJpaTest
class AlbumQueryRepositoryTest {

    private final AlbumFixtureFactory albumFixtureFactory;

    private final AlbumQueryRepository albumQueryRepository;

    @Autowired
    public AlbumQueryRepositoryTest(EntityManager entityManager) {
        this.albumFixtureFactory = new AlbumFixtureFactory(entityManager);
        this.albumQueryRepository = new AlbumQueryRepository(new JPAQueryFactory(entityManager));
    }

    @Nested
    class FindById {

        @Test
        @DisplayName("앨범을 조회한다")
        void findById() {
            // given
            Album album = albumFixtureFactory.save(
                    (entity, builder) -> {
                        builder.setNull("deletedAt");
                    }
            );
            // when
            Album findAlbum = albumQueryRepository.findById(album.getId()).orElseThrow();

            // then
            assertThat(findAlbum.getId()).isEqualTo(album.getId());
        }

        @Test
        @DisplayName("삭제된 앨범은 조회하지 않는다")
        void deleted() {
            // given
            Album album = albumFixtureFactory.save(
                    (entity, builder) -> {
                        builder.set("deletedAt", LocalDateTime.now());
                    }
            );

            // when
            Optional<Album> findAlbum = albumQueryRepository.findById(album.getId());

            // then
            assertThat(findAlbum).isEmpty();
        }
    }

}