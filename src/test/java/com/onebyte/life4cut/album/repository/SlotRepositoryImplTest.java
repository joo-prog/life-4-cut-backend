package com.onebyte.life4cut.album.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.onebyte.life4cut.album.domain.Slot;
import com.onebyte.life4cut.common.annotation.RepositoryTest;
import com.onebyte.life4cut.fixture.SlotFixtureFactory;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest(SlotRepositoryImpl.class)
class SlotRepositoryImplTest {

  @Autowired private SlotFixtureFactory slotFixtureFactory;

  @Autowired private SlotRepositoryImpl slotQueryRepositoryImpl;

  @Nested
  class FindById {
    @Test
    @DisplayName("슬롯을 조회한다")
    void findById() {
      // given
      Slot slot =
          slotFixtureFactory.save(
              (entity, builder) -> {
                builder.setNull("deletedAt");
              });

      // when
      Slot findSlot = slotQueryRepositoryImpl.findById(slot.getId()).orElseThrow();

      // then
      assertThat(findSlot.getId()).isEqualTo(slot.getId());
    }

    @Test
    @DisplayName("삭제된 슬롯은 조회하지 않는다")
    void deleted() {
      // given
      Slot slot =
          slotFixtureFactory.save(
              (entity, builder) -> {
                builder.set("deletedAt", LocalDateTime.now());
              });

      // when
      Optional<Slot> findSlot = slotQueryRepositoryImpl.findById(slot.getId());

      // then
      assertThat(findSlot).isEmpty();
    }
  }
}
