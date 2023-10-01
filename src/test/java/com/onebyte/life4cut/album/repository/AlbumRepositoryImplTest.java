package com.onebyte.life4cut.album.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.onebyte.life4cut.album.domain.Album;
import com.onebyte.life4cut.common.annotation.RepositoryTest;
import com.onebyte.life4cut.fixture.AlbumFixtureFactory;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest(AlbumRepositoryImpl.class)
class AlbumRepositoryImplTest {

    @Autowired
    private AlbumFixtureFactory albumFixtureFactory;

    @Autowired
    private AlbumRepositoryImpl albumQueryRepositoryImpl;


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
            Album findAlbum = albumQueryRepositoryImpl.findById(album.getId()).orElseThrow();

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
            Optional<Album> findAlbum = albumQueryRepositoryImpl.findById(album.getId());

            // then
            assertThat(findAlbum).isEmpty();
        }
    }

}