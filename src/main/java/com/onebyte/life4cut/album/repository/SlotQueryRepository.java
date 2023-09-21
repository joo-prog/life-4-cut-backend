package com.onebyte.life4cut.album.repository;

import com.onebyte.life4cut.album.domain.Slot;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static com.onebyte.life4cut.album.domain.QSlot.slot;

@Repository
public class SlotQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public SlotQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Slot findById(Long id) {
        return jpaQueryFactory.selectFrom(slot)
            .where(
                slot.id.eq(id),
                slot.deletedAt.isNull()
            )
            .fetchOne();
    }

}
