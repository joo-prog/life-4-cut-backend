package com.onebyte.life4cut.fixture;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import jakarta.persistence.EntityManager;

import java.util.function.BiConsumer;

public abstract class DefaultFixtureFactory<T> {

    protected EntityManager entityManager;

    protected final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();

    protected DefaultFixtureFactory() {
    }

    protected DefaultFixtureFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract T make(BiConsumer<T, ArbitraryBuilder<T>> builder);

    public abstract T save(BiConsumer<T, ArbitraryBuilder<T>> builder);

}
