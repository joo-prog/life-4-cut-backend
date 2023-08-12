package com.onebyte.life4cut.fixture;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.onebyte.life4cut.sample.domain.Sample;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.function.BiConsumer;

public class SampleFixtureFactoryFactory extends DefaultFixtureFactory<Sample> {


    public SampleFixtureFactoryFactory() {
    }

    public SampleFixtureFactoryFactory(EntityManager entityManager) {
        super(entityManager);
    }

    public Sample make(BiConsumer<Sample, ArbitraryBuilder<Sample>> builder) {
        return getBuilder(builder).sample();
    }

    public List<Sample> makes(int count, BiConsumer<Sample, ArbitraryBuilder<Sample>> builder) {
        return getBuilder(builder).sampleList(count);
    }

    public Sample save(BiConsumer<Sample, ArbitraryBuilder<Sample>> builder) {
        Sample sample = getBuilder(builder).setNull("id").sample();
        entityManager.persist(sample);

        return sample;
    }

    public List<Sample> saves(int count, BiConsumer<Sample, ArbitraryBuilder<Sample>> builder) {
        List<Sample> samples = getBuilder(builder).setNull("id").sampleList(count);
        samples.forEach(entityManager::persist);

        return samples;
    }

    private ArbitraryBuilder<Sample> getBuilder(BiConsumer<Sample, ArbitraryBuilder<Sample>> builder) {
        return fixtureMonkey.giveMeBuilder(Sample.class).thenApply(builder);
    }
}
