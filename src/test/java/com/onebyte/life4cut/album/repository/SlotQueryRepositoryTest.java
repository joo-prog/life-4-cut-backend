package com.onebyte.life4cut.album.repository;

import com.onebyte.life4cut.album.domain.Slot;
import com.onebyte.life4cut.fixture.AlbumFixtureFactory;
import com.onebyte.life4cut.fixture.SlotFixtureFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@EnableJpaAuditing
class SlotQueryRepositoryTest {

    private final SlotFixtureFactory slotFixtureFactory;

    private final SlotQueryRepository slotQueryRepository;

    @Autowired
    public SlotQueryRepositoryTest(EntityManager entityManager) {
        this.slotFixtureFactory = new SlotFixtureFactory(entityManager);
        this.slotQueryRepository = new SlotQueryRepository(new JPAQueryFactory(entityManager));
    }


    @Nested
    class FindById {
        @Test
        @DisplayName("슬롯을 조회한다")
        void findById() {
            // given
            Slot slot = slotFixtureFactory.save(
                    (entity, builder) -> {
                        builder.setNull("deletedAt");
                    }
            );

            // when
            Slot findSlot = slotQueryRepository.findById(slot.getId());

            // then
            assertThat(findSlot.getId()).isEqualTo(slot.getId());
        }

        @Test
        @DisplayName("삭제된 슬롯은 조회하지 않는다")
        void deleted() {
            // given
            Slot slot = slotFixtureFactory.save(
                    (entity, builder) -> {
                        builder.set("deletedAt", LocalDateTime.now());
                    }
            );

            // when
            Slot findSlot = slotQueryRepository.findById(slot.getId());

            // then
            assertThat(findSlot).isNull();
        }
    }


}