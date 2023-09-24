package com.onebyte.life4cut.album.repository;

import com.onebyte.life4cut.album.domain.Slot;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.onebyte.life4cut.album.domain.QSlot.slot;

@Repository
public class SlotQueryRepositoryImpl implements SlotQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public SlotQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Optional<Slot> findById(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(slot)
                .where(
                        slot.id.eq(id),
                        slot.deletedAt.isNull()
                )
                .fetchOne());
    }

}
