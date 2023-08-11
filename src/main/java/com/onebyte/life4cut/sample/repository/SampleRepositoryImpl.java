package com.onebyte.life4cut.sample.repository;

import com.onebyte.life4cut.sample.domain.Sample;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class SampleRepositoryImpl implements SampleRepository {

    private final EntityManager em;

    public SampleRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Sample save(Sample sample) {
        em.persist(sample);

        return sample;
    }
}
