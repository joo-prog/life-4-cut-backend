package com.onebyte.life4cut.fixture;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.onebyte.life4cut.sample.domain.Sample;
import jakarta.persistence.EntityManager;

import java.util.function.BiConsumer;

public class SampleFixtureFactoryFactory extends DefaultFixtureFactory<Sample> {

    private final EntityManager em;

    public SampleFixtureFactoryFactory(EntityManager em) {
        this.em = em;
    }

    public Sample make(BiConsumer<Sample, ArbitraryBuilder<Sample>> builder) {
        return getBuilder(builder).build().sample();
    }

    public Sample save(BiConsumer<Sample, ArbitraryBuilder<Sample>> builder) {
        Sample sample = getBuilder(builder).setNull("id").build().sample();
        em.persist(sample);

        return sample;
    }

    private ArbitraryBuilder<Sample> getBuilder(BiConsumer<Sample, ArbitraryBuilder<Sample>> builder) {
        return fixtureMonkey.giveMeBuilder(Sample.class).thenApply(builder);
    }
}
