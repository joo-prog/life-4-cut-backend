package com.onebyte.life4cut.sample.repository;

import com.onebyte.life4cut.common.vo.Email;
import com.onebyte.life4cut.sample.domain.Sample;
import com.onebyte.life4cut.sample.repository.dto.SampleSearchDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.onebyte.life4cut.sample.domain.QSample.sample;

@Repository
public class SampleRepositoryImpl implements SampleRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public SampleRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Sample save(Sample sample) {
        em.persist(sample);

        return sample;
    }

    @Override
    public List<Sample> findByEmail(Email email) {
        return em.createQuery("select s from Sample s where s.email = :email", Sample.class).setParameter("email", email).getResultList();
    }

    @Override
    public List<SampleSearchDto> search(Email email) {
        return query
                .select(
                        Projections.constructor(
                                SampleSearchDto.class,
                                sample.id,
                                sample.email,
                                sample.nickname))
                .from(sample)
                .where(sample.email.eq(email))
                .fetch();
    }


}
