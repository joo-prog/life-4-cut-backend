package com.onebyte.life4cut.album.repository;

import static com.onebyte.life4cut.album.domain.QSlot.slot;

import com.onebyte.life4cut.album.domain.Slot;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class SlotRepositoryImpl implements SlotRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public SlotRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  public Optional<Slot> findById(Long id) {
    return Optional.ofNullable(
        jpaQueryFactory.selectFrom(slot).where(slot.id.eq(id), slot.deletedAt.isNull()).fetchOne());
  }
}
