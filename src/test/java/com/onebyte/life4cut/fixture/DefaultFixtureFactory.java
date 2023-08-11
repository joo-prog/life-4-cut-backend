package com.onebyte.life4cut.fixture;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;

import java.util.function.BiConsumer;

public abstract class DefaultFixtureFactory<T> {

    protected final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();

    public abstract T make(BiConsumer<T, ArbitraryBuilder<T>> builder);

    public abstract T save(BiConsumer<T, ArbitraryBuilder<T>> builder);

}
